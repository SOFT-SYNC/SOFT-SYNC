package com.softsync.zerock.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "company")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company {
    
    @Id
    @Column(name = "brn", length = 20)
    private String brn;
    
    @OneToMany
    private List<Contract> contracts;
    
    @Column(name = "company_name", length = 50, nullable = false)
    private String company_name;
    
    @Column(name = "company_address", length = 300, nullable = false)
    private String company_address;
    
    @Column(name = "company_ceo", length = 20, nullable = false)
    private String company_ceo;
    
    @Column(name = "manager", length = 20, nullable = false)
    private String manager;
    
    @Column(name = "manager_email", length = 50, nullable = false)
    private String manager_email;
    
    @Column(name = "manager_tel", length = 20, nullable = false)
    private String manager_tel;
    
    @Column(name = "company_account", length = 30, nullable = false)
    private String company_account;
    
    @Column(name = "company_note", length = 300)
    private String company_note;
    

    // 생성자, getter, setter 등 필요한 코드 추가
}