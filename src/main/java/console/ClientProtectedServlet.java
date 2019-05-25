package console;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import token_generator.TokenUtilsConsole;

// @CheckJWTToken does NOT work for servlets -> returns status=200
@SuppressWarnings("unused")
public class ClientProtectedServlet {
	
	public ClientProtectedServlet() throws Exception {
		
		String uriInfo = "http://localhost:8080/JWT-SecuringJAXRS/res/login";
		String token = TokenUtilsConsole.generateTokenConsoleSeconds(uriInfo, "John Smith", 120);
		
		Client cl = ClientBuilder.newClient();
		WebTarget target = cl.target("http://localhost:8080/JWT-SecuringJAXRS/servlet");
		
		Response response = target
				.request()
				//.cookie(new Cookie("bearer", token))
				//.header("Authorization", "Bearer " + token)
				.get();
		
		System.out.println("Response: " + response);
		System.out.println("Entity: " + response.readEntity(String.class));
		
	}
	
	
	public static void main(String[] args) {
		try {
			new ClientProtectedServlet();
		} catch (Exception e) {
			System.out.println("Exception From Server: " + e.getMessage());
		}
	}
}
