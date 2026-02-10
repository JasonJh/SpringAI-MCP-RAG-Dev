package com.wjh;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Slf4j
@Aspect
public class ServiceLogAspect {

    /**
     * AOP 环绕切面 ：* 返回任意类型
     *              com.wjh.service.impl 指定的包名，要切的class类的所在包
     *              ..可以匹配当前包和子包的类
     *              * 匹配当前包以及子包的class类
     *              . 无意义
     *              * 匹配任意方法
     *              (..) 方法的参数，匹配任意参数
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.wjh.service.impl..*.*(..))")
    public Object recordTimesLog(ProceedingJoinPoint joinPoint) throws Throwable {

//        long startTime = System.currentTimeMillis();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        String point = joinPoint.getTarget().getClass().getName()
                + "."
                + joinPoint.getSignature().getName();

        stopWatch.stop();

//        long endTime = System.currentTimeMillis();

//        long elapsedTime = endTime - startTime;

        long elapsedTime = stopWatch.getTotalTimeMillis();

        if (elapsedTime > 3000) {
            log.error("{} 耗时太长 {} 毫秒", point, elapsedTime);
        }else if (elapsedTime > 2000) {
            log.warn("{} 耗时一般 {} 毫秒", point, elapsedTime);
        }else {
            log.info("{} 耗时 {} 毫秒", point, elapsedTime);
        }

        return result;
    }
}
