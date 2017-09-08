package com.mposhatov.dao;

import com.mposhatov.entity.DbWarrior;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarriorRepository extends JpaRepository<DbWarrior, Long>{
}
