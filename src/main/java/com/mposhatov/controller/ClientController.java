package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.ClientWithRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public @ResponseBody List<ClientWithRate> getRate() {

        //todo properties
//        final List<DbClient> firstClients = clientRepository
//                .findByRate(new PageRequest(0, 50));

        final List<ClientWithRate> clients = new ArrayList<>();
//        for (int i = 0; i < firstClients.size(); ++i) {
//            clients.add(new ClientWithRate(EntityConverter.toClient(firstClients.get(i)), i + 1));
//        }

        return clients;
    }

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public RedirectView register() {
//    }
}
