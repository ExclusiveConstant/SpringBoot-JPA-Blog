package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	//localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//基本path：src/main/resources/static
		//return  src/main/resources/statichome.html
		//return "home.html";
		//return  src/main/resources/static/home.html
		return "/home.html";
		
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		
		//prefix : /WEB-INF/vie
		//suffix : .jsp
		//return "/test.jsp";
		return "test";
		
	}
	
}
