package com.featuriz.sbm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.featuriz.sbm.model.User;
import com.featuriz.sbm.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;

	// Go to Registration Page
	@GetMapping("/register")
	public String register() {
		return "user/register";
	}

	// Read Form data to save into DB
	@PostMapping("/save")
	public String saveUser(@ModelAttribute User user, Model model) {
		Long id = userService.saveUser(user);
		String message = "User '" + id + "' saved successfully !";
		model.addAttribute("msg", message);
		return "user/register";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("msg", "Your username and password are invalid.");

		if (logout != null)
			model.addAttribute("msg", "You have been logged out successfully.");

		return "user/login";
	}
}
