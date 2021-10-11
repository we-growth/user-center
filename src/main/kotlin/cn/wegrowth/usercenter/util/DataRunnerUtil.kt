package cn.wegrowth.usercenter.util

import cn.wegrowth.usercenter.domain.user.User
import cn.wegrowth.usercenter.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataRunnerUtil(private val userRepository: UserRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        userRepository.run {
            deleteAll()
            save(User(username = "admin",password = BCryptPasswordEncoder().encode("admin")))
        }
    }
}