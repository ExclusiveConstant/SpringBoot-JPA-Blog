package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//Userテーブルのpkはintegerである
//DAO
//自動でBeanとして登録できる
//@Repository //省略可能
public interface UserRepository extends JpaRepository<User, Integer>{

	//JPA Naming戦略
	//SELECT * FROM user WHERE username = ? AND password = ?;
	User findByUsernameAndPassword(String username, String password);
	
	//↓は複雑なSQLをする時に
	//@Query(value="SELECT * FROM user WHERE usernmae = ?  AND password = ?2", nativeQuery = true)
	//User login(String username, String password);
}
