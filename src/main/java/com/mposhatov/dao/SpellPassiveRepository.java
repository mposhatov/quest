package com.mposhatov.dao;

import com.mposhatov.entity.DbSpellPassive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpellPassiveRepository extends JpaRepository<DbSpellPassive, Long> {
}
