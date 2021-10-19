package cn.wegrowth.usercenter.domain.service

import cn.wegrowth.usercenter.api.CreateUserRequest
import cn.wegrowth.usercenter.domain.dto.UserView
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

interface PlatformUserDetailManager : UserDetailsService {
    /**
     * Create user details
     */
    fun createUser(createUserRequest: CreateUserRequest): UserView?

    /**
     *微信openid
     */
    @Throws(UsernameNotFoundException::class)
    fun loadUserByWeChatId(username: String): UserDetails
}