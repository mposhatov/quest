package com.mposhatov.dao;

import com.mposhatov.entity.DbClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<DbClient, Long>{
    DbClient findByLogin(String login);
}
