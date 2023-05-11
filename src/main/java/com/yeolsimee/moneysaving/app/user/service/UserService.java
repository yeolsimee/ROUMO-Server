package com.yeolsimee.moneysaving.app.user.service;

import com.google.firebase.auth.*;
import com.yeolsimee.moneysaving.app.common.exception.*;
import com.yeolsimee.moneysaving.app.common.response.*;
import com.yeolsimee.moneysaving.app.user.dto.*;
import com.yeolsimee.moneysaving.app.user.entity.*;
import com.yeolsimee.moneysaving.app.user.entity.User;
import com.yeolsimee.moneysaving.app.user.repository.*;
import lombok.*;
import org.apache.commons.lang3.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.user.service
 * fileName       : UserService
 * author         : jeon-eunseong
 * date           : 2023/03/01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/01        jeon-eunseong       최초 생성
 */
@Service("userService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final FirebaseAuth firebaseAuth;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User signUp(String uid) throws FirebaseAuthException {

        UserRecord userRecord = firebaseAuth.getUser(uid);

        User user = User.builder()
                .username(userRecord.getUid())
                .name(Optional.ofNullable(userRecord.getDisplayName()).orElseGet(() -> RandomStringUtils.random(10, true, false)))
                .isNewUser("Y")
                .role(Role.ROLE_USER)
                .build();

        return userRepository.save(user);
    }

    public User getUserByUid(String uid){
        return userRepository.findByUsername(uid).orElse(null);
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.AUTH_USER));
    }

    public void updateUserInfo(UserInfoRequest userInfoRequest, User user) {
        user = UserInfoRequest.updateUserInfo(userInfoRequest, user);
        userRepository.save(user);
    }

    @Transactional
    public UserInfoResponse updateIsNewUser(User user, UserInfoRequest userInfoRequest) {
        user.changeIsNewUser(userInfoRequest.getIsNewUser());
        userRepository.save(user);
        return UserInfoResponse.of(user);
    }
}
