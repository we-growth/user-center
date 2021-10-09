package cn.wegrowth.usercenter.auth.usernamepassword

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority


class UsernamePasswordAuthenticationProvider : AuthenticationProvider{
    override fun authenticate(authentication: Authentication): Authentication {
        val username = if (authentication.principal == null) "NONE_PROVIDED" else authentication.name
        val password = authentication.credentials as String
        // 认证用户名
        if ("user" != username && "admin" != username) {
            throw BadCredentialsException("用户不存在")
        }
        // 认证密码，暂时不加密
        if ("user" == username && "123" != password || "admin" == username && "admin" != password) {
            throw BadCredentialsException("密码不正确")
        }
        val result = UsernamePasswordAuthenticationToken(
            username,
            authentication.credentials, listUserGrantedAuthorities(username)
        )
        result.details = authentication.details
        return result
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java
            .isAssignableFrom(authentication)
    }
    private fun listUserGrantedAuthorities(username: String): Set<GrantedAuthority>? {
        val authorities: MutableSet<GrantedAuthority> = HashSet()
//        if (CheckUtils.isEmpty(username)) {
//            return authorities
//        }
        authorities.add(SimpleGrantedAuthority("ROLE_USER"))
        if ("admin" == username) {
            authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
        }
        return authorities
    }
}