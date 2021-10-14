package cn.wegrowth.usercenter.configuration

import cn.wegrowth.usercenter.auth.sms.SMSCodeTokenGranter
import cn.wegrowth.usercenter.auth.wechat.WechatTokenGranter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.CompositeTokenGranter
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore


@Configuration
@EnableAuthorizationServer
class AuthorizationConfiguration(
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val userDetailsService: UserDetailsService,
    private val redisConnectionFactory: RedisConnectionFactory
) :
    AuthorizationServerConfigurerAdapter() {

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.allowFormAuthenticationForClients()
            .passwordEncoder(passwordEncoder)
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("permitAll()")
    }

    @Bean
    fun tokenStore(): TokenStore {
        return RedisTokenStore(redisConnectionFactory).run {
            setPrefix("auth-token:")
//            setSerializationStrategy(JacksonRedisTokenStoreSerializationStrategy())
            this
        }
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore())
            .reuseRefreshTokens(true)
            .authenticationManager(authenticationManager)
            //for refresh_token.
            .userDetailsService(userDetailsService)
        // add grant type
        val tokeGranters = arrayListOf(endpoints.tokenGranter)
            .run {
                add(
                    SMSCodeTokenGranter(
                        authenticationManager,
                        endpoints.tokenServices,
                        endpoints.clientDetailsService,
                        endpoints.oAuth2RequestFactory
                    )
                )
                add(
                    WechatTokenGranter(
                        authenticationManager,
                        endpoints.tokenServices,
                        endpoints.clientDetailsService,
                        endpoints.oAuth2RequestFactory
                    )
                )
                this
            }
        endpoints.tokenGranter(CompositeTokenGranter(tokeGranters))
        // token convert
        val converter = DefaultAccessTokenConverter()
        val userAuthenticationConverter = DefaultUserAuthenticationConverter()
        userAuthenticationConverter.setUserDetailsService(userDetailsService)
        converter.setUserTokenConverter(userAuthenticationConverter)
        endpoints.accessTokenConverter(converter)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient("self").secret(passwordEncoder.encode("self"))
            .authorizedGrantTypes("password", "sms_code", "refresh_token","wechat")
            .and()
            .withClient("service").secret(passwordEncoder.encode("microservice"))
            .authorizedGrantTypes("client_credentials")
    }
}