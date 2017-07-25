package com.mposhatov.dao;

import com.mposhatov.entity.DbRegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredClientRepository extends JpaRepository<DbRegisteredClient, Long>{
    DbRegisteredClient findByName(String name);
}
