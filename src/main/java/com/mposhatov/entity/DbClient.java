package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLE", joinColumns = @JoinColumn(name = "CLIENT_ID", nullable = false))
    @Column(name = "ROLE")
    @Convert(converter = RoleConverter.class)
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "NOT_FREE_QUESTS_OF_CLIENTS",
            joinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "QUEST_ID", nullable = false)})
    private List<DbQuest> notFreeQuests = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "COMPLETED_QUESTS_OF_CLIENTS",
            joinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "QUEST_ID", nullable = false)})
    private List<DbQuest> completedQuests = new ArrayList<>();

    protected DbClient() {
    }

    public DbClient(String name, String password, List<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public DbClient addNotFreeQuest(DbQuest quest) {
        notFreeQuests.add(quest);
        return this;
    }

    public DbClient addCompletedQuest(DbQuest quest) {
        completedQuests.add(quest);
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
}
