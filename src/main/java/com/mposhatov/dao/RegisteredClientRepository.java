package com.mposhatov.dao;

import com.mposhatov.entity.DbRegisteredClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegisteredClientRepository extends JpaRepository<DbRegisteredClient, Long>{

    DbRegisteredClient findByName(String name);

    @Query("select c from DbRegisteredClient c")
    List<DbRegisteredClient> findBySort(Pageable pageRequest);
}
