package com.mposhatov.dao;

import com.mposhatov.entity.DbSearchGameRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchGameRequestRepository extends JpaRepository<DbSearchGameRequest, Long>{
}
