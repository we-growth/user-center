package cn.wegrowth.usercenter.security.config

import cn.wegrowth.usercenter.domain.service.JpaPlatformUserDetailManager
import cn.wegrowth.usercenter.security.auth.sms.SmsCodeAuthenticationProvider
import cn.wegrowth.usercenter.security.auth.wechat.WechatAuthenticationProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(private val userDetailsService: JpaPlatformUserDetailManager) : WebSecurityConfigurerAdapter() {
    @Value("\${springdoc.api-docs.path}")
    private lateinit var restApiDocPath: String

    @Value("\${springdoc.swagger-ui.path}")
    private lateinit var swaggerPath: String

    @Bean
    fun passwordEncoder(): PasswordEncoder = Argon2PasswordEncoder()

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    override fun init(web: WebSecurity) {
        web.ignoring()
            .antMatchers("%s/**".format(restApiDocPath))
            .antMatchers("%s/**".format(swaggerPath))
            .antMatchers("%s/**".format("/actuator"))
    }

    override fun configure(http: HttpSecurity) {

        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()
    }

    // add AuthenticationProvider
    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        return DaoAuthenticationProvider().run {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder())
            this
        }
    }

    @Bean
    fun smsCodeAuthenticationProvider(): SmsCodeAuthenticationProvider {
        return SmsCodeAuthenticationProvider(userDetailsService)
    }

    @Bean
    fun wechatAuthenticationProvider(): WechatAuthenticationProvider {
        return WechatAuthenticationProvider(userDetailsService)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.let {
            it.authenticationProvider(smsCodeAuthenticationProvider())
            it.authenticationProvider(daoAuthenticationProvider())
            it.authenticationProvider(wechatAuthenticationProvider())
        }
    }


}