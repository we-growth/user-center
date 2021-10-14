package cn.wegrowth.usercenter.security.auth.wechat

import cn.wegrowth.usercenter.domain.service.JpaPlatformUserDetailManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication

class WechatAuthenticationProvider(
    private val userDetailsService: JpaPlatformUserDetailManager
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        val authenticationToken: WechatAuthenticationToken = authentication as WechatAuthenticationToken
        val userDetail = userDetailsService.loadUserByWeChatId(authenticationToken.principal as String)

        val authenticationResult = WechatAuthenticationToken(
            userDetail,
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
        return WechatAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}