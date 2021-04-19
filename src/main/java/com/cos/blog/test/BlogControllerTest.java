package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Spring com.cos.blog　package以下をスキャンしてすべてのファイルをメモリでnewすることではない
//特定 Annotationが付くクラスファイルをnewしてIoC Spring containerで管理を行う。
@RestController 
public class BlogControllerTest {
	//http://localhost:8080/test/hello
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello spring boot</h1>"; 
	}
}
