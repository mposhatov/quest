package com.mposhatov.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "CLIENT")
public class DbClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, unique = true, nullable = true)
    private String name;

    @Column(name = "PASSWORD", length = 20, nullable = true)
    private String password;

    @Column(name = "email", length = 50, nullable = true)
    private String email;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BACKGROUND_ID", nullable = true)
    private DbBackground photo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UP_LEVEL", nullable = false)
    private Date lastUplevel;

    @Column(name = "GOLDEN_COINS", nullable = false)
    private long goldenCoins;

    @Column(name = "DIAMONDS", nullable = false)
    private long diamonds;

    @Column(name = "AVAILABLE_CHARACTERISTICS", nullable = false)
    private long availableCharacteristics;

    @Column(name = "AVAILABLE_SKILLS", nullable = false)
    private long availableSkills;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CHARACTERISTICS_ID", nullable = true)
    private DbCharacteristics characteristics;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "CLIENTS_ROLE", joinColumns = @JoinColumn(name = "CLIENT_ID", nullable = false))
    @Column(name = "ROLE")
    @Convert(converter = RoleConverter.class)
    private List<Role> roles;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "COMPLETED_SIMPLE_GAMES_OF_CLIENT",
            joinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SIMPLE_GAME_ID", nullable = false)})
    private List<SimpleGame> completedQuests = new ArrayList<>();

    protected DbClient() {
    }

    public DbClient(List<Role> roles,
                    long attack, long defence, long spellPower,
                    long knowledge, long manaByKnowledge,
                    long strength, long healthByStrength,
                    long minDamage, long maxDamage,
                    long goldenCoins, long diamonds,
                    long availableCharacteristics, long availableSkills) {

        final Date now = new Date();

        this.createdAt = now;
        this.lastUplevel = now;

        this.roles = roles;

        this.goldenCoins = goldenCoins;
        this.diamonds = diamonds;

        this.availableCharacteristics = availableCharacteristics;
        this.availableSkills = availableSkills;

        this.characteristics =
                new DbCharacteristics(
                        attack, defence, spellPower,
                        knowledge, manaByKnowledge,
                        strength, healthByStrength,
                        minDamage, maxDamage);
    }

    public DbClient addCompletedQuest(SimpleGame quest) {
        completedQuests.add(quest);
        return this;
    }

    public DbClient addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public DbClient addRoles(Collection<Role> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public DbClient changePhoto(byte[] content, String contentType) {
        this.photo.changeBackground(content, contentType);
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public DbBackground getPhoto() {
        return photo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getLastUplevel() {
        return lastUplevel;
    }

    public DbCharacteristics getCharacteristics() {
        return characteristics;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<SimpleGame> getCompletedQuests() {
        return completedQuests;
    }

    public long getGoldenCoins() {
        return goldenCoins;
    }

    public long getDiamonds() {
        return diamonds;
    }

    public long getAvailableCharacteristics() {
        return availableCharacteristics;
    }

    public long getAvailableSkills() {
        return availableSkills;
    }
}
