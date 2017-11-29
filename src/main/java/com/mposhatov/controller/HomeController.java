package com.mposhatov.controller;

import com.mposhatov.dao.*;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.*;
import com.mposhatov.exception.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.stream.Collectors;

@Controller
@Transactional(noRollbackFor = LogicException.class)
public class HomeController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private HeroLevelRequirementRepository heroLevelRequirementRepository;

    @Autowired
    private HeroCharacteristicsRepository heroCharacteristicsRepository;

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String goHome() {
        return "welcome";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String client(HttpServletRequest request) {

        final HttpSession session = request.getSession(true);

        final ClientSession clientSession = (ClientSession) session.getAttribute(ClientSession.class.getName());

        if (clientSession == null) {

            final DbClient client =
                    clientRepository.saveAndFlush(new DbClient(Collections.singletonList(Role.ROLE_GAMER)));

            final DbHeroLevelRequirement heroLevelRequirement = heroLevelRequirementRepository.findOne(1L);

            final DbHero hero =
                    heroRepository.saveAndFlush(new DbHero(client, heroLevelRequirement.getAdditionalHeroPoint()));

            final DbHeroCharacteristics heroCharacteristics =
                    heroCharacteristicsRepository.save(
                            new DbHeroCharacteristics(hero, 1, 1, 1));

            hero.setHeroCharacteristics(heroCharacteristics);

            inventoryRepository.save(new DbInventory(hero));

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(client, null,
                            client.getRoles().stream()
                                    .map(r -> new SimpleGrantedAuthority(r.name()))
                                    .collect(Collectors.toList()));

            SecurityContextHolder.getContext().setAuthentication(auth);

            session.setAttribute(
                    ClientSession.class.getName(),
                    new ClientSession(client.getId(), Collections.singletonList(Role.ROLE_GAMER)));
        }

        return "home";
    }

    @RequestMapping(value = "/keepAlive", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void keepAlive() {
    }

}
