package com.yeolsimee.moneysaving.app.user.dto;

import com.yeolsimee.moneysaving.app.user.entity.*;
import lombok.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.user.dto
 * fileName       : UserInfoRequest
 * author         : jeon-eunseong
 * date           : 2023/03/01`
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/01        jeon-eunseong       최초 생성
 */

@Data
@Builder
public class UserInfoRequest {

    private String name;

    private String username;

    private String nickname;

    private String phoneNumber;

    private String birthday;

    private String gender;

    private String uid;

    public static User toEntity(UserInfoRequest userInfoRequest) {

        return User.builder()
                .username(userInfoRequest.getUsername())
                .name(userInfoRequest.getName())
                .role(Role.ROLE_USER)
                .uid(userInfoRequest.getUid())
                .withdrawYn("N")
                .build();
    }

    public static User updateUserInfo(UserInfoRequest userInfoRequest, User user){

        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .nickname(userInfoRequest.getNickname())
                .gender(user.getGender())
                .birthday(userInfoRequest.getBirthday())
                .phoneNumber(userInfoRequest.getPhoneNumber())
                .role(Role.ROLE_USER)
                .uid(userInfoRequest.getUid())
                .build();



    }
}
