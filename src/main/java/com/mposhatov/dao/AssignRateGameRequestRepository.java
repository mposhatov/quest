package com.mposhatov.dao;

import com.mposhatov.entity.DbAssignRateGameRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignRateGameRequestRepository extends JpaRepository<DbAssignRateGameRequest, Long>{

}
