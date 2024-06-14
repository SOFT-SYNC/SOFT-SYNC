package com.softsync.zerock.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Data
@Getter
@Setter
@Entity
@Table(name = "contract") //계약
@ToString(exclude = {"company"}) // company 필드는 toString()에서 제외
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contract {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // 자동 증가
    @Column(length = 30)  //계약번호
    private int contract_number;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_brn")
    private Company company;
    
    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "items_id") // 외래 키 설정
    private Item item;
     
    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
    @JsonIgnore //거래명세서 발행용
    private List<Orders> orders;
    
    @Column(name = "contract_date")//계약일 
    private LocalDate contract_date;

    @Column(name = "contract_path", length = 100) //계약서
    private String contract_path;
 

    @Column(name = "lead_time") //납기일
    private int lead_time;

    @Column(name = "unit_price") // 단가
    private int unit_price;

    @Column(name = "contract_yn") // 계약여부 초기생성시 n  계약등록 저장버튼 클릭시 y
    private char contractYn = 'n';

    @Column(name = "contract_note" ) //특이사항
    private String contract_note;
    

    
    

}
    // Getters 