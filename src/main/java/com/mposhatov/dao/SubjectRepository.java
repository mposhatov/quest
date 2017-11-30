package com.mposhatov.dao;

import com.mposhatov.entity.DbSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SubjectRepository extends JpaRepository<DbSubject, Long>{
}
