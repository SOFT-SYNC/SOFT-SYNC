package com.softsync.zerock.repository;

import com.softsync.zerock.entity.ShipmentList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentListRepository extends JpaRepository<ShipmentList, Long> {
}
