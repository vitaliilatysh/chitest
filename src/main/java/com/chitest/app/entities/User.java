package com.chitest.app.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String login;

    @Column
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Contact> contacts;

}
