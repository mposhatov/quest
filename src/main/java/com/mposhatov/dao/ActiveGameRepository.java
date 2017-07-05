package com.mposhatov.dao;

import com.mposhatov.entity.DbActiveGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveGameRepository extends JpaRepository<DbActiveGame, Long>{
}
