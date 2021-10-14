package cn.wegrowth.usercenter.security.auth.sms

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class SmsCodeAuthenticationToken : AbstractAuthenticationToken {
    private var principal: Any? = null
    private var credentials: Any? = null

    constructor(
        principal: Any,
        credentials: Any?,
        authorities: Collection<GrantedAuthority>?
    ) : super(authorities) {
        this.principal = principal
        this.credentials = credentials
    }

    constructor(principal: Any, credentials: Any?) : super(null) {
        this.principal = principal
        this.credentials = credentials
    }

    override fun getCredentials(): Any? {
        return this.credentials
    }

    override fun getPrincipal(): Any? {
        return this.principal
    }
}