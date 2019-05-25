package console;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import token_generator.TokenUtilsConsole;

public class ClientProtectedFail {
	
	public ClientProtectedFail() throws Exception {
		
		String uriInfo = "http://localhost:8080/JWT-SecuringJAXRS/res/login";
		
		String token = TokenUtilsConsole.generateTokenConsoleSeconds(uriInfo, "John Smith", 3);	//Needs at least 3 seconds to validate on server.
		
		Client cl = ClientBuilder.newClient();
		WebTarget target = cl.target("http://localhost:8080/JWT-SecuringJAXRS/res/protected");
		
		Response response = target
				.request()
				.header("Authorization", "Bearer " + token)
				.get();
		System.out.println("Response: " + response.readEntity(String.class));
		
		
		System.out.println("\nWaiting for token getting expired...");
		Thread.sleep(3000);
		
		String st2 = target
				.request()
				.header("Authorization", "Bearer " + token)
				.get(String.class);
		
		System.out.println("Response: " + st2);
	}

	public static void main(String[] args) {
		try {
			new ClientProtectedFail();
		} catch (Exception e) {
			System.out.println("Exception From Server: " + e.getMessage());
		}
	}
}
