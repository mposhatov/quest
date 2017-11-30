package com.mposhatov.dao;

import com.mposhatov.entity.DbClosedGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosedGameRepository extends JpaRepository<DbClosedGame, Long> {
}
