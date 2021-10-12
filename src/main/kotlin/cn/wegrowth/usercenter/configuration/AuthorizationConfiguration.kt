package cn.wegrowth.usercenter.configuration

import cn.wegrowth.usercenter.auth.sms.SMSCodeTokenGranter
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.CompositeTokenGranter

@Configuration
@EnableAuthorizationServer
class AuthorizationConfiguration(
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val userDetailsService: UserDetailsService
) :
    AuthorizationServerConfigurerAdapter() {

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.allowFormAuthenticationForClients()
            .passwordEncoder(passwordEncoder)
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("permitAll()")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
            .reuseRefreshTokens(true)
            .authenticationManager(authenticationManager)
            //for refresh_token.
            .userDetailsService(userDetailsService)
        // add grant type
        val tokeGranters = arrayListOf(endpoints.tokenGranter)
        tokeGranters.add(
            SMSCodeTokenGranter(
                authenticationManager,
                endpoints.tokenServices,
                endpoints.clientDetailsService,
                endpoints.oAuth2RequestFactory
            )
        )
        endpoints.tokenGranter(CompositeTokenGranter(tokeGranters))
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient("self").secret(passwordEncoder.encode("self"))
            .authorizedGrantTypes("password", "sms_code", "refresh_token")
            .and()
            .withClient("service").secret(passwordEncoder.encode("microservice"))
            .authorizedGrantTypes("client_credentials")
    }
}