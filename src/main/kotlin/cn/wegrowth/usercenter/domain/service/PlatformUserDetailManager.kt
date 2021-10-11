package cn.wegrowth.usercenter.domain.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.UserDetailsManager

interface PlatformUserDetailManager : UserDetailsManager {
    /**
     * Create user details
      */
    fun createUserDetails(username: String, password: String, authorities: Array<String>): UserDetails
}