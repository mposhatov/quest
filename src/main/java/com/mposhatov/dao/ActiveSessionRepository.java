package com.mposhatov.dao;

import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbActiveSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveSessionRepository extends JpaRepository<DbActiveSession, Long> {
    DbActiveSession findByClient(DbClient client);
}
