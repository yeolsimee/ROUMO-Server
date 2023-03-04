package com.yeolsimee.moneysaving.app.user.validator;

import com.yeolsimee.moneysaving.app.user.dto.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.user.validator
 * fileName       : UserValidator
 * author         : jeon-eunseong
 * date           : 2023/03/01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/01        jeon-eunseong       최초 생성
 */

@Component
public class RegisterValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterDto registerDto = (RegisterDto)target;
    }
}
