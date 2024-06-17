package com.magicmoments.backendapi.controllers;

import com.magicmoments.backendapi.controllers.forms.RegisterForm;
import com.magicmoments.backendapi.controllers.helpers.ControllersHelper;
import com.magicmoments.backendapi.controllers.validators.UsuarioValidator;
import com.magicmoments.backendapi.service.dto.UsersDto;
import com.magicmoments.backendapi.service.srv.EmailService;
import com.magicmoments.backendapi.service.srv.UsersService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UsuariosController {
    @Autowired
    UsersService usersService;

    @Autowired
    UsuarioValidator usuarioValidator;

    @Autowired
    EmailService emailService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(usuarioValidator);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterForm registerForm, BindingResult result) {
        UsersDto usersDto = new UsersDto();

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        registerForm.setPassword(registerForm.getPassword());

        ControllersHelper.formToDto(registerForm, usersDto);
        try {
            emailService.sendHtmlEmail(registerForm.getEmail(), "Welcome to magic moments!", registerForm.getName(),
                    "Welcome to Magic Moments, where every purchase creates unforgettable memories!\n" +
                            "\n" +
                            "At Magic Moments, we are dedicated to bringing you the finest selection of products that inspire joy and wonder. Whether you're looking for stylish fashion essentials, unique home decor, or thoughtful gifts for loved ones, we have something special just for you.\n" +
                            "\n" +
                            "Explore our curated collections and discover:\n" +
                            "\n" +
                            "Fashion Forward: Stay ahead with our latest trends in clothing, footwear, and accessories.\n" +
                            "Home Sweet Home: Transform your living space with our exquisite range of home decor and essentials.\n" +
                            "Gifts to Cherish: Show you care with our carefully selected gifts that make every moment magical.\n" +
                            "As a new member of our community, we want to extend our gratitude by offering you a special discount of 10% on your first purchase. Simply use code MAGIC10 at checkout to redeem your offer.\n" +
                            "\n" +
                            "Stay connected with us for exciting updates, exclusive promotions, and more delightful surprises that await you at Magic Moments.\n" +
                            "\n" +
                            "Thank you for choosing Magic Moments. Let's create magical memories together!",
                    "Magic Moments | Your Online Shopping Destination  \n" +
                            "123 Magic Street, Wonderland, USA | Phone: +1-123-456-7890 | Email: info@magicmoments.com\n" +
                            "Connect with us: Facebook | Instagram | Twitter\n" +
                            "Unsubscribe | Privacy Policy | Terms & Conditions\n");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        UsersDto savedUser = usersService.registerNewUser(usersDto);

        return ResponseEntity.ok().body(savedUser);
    }
}
