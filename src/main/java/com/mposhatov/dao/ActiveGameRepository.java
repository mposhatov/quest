package com.mposhatov.dao;

import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveGameRepository extends JpaRepository<DbActiveGame, Long>{
    DbActiveGame findByClient(DbClient client);
}
