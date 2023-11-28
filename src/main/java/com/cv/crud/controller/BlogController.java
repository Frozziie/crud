package com.cv.crud.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	@GetMapping("/blog")
	public String sayHello() {
		return "Hello!";
	}
}
