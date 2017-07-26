package com.mposhatov.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "REGISTERED_CLIENT")
public class DbRegisteredClient extends Client {

    @Column(name = "NAME", length = 20, unique = true, nullable = false)
    private String name;

    @Column(name = "PASSWORD", length = 20, nullable = false)
    private String password;

    @Cascade(CascadeType.PERSIST)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PHOTO_ID", nullable = true)
    private DbPhoto photo;

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

    protected DbRegisteredClient() {
    }

    public DbRegisteredClient(String name, String password, List<Role> roles) {
        super();
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.level = 1;
        this.experience = 0;
    }

    public DbRegisteredClient upLevel() {
        this.level++;
        return this;
    }

    public DbRegisteredClient addExperience(long experience) {
        this.experience += experience;
        return this;
    }

    public DbRegisteredClient addNotFreeQuest(DbQuest quest) {
        notFreeQuests.add(quest);
        return this;
    }

    public DbRegisteredClient addCompletedQuest(DbQuest quest) {
        completedQuests.add(quest);
        return this;
    }

    public DbRegisteredClient addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public DbRegisteredClient addRoles(Collection<Role> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public DbRegisteredClient changePhoto(DbPhoto photo) {
        if (this.photo == null) {
            this.photo = photo;
        } else {
            this.photo.update(photo.getContent(), photo.getContentType());
        }

        return this;
    }

    public boolean isGuest() {
        return this.roles.contains(Role.ROLE_ANONYMOUS);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public DbPhoto getPhoto() {
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
