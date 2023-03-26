package com.yeolsimee.moneysaving.utils;


import com.yeolsimee.moneysaving.app.user.entity.Role;
import com.yeolsimee.moneysaving.app.user.entity.User;
import com.yeolsimee.moneysaving.app.user.repository.UserRepository;
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
        userRepository.save(new User("김아무개1", "아무개1", "kim1@gmail.com", Role.ROLE_USER,"uid1"));
        userRepository.save(new User("김아무개2", "아무개2", "kim2@gmail.com", Role.ROLE_USER,"uid2"));
        userRepository.save(new User("김아무개3", "아무개3", "kim3@gmail.com", Role.ROLE_USER,"uid3"));
        userRepository.save(new User("김아무개4", "아무개4", "kim4@gmail.com", Role.ROLE_USER,"uid4"));
        userRepository.save(new User("김아무개5", "아무개5", "kim5@gmail.com", Role.ROLE_USER,"uid5"));
        userRepository.save(new User("김아무개6", "아무개6", "kim6@gmail.com", Role.ROLE_USER,"uid6"));
    }
}