package cn.wegrowth.usercenter.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager


@EnableWebSecurity
@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Value("\${springdoc.api-docs.path}")
    private lateinit var restApiDocPath: String

    @Value("\${springdoc.swagger-ui.path}")
    private lateinit var swaggerPath: String

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    override fun configure(web: WebSecurity) {
       web.ignoring()
           .antMatchers("%s/**".format("/h2"))
    }

    override fun configure(http: HttpSecurity) {

        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("%s/**".format(restApiDocPath)).permitAll()
            .antMatchers("%s/**".format(swaggerPath)).permitAll()
            .anyRequest().permitAll()
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return InMemoryUserDetailsManager(
            User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build()
        )
    }

}