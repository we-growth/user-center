package cn.wegrowth.usercenter.domain.dto

data class UserView(
    val id: String,
    val username: String,
    var access: String? = "admin"
)
