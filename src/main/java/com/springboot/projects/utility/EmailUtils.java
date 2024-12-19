package com.springboot.projects.utility;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {
	private final JavaMailSender mailSender;

	public EmailUtils(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmail(String recipient, String subject, String emailBody) throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setTo(recipient); // Pass only the email address
		helper.setSubject(subject);
		helper.setText(emailBody, true); // Pass HTML content to setText()

		mailSender.send(mimeMessage);
	}
}
