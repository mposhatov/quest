package com.mposhatov.dao;

import com.mposhatov.entity.DbActiveGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActiveGameRepository extends JpaRepository<DbActiveGame, Long> {

    @Query("select g from DbActiveGame g where g.clientId = ?1")
    List<DbActiveGame> findByClient(long clientId);

    @Query("select g from DbActiveGame g where g.clientId = ?1 and g.createdAt = " +
            "(select max(g1.createdAt) from DbActiveGame g1)")
    DbActiveGame findLastByClient(long clientId);
}
