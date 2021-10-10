package cn.wegrowth.usercenter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
//@EnableAuthorizationServer
class UserCenterApplication

fun main(args: Array<String>) {
    runApplication<UserCenterApplication>(*args)
}
