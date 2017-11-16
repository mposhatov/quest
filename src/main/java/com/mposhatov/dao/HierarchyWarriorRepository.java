package com.mposhatov.dao;

import com.mposhatov.entity.DbHierarchyWarrior;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HierarchyWarriorRepository extends JpaRepository<DbHierarchyWarrior, Long> {
}
