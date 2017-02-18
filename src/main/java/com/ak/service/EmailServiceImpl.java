package com.ak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	
	@Autowired
	private JavaMailSender jms;
	
	@Override
	public void sendEmail(String from, String to, String temat, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(from);
		message.setSubject(temat);
		message.setText(text);
		
		jms.send(message);
	}
}
