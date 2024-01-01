package io.hyao.blog.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String recipientEmail, String userName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(recipientEmail);
            helper.setFrom(new InternetAddress("hvyyao@gmail.com", "Harvey"));
            helper.setSubject("LoreLinks.com | SendEmail Demo");
            // Create a Thymeleaf context and add new variables
            Context thymeleafContext = new Context();
            // userName will be passed to HTML DOM
            thymeleafContext.setVariable("userName", userName);
            String htmlContent = templateEngine.process("emailTemplate", thymeleafContext);
            helper.setText(htmlContent, true);
            FileSystemResource resource =
                    new FileSystemResource(new File("src/main/resources/static/images/bar_s.png"));
            // Link the resource to CID in HTML
            helper.addInline("logoImage", resource);
            javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
