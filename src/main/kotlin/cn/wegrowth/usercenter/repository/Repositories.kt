package cn.wegrowth.usercenter.repository

import cn.wegrowth.usercenter.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface UserRepository : JpaRepository<User, String>, QuerydslPredicateExecutor<User>

