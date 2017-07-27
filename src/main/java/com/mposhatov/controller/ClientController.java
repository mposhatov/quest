package com.mposhatov.controller;

import com.mposhatov.dao.RegisteredClientRepository;
import com.mposhatov.dto.ClientWithRate;
import com.mposhatov.entity.DbRegisteredClient;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public @ResponseBody List<ClientWithRate> getRate() {

        //todo properties
        final List<DbRegisteredClient> firstClients = registeredClientRepository
                .findByRate(new PageRequest(0, 50));

        final List<ClientWithRate> clients = new LinkedList<>();
        for(int i = 0; i < firstClients.size(); ++i) {
            final ClientWithRate client = new ClientWithRate(EntityConverter.toClient(firstClients.get(i)), i + 1);
            clients.add(client);
        }

        return clients;
    }
}
