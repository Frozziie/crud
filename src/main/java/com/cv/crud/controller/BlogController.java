package com.cv.crud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

	@GetMapping
	public String sayHello() {
		return "Hello!";
	}
}
