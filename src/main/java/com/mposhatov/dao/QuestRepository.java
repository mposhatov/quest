package com.mposhatov.dao;

import com.mposhatov.entity.Category;
import com.mposhatov.entity.SimpleGame;
import com.mposhatov.entity.Difficulty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestRepository extends JpaRepository<SimpleGame, Long> {

    @Query("select distinct q from DbQuest q inner join q.categories c where q.approved = true " +
            "and c in ?1 and q.difficulty in ?2 order by q.difficulty, c, q.createdAt" )
    List<SimpleGame> findAvailableBy(List<Category> categories, List<Difficulty> difficulties, Pageable pageRequest);

}
