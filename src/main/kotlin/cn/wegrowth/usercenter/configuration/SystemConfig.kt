package cn.wegrowth.usercenter.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SystemConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = Argon2PasswordEncoder()
}