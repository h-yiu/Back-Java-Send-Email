package io.hyao.blog.controller;
import io.hyao.blog.entity.Users;
import io.hyao.blog.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    public final EmailService emailService;

    @Autowired
    public MyController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email")
    public String sendEmail(@RequestBody Users users) {
        emailService.sendEmail(users.getEmail(), users.getUserName());
        return "email sent successfully!";
    }
}
