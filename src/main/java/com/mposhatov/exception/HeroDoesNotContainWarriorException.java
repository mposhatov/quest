package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Hero does not contain warrior")
public class HeroDoesNotContainWarriorException extends LogicException {

    private Long heroId;
    private Long warriorId;

    public HeroDoesNotContainWarriorException(Long heroId, Long warriorId) {
        super(String.format("Hero (id = %d) does not contain warrior (id = %d)", heroId, warriorId), null);
        this.heroId = heroId;
        this.warriorId = warriorId;
    }

    public Long getHeroId() {
        return heroId;
    }

    public Long getWarriorId() {
        return warriorId;
    }
}
