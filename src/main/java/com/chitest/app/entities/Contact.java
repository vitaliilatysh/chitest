package com.chitest.app.entities;

import com.chitest.app.serialization.EmailSerializer;
import com.chitest.app.serialization.PhoneSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Size(min = 5, max = 50, message = "Should have at least 5 characters")
    @Column
    private String name;

    @JsonSerialize(using = EmailSerializer.class)
    @NotNull
    @NotEmpty(message = "Should have at least 1 email")
    @Valid
    @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER)
    private Set<Email> emails;

    @JsonSerialize(using = PhoneSerializer.class)
    @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER)
    @NotNull
    @NotEmpty(message = "Should have at least 1 phone")
    @Valid
    private Set<Phone> phones;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
