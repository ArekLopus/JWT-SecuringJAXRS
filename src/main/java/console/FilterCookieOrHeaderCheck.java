package console;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;

import token_generator.TokenUtilsConsole;

@SuppressWarnings("unused")
public class FilterCookieOrHeaderCheck {

	public FilterCookieOrHeaderCheck() {
		
		String uriInfo = "http://localhost:8080/JWT-SecuringJAXRS/res/login";
		String token = TokenUtilsConsole.generateTokenConsoleSeconds(uriInfo, "John Smith", 120);
		
		Client cl = ClientBuilder.newClient();
		WebTarget target = cl.target("http://localhost:8080/JWT-SecuringJAXRS/res/public");
		
		String st = target
				.request()
				//.cookie(new Cookie("bearer", token))
				.header("Authorization", "Bearer " + token)
				.get(String.class);
		
		System.out.println(st);
	}

	public static void main(String[] args) {
		new FilterCookieOrHeaderCheck();

	}

}
