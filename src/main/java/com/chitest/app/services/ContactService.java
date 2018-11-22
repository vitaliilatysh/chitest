package com.chitest.app.services;


import com.chitest.app.entities.Contact;
import com.chitest.app.entities.Email;
import com.chitest.app.entities.Phone;
import com.chitest.app.entities.User;
import com.chitest.app.exceptions.*;
import com.chitest.app.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.*;

@Component
public class ContactService {

    private final ContactRepository contactRepository;
    private final EmailService emailService;
    private final PhoneService phoneService;

    @Autowired
    public ContactService(ContactRepository contactRepository,
                          EmailService emailService,
                          PhoneService phoneService) {

        this.contactRepository = contactRepository;
        this.emailService = emailService;
        this.phoneService = phoneService;
    }

    @Transactional
    public void save(@RequestBody @Valid Contact contact) {

        checkExistEmail(contact);

        checkDuplicateEmail(contact);

        checkExistPhone(contact);

        checkDuplicatePhone(contact);

        Contact savedContact = contactRepository.save(contact);

        saveEmails(contact, savedContact);

        savePhones(contact, savedContact);

        contactRepository.save(savedContact);

    }

    private void savePhones(@RequestBody @Valid Contact contact, Contact savedContact) {
        for (Phone phone : contact.getPhones()) {
            phone.setContact(savedContact);
            if (phoneService.save(phone) == null) {
                throw new DuplicatePhoneException("Phone:" + phone.getPhone() + " already in the list");
            }
        }
    }

    private void saveEmails(@RequestBody @Valid Contact contact, Contact savedContact) {
        for (Email email : contact.getEmails()) {
            email.setContact(savedContact);
            if (emailService.save(email) == null) {
                throw new DuplicateEmailException("Email:" + email.getEmail() + " already in the list");
            }
        }
    }

    private void checkExistPhone(@RequestBody @Valid Contact contact) {
        for (Phone phone : contact.getPhones()) {
            Phone phoneInDb = phoneService.findByPhone(phone.getPhone());
            if (phoneInDb != null) {
                throw new PhoneAlreadyExistException(phone.getPhone());
            }
        }
    }

    private void checkExistEmail(@RequestBody @Valid Contact contact) {
        for (Email email : contact.getEmails()) {
            Email emailInDb = emailService.findByEmail(email.getEmail());
            if (emailInDb != null) {
                throw new EmailAlreadyExistException(email.getEmail());
            }
        }
    }

    private void checkDuplicatePhone(@RequestBody @Valid Contact contact) {
        Set<Phone> duplicatedPhones = phoneService.findDuplicatesPhones(contact.getPhones());
        if (duplicatedPhones.size() != 0) {
            throw new DuplicatePhoneException("Duplicated phone/s in the list");
        }
    }

    private void checkDuplicateEmail(@RequestBody @Valid Contact contact) {
        Set<Email> duplicatedEmails = emailService.findDuplicatesEmails(contact.getEmails());
        if (duplicatedEmails.size() != 0) {
            throw new DuplicateEmailException("Duplicated email/s in the list");
        }
    }

    public List<Contact> findAllByUser(User user) {
        return contactRepository.findAllByUser(user);
    }

    public Optional<Contact> findById(int contactId) {
        return contactRepository.findById(contactId);
    }

    public void deleteById(Integer contactId) {
        contactRepository.deleteById(contactId);
    }

    public Contact findByName(String name) {
        return contactRepository.findByName(name);
    }

    @Transactional
    public void update(Contact contact, Contact newContactData) {
        Contact contactInDb = contactRepository.findByName(newContactData.getName());

        if (contactInDb != null && !contact.getName().equalsIgnoreCase(newContactData.getName())) {
            throw new ContactAlreadyExistException(newContactData.getName());
        } else {
            contact.setName(newContactData.getName());

            checkEmailsBeforeUpdate(contact, newContactData);

            checkPhonesBeforeUpdate(contact, newContactData);

            List<Email> tempEmails = new ArrayList<>(contact.getEmails());
            Set<Email> newEmails = getNewEmailsFromRequest(contact, newContactData, tempEmails);

            List<Phone> tempPhones = new ArrayList<>(contact.getPhones());
            Set<Phone> newPhones = getNewPhonesFromRequest(contact, newContactData, tempPhones);

            deleteExistedContactEmails(tempEmails);

            deleteExistedContactPhones(tempPhones);

            contact.setEmails(newEmails);
            contact.setPhones(newPhones);

            contactRepository.save(contact);
        }
    }

    private void deleteExistedContactPhones(List<Phone> tempPhones) {
        for(Phone phone: tempPhones){
            phoneService.delete(phone);
        }
    }

    private void deleteExistedContactEmails(List<Email> tempEmails) {
        for(Email email: tempEmails){
            emailService.delete(email);
        }
    }

    private Set<Phone> getNewPhonesFromRequest(Contact contact, Contact newContactData, List<Phone> tempPhones) {
        Set<Phone> newPhones = new HashSet<>();
        tempPhones.removeAll(newContactData.getPhones());
        for (Phone phone : newContactData.getPhones()) {
            if(!contact.getPhones().contains(phone)) {
                phone.setContact(contact);
                Phone savedPhone = phoneService.save(phone);
                if (savedPhone == null) {
                    throw new DuplicateEmailException("Phone:" + phone.getPhone() + " already in the list");
                } else{
                    newPhones.add(savedPhone);
                }
            }
        }
        return newPhones;
    }

    private Set<Email> getNewEmailsFromRequest(Contact contact, Contact newContactData, List<Email> tempEmails) {
        Set<Email> newEmails = new HashSet<>();
        tempEmails.removeAll(newContactData.getEmails());
        for (Email email : newContactData.getEmails()) {
            if(!contact.getEmails().contains(email)) {
                email.setContact(contact);
                Email savedEmail = emailService.save(email);
                if (savedEmail == null) {
                    throw new DuplicateEmailException("Email:" + email.getEmail() + " already in the list");
                } else{
                    newEmails.add(savedEmail);
                }
            }
        }
        return newEmails;
    }

    private void checkPhonesBeforeUpdate(Contact contact, Contact newContactData) {
        for (Phone phone : newContactData.getPhones()) {
            Phone phoneInDb = phoneService.findByPhone(phone.getPhone());
            if (phoneInDb != null && !contact.getPhones().contains(phoneInDb)) {
                throw new PhoneAlreadyExistException(phone.getPhone());
            }
        }
    }

    private void checkEmailsBeforeUpdate(Contact contact, Contact newContactData) {
        for (Email email : newContactData.getEmails()) {
            Email emailInDb = emailService.findByEmail(email.getEmail());
            if (emailInDb != null && !contact.getEmails().contains(emailInDb)) {
                throw new EmailAlreadyExistException(email.getEmail());
            }
        }
    }
}
