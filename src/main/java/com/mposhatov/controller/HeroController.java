package com.mposhatov.controller;

import com.mposhatov.dao.HeroRepository;
import com.mposhatov.dao.HierarchyWarriorRepository;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Hero;
import com.mposhatov.dto.WarriorUpgrade;
import com.mposhatov.entity.DbHero;
import com.mposhatov.entity.DbHierarchyWarrior;
import com.mposhatov.entity.DbInventory;
import com.mposhatov.entity.DbWarrior;
import com.mposhatov.exception.*;
import com.mposhatov.service.validator.HeroExceptionThrower;
import com.mposhatov.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(noRollbackFor = LogicException.class)
@Controller
public class HeroController {

    private final Logger logger = LoggerFactory.getLogger(HeroController.class);

    @Autowired
    private HierarchyWarriorRepository hierarchyWarriorRepository;

    @Autowired
    private WarriorRepository warriorRepository;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private HeroExceptionThrower heroExceptionThrower;

    @RequestMapping(value = "/hero", method = RequestMethod.GET)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER', 'ROLE_ADMIN')")
    public ResponseEntity<Hero> getHero(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) throws LogicException {

        final DbHero dbHero = getHero(clientSession.getClientId());

        return new ResponseEntity<>(EntityConverter.toHero(dbHero, true, true, true, true, true, false, false), HttpStatus.OK);
    }

    @RequestMapping(value = "/hero.action/add-available-warrior", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<List<WarriorUpgrade>> addAvailableWarrior(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(name = "hierarchyWarriorId", required = true) Long hierarchyWarriorId) throws LogicException {

        final DbHero dbHero = getHero(clientSession.getClientId());

        final DbHierarchyWarrior dbHierarchyWarrior = getHierarchyWarrior(hierarchyWarriorId);

        heroExceptionThrower.throwIfHierarchyWarriorAvailable(dbHero, dbHierarchyWarrior);
        heroExceptionThrower.throwIfNotEnoughLevelToHierarchyWarrior(clientSession.getClientId(), dbHero, dbHierarchyWarrior);
        heroExceptionThrower.throwIfNotEnoughResourcesToUpdateHierarchyWarrior(dbHero, dbHierarchyWarrior);

        final DbInventory dbInventory = dbHero.getInventory();

        final List<WarriorUpgrade> warriorUpgrades = new ArrayList<>();

        dbInventory.minusGoldCoins(dbHierarchyWarrior.getUpdateCostGoldCoins());
        dbInventory.minusDiamonds(dbHierarchyWarrior.getUpdateCostDiamonds());

        dbHero.addAvailableWarrior(dbHierarchyWarrior);

        final List<DbWarrior> dbWarriors =
                warriorRepository.selectByHeroAndHierarchyWarrior(dbHero, dbHierarchyWarrior.getParentHierarchyWarrior());

        for (DbWarrior dbWarrior : dbWarriors) {

            final WarriorUpgrade warriorUpgrade =
                    new WarriorUpgrade().warriorBeforeUpgrade(EntityConverter.toWarrior(dbWarrior, true, false, false, false, false));

            dbWarrior.hierarchyWarrior(dbHierarchyWarrior);

            warriorUpgrades.add(warriorUpgrade.warriorAfterUpgrade(EntityConverter.toWarrior(dbWarrior, true, false, false, false, false)));
        }

        return new ResponseEntity<>(warriorUpgrades, HttpStatus.OK);
    }

    @RequestMapping(value = "/hero.action/buy-warrior", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<com.mposhatov.dto.Warrior> buyWarrior(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(value = "hierarchyWarriorId", required = true) Long hierarchyWarriorId) throws LogicException {

        final DbHero dbHero = getHero(clientSession.getClientId());

        final DbHierarchyWarrior dbHierarchyWarrior = getHierarchyWarrior(hierarchyWarriorId);

        heroExceptionThrower.throwIfHierarchyWarriorNotAvailable(dbHero, dbHierarchyWarrior);
        heroExceptionThrower.throwIfNotEnoughResourcesToBuyHierarchyWarrior(dbHero, dbHierarchyWarrior);

        final DbInventory inventory = dbHero.getInventory();

        inventory.minusGoldCoins(dbHierarchyWarrior.getPurchaseCostGoldCoins());
        inventory.minusDiamonds(dbHierarchyWarrior.getPurchaseCostDiamonds());

        final DbWarrior dbWarrior = dbHero.addWarrior(dbHierarchyWarrior);

        heroRepository.flush();

        return new ResponseEntity<>(EntityConverter.toWarrior(dbWarrior, true, true, true, true, true), HttpStatus.OK);
    }

    @RequestMapping(value = "/hero.action/update-main-warriors", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<Void> refreshMainWarriors(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestBody(required = true) List<Warrior> warriors) {

        final DbHero hero = heroRepository.findOne(clientSession.getClientId());

        final Map<Long, Warrior> warriorByWarriorIds =
                warriors.stream().collect(Collectors.toMap(Warrior::getId, w -> w));

        final List<DbWarrior> dbWarriors = warriorRepository.findAll(warriorByWarriorIds.keySet());

        long availableSlots = 0;

        for (DbWarrior dbWarrior : dbWarriors) {
            final Warrior warrior = warriorByWarriorIds.get(dbWarrior.getId());

            if (dbWarrior.getHero().getClientId().equals(hero.getClientId())) {
                if (warrior.getPosition() == null) {
                    dbWarrior.setNoMain();
                    availableSlots++;
                } else {
                    dbWarrior.setMain(warrior.getPosition());
                    availableSlots--;
                }
            } else {
                logger.error("Client (id = {}) does not contain warrior (id = {})", clientSession.getClientId(), dbWarrior.getId());
            }
        }

        hero.addAvailableSlots(availableSlots);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private DbHero getHero(Long heroId) throws HeroException.DoesNotExist {

        final DbHero hero = heroRepository.findOne(heroId);

        if (hero == null) {
            throw new HeroException.DoesNotExist(heroId);
        }

        return hero;
    }

    private DbHierarchyWarrior getHierarchyWarrior(Long hierarchyWarriorId) throws HierarchyWarriorException.DoesNotExist {

        final DbHierarchyWarrior hierarchyWarrior = hierarchyWarriorRepository.findOne(hierarchyWarriorId);

        if (hierarchyWarrior == null) {
            throw new HierarchyWarriorException.DoesNotExist(hierarchyWarriorId);
        }

        return hierarchyWarrior;
    }

}
