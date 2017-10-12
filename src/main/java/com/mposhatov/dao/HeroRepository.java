package com.mposhatov.dao;

import com.mposhatov.entity.DbHero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface HeroRepository extends JpaRepository<DbHero, Long> {
}
