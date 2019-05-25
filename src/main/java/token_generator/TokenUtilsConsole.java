package token_generator;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class TokenUtilsConsole {
	
	
    public static String generateTokenConsoleSeconds(String uriInfo, String name, long validTimeInSeconds) {
    	
    	Instant now = Instant.now();
    	
    	try {
            Key key = KeyGeneratorSimple.generateKeyStatic();
            String jwtToken = Jwts.builder()
                    .setSubject(name)
                    .setIssuer(uriInfo)
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plusSeconds(validTimeInSeconds)))
                    //.signWith(key, SignatureAlgorithm.HS256)
                    .signWith(key)
                    .compact();
            System.out.println("---- TokenUtilsConsole.generateTokenConsole() - token generated: " + jwtToken + " - " + key);
            return jwtToken;
		} catch (Exception e) {
			System.out.println(e);
			return "Fail in TokenUtilsConsole.generateTokenConsole()";
		}
    }
    
    
    public static String generateTokenConsoleMinutes(String uriInfo, String name, long validTimeInMinutes) {
    	
    	Instant now = Instant.now();
    	
    	try {
            Key key = KeyGeneratorSimple.generateKeyStatic();
            String jwtToken = Jwts.builder()
                    .setSubject(name)
                    .setIssuer(uriInfo)
                    .setIssuedAt(Date.from(now))
                    //.setExpiration(toDate(LocalDateTime.now().plusSeconds(validTimeInSeconds)))
                    .setExpiration(Date.from(now.plus(validTimeInMinutes, ChronoUnit.MINUTES)))
                    //.signWith(key, SignatureAlgorithm.HS256)
                    .signWith(key)
                    .compact();
            System.out.println("---- TokenUtilsConsole.generateTokenConsole() - token generated: " + jwtToken + " - " + key);
            return jwtToken;
		} catch (Exception e) {
			System.out.println(e);
			return "Fail in TokenUtilsConsole.generateTokenConsole()";
		}
    }
    
    
    
    
    public static Jws<Claims> parseTokenClaims(String token) {
    	
    	Key key = KeyGeneratorSimple.generateKeyStatic();
    	
    	try {
    		
    		Jws<Claims> parsedClaimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    		
    		return parsedClaimsJws;
    		
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
    }
    
    
    public static long tokenTimeToLive(String token) {
    	
    	try {
    		
    		Jws<Claims> parsedTokenClaims = TokenUtilsConsole.parseTokenClaims(token);
    		
    		if(parsedTokenClaims != null) {
    			Claims body = parsedTokenClaims.getBody();
    			int iat = (int) body.get("iat");
    			int exp = (int) body.get("exp");
    			
    			long tokensLeftTimeToLive = (body.getExpiration().getTime() - System.currentTimeMillis()) /1000;
    			
    			System.out.println("Token's live time (sec): " + (exp - iat) + ", Token Expires in (sec): " + tokensLeftTimeToLive);
    			
    			return tokensLeftTimeToLive; 
    		} else {
    			return -1;
    		}
    		
		} catch (Exception e) {
			System.out.println("Exception in TokenUtilsConsole.tokenTimeToLive(): " + e.getMessage());
			return -1;
		}
    	
	}
    
    public static String addTimeToToken(String token, long timeInSecondsToAdd) {
    	
    	Key key = KeyGeneratorSimple.generateKeyStatic();
    	
    	try {
    		
    		Jws<Claims> parsedClaimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    		Claims claims = parsedClaimsJws.getBody();
    		
    		String jwtToken = Jwts.builder()
    			.setClaims(claims)
    			.setExpiration(Date.from(Instant.now().plus(timeInSecondsToAdd, ChronoUnit.SECONDS)))
    			.signWith(key)
                .compact();
    			
            return jwtToken;
		} catch (Exception e) {
			System.out.println("Exception in TokenUtilsConsole.addTimeToToken(): " + e.getMessage());
			return "Fail in TokenUtilsConsole.addTimeToToken()";
		}
    }
    
}