package com.mposhatov.dao;

import com.mposhatov.entity.DbRegisteredClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegisteredClientRepository extends JpaRepository<DbRegisteredClient, Long>{

    DbRegisteredClient findByName(String name);

    @Query("select c from DbRegisteredClient c order by c.experience desc, c.lastIncrease asc")
    List<DbRegisteredClient> findByRate(Pageable pageRequest);

    @Query("select c from DbRegisteredClient c where c.experience >= ?1 order by c.experience desc, c.lastIncrease asc")
    List<DbRegisteredClient> findUpperByExperience(long experience);
}
