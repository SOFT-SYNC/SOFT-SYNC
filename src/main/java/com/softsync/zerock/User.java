package com.softsync.zerock;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "softsync_employee") // 데이터베이스 테이블 이름이 "petfit_users"
public class User {

	@Id
	@Column(nullable = false, name = "id")
	private String employeeId; // 아이디

	@Column(nullable = false)
	private String password; // 비밀번호

	@Column(nullable = false)
	private String name; // 이름

	@Column(nullable = false)
	private String gender; // 성별

	@Column(nullable = false)
	private String email; // 이메일

	@Column(nullable = false)
	private String phone; // 전화번호

	@Column(nullable = false)
	private Integer EMP_num; // 사번
	
	@Column(nullable = false)
	private String department; // 부서

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date u_registerDate; //가입일

	// 기본 생성자
	public User() {

	}
	
	
	

}
