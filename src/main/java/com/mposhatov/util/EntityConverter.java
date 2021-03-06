package com.mposhatov.util;

import com.mposhatov.dto.*;
import com.mposhatov.dto.BodyPart;
import com.mposhatov.entity.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class EntityConverter {

    public static Client toClient(DbClient dbClient, boolean withHero, boolean withWarriors, boolean onlyMainWarriors, boolean withCharacteristic,
                                  boolean withSpellAttack, boolean withSpellHeals, boolean withSpellExhortations, boolean withSpellPassives) {
        return new Client(dbClient.getId(), dbClient.getLogin(), dbClient.getEmail(),
                dbClient.getPhoto() != null ? toBackground(dbClient.getPhoto()) : null,
                dbClient.getCreatedAt(), dbClient.getRating(),
                withHero ?
                        dbClient.getHero() != null ?
                                toHero(dbClient.getHero(), withSpellAttack, withSpellHeals, withSpellExhortations, withSpellPassives, withWarriors, onlyMainWarriors, withCharacteristic)
                                : null
                        : null);
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

    public static Hero toHero(DbHero dbHero,
                              boolean withSpellAttacks, boolean withSpellHeals, boolean withSpellExhortations, boolean withSpellPassives,
                              boolean withWarriors, boolean onlyMainWarriors, boolean withCharacteristic) {
        return new Hero(dbHero.getName(), toHeroCharacteristics(dbHero.getHeroCharacteristics()),
                toInventory(dbHero.getInventory()),
                withWarriors ?
                        dbHero.getWarriors() != null ?
                                dbHero.getWarriors().stream()
                                        .filter(w -> !onlyMainWarriors || w.isMain())
                                        .map(w -> toWarrior(w, withCharacteristic, withSpellAttacks, withSpellHeals, withSpellExhortations, withSpellPassives)).collect(Collectors.toList())
                                : null
                        : new ArrayList<>(), toClient(dbHero.getClient(), false, false, false, false, false, false, false, false),
                withSpellAttacks ?
                        dbHero.getSpellAttacks().stream()
                                .map(sa -> toSpellAttack(sa, false, false))
                                .collect(Collectors.toList())
                        : null);
    }

    public static HeroCharacteristics toHeroCharacteristics(DbHeroCharacteristics dbHeroCharacteristics) {
        return new HeroCharacteristics(dbHeroCharacteristics.getAttack(),
                dbHeroCharacteristics.getPhysicalDefense(), dbHeroCharacteristics.getMagicDefense(),
                dbHeroCharacteristics.getSpellPower(), dbHeroCharacteristics.getMana());
    }

    public static Warrior toWarrior(DbWarrior dbWarrior, boolean withCharacteristic,
                                    boolean withSpellAttacks, boolean withSpellHeals, boolean withSpellExhortations, boolean withSpellPassives) {
        final DbHierarchyWarrior dbHierarchyWarrior = dbWarrior.getHierarchyWarrior();
        return new Warrior(dbWarrior.getId(),
                dbHierarchyWarrior.getName(),
                dbHierarchyWarrior.getPictureName(),
                dbHierarchyWarrior.getLevel(),
                dbHierarchyWarrior.getKilledExperience(),
                dbHierarchyWarrior.getImprovementExperience(),
                dbWarrior.isMain(),
                dbWarrior.getPosition(),
                toHero(dbWarrior.getHero(), false, false, false, false, false, false, false),
                withCharacteristic ? toWarriorCharacteristics(dbHierarchyWarrior.getWarriorCharacteristics()) : null,
                dbWarrior.getExperience(),
                withSpellAttacks ?
                        dbHierarchyWarrior.getSpellAttacks().stream()
                                .map(sa -> toSpellAttack(sa, false, false))
                                .collect(Collectors.toList())
                        : null,
                withSpellHeals ?
                        dbHierarchyWarrior.getSpellHeals().stream()
                                .map(sh -> toSpellHeal(sh, false, false)).collect(Collectors.toList())
                        : null);
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

    public static SpellAttack toSpellAttack(DbSpellAttack dbSpellAttack, boolean withChildren, boolean withParent) {
        return new SpellAttack(dbSpellAttack.getId(),
                dbSpellAttack.getName(),
                dbSpellAttack.getDescription(),
                dbSpellAttack.getPictureName(),
                dbSpellAttack.getMana(),
                dbSpellAttack.getPurchaseCostGoldCoins(),
                dbSpellAttack.getPurchaseCostDiamonds(),
                dbSpellAttack.getUpdateCostGoldCoins(),
                dbSpellAttack.getUpdateCostDiamonds(),
                dbSpellAttack.getRequirementHeroLevel(),
                dbSpellAttack.getDamage(),
                dbSpellAttack.getDamageBySpellPower(),
                withParent ? toSpellAttack(dbSpellAttack, withChildren, withParent) : null,
                withChildren ?
                        dbSpellAttack.getChildrenSpellAttacks() != null && dbSpellAttack.getChildrenSpellAttacks().isEmpty() ?
                                dbSpellAttack.getChildrenSpellAttacks()
                                        .stream()
                                        .map(sa -> toSpellAttack(sa, withChildren, withParent))
                                        .collect(Collectors.toList())
                                : null
                        : null);
    }

    public static SpellHeal toSpellHeal(DbSpellHeal dbSpellHeal, boolean withChildren, boolean withParent) {
        return new SpellHeal(dbSpellHeal.getId(),
                dbSpellHeal.getName(),
                dbSpellHeal.getDescription(),
                dbSpellHeal.getPictureName(),
                dbSpellHeal.getMana(),
                dbSpellHeal.getPurchaseCostGoldCoins(),
                dbSpellHeal.getPurchaseCostDiamonds(),
                dbSpellHeal.getUpdateCostGoldCoins(),
                dbSpellHeal.getUpdateCostDiamonds(),
                dbSpellHeal.getRequirementHeroLevel(),
                dbSpellHeal.getHealth(),
                dbSpellHeal.getHealthBySpellPower(),
                withParent ?
                        dbSpellHeal.getParentSpellHeal() != null ? toSpellHeal(dbSpellHeal, withChildren, withParent)
                                : null
                        : null,
                withChildren ?
                        dbSpellHeal.getChildrenSpellHeals() != null && dbSpellHeal.getChildrenSpellHeals().isEmpty() ?
                                dbSpellHeal.getChildrenSpellHeals()
                                        .stream()
                                        .map(sp -> toSpellHeal(sp, withChildren, withParent))
                                        .collect(Collectors.toList())
                                : null
                        : null);
    }
}
