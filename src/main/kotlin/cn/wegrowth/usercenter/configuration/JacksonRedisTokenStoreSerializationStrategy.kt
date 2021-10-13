package cn.wegrowth.usercenter.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy

class JacksonRedisTokenStoreSerializationStrategy : RedisTokenStoreSerializationStrategy {
    override fun <T : Any?> deserialize(bytes: ByteArray?, clazz: Class<T>?): T? {
        bytes?.let { return jacksonObjectMapper().readValue(bytes, clazz) }

        return null

    }

    override fun deserializeString(bytes: ByteArray?): String? {
        return bytes?.toString()
    }

    override fun serialize(`object`: Any?): ByteArray {
        return jacksonObjectMapper().writeValueAsBytes(`object`)
    }

    override fun serialize(data: String): ByteArray {
        return data.toByteArray();
    }
}