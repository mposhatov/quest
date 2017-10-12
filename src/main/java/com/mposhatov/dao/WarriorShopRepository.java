package com.mposhatov.dao;

import com.mposhatov.entity.DbWarriorShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WarriorShopRepository extends JpaRepository<DbWarriorShop, Long> {
}
