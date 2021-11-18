package com.featuriz.sbm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sb")
public class SecController {
	
	@GetMapping("/home")
	public String getHomePage() {
		return "sb/homePage";
	}
	
	@GetMapping("/welcome")
	public String getWelcomePage() {
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
