package com.ak.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ak.entity.Book;
import com.ak.entity.Rent;
import com.ak.entity.User;
import com.ak.entity.User.Role;
import com.ak.service.BookService;
import com.ak.service.RentService;
import com.ak.service.UserService;

@Controller
public class RentController {

	@Autowired
	private RentService rentService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/rents",method = RequestMethod.GET)
	public String getRentPage(Model model, Principal principal){
		String email = principal.getName();
		User user = userService.findByEmail(email);
		
		List<Rent> rents;
		if(user.getRole()==Role.USER){
			rents = rentService.findByUserOrderByCreatedDateDesc(user);
		}else{
			rents = rentService.findAll();
		}
		model.addAttribute("rentsList",rents);
		return "rents";
	}
	@RequestMapping(value="/rent/book/{bookId}",method = RequestMethod.POST)
	public String createRent(@PathVariable Long bookId, Principal principal){
		String email = principal.getName();
		User user = userService.findByEmail(email);
		
		Book book = bookService.findOne(bookId);
		rentService.createRent(user, book);
		
		return "redirect:/rents";
	}
}
