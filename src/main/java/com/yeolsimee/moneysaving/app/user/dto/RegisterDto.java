package com.yeolsimee.moneysaving.app.user.dto;

import com.yeolsimee.moneysaving.app.user.entity.*;
import lombok.*;

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

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private String birthday;

    private String address;

    public static User toEntity(RegisterDto registerDto) {

        return User.builder()
                .username(registerDto.getUsername())
                .name(registerDto.getName())
                .password(registerDto.getPassword())
                .email(registerDto.getEmail())
                .phoneNumber(registerDto.getPhoneNumber())
                .birthday(registerDto.getBirthday())
                .address(registerDto.getAddress())
                .role(Role.ROLE_USER)
                .build();
    }
}
