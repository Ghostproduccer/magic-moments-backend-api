package com.magicmoments.backendapi.controllers;

import com.magicmoments.backendapi.service.srv.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/send-html-email")
    public String sendHtmlEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String name, @RequestParam String body, @RequestParam String footer) {
        try {
            emailService.sendHtmlEmail(to, subject, name, body, footer);
            return "HTML Email enviado correctamente";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending HTML email";
        }
    }

}
