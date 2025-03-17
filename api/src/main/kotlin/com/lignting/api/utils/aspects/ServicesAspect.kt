package com.lignting.api.utils.aspects

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Aspect
@Component
class ServicesAspect {
    private val logger by lazy { LoggerFactory.getLogger(this::class.java) }

    @Around("execution(* com.lignting.api.services.*.*(..))")
    fun resultHandle(joinPoint: ProceedingJoinPoint): Any {
        try {
            return joinPoint.proceed().success()
        } catch (e: RuntimeException) {
            logger.warn(e.message,e)
            return joinPoint.args.toList().failed(e.message!!)
        } catch (e: Exception) {
            logger.error(e.message, e)
            return joinPoint.args.toList().error(e.message!!)
        }
    }
}


data class ResponseBody<T>(
    val data: T,
    val code: Int,
    val message: String
)

fun <T> T.success(message: String = "success!") =
    ResponseBody(
        this,
        HttpStatus.OK.value(),
        message
    )

fun <T> T.failed(message: String = "failed!") =
    ResponseBody(
        this,
        HttpStatus.BAD_REQUEST.value(),
        message
    )

fun <T> T.error(message: String = "error!") =
    ResponseBody(
        this,
        HttpStatus.I_AM_A_TEAPOT.value(),
        message
    )