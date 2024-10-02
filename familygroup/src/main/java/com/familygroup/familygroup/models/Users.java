package com.familygroup.familygroup.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "seq_users", sequenceName = "seq_user", initialValue = 1, allocationSize = 1)
public class Users {

    @Id
    @GeneratedValue(generator = "seq_users", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank @NotNull
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank @NotNull
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"},
    name = "unique_role_user"),

    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "users", unique = false,
    foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT)),

    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "role", unique = false,
    foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT))
    
    )
    List<Role> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_group",
    joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    Set<Group> groups = new HashSet<>();


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    
    
}
