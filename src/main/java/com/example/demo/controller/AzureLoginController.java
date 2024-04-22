package com.example.demo.controller;



import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AzureLoginController {
	
	

	 @GetMapping("/loginpage")
	    public String getLoginPage() {
	        return "login";
	    }
	  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	 @GetMapping("/greet")
	 
	  public String greet() {
		  return "greet";
	  }
	 
	 @GetMapping("/useroradmin")
	  @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	  public String userAdminAccess() {
	    return "<div>User and admin Content.</div>";
	  }

	  
	  @GetMapping("/admin")
	  @PreAuthorize("hasRole('ADMIN')")
	  public String adminAccess() {
		  
		  
	    return "<div>Admin Board.</div>";
	  }
}

