package cn.wegrowth.usercenter.util

import cn.wegrowth.usercenter.domain.user.User
import cn.wegrowth.usercenter.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataRunnerUtil(private val userRepository: UserRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        userRepository.run {
            deleteAll()

            save(
                User(
                    username = "admin", password = Argon2PasswordEncoder().encode("admin"), wechatOpenId = null
                )
            )

            save(
                User(
                    username = "18192670730", password = null, wechatOpenId = null
                )
            )
            save(
                User(
                    username = "", password = null, wechatOpenId = "wexin_openId"
                )
            )
        }
    }
}