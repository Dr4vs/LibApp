package com.ak.schedulers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ak.entity.User;
import com.ak.service.EmailService;
import com.ak.service.UserService;

@Service
public class AdvService {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private EmailService emailService;
	
	
	
	@Scheduled(fixedDelay=10000)	
	public void sendMailToAll(){
		List<User> users = userService.findAll();
		users.forEach(user->emailService.sendEmail("liberarydamiantest@gmail.com", user.getEmail(), "Spam", "Wypożycz ksiązke"));
	}
}
