package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hero does not contain spell attack")
public class HeroDoesNotContainSpellAttackException extends LogicException {

    private Long heroId;
    private Long spellAttackId;

    public HeroDoesNotContainSpellAttackException(Long heroId, Long spellAttackId) {
        super(String.format("Hero (id = %d) does not contain spell attack (id = %d)", heroId, spellAttackId), null);
        this.heroId = heroId;
        this.spellAttackId = spellAttackId;
    }

    public Long getHeroId() {
        return heroId;
    }

    public Long getSpellAttackId() {
        return spellAttackId;
    }
}
