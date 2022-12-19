package com.cos.blog.model;



import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Lob//대용량 데이터
	private String content;
	
	private int count;//조회수
	
	@ManyToOne(fetch=FetchType.EAGER)//Many=Board, User=One 한명유저 여러 게시글 쓸 수 있다.
	@JoinColumn(name="userId")
	private User user;
	//DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.
	//eager바로 가져옴
	
	@OneToMany(mappedBy="board",fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
	//eager은 한번에 가져오고, lazy는 필요할 때 가져옴 여기서 ontomany의 기본은 lazy라서 설정 해줘야됨
	//mappedBy 연관관계의 주인이 아니다(난 FK가 아니에요) DB에 칼럼 만들지 마세요.
	//lazy 클릭하면 가져옴
	@OrderBy("id desc")
	@JsonIgnoreProperties({"board"})
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
}
