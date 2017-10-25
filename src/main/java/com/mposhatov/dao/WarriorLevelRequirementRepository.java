package com.mposhatov.dao;

import com.mposhatov.entity.DbWarriorLevelRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarriorLevelRequirementRepository extends JpaRepository<DbWarriorLevelRequirement, Long> {

    DbWarriorLevelRequirement findByWarriorNameAndLevel(String warriorName, Long level);
}
