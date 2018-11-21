package com.chitest.app.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
@Entity
@Table(name = "emails")

public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private int id;

    @Column
    @NotNull
    @javax.validation.constraints.Email(message = "Should have format name@domain.com")
    private String email;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    @JsonIgnore
    private Contact contact;

    public Email(String email) {
        this.email = email;
    }
}
