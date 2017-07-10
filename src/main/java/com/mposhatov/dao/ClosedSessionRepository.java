package com.mposhatov.dao;

import com.mposhatov.entity.DbClosedSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosedSessionRepository extends JpaRepository<DbClosedSession, Long>{
}
