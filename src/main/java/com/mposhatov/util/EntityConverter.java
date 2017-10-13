package com.mposhatov.util;

import com.mposhatov.dto.*;
import com.mposhatov.dto.BodyPart;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class EntityConverter {

    public static Client toClient(DbClient client, boolean withHero, boolean withWarriors, boolean withCharacteristic) {
        return new Client(client.getId(), client.getLogin(), client.getEmail(),
                client.getPhoto() != null ? toBackground(client.getPhoto()) : null,
                client.getCreatedAt(), client.getRate(),
                withHero ? client.getHero() != null ? toHero(client.getHero(), withWarriors, withCharacteristic) : null : null);
    }

    public static Background toBackground(DbBackground dbBackground) {
        return new Background(dbBackground.getId(), dbBackground.getContentType());
    }

    public static Hero toHero(DbHero hero, boolean withWarriors, boolean withCharacteristic) {
        return new Hero(hero.getName(), toHeroCharacteristics(hero.getHeroCharacteristics()),
                toInventory(hero.getInventory()),
                withWarriors ?
                        hero.getWarriors() != null ?
                                hero.getWarriors().stream().map(w -> toWarrior(w, withCharacteristic)).collect(Collectors.toList())
                                : null
                        : new ArrayList<>());
    }

    public static Warrior toWarrior(DbWarrior warrior, boolean withCharacteristic) {
        final DbWarriorDescription description = warrior.getCreaturesDescription();
        return new Warrior(warrior.getId(),
                description != null ? description.getName() : null,
                description != null ? description.getPictureName() : null,
                warrior.isMain(),
                withCharacteristic ?
                        warrior.getWarriorCharacteristics() != null ?
                                toWarriorCharacteristics(warrior.getWarriorCharacteristics())
                                : null
                        : null);
    }


    public static HeroCharacteristics toHeroCharacteristics(DbHeroCharacteristics heroCharacteristics) {
        return new HeroCharacteristics(heroCharacteristics.getAttack(),
                heroCharacteristics.getPhysicalDefense(), heroCharacteristics.getMagicDefense(),
                heroCharacteristics.getSpellPower(), heroCharacteristics.getMana());
    }

    public static WarriorCharacteristics toWarriorCharacteristics(DbWarriorCharacteristics characteristics) {
        return new WarriorCharacteristics(characteristics.getHealth(),
                characteristics.getMana(), characteristics.getSpellPower(),
                characteristics.getAttack(), characteristics.getAttackType(), characteristics.getAdditionalDamagePercent(),
                characteristics.getPhysicalDefense(), characteristics.getMagicDefense(),
                characteristics.getMinDamage(), characteristics.getMaxDamage(),
                characteristics.getVelocity(), characteristics.getProbableOfEvasion(),
                characteristics.getPhysicalBlockPercent(), characteristics.getMagicalBlockPercent(),
                characteristics.getVampirism(),
                characteristics.getCriticalDamageChange(), characteristics.getCriticalDamageMultiplier(),
                characteristics.getChangeOfStun());
    }

    public static Inventory toInventory(DbInventory inventory) {
        return new Inventory(inventory.getSubjects().stream().map(EntityConverter::toSubject).collect(Collectors.toList()));
    }

    public static Subject toSubject(DbSubject subject) {
        final DbSubjectDescription subjectDescription = subject.getSubjectDescription();
        return new Subject(subject.getId(), subjectDescription.getName(), subjectDescription.getDescription(),
                subjectDescription.getPictureName(), toBodyPart(subjectDescription.getBodyPart()), subject.isMain());
    }

    public static BodyPart toBodyPart(com.mposhatov.entity.BodyPart bodyPart) {
        return new BodyPart(bodyPart.name(), bodyPart.getTitle());
    }

}
