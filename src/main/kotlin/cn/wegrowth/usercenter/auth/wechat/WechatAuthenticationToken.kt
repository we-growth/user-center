package cn.wegrowth.usercenter.auth.wechat

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class WechatAuthenticationToken
    : AbstractAuthenticationToken {
    private  var principal: Any? = null

    constructor(
        principal: Any?,
        authorities: Collection<GrantedAuthority>?
    ) : super(authorities) {
        this.principal = principal
    }

    constructor(principal: String) : super(null) {
        this.principal = principal
    }

    override fun getCredentials(): Any? {
        //TODO("Not yet implemented")
        return null
    }

    override fun getPrincipal(): String {
        return this.principal!!.toString()
    }

}