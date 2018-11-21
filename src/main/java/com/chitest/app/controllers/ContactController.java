package com.chitest.app.controllers;

import com.chitest.app.entities.Contact;
import com.chitest.app.entities.User;
import com.chitest.app.exceptions.ContactAlreadyExistException;
import com.chitest.app.exceptions.ContactNotFoundException;
import com.chitest.app.services.ContactService;
import com.chitest.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    @Autowired
    public ContactController( ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @GetMapping("/contacts")
    public @ResponseBody
    List<Contact> getAllContacts(Principal principal) {
        User user = userService.findByLogin(principal.getName());
        return contactService.findAllByUser(user);
    }

    @DeleteMapping("/contacts/{id}")
    public @ResponseBody
    void deleteContactById(@PathVariable("id") Integer contactId) {
        Optional<Contact> contact = contactService.findById(contactId);

        if (!contact.isPresent()) {
            throw new ContactNotFoundException(contactId);
        } else {
            contactService.deleteById(contactId);
        }
    }

    @PostMapping(path = "/contacts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Contact contact, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        Contact contactInDb = contactService.findByName(contact.getName());

        if (contactInDb != null) {
            throw new ContactAlreadyExistException(contact.getName());
        } else {
            contact.setUser(user);
            contactService.save(contact);
        }
    }

    @PutMapping(path = "/contacts/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("id") Integer contactId, @Valid @RequestBody Contact contactNewData) {
        Optional<Contact> contactInDb = contactService.findById(contactId);

        if (!contactInDb.isPresent()) {
            throw new ContactNotFoundException(contactId);
        } else {
            contactService.update(contactInDb.get(), contactNewData);
        }

    }

}
