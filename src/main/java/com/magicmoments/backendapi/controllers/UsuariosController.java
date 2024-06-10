package com.magicmoments.backendapi.controllers;

import com.magicmoments.backendapi.controllers.forms.RegisterForm;
import com.magicmoments.backendapi.controllers.helpers.ControllersHelper;
import com.magicmoments.backendapi.controllers.validators.UsuarioValidator;
import com.magicmoments.backendapi.service.dto.UsersDto;
import com.magicmoments.backendapi.service.srv.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuariosController {
    @Autowired
    UsersService usersService;

    @Autowired
    UsuarioValidator usuarioValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(usuarioValidator);
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterForm registerForm, BindingResult result) {
        UsersDto usersDto = new UsersDto();

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        ControllersHelper.formToDto(registerForm, usersDto);

        UsersDto savedUser = usersService.saveUser(usersDto);

        return ResponseEntity.ok(savedUser);
    }
}
