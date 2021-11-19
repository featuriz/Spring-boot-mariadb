package com.featuriz.sbm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.featuriz.sbm.model.User;

@Controller
@RequestMapping("/sb")
public class SecController {

	@GetMapping("/home")
	public String getHomePage(Authentication authentication, Model model) {
//		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		User u = (User) authentication.getPrincipal();
		model.addAttribute("cu", u);
		return "sb/homePage";
	}

	@GetMapping("/welcome")
	public String getWelcomePage(Authentication authentication, Model model) {

		User u = (User) authentication.getPrincipal();
		model.addAttribute("cu", u);
		return "sb/welcomePage";
	}

	@GetMapping("/admin")
	public String getAdminPage() {
		return "sb/adminPage";
	}

	@GetMapping("/emp")
	public String getEmployeePage() {
		return "sb/empPage";
	}

	@GetMapping("/mgr")
	public String getManagerPage() {
		return "sb/mgrPage";
	}

	@GetMapping("/common")
	public String getCommonPage() {
		return "sb/commonPage";
	}

	@GetMapping("/accessDenied")
	public String getAccessDeniedPage() {
		return "sb/accessDeniedPage";
	}
}
