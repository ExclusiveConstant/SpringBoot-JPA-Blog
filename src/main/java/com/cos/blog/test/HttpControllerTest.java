package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//レスポンスがHtmlファイルの時
//@Controller

//レスポンスがDataである時
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest : ";
	
	//localhost:8080/http/lombok
	//application.ymlの設定によりport番号とContext-rootを設定
    //localhost:8000/blog/http/lombok
	@GetMapping("/http/lombok")
	public String lombokTest() {
		
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		System.out.println(TAG+"Getter : " + m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG+"Getter : " + m.getUsername())	;
		return "lombok test 완료";
	}
	
	
	//http://localhost:8080/http/get
	@GetMapping("/http/get")
	//public String getTest(@RequestParam int id, @RequestParam String username) {
	//return "GET Request" + id + username;
	//Get Request  only QueryString data transfer 
	public String getTest(Member m) {	
		return "GET Request" + m.getId() + m.getUsername() + m.getPassword()+m.getEmail();
	}
	
	//http://localhost:8080/http/post
	@PostMapping("/http/post")
	//public String postTest(Member m) {
		//return "POST Request" + m.getId() + m.getUsername() + m.getPassword()+m.getEmail();
	public String postTest(@RequestBody Member m) { //MessageConvert(Spring Boot)
			return "post Request : " + m.getId() +","+ m.getUsername() +","+ m.getPassword() +","+  m.getEmail();
	}
	
	//http://localhost:8080/http/put
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		
		return "PUT Request : " + m.getId() +","+ m.getUsername() +","+ m.getPassword() +","+  m.getEmail();
	}
	
	//http://localhost:8080/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		
		return "DELETE Request";
	}
	
	
}
