package com.mposhatov.controller;

import com.mposhatov.dao.ClientGameResultRepository;
import com.mposhatov.exception.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@Transactional(noRollbackFor = LogicException.class)
public class ClosedGameController {

    @Autowired
    private ClientGameResultRepository clientGameResultRepository;

//    @RequestMapping(value = "/client-game-result", method = RequestMethod.GET)
//    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
//    @ResponseBody
//    public ResponseEntity<ClientGameResult> getClosedGame(
//            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
//            @RequestParam("closedGameId") Long closedGameId) {
//
//        final DbClientGameResult clientGameResult =
//                clientGameResultRepository.findByClientIdAndClosedGameId(clientSession.getClientId(), closedGameId);
//
//        return new ResponseEntity<>(EntityConverter.toClientGameResult(clientGameResult), HttpStatus.OK);
//    }
}
