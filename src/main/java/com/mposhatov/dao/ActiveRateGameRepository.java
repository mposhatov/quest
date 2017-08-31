package com.mposhatov.dao;

import com.mposhatov.entity.DbActiveRateGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveRateGameRepository extends JpaRepository<DbActiveRateGame, Long>{
}
