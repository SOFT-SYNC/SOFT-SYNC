package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.InventoryPeriod;

@Repository
public interface InventoryPeriodRepository extends JpaRepository<InventoryPeriod, Long> {
    // 추가적인 메서드가 필요한 경우 여기에 추가합니다.
}
