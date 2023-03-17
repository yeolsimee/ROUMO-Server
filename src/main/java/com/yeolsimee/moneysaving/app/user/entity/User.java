package com.yeolsimee.moneysaving.app.user.entity;

import com.yeolsimee.moneysaving.app.common.entity.BaseEntity;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import javax.persistence.*;
import java.util.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.user
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
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User extends BaseEntity implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private String address;

    public User(String name, String username, String email, String password, Role role, String phoneNumber, String birthday, String address) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.address = address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getValue()));
        return authorities;
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
}
