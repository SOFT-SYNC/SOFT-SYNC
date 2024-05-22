package com.softsync.zerock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softsync.zerock.entity.ReceiveList;

public interface ReceiveListRepository extends JpaRepository<ReceiveList, Long>{

	 List<ReceiveList> findByReceiving_ReceiveNumber(int receiveNumber);
}
