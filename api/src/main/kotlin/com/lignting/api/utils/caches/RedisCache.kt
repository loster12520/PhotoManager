package com.lignting.api.utils.caches

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisCache(private val redisTemplate: RedisTemplate<String, Any>) {
    fun setCacheObject(key:String, value:Any) = redisTemplate.opsForValue().set(key, value)
    fun getCacheObject(key:String) = redisTemplate.opsForValue().get(key)
    fun deleteCacheObject(key:String) = redisTemplate.delete(key)

}