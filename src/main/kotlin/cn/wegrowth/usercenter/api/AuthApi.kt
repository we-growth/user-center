package cn.wegrowth.usercenter.api

import cn.wegrowth.usercenter.domain.dto.UserView
import cn.wegrowth.usercenter.domain.service.JpaPlatformUserDetailManager
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/public")
@Tag(name = "Authentication")
class AuthApi(private val userDetailsService: JpaPlatformUserDetailManager) {

    @PostMapping("register")
    @Operation(summary = "用户注册")
    fun createUser(@RequestBody createUserRequest: CreateUserRequest): UserView? {
        return userDetailsService.createUser(createUserRequest)
    }

}