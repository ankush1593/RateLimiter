package controller;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import annotations.RateLimit;

@RestController
public class WelcomeController {
	
	@RateLimit(limit = 3, duration = 60, unit = TimeUnit.SECONDS)
	@RequestMapping("/welcome")
	  public String welcome(HttpServletRequest request, @RequestParam("clientId") String clientId) {
	    return "Test RateLimit API";
	  }

}