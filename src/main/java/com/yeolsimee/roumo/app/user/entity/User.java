package com.yeolsimee.roumo.app.user.entity;

import com.yeolsimee.roumo.app.category.entity.*;
import com.yeolsimee.roumo.app.common.entity.BaseEntity;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import javax.persistence.*;
import java.util.*;

/**
 * packageName    : com.yeolsimee.roumo.app.user
 * fileName       : User
 * author         : jeon-eunseong
 * date           : 2023/03/01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/01        jeon-eunseong       최초 생성
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Users")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
public class User extends BaseEntity implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column
    private String name;

    @Column(nullable = false)
    private String username;

    @Column
    private String nickname;

    @Column
    private String gender;

    @Column
    private String birthday;

    @Column
    private String phoneNumber;

    @Column
    private String isNewUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany
    private List<Category> categoryList;

    public User(String name, String username, Role role) {
        this.name = name;
        this.username = username;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getValue()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void changeIsNewUser(String isNewUser) {
        this.isNewUser = isNewUser;
    }

}
