package com.mposhatov.service.validator;

import com.mposhatov.exception.ActiveGameException;
import com.mposhatov.holder.ActiveGame;
import org.springframework.stereotype.Component;

@Component
public class ActiveGameExceptionThrower {

    public void throwExceptionIfActiveGameDoesNotContainTwoClients(ActiveGame activeGame) throws ActiveGameException.DoesNotContainTwoClients {

        if (activeGame.getFirstClient() == null || activeGame.getSecondClient() == null) {
            throw new ActiveGameException.DoesNotContainTwoClients(activeGame.getId());
        }

    }

    public void throwExceptionIfActiveGameDoesNotContainWinClient(ActiveGame activeGame) throws ActiveGameException.DoesNotContainWinClient {

        if (activeGame.getWinClientId() == null) {
            throw new ActiveGameException.DoesNotContainWinClient(activeGame.getId());
        }

    }

}
