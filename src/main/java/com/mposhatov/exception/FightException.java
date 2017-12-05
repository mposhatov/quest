package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FightException {

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Wrong target")
    public static class WrongTarget extends LogicException {

        private String expectedTarget;

        public WrongTarget(String expectedTarget) {
            super(String.format("Wrong target. Expected target %s", expectedTarget), null);
            this.expectedTarget = expectedTarget;
        }

        public String getExpectedTarget() {
            return expectedTarget;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Not enough mana")
    public static class NotEnoughMana extends LogicException {

        private Integer expectedMana;
        private Integer foundMana;

        public NotEnoughMana(Integer expectedMana, Integer foundMana) {
            super(String.format("Not enough mana. Expected mana: %d, found mana: %d", expectedMana, foundMana), null);
            this.expectedMana = expectedMana;
            this.foundMana = foundMana;
        }

        public Integer getExpectedMana() {
            return expectedMana;
        }

        public Integer getFoundMana() {
            return foundMana;
        }
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Expected another client")
    public static class ExpectedAnotherClient extends LogicException {

        private Long clientId;

        public ExpectedAnotherClient(Long clientId) {
            super(String.format("Expected another client. Current client (id = %d)", clientId), null);
            this.clientId = clientId;
        }

        public Long getClientId() {
            return clientId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hero or hero warriors does not contain spell attack")
    public static class DoesNotContainSpellAttack extends LogicException {

        private Long spellAttackId;

        public DoesNotContainSpellAttack(Long spellAttackId) {
            super(String.format("Hero or hero warriors does not contain spell attack (id = %d)", spellAttackId), null);
            this.spellAttackId = spellAttackId;
        }

        public Long getSpellAttackId() {
            return spellAttackId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hero or hero warriors does not contain spell exhortation")
    public static class DoesNotContainSpellExhortain extends LogicException {

        private Long spellExhortationId;

        public DoesNotContainSpellExhortain(Long spellExhortationId) {
            super(String.format("Hero or hero warriors does not contain spell exhortation (id = %d)", spellExhortationId), null);
            this.spellExhortationId = spellExhortationId;
        }

        public Long getSpellExhortationId() {
            return spellExhortationId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hero or hero warriors does not contain spell heal")
    public static class DoesNotContainSpellHeal extends LogicException {

        private Long spellHealId;

        public DoesNotContainSpellHeal(Long spellHealId) {
            super(String.format("Hero or hero warriors does not contain spell heal (id = %d)", spellHealId), null);
            this.spellHealId = spellHealId;
        }

        public Long getSpellHealId() {
            return spellHealId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hero or hero warriors does not contain spell passive")
    public static class DoesNotContainSpellPassive extends LogicException {

        private Long spellPassiveId;

        public DoesNotContainSpellPassive(Long spellPassiveId) {
            super(String.format("Hero or hero warriors does not contain spell passive (id = %d)", spellPassiveId), null);
            this.spellPassiveId = spellPassiveId;
        }

        public Long getSpellPassiveId() {
            return spellPassiveId;
        }
    }

}
