package com.mposhatov.dao;

import com.mposhatov.entity.DbHeroCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroCharacteristicsRepository extends JpaRepository<DbHeroCharacteristics, Long> {
}
