package aspect;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import annotations.RateLimit;
import entities.Client;
import exception.ClientIdException;
import exception.RateLimitException;

@Component
@Aspect
public class RateLimitAspect {
	
	private static final Logger logger =
		  LoggerFactory.getLogger(RateLimitAspect.class);

		  // Using HashMap for storage for convenience. For scalability and usage in distributed systems we use
		  // Redis or similar Key, Value store.
	
		  HashMap<String, ArrayList<LocalDateTime>> clientIdTimeMap = new HashMap<String, ArrayList<LocalDateTime>>();
		  
		  @Pointcut("@annotation(rateLimit)")
		  private void annotatedWithRateLimit(RateLimit rateLimit) {}

		  @Pointcut("@within(org.springframework.stereotype.Controller)"
		      + " || @within(org.springframework.web.bind.annotation.RestController)")
		  private void controllerMethods() {}

		  @Before("controllerMethods() && annotatedWithRateLimit(rateLimit)")
		  public void rateLimitProcess(final JoinPoint joinPoint, RateLimit rateLimit) throws RateLimitException, ClientIdException {
				logger.debug("<<<<<<<<<<<<<< Starting rateLimitProcess() >>>>>>>>>>>>>");
				HttpServletRequest request = getRequest(joinPoint.getArgs());
				if (request == null) {
				  logger.error("No request recieved.");
				  return;
				}

				String clientId = request.getParameter("clientId");
				
				if (clientId == null) {
					  logger.error("No clientId recived.");
					  throw new ClientIdException();
				}
				
				if (!clientIdTimeMap.containsKey(clientId)) {
					Client client = new Client(clientId);
					client.getAccessTimeList().add(LocalDateTime.now());
					clientIdTimeMap.put(clientId, client.getAccessTimeList());
				}else if (clientIdTimeMap.get(clientId).size()  < rateLimit.limit()) {
					clientIdTimeMap.get(clientId).add(LocalDateTime.now());
				}else {
					ArrayList<LocalDateTime> updatedList = getTimeUpdatedList(clientIdTimeMap.get(clientId),rateLimit);
					
					if (updatedList.size() < rateLimit.limit()) {
						clientIdTimeMap.get(clientId).add(LocalDateTime.now());
					}else {
						throw new RateLimitException();
					}
				}
				logger.debug("<<<<<<<<<<<<<< Ending rateLimitProcess() >>>>>>>>>>>>>");
		  }

		  private ArrayList<LocalDateTime> getTimeUpdatedList(ArrayList<LocalDateTime> accessTimeList, RateLimit rateLimit) {
			int lastUsableTimeIndex = 0;
			for(int i=0; i< accessTimeList.size(); i++) {
				if((accessTimeList.get(i).getSecond() - LocalDateTime.now().getSecond()) > rateLimit.duration()) {
					lastUsableTimeIndex = i;
				}
			}
			accessTimeList.subList(0, lastUsableTimeIndex).clear();
			return accessTimeList;
		}

		private HttpServletRequest getRequest(Object[] args) {
		    for (Object arg : args) {
		      if (arg instanceof HttpServletRequest) {
		        return (HttpServletRequest)arg;
		      }
		    }
		    return null;
		  }
	
}
