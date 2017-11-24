package com.mposhatov.dao;

import com.mposhatov.entity.DbSpellAttack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpellAttackRepository extends JpaRepository<DbSpellAttack, Long> {
}
