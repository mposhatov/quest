package com.mposhatov.dao;

import com.mposhatov.entity.Category;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.entity.Difficulty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestRepository extends JpaRepository<DbQuest, Long> {

    @Query("select distinct q from DbQuest q inner join q.categories c where q.approved = true " +
            "and c in ?1 and q.difficulty in ?2 order by q.difficulty, c, q.createdAt" )
    List<DbQuest> findAvailableBy(List<Category> categories, List<Difficulty> difficulties, Pageable pageRequest);

}
