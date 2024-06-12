package com.softsync.zerock.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.DTO.InventoryPeriodSummaryDTO;
import com.softsync.zerock.entity.InventoryPeriod;

@Repository
public interface InventoryPeriodRepository extends JpaRepository<InventoryPeriod, Long> {

	@Query("SELECT new com.softsync.zerock.DTO.InventoryPeriodSummaryDTO("
			+ "ip.inventory.item.itemCode, ip.inventory.item.itemName, "
			+ "SUM(COALESCE(sl.quantity, 0)), SUM(COALESCE(rl.quantity, 0)), "
			+ "COALESCE((ip.inventory.initialQuantity + (SELECT SUM(COALESCE(rl2.quantity, 0) - COALESCE(sl2.quantity, 0)) "
			+ "FROM InventoryPeriod ip2 " + "LEFT JOIN ip2.shipmentList sl2 " + "LEFT JOIN ip2.receiveList rl2 "
			+ "WHERE ip2.date < :startDate AND ip2.inventory.id = ip.inventory.id)), ip.inventory.initialQuantity) AS initialInventory, "
			+ "COALESCE((ip.inventory.initialQuantity + (SELECT SUM(COALESCE(rl3.quantity, 0) - COALESCE(sl3.quantity, 0)) "
			+ "FROM InventoryPeriod ip3 " + "LEFT JOIN ip3.shipmentList sl3 " + "LEFT JOIN ip3.receiveList rl3 "
			+ "WHERE ip3.date <= :endDate AND ip3.inventory.id = ip.inventory.id)), ip.inventory.initialQuantity) AS finalInventory, "
			+ "(COALESCE((ip.inventory.initialQuantity + (SELECT SUM(COALESCE(rl3.quantity, 0) - COALESCE(sl3.quantity, 0)) "
			+ "FROM InventoryPeriod ip3 " + "LEFT JOIN ip3.shipmentList sl3 " + "LEFT JOIN ip3.receiveList rl3 "
			+ "WHERE ip3.date <= :endDate AND ip3.inventory.id = ip.inventory.id)), ip.inventory.initialQuantity)) * "
			+ "COALESCE(rl.receiving.orders.contract.unit_price, 0) AS finalInventoryValue) "
			+ "FROM InventoryPeriod ip " + "LEFT JOIN ip.shipmentList sl " + "LEFT JOIN ip.receiveList rl "
			+ "LEFT JOIN rl.receiving r " + "LEFT JOIN r.orders o " + "LEFT JOIN o.contract c "
			+ "WHERE ip.date BETWEEN :startDate AND :endDate "
			+ "GROUP BY ip.inventory.item.itemCode, ip.inventory.item.itemName, ip.inventory.id")
	Page<InventoryPeriodSummaryDTO> findInventoryPeriodSummaries(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate, Pageable pageable);

	@Query("SELECT new com.softsync.zerock.DTO.InventoryPeriodSummaryDTO("
			+ "ip.inventory.item.itemCode, ip.inventory.item.itemName, "
			+ "SUM(COALESCE(sl.quantity, 0)), SUM(COALESCE(rl.quantity, 0)), "
			+ "COALESCE((ip.inventory.initialQuantity + (SELECT SUM(COALESCE(rl2.quantity, 0) - COALESCE(sl2.quantity, 0)) "
			+ "FROM InventoryPeriod ip2 " + "LEFT JOIN ip2.shipmentList sl2 " + "LEFT JOIN ip2.receiveList rl2 "
			+ "WHERE ip2.inventory.id = ip.inventory.id)), ip.inventory.initialQuantity) AS initialInventory, "
			+ "COALESCE((ip.inventory.initialQuantity + (SELECT SUM(COALESCE(rl3.quantity, 0) - COALESCE(sl3.quantity, 0)) "
			+ "FROM InventoryPeriod ip3 " + "LEFT JOIN ip3.shipmentList sl3 " + "LEFT JOIN ip3.receiveList rl3 "
			+ "WHERE ip3.inventory.id = ip.inventory.id)), ip.inventory.initialQuantity) AS finalInventory, "
			+ "(COALESCE((ip.inventory.initialQuantity + (SELECT SUM(COALESCE(rl3.quantity, 0) - COALESCE(sl3.quantity, 0)) "
			+ "FROM InventoryPeriod ip3 " + "LEFT JOIN ip3.shipmentList sl3 " + "LEFT JOIN ip3.receiveList rl3 "
			+ "WHERE ip3.inventory.id = ip.inventory.id)), ip.inventory.initialQuantity)) * "
			+ "COALESCE(rl.receiving.orders.contract.unit_price, 0) AS finalInventoryValue) "
			+ "FROM InventoryPeriod ip " + "LEFT JOIN ip.shipmentList sl " + "LEFT JOIN ip.receiveList rl "
			+ "LEFT JOIN rl.receiving r " + "LEFT JOIN r.orders o " + "LEFT JOIN o.contract c "
			+ "GROUP BY ip.inventory.item.itemCode, ip.inventory.item.itemName, ip.inventory.id")
	Page<InventoryPeriodSummaryDTO> findAllInventoryPeriodSummaries(Pageable pageable);

	List<InventoryPeriod> findByShipmentListId(Long shipmentListId);
}
