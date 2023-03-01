package com.yeolsimee.moneysaving.app.user.repository;

import com.yeolsimee.moneysaving.app.user.entity.*;
import org.springframework.data.jpa.repository.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.user.repository
 * fileName       : RoleRepository
 * author         : jeon-eunseong
 * date           : 2023/03/01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/01        jeon-eunseong       최초 생성
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
