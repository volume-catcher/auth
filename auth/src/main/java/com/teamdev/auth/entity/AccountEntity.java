package com.teamdev.auth.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "account")
public class AccountEntity {

    @Id
    @Column(name = "account_id", columnDefinition ="VARCHAR(20)", updatable = false)
    @Size(max = 20)
    @Comment("계정ID")
    private String id;

    @Column(name = "password", nullable = false)
    @Size(max = 60)
    @Comment("비밀번호")
    private String password;

    @ManyToMany
    @JoinTable(
            name="account_role",
            joinColumns={@JoinColumn(name = "account_id")},
            inverseJoinColumns={@JoinColumn(name="role_id")})
    private Set<RoleEntity> roles;

    @Builder
    public AccountEntity(String id, String password, Set<RoleEntity> roles) {
        this.id = id;
        this.password = password;
        this.roles = roles;
    }
}
