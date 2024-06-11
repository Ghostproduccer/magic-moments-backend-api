package com.magicmoments.backendapi.service.dto;

import com.magicmoments.backendapi.model.Roles;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UsersDto {
    private int id;
    private String email;
    private String name;
    private String surname;
    private String username;
    private String password;

    private Set<Roles> roles = new HashSet<>();

    // form inputs
    private String confirmPassword;
    private String tokenJWT;
}
