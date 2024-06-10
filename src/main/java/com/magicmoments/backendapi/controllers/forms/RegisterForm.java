package com.magicmoments.backendapi.controllers.forms;

import lombok.Data;

@Data
public class RegisterForm extends BaseForm {
    private String email;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String confirmPassword;
}