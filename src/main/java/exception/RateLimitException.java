package exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitException extends Exception {

	public RateLimitException() {
	    super("Rate limit exceeded.");
	  }
	
	public RateLimitException(String msg) {
	    super(msg);
	  }
	
}
