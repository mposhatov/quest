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

    @Column(name = "LOGIN", length = 20, nullable = true)
    private String login;

    @Column(name = "PASSWORD", length = 20, nullable = true)
    private String password;

    @Column(name = "EMAIL", length = 50, nullable = true)
    private String email;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BACKGROUND_ID", nullable = true)
    private DbBackground photo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "CLIENTS_ROLE", joinColumns = @JoinColumn(name = "CLIENT_ID", nullable = false))
    @Column(name = "ROLE")
    @Convert(converter = RoleConverter.class)
    private List<Role> roles;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UP_LEVEL", nullable = false)
    private Date lastUplevel;

    @Column(name = "RATING", nullable = false)
    private long rating;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "HERO_ID", nullable = false)
    private DbHero hero;

    protected DbClient() {
    }

    public DbClient(List<Role> roles, long id) {
        final Date now = new Date();

        this.createdAt = now;
        this.lastUplevel = now;

        this.roles = roles;

        this.hero = new DbHero(id);
    }

    public DbClient addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public DbClient addRoles(Collection<Role> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public DbClient addRating(long rating) {
        this.rating += rating;
        return this;
    }

    public DbClient changePhoto(byte[] content, String contentType) {
        this.photo.changeBackground(content, contentType);
        return this;
    }

    public Long getId() {
        return id;
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

    public List<Role> getRoles() {
        return roles;
    }

    public DbHero getHero() {
        return hero;
    }

    public long getRating() {
        return rating;
    }

    public String getLogin() {
        return login;
    }
}