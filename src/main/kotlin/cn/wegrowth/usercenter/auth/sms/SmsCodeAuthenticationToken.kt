package cn.wegrowth.usercenter.auth.sms

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class SmsCodeAuthenticationToken(authorities: MutableCollection<out GrantedAuthority>?) :
    AbstractAuthenticationToken(authorities) {

    override fun getCredentials(): Any {
        TODO("Not yet implemented")
    }

    override fun getPrincipal(): Any {
        TODO("Not yet implemented")
    }
}