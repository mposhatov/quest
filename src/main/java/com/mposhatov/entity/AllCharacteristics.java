package com.mposhatov.entity;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
public class AllCharacteristics {

    @Column(name = "ATTACK", nullable = false)
    protected long attack;

    @Column(name = "PHYSICAL_DEFENSE", nullable = false)
    protected long physicalDefense;

    @Column(name = "MAGIC_DEFENSE", nullable = false)
    protected long magicDefense;

    @Column(name = "SPELL_POWER", nullable = false)
    protected long spellPower;

    @Column(name = "KNOWLEDGE", nullable = false)
    protected long knowledge;

    @Column(name = "STRENGTH", nullable = false)
    protected long strength;

    @Column(name = "HEALTH", nullable = false)
    protected long health;

    @Column(name = "MANA", nullable = false)
    protected long mana;

    @Column(name = "MIN_DAMAGE", nullable = false)
    protected long minDamage;

    @Column(name = "MAX_DAMAGE", nullable = false)
    protected long maxDamage;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CLIENTS_CRITICAL_DAMAGE",
            joinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "CRITICAL_DAMAGE_ID", nullable = false)})
    private List<DbCriticalDamage> criticalDamages;



}
