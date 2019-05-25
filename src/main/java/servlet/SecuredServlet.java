package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import token_interceptor.CheckJWTToken;

@WebServlet("/servlet")
@CheckJWTToken
public class SecuredServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/plain");
    	PrintWriter out = response.getWriter();
    	
        String webName = null;
        if (request.getUserPrincipal() != null) {
            webName = request.getUserPrincipal().getName();
        }
        
        out.print(
        		"This is a protected servlet \n" +
                "Username: " + webName + "\n" +
                "User has role \"admin\": " + request.isUserInRole("admin") + "\n" +
                "User has role \"boss\": " + request.isUserInRole("boss") + "\n"
        );
        
        response.flushBuffer();
    }

}
