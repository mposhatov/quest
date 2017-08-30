package com.mposhatov.dao;

import com.mposhatov.entity.DbLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<DbLevel, Long>{
}
