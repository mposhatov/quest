package com.mposhatov.dao;

import com.mposhatov.entity.DbSpellHeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpellHealRepository extends JpaRepository<DbSpellHeal, Long> {
}
