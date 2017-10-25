package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.HeroRepository;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dao.WarriorShopRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Hero;
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

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional(noRollbackFor = LogicException.class)
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WarriorShopRepository warriorShopRepository;

    @Autowired
    private WarriorRepository warriorRepository;

    @Autowired
    private HeroRepository heroRepository;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST', 'ROLE_ADMIN')")
    public ResponseEntity<List<Client>> clients() {

        final List<Client> clients =
                clientRepository.findAll().stream()
                        .map(cl -> EntityConverter.toClient(cl, true, true, false, false))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @RequestMapping(value = "/client.action/buy-warrior", method = RequestMethod.GET)//POST
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

}
