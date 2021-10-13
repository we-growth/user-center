package cn.wegrowth.usercenter.auth.sms

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService

class SmsCodeAuthenticationProvider(
    private val userDetailsService: UserDetailsService
) : AuthenticationProvider {


    override fun authenticate(authentication: Authentication): Authentication {
        val authenticationToken: SmsCodeAuthenticationToken = authentication as SmsCodeAuthenticationToken
        val userDetail = userDetailsService.loadUserByUsername(authenticationToken.principal.toString())

        val authenticationResult = SmsCodeAuthenticationToken(
            authenticationToken.principal, authenticationToken.credentials,
            userDetail.authorities
        )
        authenticationResult.details = authenticationToken.details
        return authenticationResult
    }

    override fun supports(authentication: Class<*>): Boolean {
        return SmsCodeAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}