package cn.wegrowth.usercenter.security.config

import cn.wegrowth.usercenter.security.auth.sms.SMSCodeTokenGranter
import cn.wegrowth.usercenter.security.auth.wechat.WechatTokenGranter
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
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
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

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val jwtAccessTokenConverter = JwtAccessTokenConverter()
        jwtAccessTokenConverter.setSigningKey("cn.wegrowth")
        return jwtAccessTokenConverter
    }

    @Bean
    fun userTokenEnhance(): TokenEnhancer {
        return UserTokenEnhancer()
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        //add token enhancer
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(arrayListOf(userTokenEnhance(), jwtAccessTokenConverter()))
        endpoints.tokenEnhancer(tokenEnhancerChain)

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
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient("self").secret(passwordEncoder.encode("self"))
            .authorizedGrantTypes("password", "sms_code", "refresh_token", "wechat")
            .and()
            .withClient("service").secret(passwordEncoder.encode("microservice"))
            .scopes("scope_system")
            .authorizedGrantTypes("client_credentials")
    }
}