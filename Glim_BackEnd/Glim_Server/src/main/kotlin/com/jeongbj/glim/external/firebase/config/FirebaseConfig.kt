package com.jeongbj.glim.external.firebase.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
class FirebaseConfig(
    @Value("\${firebase.credential-path}")
    private val credentialPath: String
) {

    @Bean
    fun firebaseApp(): FirebaseApp {
        val options = FirebaseOptions.builder()
            .setCredentials(
                GoogleCredentials.fromStream(FileInputStream(credentialPath))
            )
            .build()

        return FirebaseApp.initializeApp(options)
    }
}