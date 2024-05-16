package com.softsync.zerock.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee") // 데이터베이스 테이블 이름이 "petfit_users"
@DynamicUpdate
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
   private Integer EMPNum; // 사번
   
   @Column(nullable = false)
   private String department; // 부서

   @Column(nullable = false, updatable = false)
   @CreationTimestamp
   private Date regDate; //가입일
   
   
   
   /*
    * @LastModifiedDate
    * 
    * @Column private LocalDateTime modDate;
    */
   
   /*
    * @Column private Integer approval;
    * 
    * @Column(nullable = true) private String position;
    */
   

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
      return EMPNum;
   }

   public void setEMP_num(Integer eMP_num) {
      EMPNum = eMP_num;
   }

   public String getDepartment() {
      return department;
   }

   public void setDepartment(String department) {
      this.department = department;
   }

   public Date getRegDate() {
      return regDate;
   }

   public void setRegDate(Date regDate) {
      this.regDate = regDate;
   }
   
   
   

}