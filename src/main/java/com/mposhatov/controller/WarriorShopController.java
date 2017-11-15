package com.mposhatov.controller;

import com.mposhatov.dao.WarriorShopRepository;
import com.mposhatov.dto.WarriorShop;
import com.mposhatov.entity.DbWarriorShop;
import com.mposhatov.exception.LogicException;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(noRollbackFor = LogicException.class)
@Controller
public class WarriorShopController {

    @Autowired
    private WarriorShopRepository warriorShopRepository;

    @RequestMapping(value = "/warrior-shops", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST', 'ROLE_ADMIN')")
    public ResponseEntity<List<WarriorShop>> getWarriorShops() {

        final List<DbWarriorShop> dbWarriorShops = warriorShopRepository.findAll();

        final List<WarriorShop> warriorShops =
                dbWarriorShops.stream().map(EntityConverter::toWarriorShop).collect(Collectors.toList());

        return new ResponseEntity<>(warriorShops, HttpStatus.OK);
    }
}
