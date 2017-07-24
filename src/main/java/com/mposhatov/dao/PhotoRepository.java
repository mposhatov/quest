package com.mposhatov.dao;

import com.mposhatov.entity.DbPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<DbPhoto, Long>{
}
