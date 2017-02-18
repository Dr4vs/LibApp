package com.ak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ak.entity.User;
import com.ak.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/users")
	public String getUserList(Model model){
		List<User> users = userService.findAll();
		model.addAttribute("usersList",users);
		return "users";
	}
	
	@RequestMapping(value="/create-user",method = RequestMethod.GET)
	public String createUser(){
		return "user-create";
	}
	
	@RequestMapping(value="/create-user",method = RequestMethod.POST)
	public String saveUser(
			@RequestParam(required=false)Long id,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String email,
			@RequestParam String password)
	{
		User user = new User(firstName,lastName,email,password);
		userService.save(user);
		return "redirect:/users";
	}
	
	@RequestMapping(value="/user/delete/{id}")
	public String deleteUser(@PathVariable Long id){
		userService.delete(id);
		return "users";
	}
	
	@RequestMapping(value="/user/edit/{id}")
	public String editUser(@PathVariable Long id,Model model){
		User user = userService.findOne(id);
		model.addAttribute(user);
		return "user-create";
	}
}
