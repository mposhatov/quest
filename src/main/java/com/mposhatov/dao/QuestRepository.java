package com.mposhatov.dao;

import com.mposhatov.entity.DbQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestRepository extends JpaRepository<DbQuest, Long>{

    @Query("select q from DbQuest q where q.free = true and q.approved = true")
    List<DbQuest> findAccessible();
}
