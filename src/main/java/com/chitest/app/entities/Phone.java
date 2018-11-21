package com.chitest.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private int id;

    @Column
    @NotNull
    @Pattern(regexp = "\\+\\d{12}", message = "Should be in format +380XXXXXXXXX where X - any digit [0-9]")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    @JsonIgnore
    private Contact contact;

    public Phone(String phone) {
        this.phone = phone;
    }
}
