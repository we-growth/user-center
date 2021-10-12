package cn.wegrowth.usercenter.api

import org.joda.time.DateTime
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class FooApi {
    @GetMapping("foo")
    fun getFoo(hello: String): String {
        return hello + DateTime.now()
    }

    @GetMapping("foo2")
    @PreAuthorize("hasRole('foo')")
    fun getUserFoo(hello: String): String {
        return hello + " at " + DateTime.now()
    }
}