package cn.wegrowth.usercenter.domain.service

import cn.wegrowth.usercenter.domain.user.QUser
import cn.wegrowth.usercenter.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


@Service("userDetailsService")
class JpaPlatformUserDetailManager(private val userRepository: UserRepository) : PlatformUserDetailManager {
    /**
     * Create user details
     */
    override fun createUserDetails(username: String, password: String, authorities: Array<String>): UserDetails {
        TODO("Not yet implemented")
    }


    override fun loadUserByUsername(username: String?): UserDetails {
//        TODO("Not yet implemented")
        return userRepository.findOne(QUser.user.username.eq(username)).get()
    }


    override fun createUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }


    override fun deleteUser(username: String?) {
        TODO("Not yet implemented")
    }


    override fun changePassword(oldPassword: String?, newPassword: String?) {
        TODO("Not yet implemented")
    }


    override fun userExists(username: String?): Boolean {
        TODO("Not yet implemented")
    }
}