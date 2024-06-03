package com.softsync.zerock.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Inspection;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {

	List<Inspection> findByOrdersOrderNo(String orderNo);
	/*
	 * Optional<Inspection> findById(Integer inspecNo);
	 */

	Inspection findByInspecNo(Long inspecNo);
		
	Long countByinspecYn(char inspecYn);
	
	@Query("SELECT COUNT(*) FROM Inspection WHERE inspecYn BETWEEN :startDate AND :endDate")
	Long countByInspecPlanDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
