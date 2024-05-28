package com.softsync.zerock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Inspection;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {

	List<Inspection> findByOrdersOrderNo(String orderNo);


}