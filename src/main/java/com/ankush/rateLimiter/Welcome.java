package com.ankush.rateLimiter;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {
	
	//@RequestMapping("/welcome")
	public String welcome() {
		return "It's running...";
	}

}
