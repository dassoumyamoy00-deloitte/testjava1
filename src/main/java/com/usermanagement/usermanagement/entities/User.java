package com.usermanagement.usermanagement.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Users")
@Entity(name = "Users")
@Getter
@Setter
public class User {

    @Id
    private String email;

    private String firstName;
    private String lastName;
    private String password;
}
