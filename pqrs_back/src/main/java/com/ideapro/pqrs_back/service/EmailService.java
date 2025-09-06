package com.ideapro.pqrs_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private final JavaMailSender emailSender;

    public EmailService(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void enviarConfirmacionPQRS(String emailDestinatario, String numeroRadicado) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("testpqrscontest@gmail.com");
        message.setTo(emailDestinatario);
        message.setSubject("Confirmación de radicado PQRS");
        message.setText("Su PQRS ha sido radicada exitosamente.\n\n" +
                       "Número de radicado: " + numeroRadicado + "\n\n" +
                       "Por favor, guarde este número para hacer seguimiento a su solicitud.\n\n" +
                       "Gracias por usar nuestro sistema.");

        emailSender.send(message);
    }
}
