package com.mposhatov.dao;

import com.mposhatov.entity.DbEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<DbEvent, Long>{
}
