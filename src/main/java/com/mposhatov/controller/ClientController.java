package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.HeroRepository;
import com.mposhatov.dao.WarriorShopRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Hero;
import com.mposhatov.entity.DbHero;
import com.mposhatov.entity.DbInventory;
import com.mposhatov.entity.DbWarrior;
import com.mposhatov.entity.DbWarriorShop;
import com.mposhatov.exception.ClientDoesNotExistException;
import com.mposhatov.exception.HeroDoesNotExistException;
import com.mposhatov.exception.NotEnoughResourcesToBuyWarrior;
import com.mposhatov.exception.WarriorShopDoesNotExistException;
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
@Transactional
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WarriorShopRepository warriorShopRepository;

    @Autowired
    private HeroRepository heroRepository;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST', 'ROLE_ADMIN')")
    public ResponseEntity<List<Client>> clients() {

        final List<Client> dbClients =
                clientRepository.findAll().stream()
                        .map(cl -> EntityConverter.toClient(cl, false, false))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(dbClients, HttpStatus.OK);
    }

    @RequestMapping(value = "/client.action/buy-warrior", method = RequestMethod.GET)//todo POST
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    private ResponseEntity<Hero> buyWarrior(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam(value = "warriorShopId", required = true) long warriorShopId) throws WarriorShopDoesNotExistException, HeroDoesNotExistException, ClientDoesNotExistException, NotEnoughResourcesToBuyWarrior {

        final DbWarriorShop dbWarriorShop = warriorShopRepository.findOne(warriorShopId);

        if (dbWarriorShop == null) {
            throw new WarriorShopDoesNotExistException(warriorShopId);
        }

        final DbHero dbHero = heroRepository.findOne(clientSession.getHeroId());

        if (dbHero == null) {
            throw new HeroDoesNotExistException(clientSession.getHeroId());
        }

        final DbInventory inventory = dbHero.getInventory();

        if (inventory.getDiamonds() >= dbWarriorShop.getPriceOfDiamonds()
                && inventory.getGoldenCoins() >= dbWarriorShop.getPriceOfGoldenCoins()) {

            dbHero.addWarrior(new DbWarrior(dbHero, dbWarriorShop.getCreaturesDescription()));
        } else {
            throw new NotEnoughResourcesToBuyWarrior(warriorShopId);
        }

        return new ResponseEntity<>(EntityConverter.toHero(dbHero, true), HttpStatus.OK);
    }

}
