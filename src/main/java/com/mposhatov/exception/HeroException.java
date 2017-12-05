package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class HeroException {

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Hero does not exist")
    public static class DoesNotExist extends LogicException {

        private Long heroId;

        public DoesNotExist(Long heroId) {
            super(String.format("Hero (id = %d) does not exist", heroId), null);
            this.heroId = heroId;
        }

        public Long getHeroId() {
            return heroId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hero does not contain main warriors")
    public static class DoesNotContainMainWarriors extends LogicException {

        private Long heroId;

        public DoesNotContainMainWarriors(Long heroId) {
            super(String.format("Hero (id = %d) does not contain main warriors", heroId), null);
            this.heroId = heroId;
        }

        public Long getHeroId() {
            return heroId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hierarchy warrior already available")
    public static class HierarchyWarriorAvailable extends LogicException {

        private Long hierarchyWarriorId;

        public HierarchyWarriorAvailable(Long hierarchyWarriorId) {
            super(String.format("Hierarchy warrior (id = %d) already available", hierarchyWarriorId), null);
            this.hierarchyWarriorId = hierarchyWarriorId;
        }

        public Long getHierarchyWarriorId() {
            return hierarchyWarriorId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hierarchy warrior does not available")
    public static class HierarchyWarriorNotAvailable extends LogicException {

        private Long hierarchyWarriorId;

        public HierarchyWarriorNotAvailable(Long hierarchyWarriorId) {
            super(String.format("Hierarchy warrior (id = %d) does not available", hierarchyWarriorId), null);
            this.hierarchyWarriorId = hierarchyWarriorId;
        }

        public Long getHierarchyWarriorId() {
            return hierarchyWarriorId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Not enough level to work with this warrior")
    public static class NotEnoughLevelToHierarchyWarrior extends LogicException {

        private Long clientId;
        private Long hierarchyWarriorId;
        private Integer requirementLevel;

        public NotEnoughLevelToHierarchyWarrior(Long clientId, Long hierarchyWarriorId, Integer requirementLevel) {
            super(String.format("Client (id = %d ) is not enough level (requirement level = %d) " +
                    "to work with warrior (id = %d)", clientId, requirementLevel, hierarchyWarriorId), null);
            this.clientId = clientId;
            this.hierarchyWarriorId = hierarchyWarriorId;
            this.requirementLevel = requirementLevel;
        }

        public Long getHierarchyWarriorId() {
            return hierarchyWarriorId;
        }

        public Integer getRequirementLevel() {
            return requirementLevel;
        }

        public Long getClientId() {
            return clientId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Client is not enough resources to buy warrior")
    public static class NotEnoughResourcesToBuyHierarchyWarrior extends LogicException {

        private Long clientId;
        private Long hierarchyWarriorId;

        public NotEnoughResourcesToBuyHierarchyWarrior(Long clientId, Long hierarchyWarriorId) {
            super(String.format("Client (id = %d) is not enough resources to buy warrior (id = %d)", clientId, hierarchyWarriorId), null);
            this.clientId = clientId;
            this.hierarchyWarriorId = hierarchyWarriorId;
        }

        public Long getClientId() {
            return clientId;
        }

        public long getHierarchyWarriorId() {
            return hierarchyWarriorId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Client is not enough resources to upgrade warrior")
    public static class NotEnoughResourcesToUpgradeHierarchyWarrior extends LogicException {

        private Long clientId;
        private Long hierarchyWarriorId;

        public NotEnoughResourcesToUpgradeHierarchyWarrior(Long clientId, Long hierarchyWarriorId) {
            super(String.format("Client (id = %d) is not enough resources to upgrade warrior (id = %d)", clientId, hierarchyWarriorId), null);
            this.clientId = clientId;
            this.hierarchyWarriorId = hierarchyWarriorId;
        }

        public Long getClientId() {
            return clientId;
        }

        public long getHierarchyWarriorId() {
            return hierarchyWarriorId;
        }
    }
}
