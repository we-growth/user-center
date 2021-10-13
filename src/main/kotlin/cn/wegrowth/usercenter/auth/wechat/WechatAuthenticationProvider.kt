package cn.wegrowth.usercenter.auth.wechat

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService

class WechatAuthenticationProvider(
    private val userDetailsService: UserDetailsService) : AuthenticationProvider{
    override fun authenticate(authentication: Authentication?): Authentication {
        val authenticationToken: WechatAuthenticationToken = authentication as WechatAuthenticationToken
        val userDetail = userDetailsService.loadUserByUsername(authenticationToken.principal)

        val authenticationResult = WechatAuthenticationToken(
            authenticationToken.principal,
            userDetail.authorities
        ).run {
            // now authenticated is true
            isAuthenticated = true
            this
        }
        authenticationResult.details = authenticationToken.details
        return authenticationResult
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return WechatAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}