package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

@Controller
public class AdminController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value="/admin", method = {RequestMethod.GET, RequestMethod.POST})
    public String profile() {
        return "admin";
    }

    @RequestMapping(value="/client", method = {RequestMethod.GET, RequestMethod.POST})
    public String addClient() {
        DbClient mposhatov2 = new DbClient("01234567890123456789", "mposhatov2",
                Arrays.asList(Role.ROLE_GAMER, Role.ROLE_ADMIN));
        clientRepository.save(mposhatov2);
        return "loginPage";
    }

}
