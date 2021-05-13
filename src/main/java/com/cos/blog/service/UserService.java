package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//Spring ComponentScanを通してBeanに登録する.IoCをする。
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void userRegister(User user) {
			userRepository.save(user);
	}
	
	@Transactional(readOnly = true) // Selectする時にトランザクションをStartして、サービスを終了する時にトランザクションを終了(整合性)
	public User userLogin(User user) {
			return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
	}
}
