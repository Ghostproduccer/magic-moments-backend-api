package com.magicmoments.backendapi.controllers.validators;

import com.magicmoments.backendapi.controllers.forms.RegisterForm;
import com.magicmoments.backendapi.service.srv.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UsuarioValidator implements Validator {
    @Autowired
    UsersService usersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterForm usuario = (RegisterForm) target;

        // required inputs
        ValidationUtils.rejectIfEmpty(errors, "email", "form.required");
        ValidationUtils.rejectIfEmpty(errors, "name", "form.required");
        ValidationUtils.rejectIfEmpty(errors, "surname", "form.required");
        ValidationUtils.rejectIfEmpty(errors, "username", "form.required");
        ValidationUtils.rejectIfEmpty(errors, "password", "form.required");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "form.required");

        if (usuario.getUsername().length() < 5) {
            errors.rejectValue("password", "test");
        }

    }
}
