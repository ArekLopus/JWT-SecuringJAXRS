package token_generator;

import java.security.Key;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;

import io.jsonwebtoken.Jwts;

@ApplicationScoped
public class TokenUtils {
	
    @Inject
    private KeyGenerator keyGenerator;
	
    
    public String generateToken(UriInfo uriInfo, String name, long validTimeInMinutes) {
    	
    	Instant now = Instant.now();
    	
    	try {
            Key key = keyGenerator.generateKey();
            String jwtToken = Jwts.builder()
                    .setSubject(name)
                    .setIssuer(uriInfo.getAbsolutePath().toString())
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(validTimeInMinutes, ChronoUnit.MINUTES)))
                    //.signWith(key, SignatureAlgorithm.HS256)
                    .signWith(key)
                    .compact();
            
            System.out.println("---- TokenUtils.generateToken() - token generated: " + jwtToken + " - " + key);
            return jwtToken;
            
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "Fail in TokenUtils.generateToken()";
		}
    }
    
    
    //Used by User to hash password
    public static String digestPassword(String plainTextPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainTextPassword.getBytes("UTF-8"));
            byte[] passwordDigest = md.digest();
            return new String(Base64.getEncoder().encode(passwordDigest));
        } catch (Exception e) {
            throw new RuntimeException("Exception encoding password", e);
        }
    }
}