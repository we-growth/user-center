package cn.wegrowth.usercenter.configuration

import cn.wegrowth.usercenter.auth.usernamepassword.UsernamePasswordAuthenticationProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Value("\${springdoc.api-docs.path}")
    private lateinit var restApiDocPath: String

    @Value("\${springdoc.swagger-ui.path}")
    private lateinit var swaggerPath: String

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider())
    }
    override fun configure(http: HttpSecurity) {
//        http.formLogin().permitAll()
        // set permissions on endpoints
        http.authorizeRequests()
            .antMatchers("/oauth/token").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("%s/**".format(restApiDocPath)).permitAll()
            .antMatchers("%s/**".format(swaggerPath)).permitAll()
            .anyRequest().authenticated()

    }
    @Bean
    fun usernamePasswordAuthenticationProvider() : UsernamePasswordAuthenticationProvider {
        return UsernamePasswordAuthenticationProvider()
    }

}