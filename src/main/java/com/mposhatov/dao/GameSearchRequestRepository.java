package com.mposhatov.dao;

import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbGameSearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSearchRequestRepository extends JpaRepository<DbGameSearchRequest, Long>{
    DbGameSearchRequest findByClient(DbClient client);
}
