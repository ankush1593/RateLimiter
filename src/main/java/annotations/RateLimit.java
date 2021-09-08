/**
 * 
 */
package annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * @author Ankush
 *
 */
public @interface RateLimit {
	int limit() default 10;
	
	long duration() default 60;
	
	TimeUnit unit() default TimeUnit.SECONDS;
}
