package cn.wegrowth.usercenter.api

import cn.wegrowth.usercenter.domain.UserConverter
import cn.wegrowth.usercenter.domain.dto.UserView
import cn.wegrowth.usercenter.domain.user.User
import cn.wegrowth.usercenter.repository.UserRepository
import com.querydsl.core.types.Predicate
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.binding.QuerydslPredicate
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Tag(name = "UserAPI")
class UserApi(
    private val userConverter: UserConverter,
    private val userRepository: UserRepository
) {

    @GetMapping("/currentUser")
    @Operation(summary = "当用用户")
    fun currentUser(): UserView? {
        val user = SecurityContextHolder.getContext().getAuthentication().principal as User
        val userview = userConverter.asView(user)
        userview.access = "admin"
        return userview
    }

    @PostMapping("/logout")
    @Operation(summary = "登出")
    fun logOutCurrentUser(): ResponseEntity<Any> {
        return ResponseEntity.ok().body("bye.bye")
    }

    @GetMapping("/users")
    @Operation(summary = "当前用户（分页查询）")
    @PageableAsQueryParam
    fun queryUserLists(
        @QuerydslPredicate(root = User::class) predicate: Predicate,
        pageable: Pageable,
    ): ResponseEntity<Page<UserView>> {


        val users = userRepository
            .findAll(predicate, pageable)
            .map { user -> userConverter.asView(user) }
        return ResponseEntity.ok().body(users)
    }
}