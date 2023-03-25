package com.yeolsimee.moneysaving.config;

import com.google.auth.oauth2.*;
import com.google.firebase.*;
import com.google.firebase.auth.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.io.*;

/**
 * packageName    : com.yeolsimee.moneysaving.config
 * fileName       : FirebaseConfig
 * author         : jeon-eunseong
 * date           : 2023/03/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/04        jeon-eunseong       최초 생성
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {

    @Value("${app.firebase-configuration-file}")
    private String firebaseSdkPath;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        System.out.println(firebaseSdkPath);
        log.info("Initializing Firebase.");
        FileInputStream serviceAccount =
                new FileInputStream(firebaseSdkPath);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        log.info("FirebaseApp initialized" + app.getName());
        return app;
    }

    @Bean
    public FirebaseAuth getFirebaseAuth() throws IOException {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp());
        return firebaseAuth;
    }
}
