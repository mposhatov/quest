package com.mposhatov.dao;

import com.mposhatov.entity.DbStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<DbStep, Long>{
}
