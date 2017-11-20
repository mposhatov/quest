package com.mposhatov.util;

import com.mposhatov.dto.*;
import com.mposhatov.dto.BodyPart;
import com.mposhatov.entity.*;
import com.mposhatov.exception.InvalidCurrentStepInQueueException;
import com.mposhatov.holder.ActiveGame;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class EntityConverter {

    public static Client toClient(DbClient dbClient, boolean withHero, boolean withWarriors, boolean onlyMainWarriors, boolean withCharacteristic) {
        return new Client(dbClient.getId(), dbClient.getLogin(), dbClient.getEmail(),
                dbClient.getPhoto() != null ? toBackground(dbClient.getPhoto()) : null,
                dbClient.getCreatedAt(), dbClient.getRating(),
                withHero ? dbClient.getHero() != null ? toHero(dbClient.getHero(), withWarriors, onlyMainWarriors, withCharacteristic) : null : null);
    }

    public static Background toBackground(DbPhoto dbPhoto) {
        return new Background(dbPhoto.getClientId(), dbPhoto.getContentType());
    }

    public static Inventory toInventory(DbInventory dbInventory) {
        return new Inventory(dbInventory.getSubjects().stream().map(EntityConverter::toSubject).collect(Collectors.toList()));
    }

    public static Subject toSubject(DbSubject dbSubject) {
        final DbSubjectDescription subjectDescription = dbSubject.getSubjectDescription();
        return new Subject(dbSubject.getId(), subjectDescription.getName(), subjectDescription.getDescription(),
                subjectDescription.getPictureName(), toBodyPart(subjectDescription.getBodyPart()), dbSubject.isMain());
    }

    public static BodyPart toBodyPart(com.mposhatov.entity.BodyPart bodyPart) {
        return new BodyPart(bodyPart.name(), bodyPart.getTitle());
    }

    public static Hero toHero(DbHero dbHero, boolean withWarriors, boolean onlyMainWarriors, boolean withCharacteristic) {
        return new Hero(dbHero.getName(), toHeroCharacteristics(dbHero.getHeroCharacteristics()),
                toInventory(dbHero.getInventory()),
                withWarriors ?
                        dbHero.getWarriors() != null ?
                                dbHero.getWarriors().stream()
                                        .filter(w -> !onlyMainWarriors || w.isMain())
                                        .map(w -> toWarrior(w, withCharacteristic)).collect(Collectors.toList())
                                : null
                        : new ArrayList<>(), toClient(dbHero.getClient(), false, false, false, false));
    }

    public static HeroCharacteristics toHeroCharacteristics(DbHeroCharacteristics dbHeroCharacteristics) {
        return new HeroCharacteristics(dbHeroCharacteristics.getAttack(),
                dbHeroCharacteristics.getPhysicalDefense(), dbHeroCharacteristics.getMagicDefense(),
                dbHeroCharacteristics.getSpellPower(), dbHeroCharacteristics.getMana());
    }

    public static Warrior toWarrior(DbWarrior dbWarrior, boolean withCharacteristic) {
        final DbHierarchyWarrior dbHierarchyWarrior = dbWarrior.getHierarchyWarrior();
        return new Warrior(dbWarrior.getId(),
                dbHierarchyWarrior.getName(),
                dbHierarchyWarrior.getPictureName(),
                dbHierarchyWarrior.getLevel(),
                dbHierarchyWarrior.getKilledExperience(),
                dbWarrior.isMain(),
                dbWarrior.getPosition(),
                toHero(dbWarrior.getHero(), false, false, false),
                withCharacteristic ? toWarriorCharacteristics(dbHierarchyWarrior.getWarriorCharacteristics()) : null);
    }

    public static WarriorCharacteristics toWarriorCharacteristics(DbWarriorCharacteristics dbWarriorCharacteristics) {
        return new WarriorCharacteristics(dbWarriorCharacteristics.getHealth(),
                dbWarriorCharacteristics.getMana(),
                dbWarriorCharacteristics.getSpellPower(),
                dbWarriorCharacteristics.getAttack(),
                dbWarriorCharacteristics.getAttackType(),
                dbWarriorCharacteristics.getRangeType(),
                dbWarriorCharacteristics.getAdditionalDamagePercent(),
                dbWarriorCharacteristics.getPhysicalDefense(),
                dbWarriorCharacteristics.getMagicDefense(),
                dbWarriorCharacteristics.getVelocity(),
                dbWarriorCharacteristics.getActivatedDefensePercent(),
                dbWarriorCharacteristics.getProbableOfEvasion(),
                dbWarriorCharacteristics.getPhysicalBlockPercent(),
                dbWarriorCharacteristics.getMagicalBlockPercent(),
                dbWarriorCharacteristics.getVampirism(),
                dbWarriorCharacteristics.getCriticalDamageChange(),
                dbWarriorCharacteristics.getCriticalDamageMultiplier(),
                dbWarriorCharacteristics.getChangeOfStun());
    }

    public static HierarchyWarrior toHierarchyWarrior(DbHierarchyWarrior dbHierarchyWarrior, boolean withChildren, boolean withParent) {
        return new HierarchyWarrior(dbHierarchyWarrior.getId(),
                dbHierarchyWarrior.getName(),
                dbHierarchyWarrior.getDescription(),
                dbHierarchyWarrior.getLevel(),
                dbHierarchyWarrior.getPictureName(),
                dbHierarchyWarrior.getKilledExperience(),
                dbHierarchyWarrior.getImprovementExperience(),
                toWarriorCharacteristics(dbHierarchyWarrior.getWarriorCharacteristics()),
                withParent ?
                        dbHierarchyWarrior.getParentHierarchyWarrior() != null ?
                                toHierarchyWarrior(dbHierarchyWarrior.getParentHierarchyWarrior(), withChildren, withParent) : null
                        : null,
                withChildren ?
                        dbHierarchyWarrior.getChildrenHierarchyWarriors() != null ?
                                dbHierarchyWarrior.getChildrenHierarchyWarriors().stream()
                                        .map(hw -> toHierarchyWarrior(hw, withChildren, withParent))
                                        .collect(Collectors.toList()) : null
                        : null,
                dbHierarchyWarrior.getUpdateCostGoldCoins(),
                dbHierarchyWarrior.getUpdateCostDiamonds());
    }

    public static ClientGameResult toClientGameResult(DbClientGameResult dbClientGameResult) {
        return new ClientGameResult(dbClientGameResult.getId(), dbClientGameResult.isWin(), dbClientGameResult.getRating());
    }

    public static ClosedGame toClosedGame(DbClosedGame dbClosedGame) {
        return new ClosedGame(dbClosedGame.getId(), dbClosedGame.getStartTime(), dbClosedGame.getFinishTime(),
                dbClosedGame.getClientGameResults() != null ?
                        dbClosedGame.getClientGameResults()
                                .stream().map(EntityConverter::toClientGameResult).collect(Collectors.toList())
                        : null);
    }

    public static StepActiveGame toStepActiveGame(ActiveGame dbActiveGame, Long forClientId) throws InvalidCurrentStepInQueueException {
        Client meClient = forClientId == dbActiveGame.getFirstClient().getId() ? dbActiveGame.getFirstClient() : dbActiveGame.getSecondClient();
        Client anotherClient = meClient.getId() == dbActiveGame.getFirstClient().getId() ? dbActiveGame.getSecondClient() : dbActiveGame.getFirstClient();
        return new StepActiveGame(meClient, anotherClient,
                dbActiveGame.getQueueWarriors(), dbActiveGame.existCurrentWarrior() ? dbActiveGame.getCurrentWarrior() : null,
                dbActiveGame.getWinClientIds() != null && !dbActiveGame.getWinClientIds().isEmpty());
    }


}
