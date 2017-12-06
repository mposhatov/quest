package com.mposhatov.dto;

public class Effect {

    private Long id;
    private String name;
    private String description;
    private String pictureName;
    private QuantifiableWarriorCharacteristics characteristics;
    private Integer leftSteps;

    public Effect(Long id, String name, String description, String pictureName, QuantifiableWarriorCharacteristics characteristics, Integer leftSteps) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.characteristics = characteristics;
        this.leftSteps = leftSteps;
    }

    public Effect stepUp() {
        this.leftSteps--;
        return this;
    }

    public boolean isExpired() {
        return this.leftSteps <= 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureName() {
        return pictureName;
    }

    public QuantifiableWarriorCharacteristics getCharacteristics() {
        return characteristics;
    }

    public Integer getLeftSteps() {
        return leftSteps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Effect effect = (Effect) o;

        if (id != null ? !id.equals(effect.id) : effect.id != null) return false;
        return name != null ? name.equals(effect.name) : effect.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
