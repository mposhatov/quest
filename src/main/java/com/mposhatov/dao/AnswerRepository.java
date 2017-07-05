package com.mposhatov.dao;

import com.mposhatov.entity.DbAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<DbAnswer, Long>{
}
