package pl.lodz.p.it.ssbd2020.ssbd04.interceptors;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.UnauthorizedException;

import javax.annotation.Resource;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interceptor rejestrujący w dzienniku zdarzeń wywołania metod komponentów EJB.
 */
public class TrackingInterceptor {
    private static final Logger LOGGER = Logger.getLogger(TrackingInterceptor.class.getName());
    @Resource
    private SessionContext sessionContext;

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        String result = null;
        try {
            Object returnValue = context.proceed();
            if (returnValue != null) {
                result = String.format("returned a value: %s", returnValue);
            } else {
                result = "returned no value";
            }
            return returnValue;
        } catch (Exception e) {
            result = String.format("threw an exception: %s, cause: %s",
                    e.toString(), e.getCause());
            if (e instanceof EJBAccessException) {
                throw UnauthorizedException.unauthorized(e);
            }
            throw e;
        } finally {
            logAction(context, result);
        }
    }

    private void logAction(InvocationContext context, String result) {
        String params = context.getParameters() == null ? "[]" : Arrays.toString(context.getParameters());
        LOGGER.log(Level.INFO, "User: {0}, method: {1}, passed parameters: {2}, result: {3}", new Object[]{
                sessionContext.getCallerPrincipal().toString(),
                context.getMethod().toGenericString(),
                params,
                result});
    }
}
