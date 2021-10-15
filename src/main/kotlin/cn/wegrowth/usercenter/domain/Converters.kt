package cn.wegrowth.usercenter.domain

import cn.wegrowth.usercenter.api.CreateUserRequest
import cn.wegrowth.usercenter.domain.dto.UserView
import cn.wegrowth.usercenter.domain.user.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface UserConverter {
    fun asView(person: User): UserView
    @Mapping(target = "password",ignore = true)
    fun from(createUserRequest: CreateUserRequest): User
}