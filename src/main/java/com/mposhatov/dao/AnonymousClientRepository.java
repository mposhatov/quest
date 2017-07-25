package com.mposhatov.dao;

import com.mposhatov.entity.DbAnonymousClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousClientRepository extends JpaRepository<DbAnonymousClient, Long>{
    DbAnonymousClient findByJsessionId(String jsessionId);
}
