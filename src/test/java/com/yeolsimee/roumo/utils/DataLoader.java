package com.yeolsimee.roumo.utils;


import com.yeolsimee.roumo.app.user.entity.Role;
import com.yeolsimee.roumo.app.user.entity.User;
import com.yeolsimee.roumo.app.user.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Component
public class DataLoader {
    private UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void loadData() {
        userRepository.save(new User("김아무개1", "a1", Role.ROLE_USER));
        userRepository.save(new User("김아무개2", "a2", Role.ROLE_USER));
        userRepository.save(new User("김아무개3", "a3", Role.ROLE_USER));
        userRepository.save(new User("김아무개4", "a4", Role.ROLE_USER));
        userRepository.save(new User("김아무개5", "a5", Role.ROLE_USER));
        userRepository.save(new User("김아무개6", "a6", Role.ROLE_USER));
    }
}