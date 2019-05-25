package console;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ClientPublic {

	public ClientPublic() {
		
		Client cl = ClientBuilder.newClient();
		WebTarget target = cl.target("http://localhost:8080/JWT-SecuringJAXRS/res/public");
		
		String st = target
				.request()
				.get(String.class);
		
		System.out.println(st);
	}

	public static void main(String[] args) {
		new ClientPublic();

	}

}
