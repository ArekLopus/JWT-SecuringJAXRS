package jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import token_interceptor.CheckJWTToken;

// http://localhost:8080/JWT-SecuringJAXRS/res/public
// http://localhost:8080/JWT-SecuringJAXRS/res/protected

// http://localhost:8080/JWT-SecuringJAXRS/res/login

@Path("/")
public class TestResources {
    
	
	@GET
    @Path("public")
    public Response echo() {
        return Response.ok().entity("This is a message from the public resource").build();
    }
    
	
	@CheckJWTToken
    @GET
    @Path("protected")
    public Response echoWithJWTToken() {
        return Response.ok().entity("This is a message from the protected resource").build();
    }
    
    
}
