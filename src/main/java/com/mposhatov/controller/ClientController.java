package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.HeroRepository;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Hero;
import com.mposhatov.entity.DbHero;
import com.mposhatov.entity.DbWarrior;
import com.mposhatov.exception.HeroDoesNotExistException;
import com.mposhatov.exception.WarriorDoesNotExistException;
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
    private WarriorRepository warriorRepository;

    @Autowired
    private HeroRepository heroRepository;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST', 'ROLE_ADMIN')")
    public ResponseEntity<List<Client>> clients() {

        final List<Client> dbClients =
                clientRepository.findAll().stream().map(EntityConverter::toClient).collect(Collectors.toList());

        return new ResponseEntity<>(dbClients, HttpStatus.OK);
    }

    @RequestMapping(value = "/client.action/warrior", method = RequestMethod.GET)//todo POST
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    private ResponseEntity<Hero> addWarrior(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam("warriorId") long warriorId) throws WarriorDoesNotExistException, HeroDoesNotExistException {

        final DbWarrior dbWarrior = warriorRepository.findOne(warriorId);

        if (dbWarrior == null) {
            throw new WarriorDoesNotExistException(warriorId);
        }

        final DbHero dbHero = heroRepository.findOne(clientSession.getHeroId());

        if (dbHero == null) {
            throw new HeroDoesNotExistException(clientSession.getHeroId());
        }

        dbHero.addWarrior(dbWarrior);

        return new ResponseEntity<>(EntityConverter.toHero(dbHero), HttpStatus.OK);
    }

}
