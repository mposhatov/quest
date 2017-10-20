package com.mposhatov.dao;

import com.mposhatov.entity.DbInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<DbInventory, Long> {
}
