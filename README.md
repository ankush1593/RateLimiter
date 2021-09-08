# RateLimiter
Simple Spring Boot Project to limit number of requests per tenant in an API

# Usage

1. Annotate a Controller method with @RateLimit with params as numOfRequests, duration and timeUnit.
2. ClientId is needed as URL Parameter to uniquely identify a client. If clientId is not provided it'll throw exception

Example:-

 ```java
  @RateLimit(limit = 3, duration = 60, unit = TimeUnit.SECONDS)
  @RequestMapping("/test")
  public String test(HttpServletRequest request) {
    return "Testing Rate Limiter";
  }
```

Sample URL to test - http://localhost:8080/welcome?clientId=1

If the client exceeds the rate limit, the response status would be 429.
