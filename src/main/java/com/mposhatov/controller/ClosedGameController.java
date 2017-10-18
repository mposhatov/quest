package com.mposhatov.controller;

import com.mposhatov.dao.ClientGameResultRepository;
import com.mposhatov.dto.ClientGameResult;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClientGameResult;
import com.mposhatov.exception.ActiveGameDoesNotExistException;
import com.mposhatov.exception.ClientGameResultDoesNotExist;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Transactional(noRollbackFor = LogicException.class)
public class ClosedGameController {

    @Autowired
    private ClientGameResultRepository clientGameResultRepository;

    @RequestMapping(value = "/client-game-result", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    @ResponseBody
    public ResponseEntity<ClientGameResult> getClosedGame(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientIsNotInTheQueueException, ActiveGameDoesNotExistException, ClientGameResultDoesNotExist {

        final DbClientGameResult clientGameResult =
                clientGameResultRepository.findByClientId(clientSession.getClientId());

        if (clientGameResult == null) {
            throw new ClientGameResultDoesNotExist(clientSession.getClientId());
        }

        return new ResponseEntity<>(EntityConverter.toClientGameResult(clientGameResult), HttpStatus.OK);
    }
}
