package com.softsync.zerock.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "contract") //계약
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // 자동 증가
    @Column(length = 30)  //계약번호
    private int contract_number;

    
    @ManyToOne
    @JoinColumn(name = "brn", referencedColumnName = "brn")
    private Company company;
    
    @ManyToOne
    @JoinColumn(name = "contract_item_code") //품목코드
    private Item item;

    @Column(name = "contract_date")//계약일 
    private LocalDateTime contract_date;

    @Column(name = "contract_path", length = 100) //계약서
    private String contract_path;
 

    @Column(name = "lead_time") //납기일
    private LocalDate lead_time;

    @Column(name = "unit_price") // 단가
    private int unit_price;

    @Column(name = "contract_yn" ) // 계약여부 초기생성시 n  계약등록 저장버튼 클릭시 y
    private char contract_yn = 'y';

    @Column(name = "contract_note" ) //특이사항
    private String contract_note;
}
    // Getters 