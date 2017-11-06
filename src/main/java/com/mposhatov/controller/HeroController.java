package com.mposhatov.controller;

import com.mposhatov.dao.HeroRepository;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dao.WarriorShopRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Hero;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.*;
import com.mposhatov.exception.*;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(noRollbackFor = LogicException.class)
@Controller
public class HeroController {

    @Autowired
    private WarriorShopRepository warriorShopRepository;

    @Autowired
    private WarriorRepository warriorRepository;

    @Autowired
    private HeroRepository heroRepository;

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

    @RequestMapping(value = "/hero.action/refresh-main-warriors", method = RequestMethod.GET)//POST
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<List<Warrior>> refreshMainWarriors(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam Map<Long, Integer> positionByWarriorIds) {

        final DbHero hero = heroRepository.findOne(clientSession.getClientId());

        final List<DbWarrior> oldMainDbWarriors = warriorRepository.findMainByHero(hero);

        oldMainDbWarriors.forEach(DbWarrior::setNoMain);

        hero.addAvailableSlots(oldMainDbWarriors.size());

        final List<DbWarrior> newMainDbWarriors = warriorRepository.findAll(positionByWarriorIds.keySet());

        final Iterator<DbWarrior> newMainDbWarriorsIterator = newMainDbWarriors.iterator();

        while (newMainDbWarriorsIterator.hasNext()) {
            final DbWarrior warrior = newMainDbWarriorsIterator.next();
            if (!warrior.getHero().getClientId().equals(hero.getClientId())) {
                newMainDbWarriorsIterator.remove();
            } else if (hero.getAvailableSlots() > 0) {
                warrior.setMain(positionByWarriorIds.get(warrior.getId()));
                hero.addAvailableSlots(-1);
            }
        }

        final List<Warrior> warriors =
                newMainDbWarriors.stream()
                        .map(w -> EntityConverter.toWarrior(w, false))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(warriors, HttpStatus.OK);
    }

}
