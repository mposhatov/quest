package com.mposhatov.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "CLIENT")
public class DbClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, unique = true, nullable = false)
    private String name;

    @Column(name = "PASSWORD", length = 20, nullable = false)
    private String password;

    @Lob
    @Column(name = "PHOTO", nullable = true)
    private byte[] photo;

    @Column(name = "LEVEL", nullable = false)
    private long level;

    @Column(name = "EXPERIENCE", nullable = false)
    private long experience;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "CLIENTS_ROLE", joinColumns = @JoinColumn(name = "CLIENT_ID", nullable = false))
    @Column(name = "ROLE")
    @Convert(converter = RoleConverter.class)
    private List<Role> roles;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "COMPLETED_QUESTS_OF_CLIENTS",
            joinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "QUEST_ID", nullable = false)})
    private List<DbQuest> completedQuests = new ArrayList<>();

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "NOT_FREE_QUESTS_OF_CLIENTS",
            joinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "QUEST_ID", nullable = false)})
    private List<DbQuest> notFreeQuests = new ArrayList<>();

    protected DbClient() {
    }

    public DbClient(String name, String password, List<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.level = 1;
    }

    public DbClient upLevel() {
        this.level++;
        return this;
    }

    public DbClient addExperience(long experience) {
        this.experience += experience;
        return this;
    }

    public DbClient addNotFreeQuest(DbQuest quest) {
        notFreeQuests.add(quest);
        return this;
    }

    public DbClient addCompletedQuest(DbQuest quest) {
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<DbQuest> getNotFreeQuests() {
        return notFreeQuests;
    }

    public List<DbQuest> getCompletedQuests() {
        return completedQuests;
    }

    public long getLevel() {
        return level;
    }

    public long getExperience() {
        return experience;
    }
}
