package com.mposhatov.springUtil;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.entity.DbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class ContextHolder {

    @Autowired
    private ClientRepository clientRepository;

    public DbClient getClient() {
        DbClient client = null;
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getPrincipal().equals("QUEST")) {
            final User principal = (User) authentication.getPrincipal();
            client = clientRepository.findByName(principal.getUsername());
        }
        return client;
    }
}
