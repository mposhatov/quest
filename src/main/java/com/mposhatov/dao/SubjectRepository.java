package com.mposhatov.dao;

import com.mposhatov.entity.DbSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<DbSubject, Long>{
}
