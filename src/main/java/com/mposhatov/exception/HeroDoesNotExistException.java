package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.UNPROCESSABLE_ENTITY,
        reason = "Hero does not exist")
public class HeroDoesNotExistException extends LogicException {

    private long heroId;

    public HeroDoesNotExistException(long heroId) {
        super(String.format("Hero (id = %d) does not exist", heroId), null);
        this.heroId = heroId;
    }

    public long getHeroId() {
        return heroId;
    }
}
