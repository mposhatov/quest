package com.mposhatov.dto;

public class WarriorUpgrade {
    private Warrior warriorBeforeUpgrade;
    private Warrior warriorAfterUpgrade;

    public WarriorUpgrade() {
    }

    public WarriorUpgrade(Warrior warriorBeforeUpgrade, Warrior warriorAfterUpgrade) {
        this.warriorBeforeUpgrade = warriorBeforeUpgrade;
        this.warriorAfterUpgrade = warriorAfterUpgrade;
    }

    public WarriorUpgrade warriorBeforeUpgrade(Warrior warriorBeforeUpgrade) {
        this.warriorBeforeUpgrade = warriorBeforeUpgrade;
        return this;
    }

    public WarriorUpgrade warriorAfterUpgrade(Warrior warriorAfterUpgrade) {
        this.warriorAfterUpgrade = warriorAfterUpgrade;
        return this;
    }

    public Warrior getWarriorBeforeUpgrade() {
        return warriorBeforeUpgrade;
    }

    public void setWarriorBeforeUpgrade(Warrior warriorBeforeUpgrade) {
        this.warriorBeforeUpgrade = warriorBeforeUpgrade;
    }

    public Warrior getWarriorAfterUpgrade() {
        return warriorAfterUpgrade;
    }

    public void setWarriorAfterUpgrade(Warrior warriorAfterUpgrade) {
        this.warriorAfterUpgrade = warriorAfterUpgrade;
    }
}
