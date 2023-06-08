package com.yeolsimee.roumo.app.user.repository;

import com.yeolsimee.roumo.app.user.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

/**
 * packageName    : com.yeolsimee.roumo.app.user.repository
 * fileName       : UserRepository
 * author         : jeon-eunseong
 * date           : 2023/03/01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/01        jeon-eunseong       최초 생성
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String uid);
}
