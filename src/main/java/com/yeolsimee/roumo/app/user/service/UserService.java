package com.yeolsimee.roumo.app.user.service;

import com.google.firebase.auth.*;
import com.yeolsimee.roumo.app.category.repository.*;
import com.yeolsimee.roumo.app.common.exception.*;
import com.yeolsimee.roumo.app.common.response.*;
import com.yeolsimee.roumo.app.routine.entity.*;
import com.yeolsimee.roumo.app.routine.repository.*;
import com.yeolsimee.roumo.app.routinehistory.repository.*;
import com.yeolsimee.roumo.app.user.dto.*;
import com.yeolsimee.roumo.app.user.entity.*;
import com.yeolsimee.roumo.app.user.entity.User;
import com.yeolsimee.roumo.app.user.repository.*;
import lombok.*;
import org.apache.commons.lang3.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * packageName    : com.yeolsimee.roumo.app.user.service
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
    private final RoutineRepository routineRepository;
    private final RoutineHistoryRepository routineHistoryRepository;
    private final CategoryRepository categoryRepository;

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

    public Authentication getAuthentication(String uid){

        User user = getUserByUid(uid);
        if(Objects.isNull(user)){
            throw new EntityNotFoundException(ResponseMessage.AUTH_USER);
        }
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
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

    @Transactional
    public void withdraw(User user) {
        List<Routine> routineList = routineRepository.findByUserId(user.getId());
        routineList.forEach(routine -> routineHistoryRepository.deleteByRoutineId(routine.getId()));
        routineRepository.deleteByUser(user);
        categoryRepository.deleteByUser(user);
        userRepository.delete(user);
    }

    public void recovery(String jwt) throws FirebaseAuthException {

        FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(jwt);
        String uid = firebaseToken.getUid();
        User user = getUserByUid(uid);
        userRepository.save(user);

        List<Routine> routineList = routineRepository.findByUserId(user.getId());
        routineList.forEach(routine -> routine.changeRoutineDeleteYN("N"));
        routineRepository.saveAll(routineList);

    }
}
