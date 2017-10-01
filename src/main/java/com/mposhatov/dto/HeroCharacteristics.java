package com.mposhatov.dto;

public class HeroCharacteristics extends MailCharacteristics {


    public HeroCharacteristics() {
    }

    public HeroCharacteristics(long mana, long spellPower, long attack, long physicalDefense, long magicDefense) {
        super(mana, spellPower, attack, physicalDefense, magicDefense);
    }
}
