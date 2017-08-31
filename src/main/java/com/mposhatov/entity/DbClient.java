package com.mposhatov.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CLIENT")
public class DbClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, nullable = true)
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

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "CLIENTS_ROLE", joinColumns = @JoinColumn(name = "CLIENT_ID", nullable = false))
    @Column(name = "ROLE")
    @Convert(converter = RoleConverter.class)
    private List<Role> roles;

    //==================================================

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UP_LEVEL", nullable = false)
    private Date lastUplevel;

    //==================================================

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CHARACTERISTICS_ID", nullable = false)
    private DbCharacteristics characteristics;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "INVENTORY_ID", nullable = false)
    private DbInventory inventory;

    //==================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTIVE_RATE_GAME_ID", nullable = true)
    private DbActiveRateGame activeRateGame;

    //==================================================

    @Column(name = "RATE", nullable = false)
    private long rate;

    protected DbClient() {
    }

    public DbClient(List<Role> roles,
                    long attack, long defence, long spellPower,
                    long knowledge, long strength,
                    long minDamage, long maxDamage,
                    long goldenCoins, long diamonds) {

        final Date now = new Date();

        this.createdAt = now;
        this.lastUplevel = now;

        this.rate = 0;

        this.roles = roles;

        //todo можно вынести(подумать)
        this.characteristics =
                new DbCharacteristics(
                        attack, defence, spellPower,
                        knowledge, strength,
                        minDamage, maxDamage,
                        2, 1);

        this.inventory = new DbInventory(this, goldenCoins, diamonds, null);
    }

    public DbClient addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public DbClient addRoles(Collection<Role> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public DbClient setActiveRateGame(DbActiveRateGame activeRateGame) {
        this.activeRateGame = activeRateGame;
        return this;
    }

    public DbClient addRate(long rate) {
        this.rate += rate;
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

    public DbInventory getInventory() {
        return inventory;
    }

    public DbActiveRateGame getActiveRateGame() {
        return activeRateGame;
    }

    public long getRate() {
        return rate;
    }
}
