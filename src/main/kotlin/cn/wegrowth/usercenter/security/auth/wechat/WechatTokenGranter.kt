package cn.wegrowth.usercenter.security.auth.wechat

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException
import org.springframework.security.oauth2.provider.*
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices

class WechatTokenGranter(
    private val authenticationManager: AuthenticationManager,
    tokenServices: AuthorizationServerTokenServices,
    clientDetailsService: ClientDetailsService,
    requestFactory: OAuth2RequestFactory,
) : AbstractTokenGranter(tokenServices, clientDetailsService, requestFactory, "wechat") {
    override fun getOAuth2Authentication(client: ClientDetails, tokenRequest: TokenRequest): OAuth2Authentication {
        val parameters: Map<String, String> = LinkedHashMap(tokenRequest.requestParameters)
        val openId = parameters["openid"] ?: throw InvalidGrantException("openid parameters is required.")

        var userAuth: Authentication = WechatAuthenticationToken(openId)

        userAuth = authenticationManager.authenticate(userAuth)

        if (userAuth == null || !userAuth.isAuthenticated) {
            throw InvalidGrantException("authenticated with wechat failed: $openId")
        }

        val storedOAuth2Request = requestFactory.createOAuth2Request(client, tokenRequest)
        return OAuth2Authentication(storedOAuth2Request, userAuth)
    }
}