package cn.wegrowth.usercenter.api

import cn.wegrowth.usercenter.domain.dto.UserView
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
@RequestMapping("api/auth")
@Tag(name = "Authentication")
class AuthApi(
    @Resource
    private val tokenEndpoint: TokenEndpoint
) {

    @Operation(summary = "用户登录")
    @PostMapping(path = ["/login"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun loginWithUsername(@RequestBody userDto: UserDto): UserView {
        TODO("not impl")
    }

    data class UserDto(
        val username: String,
        val password: String
    )
}