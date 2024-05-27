package com.softsync.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tiqkdahs@naver.com"); // 보내는 사람의 이메일 주소
        message.setTo(to); // 받는 사람의 이메일 주소
        message.setSubject(subject); // 메일 제목
        message.setText(text); // 메일 내용

        mailSender.send(message);
    }
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tiqkdahs@naver.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        
        mailSender.send(message);
    }
}