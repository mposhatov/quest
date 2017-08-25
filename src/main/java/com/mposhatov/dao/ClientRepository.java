package com.mposhatov.dao;

import com.mposhatov.entity.DbClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<DbClient, Long>{

    DbClient findByName(String name);

//    @Query("select c from DbClient c order by c.experience desc, c.lastIncrease asc")
//    List<DbClient> findByRate(Pageable pageRequest);

//    @Query("select c from DbClient c where c.experience >= ?1 order by c.experience desc, c.lastIncrease asc")
//    List<DbClient> findUpperByExperience(long experience);
}
