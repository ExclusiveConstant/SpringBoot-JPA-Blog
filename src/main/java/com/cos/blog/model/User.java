package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity//User 클래스가 MySQL Table Create
//@DynamicInsert // insert時にnullのfieldは除外してくれることでColumnDefaultが動作するようにする
public class User {

	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //prjで連結されたDBの戦略を従う
	private int id; // Sequence, auto_increment
	
	@Column(nullable = false, length = 30, unique = true)
	private String username; // id
	
	@Column(nullable = false, length = 100) //123456 => ハッシュで暗号化するからlengthを100
	private String password; 
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("'user'") // "+'user'+" // defaultの値入れないようにしてSetで登録するように変更
	//private String role; //Enumの方が良いが、一応Stringで　//admin, user, manager
	//DBではRoleTypeというのはない
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enumに変更、 //ADMIN , USER
	
	
	@CreationTimestamp //時間を自動入力
	private Timestamp createDate;
	
}
