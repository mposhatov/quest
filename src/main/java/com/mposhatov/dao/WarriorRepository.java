package com.mposhatov.dao;

import com.mposhatov.entity.DbHero;
import com.mposhatov.entity.DbWarrior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WarriorRepository extends JpaRepository<DbWarrior, Long> {

    @Query("select w from DbWarrior w where w.hero = ?1 and w.main = true")
    List<DbWarrior> findMainByHero(DbHero hero);

    @Query("select count(w) from DbWarrior w where w.hero = ?1 and w.main = true")
    int countMainByByHero(DbHero hero);

}
