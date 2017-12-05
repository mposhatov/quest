package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SpellException {

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Spell attack does not exist")
    public static class SpellAttackDoesNotExist extends LogicException {

        private Long spellAttackId;

        public SpellAttackDoesNotExist(Long spellAttackId) {
            super(String.format("Spell attack (id = %d) does not exist", spellAttackId), null);
            this.spellAttackId = spellAttackId;
        }

        public Long getSpellAttackId() {
            return spellAttackId;
        }
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Spell exhortation does not exist")
    public static class SpellExhortationDoesNotExist extends LogicException {

        private Long spellExhortationId;

        public SpellExhortationDoesNotExist(Long spellExhortationId) {
            super(String.format("Spell exhortation (id = %d) does not exist", spellExhortationId), null);
            this.spellExhortationId = spellExhortationId;
        }

        public Long getSpellExhortationId() {
            return spellExhortationId;
        }
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Spell heal does not exist")
    public static class SpellHeallDoesNotExist extends LogicException {

        private Long spellHealId;

        public SpellHeallDoesNotExist(Long spellHealId) {
            super(String.format("Spell heal (id = %d) does not exist", spellHealId), null);
            this.spellHealId = spellHealId;
        }

        public Long getSpellHealId() {
            return spellHealId;
        }
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Spell passive does not exist")
    public static class SpellPassiveDoesNotExist extends LogicException {

        private Long spellPassiveId;

        public SpellPassiveDoesNotExist(Long spellPassiveId) {
            super(String.format("Spell passive (id = %d) does not exist", spellPassiveId), null);
            this.spellPassiveId = spellPassiveId;
        }

        public Long getSpellPassiveId() {
            return spellPassiveId;
        }
    }
}
