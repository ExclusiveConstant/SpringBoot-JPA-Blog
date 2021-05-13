package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email
		
		System.out.println("UserApiController : save 呼び出し");
		//実際のDBにinsertをして
		user.setRole(RoleType.USER);
		userService.userRegister(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //Java Objectを　Jsonで変換して　return (jackson Library)
		
	}
	
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user){
		System.out.println("UserApiController : login呼び出し");
		User principal = userService.userLogin(user); //principal(接近主体)
		
		if(principal != null) { 
			session.setAttribute("principal",principal);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	
	
	
	
	
}
