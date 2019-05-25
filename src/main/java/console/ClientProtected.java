package console;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

public class ClientProtected {
	
	public ClientProtected() throws Exception {
		
		Client cl = ClientBuilder.newClient();
		WebTarget loginTarget = cl.target("http://localhost:8080/JWT-SecuringJAXRS/res/login");
		WebTarget target = cl.target("http://localhost:8080/JWT-SecuringJAXRS/res/protected");
		
		Form form = new Form();
		form.param("name", "John Smith");
		form.param("password", "secret");
		
		Response loginResponse = loginTarget
				.request()
				.post(Entity.form(form));
//		Response loginResponse = loginTarget.request().get();
		
		String token = loginResponse.readEntity(String.class);
		System.out.println("Token: " + token);
		
		Response response = target
				.request()
				.header("Authorization", "Bearer " + token)
				.get();
		
		System.out.println("Response: " + response);
		System.out.println("Entity: " + response.readEntity(String.class));

	}
	
	public static void main(String[] args) {
		try {
			new ClientProtected();
		} catch (Exception e) {
			System.out.println("Exception From Server: " + e.getMessage());
		}
	}
}
