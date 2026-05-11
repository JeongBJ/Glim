package com.jeongbj.glim.config

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableEncryptableProperties
@Configuration
class JasyptConfig {

    @Value($$"${jasypt.encryptor.password}")
    private lateinit var encryptKey: String

    @Bean("jasyptStringEncryptor")
    fun stringEncryptor(): StringEncryptor {
        val config = SimpleStringPBEConfig().apply {
            password = encryptKey
            algorithm = "PBEWITHHMACSHA512ANDAES_256"
            keyObtentionIterations = 10000
            poolSize = 2
            setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
            setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator")
            stringOutputType = "base64"
        }

        return StandardPBEStringEncryptor().apply {
            setConfig(config)
        }
    }
}