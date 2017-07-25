package com.mposhatov.dao;

import com.mposhatov.entity.DbRegisteredClient;
import com.mposhatov.entity.DbActiveSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveSessionRepository extends JpaRepository<DbActiveSession, Long> {
    DbActiveSession findByClient(DbRegisteredClient client);
}
