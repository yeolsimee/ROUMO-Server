package com.yeolsimee.moneysaving.app.user.service;

import com.google.firebase.auth.*;
import com.yeolsimee.moneysaving.app.common.exception.*;
import com.yeolsimee.moneysaving.app.common.response.*;
import com.yeolsimee.moneysaving.app.user.dto.*;
import com.yeolsimee.moneysaving.app.user.entity.User;
import com.yeolsimee.moneysaving.app.user.repository.*;
import lombok.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

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

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User signup(FirebaseToken firebaseToken) {

        User user = UserInfoRequest.toEntity(UserInfoRequest.builder()
                .username(firebaseToken.getEmail())
                .name(firebaseToken.getName())
                .uid(firebaseToken.getUid())
                .build());
        return userRepository.save(user);
    }

    public User getUserByUid(String uid){
        return userRepository.findByUid(uid).orElse(null);
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.AUTH_USER));
    }
}
