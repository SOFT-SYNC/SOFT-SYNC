package com.softsync.zerock.DTO;

import java.util.List;

import com.softsync.zerock.entity.Receiving;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceivingPageDTO{
    private List<Receiving> receivings; // 한 페이지에 출력할 리시빙 목록
    private int totalPages; // 총 페이지 수
    private long totalElements; // 전체 요소 수

    // 생성자, Getter 및 Setter 메서드 생략
}