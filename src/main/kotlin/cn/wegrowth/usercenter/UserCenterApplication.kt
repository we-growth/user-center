package cn.wegrowth.usercenter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer

@SpringBootApplication
@EnableDiscoveryClient
@EnableAuthorizationServer
class UserCenterApplication

fun main(args: Array<String>) {
    runApplication<UserCenterApplication>(*args)
}
