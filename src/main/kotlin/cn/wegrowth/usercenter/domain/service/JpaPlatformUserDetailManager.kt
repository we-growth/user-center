package cn.wegrowth.usercenter.domain.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component


//@Component
class JpaPlatformUserDetailManager : PlatformUserDetailManager {
    /**
     * Create user details
     */
    override fun createUserDetails(username: String, password: String, authorities: Array<String>): UserDetails {
        TODO("Not yet implemented")
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the `UserDetails`
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never `null`)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("Not yet implemented")
    }

    /**
     * Create a new user with the supplied details.
     */
    override fun createUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }

    /**
     * Update the specified user.
     */
    override fun updateUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }

    /**
     * Remove the user with the given login name from the system.
     */
    override fun deleteUser(username: String?) {
        TODO("Not yet implemented")
    }

    /**
     * Modify the current user's password. This should change the user's password in the
     * persistent user repository (datbase, LDAP etc).
     * @param oldPassword current password (for re-authentication if required)
     * @param newPassword the password to change to
     */
    override fun changePassword(oldPassword: String?, newPassword: String?) {
        TODO("Not yet implemented")
    }

    /**
     * Check if a user with the supplied login name exists in the system.
     */
    override fun userExists(username: String?): Boolean {
        TODO("Not yet implemented")
    }
}