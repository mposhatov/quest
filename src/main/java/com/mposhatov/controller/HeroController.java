package com.mposhatov.controller;

import com.mposhatov.dao.HeroRepository;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dao.WarriorShopRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Hero;
import com.mposhatov.entity.*;
import com.mposhatov.exception.*;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(noRollbackFor = LogicException.class)
@Controller
public class HeroController {

    private final Logger logger = LoggerFactory.getLogger(HeroController.class);

    @Autowired
    private WarriorShopRepository warriorShopRepository;

    @Autowired
    private WarriorRepository warriorRepository;

    @Autowired
    private HeroRepository heroRepository;

    @RequestMapping(value = "/hero", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST', 'ROLE_ADMIN')")
    public ResponseEntity<Hero> getHero(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {

        final DbHero dbHero = heroRepository.findOne(clientSession.getClientId());

        return new ResponseEntity<>(EntityConverter.toHero(dbHero, true, false, false), HttpStatus.OK);
    }

    @RequestMapping(value = "/hero.action/buy-warrior", method = RequestMethod.GET)//POST
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<Hero> buyWarrior(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam(value = "warriorShopId", required = true) Long warriorShopId) throws WarriorShopDoesNotExistException, HeroDoesNotExistException, ClientDoesNotExistException, NotEnoughResourcesToBuyWarrior {

        final DbWarriorShop dbWarriorShop = warriorShopRepository.findOne(warriorShopId);


        if (dbWarriorShop == null) {
            throw new WarriorShopDoesNotExistException(warriorShopId);
        }

        final DbHero dbHero = heroRepository.findOne(clientSession.getClientId());

        if (dbHero == null) {
            throw new HeroDoesNotExistException(clientSession.getClientId());
        }

        final DbInventory inventory = dbHero.getInventory();

        if (inventory.getDiamonds() >= dbWarriorShop.getPriceOfDiamonds()
                && inventory.getGoldenCoins() >= dbWarriorShop.getPriceOfGoldenCoins()) {

            final DbWarriorDescription warriorDescription = dbWarriorShop.getWarriorDescription();

            final DbWarrior dbWarrior = warriorRepository.save(new DbWarrior(dbHero, warriorDescription));

            dbWarrior.setWarriorCharacteristics(new DbWarriorCharacteristics(dbWarrior, warriorDescription.getWarriorShopCharacteristics()));

            dbHero.addWarrior(dbWarrior);

            inventory.minusGoldenCoins(dbWarriorShop.getPriceOfGoldenCoins());
            inventory.minusDiamonds(dbWarriorShop.getPriceOfDiamonds());

            heroRepository.flush();
        } else {
            throw new NotEnoughResourcesToBuyWarrior(warriorShopId);
        }

        return new ResponseEntity<>(EntityConverter.toHero(dbHero, true, false, false), HttpStatus.OK);
    }

    @RequestMapping(value = "/hero.action/update-main-warriors", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<Void> refreshMainWarriors(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestBody List<Warrior> warriors) {

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

}
