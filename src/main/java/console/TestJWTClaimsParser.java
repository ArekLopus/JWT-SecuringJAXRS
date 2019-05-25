package console;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import token_generator.TokenUtilsConsole;

public class TestJWTClaimsParser {

	public TestJWTClaimsParser() {
		
		String uriInfo = "http://localhost:8080/JWT-SecuringJAXRS/res/login";
		
		String token = TokenUtilsConsole.generateTokenConsoleSeconds(uriInfo, "John Smith", 120);
		//String token = TokenUtilsConsole.generateTokenConsoleMinutes(uriInfo, "John Smith", 3);
		
		Jws<Claims> parsedTokenClaims = TokenUtilsConsole.parseTokenClaims(token);
		System.out.println(parsedTokenClaims);
		
		
		if(parsedTokenClaims != null) {
			Claims body = parsedTokenClaims.getBody();
			int iat = (int) body.get("iat");
			int exp = (int) body.get("exp");
			
			System.out.println("Difference (sec): " + (exp - iat));
		}
		
		
		System.out.println("--- Main Thread Finished ---");
	}

	public static void main(String[] args) {
		new TestJWTClaimsParser();

	}

}
