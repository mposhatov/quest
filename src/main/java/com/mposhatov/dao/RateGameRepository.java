package com.mposhatov.dao;

import com.mposhatov.entity.DbRateGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateGameRepository extends JpaRepository<DbRateGame, Long>{
}
