package com.yeolsimee.moneysaving.app.user.dto;

import com.yeolsimee.moneysaving.app.user.entity.*;
import lombok.*;

import javax.validation.constraints.*;
import java.util.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.user.dto
 * fileName       : RegisterDto
 * author         : jeon-eunseong
 * date           : 2023/03/01`
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/01        jeon-eunseong       최초 생성
 */

@Data
public class RegisterDto {

    private String name;

    @NotNull
    private String username;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String birthday;

    @NotNull
    private String address;

    public static User toEntity(RegisterDto registerDto) {
        Role role = new Role(Role.USER);
        return User.builder()
                .username(registerDto.getUsername())
                .name(registerDto.getName())
                .password(registerDto.getPassword())
                .email(registerDto.getEmail())
                .phoneNumber(registerDto.getPhoneNumber())
                .birthday(registerDto.getBirthday())
                .address(registerDto.getAddress())
                .roles(Collections.singleton(role))
                .build();
    }
}
