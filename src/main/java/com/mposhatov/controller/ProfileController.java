package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.entity.DbClient;
import com.mposhatov.service.GameManager;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Transactional
public class ProfileController {

    @Autowired
    private GameManager gameManager;

    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE ,method = RequestMethod.GET)
    public @ResponseBody Client profile(
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = false) Client client) {
        final DbClient dbClient = clientRepository.findOne(client.getId());
        return EntityConverter.toClient(dbClient);
    }
}
