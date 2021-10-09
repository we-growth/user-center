package cn.wegrowth.usercenter.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer

@EnableAuthorizationServer
//@Configuration
class AuthorizationConfiguration : AuthorizationServerConfigurerAdapter() {

    @Bean
    fun passwordEncode(): PasswordEncoder = BCryptPasswordEncoder()

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
       security.passwordEncoder(passwordEncode())
    }
}