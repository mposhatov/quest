package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hero does not contain main warriors")
public class HeroDoesNotContainMainWarriors extends LogicException {

    private Long heroId;

    public HeroDoesNotContainMainWarriors(Long heroId) {
        super(String.format("Hero (id = %d) does not contain main warriors", heroId), null);
        this.heroId = heroId;
    }

    public Long getHeroId() {
        return heroId;
    }
}
