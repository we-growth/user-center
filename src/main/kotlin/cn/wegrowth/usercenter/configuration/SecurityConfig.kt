package cn.wegrowth.usercenter.configuration

import cn.wegrowth.usercenter.auth.sms.SmsCodeAuthenticationProvider
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
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(private val userDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {
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
    fun daoAuthenticationProvier(): DaoAuthenticationProvider {
        return DaoAuthenticationProvider().run {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder())
            this
        }
    }

    @Bean
    fun smsCodeAuthenticationProvier(): SmsCodeAuthenticationProvider {
        return SmsCodeAuthenticationProvider(userDetailsService)
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.let {
            it.authenticationProvider(smsCodeAuthenticationProvier())
            it.authenticationProvider(daoAuthenticationProvier())
        }
    }


}