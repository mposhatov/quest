package com.mposhatov.dao;

import com.mposhatov.entity.DbClientGameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientGameResultRepository extends JpaRepository<DbClientGameResult, Long> {

    @Query("select cgr from DbClientGameResult cgr where cgr.client.id = ?1")
    DbClientGameResult findByClientId(long clientId);
}
