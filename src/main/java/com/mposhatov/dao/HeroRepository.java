package com.mposhatov.dao;

import com.mposhatov.entity.DbHero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<DbHero, Long> {
}
