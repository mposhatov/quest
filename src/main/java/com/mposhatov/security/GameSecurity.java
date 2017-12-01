package com.mposhatov.security;

import com.mposhatov.dto.ClientSession;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Transactional
@Component("gameSecurity")
public class GameSecurity {

    public boolean hasAnyRolesOnClientSession(ClientSession clientSession, String... roleNames) {

        boolean result = false;

        if (clientSession != null) {
            result = clientSession.getRoles().stream()
                    .map(Enum::name)
                    .anyMatch(r -> Arrays.asList(roleNames).contains(r));
        }

        return result;
    }

}
