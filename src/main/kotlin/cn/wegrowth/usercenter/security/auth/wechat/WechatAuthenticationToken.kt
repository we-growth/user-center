package cn.wegrowth.usercenter.security.auth.wechat

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class WechatAuthenticationToken
    : AbstractAuthenticationToken {
    private var principal: Any

    constructor(
        principal: Any,
        authorities: Collection<GrantedAuthority>?
    ) : super(authorities) {
        this.principal = principal
    }

    constructor(principal: Any) : super(null) {
        this.principal = principal
    }

    override fun getCredentials(): Any {
        return "[N/A]"
    }

    override fun getPrincipal(): Any {
        return this.principal
    }


}