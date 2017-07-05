package com.mposhatov.dao;

import com.mposhatov.entity.DbQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<DbQuest, Long>{
}
