package common.aspect;

import com.adamzfc.util.LogUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

/**
 * Created by adamzfc on 3/6/17.
 */
@SuppressWarnings("checkstyle:illegalthrows")
@Aspect
public class TimeLogAspect {
    private static final String TAG = "TimeLog";

    /**
     * point cut
     */
    @Pointcut("execution(@common.annotation.TimeLog * *(..))")
    public void methodAnnotated() {
    }

    /**
     * point cut
     */
    @Pointcut("execution(@common.annotation.TimeLog *.new(..))")
    public void constructorAnnotated() {
    }

    /**
     * around
     *
     * @param joinPoint join point
     * @return result
     * @throws Throwable throwable
     */
    @Around("methodAnnotated() || constructorAnnotated()")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LogUtil.showLog(TAG, methodSignature.getMethod().getDeclaringClass().getCanonicalName());
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(methodName).append(':');
        for (Object obj : joinPoint.getArgs()) {
            if (obj instanceof String) {
                keyBuilder.append((String) obj);
            } else if (obj instanceof Class) {
                keyBuilder.append(((Class) obj).getSimpleName());
            }
        }
        String key = keyBuilder.toString();
        // 打印时间差
        LogUtil.showLog(TAG, (className + "." + key + " --->:"
                + "[" + (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime))
                + "ms]"));
        return result;
    }
}
