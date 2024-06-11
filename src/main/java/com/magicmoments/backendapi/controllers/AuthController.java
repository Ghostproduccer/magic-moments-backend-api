package com.magicmoments.backendapi.controllers;

import com.magicmoments.backendapi.controllers.forms.RegisterForm;
import com.magicmoments.backendapi.controllers.helpers.ControllersHelper;
import com.magicmoments.backendapi.controllers.validators.UsuarioValidator;
import com.magicmoments.backendapi.security.jwt.JwtUtils;
import com.magicmoments.backendapi.security.services.UserDetailsImpl;
import com.magicmoments.backendapi.service.dto.UsersDto;
import com.magicmoments.backendapi.service.srv.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersService usersService;

    @Autowired
    UsuarioValidator usuarioValidator;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(usuarioValidator);
    }

    /*@PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }*/


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterForm registerForm, BindingResult result) {
        UsersDto usersDto = new UsersDto();

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        ControllersHelper.formToDto(registerForm, usersDto);

        UsersDto savedUser = usersService.registerNewUser(usersDto);


        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(registerForm.getUsername(), registerForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(savedUser);
    }
}
