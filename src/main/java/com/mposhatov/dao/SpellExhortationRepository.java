package com.mposhatov.dao;

import com.mposhatov.entity.DbSpellExhortation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpellExhortationRepository extends JpaRepository<DbSpellExhortation, Long> {
}
