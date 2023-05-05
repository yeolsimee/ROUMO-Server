package com.yeolsimee.moneysaving.app.user.dto;

import com.yeolsimee.moneysaving.app.user.entity.User;
import lombok.*;

@Data
@Builder
public class UserInfoResponse {

    private String name;

    private String username;

    private String nickname;

    private String gender;

    private String phoneNumber;

    private String birthday;

    private String isNewUser;

    public static UserInfoResponse of(User user) {
        return UserInfoResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .isNewUser(user.getIsNewUser())
                .build();
    }
}
