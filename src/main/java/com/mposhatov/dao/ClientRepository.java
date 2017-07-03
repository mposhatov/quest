package com.mposhatov.dao;

import com.mposhatov.entity.DbClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<DbClient, Long>{
    DbClient findByName(String name);
}
