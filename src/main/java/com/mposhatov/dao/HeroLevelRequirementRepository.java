package com.mposhatov.dao;

import com.mposhatov.entity.DbHeroLevelRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroLevelRequirementRepository extends JpaRepository<DbHeroLevelRequirement, Long> {
}
