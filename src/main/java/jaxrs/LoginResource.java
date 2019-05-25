package jaxrs;

import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import token_generator.TokenUtils;

@Path("/")
public class LoginResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    TokenUtils tu;

    
    @GET
    @Path("/login")
    public Response authenticateUserGet() {

        try {

            System.out.println("#### login !");

            //authenticate(name, password);

            // Generates a token
            String token = tu.generateToken(uriInfo, "John Smith", 2);
            System.out.println("Token: " + token);
            
            // Return the token on the response
            return Response.ok(token).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            
        } catch (Exception e) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }
    

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("name") String name, @FormParam("password") String password) {

        try {

            System.out.println("#### login - name/password : " + name + "/" + password);
            
            if(!name.equals("John Smith")) {
            	throw new AuthenticationException("Credentials not valid.");
            }
            
            //authenticate(name, password);
            
            // Generates a token
            String token = tu.generateToken(uriInfo, name, 2);
            
            // Return the token on the response
            return Response.ok(token).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            
        } catch (Exception e) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }
    
    
    
//    private void authenticate(String name, String password) throws Exception {
//        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN_PASSWORD, User.class);
//        query.setParameter("login", name);
//        query.setParameter("password", PasswordUtils.digestPassword(password));
//        User user = query.getSingleResult();
//
//        if (user == null)
//            throw new SecurityException("Invalid user/password");
//    }
    
    
}