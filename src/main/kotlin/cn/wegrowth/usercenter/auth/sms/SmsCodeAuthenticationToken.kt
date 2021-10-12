package cn.wegrowth.usercenter.auth.sms

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class SmsCodeAuthenticationToken : AbstractAuthenticationToken {
    private var principal: Any? = null
    private var credentials: Any? = null

    constructor(
        principal: Any?,
        credentials: Any?,
        authorities: Collection<GrantedAuthority>?
    ) : super(authorities) {
        this.principal = principal
        this.credentials = credentials
        super.setAuthenticated(true)
    }

    constructor(principal: String, credentials: String) : super(null) {
        this.principal = principal
        this.credentials = credentials
        super.setAuthenticated(false)
    }

    override fun getCredentials(): Any? {
        return this.credentials
    }

    override fun getPrincipal(): Any? {
        return this.credentials
    }
}