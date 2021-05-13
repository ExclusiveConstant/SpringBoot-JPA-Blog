package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //大容量データ
	private String content; //summer note library <html>tagと混ぜてデザインされる
	
	@ColumnDefault("0")
	private int count; //既読数
	
	//ManyToOneの基本戦略は　fetch = FetchType.EAGER (必ず取ってくる)
	@ManyToOne(fetch = FetchType.EAGER) //many => board   user => one
	@JoinColumn(name="userId")
	private User userId; //DBではObjectを保存できないが、JavaではObjectを保存できるので
	
	//mappedBy = board    replyテーブルのboardがFKである　(boardはfield名　column名)
	//boardテーブルを取得する時にJoinで値を取るからBoardテーブルにreplyというカラムを生成する必要はないとJPAに教える
	//OneToManyのdefault Strategyがfetch = FetchType.Lazyになっているので、EAGERと明示する
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // one => board  reply => many 
	//@JoinColumn(name="id") 正規化の為に不要。
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
