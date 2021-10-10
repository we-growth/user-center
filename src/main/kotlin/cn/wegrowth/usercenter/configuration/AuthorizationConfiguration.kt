package cn.wegrowth.usercenter.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer

@EnableAuthorizationServer
@Configuration
class AuthorizationConfiguration(
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: BCryptPasswordEncoder
//    private val usernamePasswordAuthenticationProvider: UsernamePasswordAuthenticationProvider
) :
    AuthorizationServerConfigurerAdapter() {


    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.allowFormAuthenticationForClients()
            .passwordEncoder(passwordEncoder)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(authenticationManager)
//            .reuseRefreshTokens(true)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient("self").secret(passwordEncoder.encode("self"))
    }
}