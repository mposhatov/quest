package com.mposhatov.controller;

import com.mposhatov.dao.HierarchyWarriorRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.HierarchyWarrior;
import com.mposhatov.entity.DbHierarchyWarrior;
import com.mposhatov.exception.LogicException;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(noRollbackFor = LogicException.class)
@Controller
public class HierarchyWarriorController {

    @Autowired
    private HierarchyWarriorRepository hierarchyWarriorRepository;

    @RequestMapping(value = "/hierarchy-warriors", method = RequestMethod.GET)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER', 'ROLE_ADMIN')")
    public ResponseEntity<List<HierarchyWarrior>> getWarriorShops(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) {

        final List<DbHierarchyWarrior> dbHierarchyWarriors = hierarchyWarriorRepository.findAll();

        final List<HierarchyWarrior> hierarchyWarriors = dbHierarchyWarriors.stream()
                .map(hw -> EntityConverter.toHierarchyWarrior(hw, false, false))
                .collect(Collectors.toList());

        return new ResponseEntity<>(hierarchyWarriors, HttpStatus.OK);
    }
}
