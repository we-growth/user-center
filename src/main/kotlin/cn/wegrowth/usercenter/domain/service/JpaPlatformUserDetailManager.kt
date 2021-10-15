package cn.wegrowth.usercenter.domain.service

import cn.wegrowth.usercenter.api.CreateUserRequest
import cn.wegrowth.usercenter.domain.UserConverter
import cn.wegrowth.usercenter.domain.dto.UserView
import cn.wegrowth.usercenter.domain.user.QUser
import cn.wegrowth.usercenter.domain.user.User
import cn.wegrowth.usercenter.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service("userDetailsService")
class JpaPlatformUserDetailManager(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter,
    private val passwordEncoder: PasswordEncoder
) : PlatformUserDetailManager {

    override fun loadUserByWeChatId(wechatOpenId: String): UserDetails {
        return userRepository.findOne(
            QUser.user.wechatOpenId.eq(wechatOpenId)
        ).orElseThrow { UsernameNotFoundException("User not Found") }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        return userRepository.findOne(
            QUser.user.username.eq(username)
        ).orElseThrow { UsernameNotFoundException("User not Found") }
    }

    override fun createUser(createUserRequest: CreateUserRequest): UserView? {
        val user: User = userConverter.from(createUserRequest)
        //encoder the password
        user.withEnconderPassword(passwordEncoder.encode(createUserRequest.password))
        return userConverter.asView(userRepository.save(user))
    }
}