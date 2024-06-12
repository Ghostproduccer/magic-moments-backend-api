package com.magicmoments.backendapi.controllers.forms;

import lombok.Data;

@Data
public class LoginForm extends BaseForm {
    private String username;
    private String password;
}
