package com.backend2.backend2_pensionat_with_maven.event.listener;


import com.backend2.backend2_pensionat_with_maven.event.RegistrationCompleteEvent;
import com.backend2.backend2_pensionat_with_maven.services.UserService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    private final JavaMailSender mailSender;
    private String theUser;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //theUser = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        //userService.saveUserVerificationToken(theUser, verificationToken);
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        log.info("Click the link to complete your registration : {}", url);

    }

    public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException, jakarta.mail.MessagingException {
        theUser ="o.ekstrom@hotmail.se";
        String subject = "Password Reset Request Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, " + ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(theUser);
        messageHelper.setFrom("o.ekstrom@hotmail.se", senderName);
        messageHelper.setTo(theUser/*.getUsername()*/);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
