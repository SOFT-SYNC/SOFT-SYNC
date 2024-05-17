package com.softsync.zerock.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getEMP_num() {
		return EMP_num;
	}

	public void setEMP_num(Integer eMP_num) {
		EMP_num = eMP_num;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getU_registerDate() {
		return u_registerDate;
	}

	public void setU_registerDate(Date u_registerDate) {
		this.u_registerDate = u_registerDate;
	}
	
	
	

}
