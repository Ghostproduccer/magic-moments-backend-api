package com.magicmoments.backendapi.controllers.validators;

import com.magicmoments.backendapi.controllers.forms.LoginForm;
import com.magicmoments.backendapi.service.srv.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LoginValidator implements Validator {
    @Autowired
    UsersService usersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginForm login = (LoginForm) target;

    }
}
