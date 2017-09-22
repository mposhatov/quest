package com.mposhatov.util;

import com.mposhatov.dto.*;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EntityConverter {

    public static Client toClient(DbClient client) {
        return new Client(client.getId(), client.getPhoto() != null ? toBackground(client.getPhoto()) : null,
                toHero(client.getHero()));
    }

    public static Background toBackground(DbBackground dbBackground) {
        return new Background(dbBackground.getId(), dbBackground.getContentType());
    }

    public static Hero toHero(DbHero hero) {
        return new Hero(toHeroCharacteristics(hero.getCharacteristics()), toInventory(hero.getInventory()),
                hero.getWarriors().stream().map(EntityConverter::toWarrior).collect(Collectors.toList()));
    }

    public static Warrior toWarrior(DbWarrior warrior) {
        final DbWarriorDescription description = warrior.getCreaturesDescription();
        return new Warrior(description.getId(), description.getName(),
                description.getPictureName(), toWarriorCharacteristics(warrior.getCharacteristics()));
    }


    public static HeroCharacteristics toHeroCharacteristics(DbHeroCharacteristics heroCharacteristics) {
        return new HeroCharacteristics(heroCharacteristics.getAttack(),
                heroCharacteristics.getPhysicalDefense(), heroCharacteristics.getMagicDefense(),
                heroCharacteristics.getSpellPower(), heroCharacteristics.getMana());
    }

    public static WarriorCharacteristics toWarriorCharacteristics(DbWarriorCharacteristics characteristics) {
        return new WarriorCharacteristics(characteristics.getHealth(),
                characteristics.getMana(), characteristics.getSpellPower(),
                characteristics.getAttack(), characteristics.getPhysicalDefense(), characteristics.getMagicDefense(),
                characteristics.getMinDamage(), characteristics.getMaxDamage(),
                characteristics.getProbableOfEvasion(), characteristics.getBlockPercent(), characteristics.getProbableOfEvasion(),
                characteristics.getAdditionalDamagePercent(), characteristics.getVampirism(),
                characteristics.getChangeOfDoubleDamage(), characteristics.getChangeOfStun());
    }

    public static Inventory toInventory(DbInventory inventory) {
        return new Inventory(inventory.getSubjects().stream().map(EntityConverter::toSubject).collect(Collectors.toList()));
    }

    public static Subject toSubject(DbSubject subject) {
        return new Subject(subject.getId(), subject.getName(), subject.getDescription(), subject.getPictureName());
    }

}
