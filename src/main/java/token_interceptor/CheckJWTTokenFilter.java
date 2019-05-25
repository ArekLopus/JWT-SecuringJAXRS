	package token_interceptor;

import java.security.Key;
import java.util.Map;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import token_generator.KeyGenerator;

@CheckJWTToken
@Provider
@Priority(Priorities.AUTHENTICATION)
public class CheckJWTTokenFilter implements ContainerRequestFilter {
    
	@Inject
    private KeyGenerator keyGenerator;
	private String token = "No token";
	private ContainerRequestContext requestContext;
	
	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
		
		this.requestContext = requestContext;
		
		System.out.println("--- Token Filter --- ");
		
		String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
		Map<String, Cookie> cookies = requestContext.getCookies();
        Cookie cookie = cookies.get("bearer");
        		
        if((authHeader == null || !authHeader.startsWith("Bearer")) && cookie == null) {
        	Response resp = Response.status(Response.Status.UNAUTHORIZED).build();
        	requestContext.abortWith(resp);
        	return;
        }
        
        
        if(cookie != null) {
        	checkToken(cookie.getValue());
        } else {
        	checkToken(authHeader);
        }
        
    }
	
	private void checkToken(String header) {
		try {
            // Extract the token from the HTTP Authorization header
            if(header.startsWith("Bearer")) {
            	token = header.substring("Bearer".length()).trim();
            } else {
            	token = header;
            }
            	
            // Validate the token
            Key key = keyGenerator.generateKey();
            Jws<Claims> parsedClaimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            System.out.println("---- valid token : " + parsedClaimsJws);
            
        } catch (Exception e) {
        	System.out.println("---- invalid token : " + token);
        	System.out.println("---- Exception : " + e.getMessage());
        	Response resp = Response.status(Response.Status.UNAUTHORIZED).build();
        	//resp.getHeaders().add("WWW-Authenticate", "Bearer");
        	requestContext.abortWith(resp);
        }
	}
}