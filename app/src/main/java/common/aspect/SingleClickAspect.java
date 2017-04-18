package common.aspect;

import android.view.View;

import com.adamzfc.androidbase.R;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

/**
 * Created by adamzfc on 3/6/17.
 */
@SuppressWarnings("checkstyle:illegalthrows")
@Aspect
public class SingleClickAspect {
    private static final int TIME_TAG = R.id.click_time;
    private static final int MIN_CLICK_DELAY_TIME = 600;

    /**
     * point cut
     */
    @Pointcut("execution(@common.annotation.SingleClick * *(..))")
    public void methodAnnotated() {
    }

    /**
     * around
     * @param joinPoint join point
     * @throws Throwable throwable
     */
    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
            }
        }
        if (view != null) {
            Object tag = view.getTag(TIME_TAG);
            long lastClickTime = ((tag != null) ? (long) tag : 0);
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                view.setTag(TIME_TAG, currentTime);
                joinPoint.proceed();
            }
        }
    }
}
