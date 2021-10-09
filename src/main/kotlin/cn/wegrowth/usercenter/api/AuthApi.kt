package cn.wegrowth.usercenter.api

import cn.wegrowth.usercenter.domain.dto.UserView
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Authentication")
@RestController
@RequestMapping("api/public")
class AuthApi {
    @PostMapping("register")
    fun register(): UserView {
        TODO("not implement.")
    }
}