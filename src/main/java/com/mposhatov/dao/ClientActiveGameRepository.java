package com.mposhatov.dao;

import com.mposhatov.entity.DbClientActiveGame;
import com.mposhatov.entity.DbRegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientActiveGameRepository extends JpaRepository<DbClientActiveGame, Long>{
    DbClientActiveGame findByClient(DbRegisteredClient client);
}
