package cn.wegrowth.usercenter.configuration.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {
    @Value("\${springdoc.api-docs.path}")
    private lateinit var restApiDocPath: String

    @Value("\${springdoc.swagger-ui.path}")
    private lateinit var swaggerPath:String

    override fun configure(http: HttpSecurity) {
        // set permissions on endpoints
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("%s/**".format(restApiDocPath)).permitAll()
            .antMatchers("%s/**".format(swaggerPath)).permitAll()
            .anyRequest().authenticated()

    }

}