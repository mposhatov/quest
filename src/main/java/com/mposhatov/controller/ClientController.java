package com.mposhatov.controller;

import com.mposhatov.dao.RegisteredClientRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.entity.DbRegisteredClient;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientController {

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public @ResponseBody List<Client> getRate() {

        //todo properties
        final List<DbRegisteredClient> firstClients = registeredClientRepository
                .findBySort(new PageRequest(0, 50, new Sort(Sort.Direction.DESC, "experience")));

        return firstClients.stream().map(EntityConverter::toClient).collect(Collectors.toList());
    }
}
