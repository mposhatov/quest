package com.mposhatov.dao;

import com.mposhatov.entity.DbHero;
import com.mposhatov.entity.DbHierarchyWarrior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HierarchyWarriorRepository extends JpaRepository<DbHierarchyWarrior, Long> {

    @Query("select hw from DbHero h inner join h.availableHierarchyWarriors hw where h = ?1 and hw.parentHierarchyWarrior = ?2")
    DbHierarchyWarrior findNextAvailableToUpdateForHero(DbHero hero, DbHierarchyWarrior hierarchyWarrior);

}
