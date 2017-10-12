package com.mposhatov.dao;

import com.mposhatov.entity.DbLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LevelRepository extends JpaRepository<DbLevel, Long>{
}
