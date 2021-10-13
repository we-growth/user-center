package cn.wegrowth.usercenter.auth.sms

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService

class SmsCodeAuthenticationProvider(
    private val userDetailsService: UserDetailsService
) : AuthenticationProvider {


    override fun authenticate(authentication: Authentication): Authentication {
        val authenticationToken: SmsCodeAuthenticationToken = authentication as SmsCodeAuthenticationToken
        val userDetail = userDetailsService.loadUserByUsername(authenticationToken.principal)

        val authenticationResult = SmsCodeAuthenticationToken(
            authenticationToken.principal, authenticationToken.credentials,
            userDetail.authorities
        ).run {
            // now authenticated is true
            isAuthenticated = true
            this
        }
        authenticationResult.details = authenticationToken.details
        return authenticationResult
    }

    override fun supports(authentication: Class<*>): Boolean {
        return SmsCodeAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}