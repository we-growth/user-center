package cn.wegrowth.usercenter.security.config

import cn.wegrowth.usercenter.domain.user.User
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer

class UserTokenEnhancer : TokenEnhancer {
    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val principal = authentication.principal
        if (principal is User) {
            if (accessToken is DefaultOAuth2AccessToken) {
                val additionalInfo = hashMapOf<String, Any?>("user_id" to principal.id)
                accessToken.additionalInformation = additionalInfo
            }
        }
        return accessToken
    }
}