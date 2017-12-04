package com.mposhatov.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "SPELL_PASSIVE_CHARACTERISTICS")
public class DbSpellPassiveCharacteristics extends QuantifiableWarriorCharacteristics {

    @Id
    @GeneratedValue(generator = "spell_passive")
    @GenericGenerator(name = "spell_passive", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "spell_passive")})
    @Column(name = "SPELL_PASSIVE_ID")
    private Long spellPassiveId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPELL_PASSIVE_ID", nullable = false)
    private DbSpellPassive spellPassive;

    protected DbSpellPassiveCharacteristics() {
    }

    public DbSpellPassiveCharacteristics(DbSpellPassive spellPassive, int attack, int physicalDefense, int magicDefense, int health, int velocity, int activatedDefensePercent) {
        super(attack, physicalDefense, magicDefense, health, velocity, activatedDefensePercent);
        this.spellPassive = spellPassive;
    }

    public Long getSpellPassiveId() {
        return spellPassiveId;
    }

    public DbSpellPassive getSpellPassive() {
        return spellPassive;
    }
}
