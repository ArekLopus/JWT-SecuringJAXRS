package an_info;

//-This app uses JWT Token for Authentication.
//-User needs to login to get JWT token in Authorization header of response and pass it in every request in header 'Authorization: Bearer token'
//-Cookie version set a cookie so no need to pass in every request.


//-@CheckJWTToken is used to annotate resources we want to protect (here ./res/protected)
// It uses @NameBinding to bind the ContainerRequestFilter which checks if incoming request has a valid JWT token.  


//-When user logs in at /res/login - TokenUtils.generateToken() generates a new token that has:
// • subject - name		 • issuer - uriInfo.getAbsolutePath()	  • expiration - set as a method argument in minutes


//-CheckJWTTokenFilter validates the token. Jwts throws an exception if the token is not valid.
//	String token = authorizationHeader.substring("Bearer".length()).trim();
//	Key key = keyGenerator.generateKey();
//	Jws<Claims> parsedClaimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
//	System.out.println("---- valid token : " + parsedClaimsJws);

//-Exception of an expired token:
//	io.jsonwebtoken.ExpiredJwtException: JWT expired at 2019-02-19T15:06:14Z.
//	Current time: 2019-02-19T15:06:15Z, a difference of 1354 milliseconds.  Allowed clock skew: 0 milliseconds.


//		ServletFilter_TokenTimeExtender - extends tokens time to live
//-It is easier for cookies, cookie has its lifetime so after it expires it is gone.
//-It is only needed to check if it exists (for headers we need to set it for every request and response).

// Cookies 
//-If token's time to live goes below 90 seconds, it is set to 120 seconds again for the token and cookie.

// Headers
//-For Authorization header we need to pass token back and forth between a client and the server in EVERY request and response
//-Client needs to check for a token in every response, get it, and set it in every request.
//-All Client calls need to have Authorization header, not only to protected resources, otherwise we dont extend time.

public class Info {}
