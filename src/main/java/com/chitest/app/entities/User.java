package com.chitest.app.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 15, message = "Should have at least 5 characters, maximum - 15")
    private String login;

    @Column
    @NonNull
    @NotEmpty
    @Size(min = 5, max = 60, message = "Should have at least 5 characters, maximum - 60")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Contact> contacts;

}
