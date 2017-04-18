package common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by adamzfc on 3/6/17.
 */
@SuppressWarnings("checkstyle:illegalthrows")
@Aspect
public class CheckLoginAspect {

    /**
     * method annotated
     */
    @Pointcut("execution(@common.annotation.CheckLogin * *(..))")
    public void methodAnnotated() {
    }

    /**
     * around join point
     * @param joinPoint join point
     * @throws Throwable throwable
     */
    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // if no login
//        if () {
//            return;
//        }
        joinPoint.proceed();
    }
}