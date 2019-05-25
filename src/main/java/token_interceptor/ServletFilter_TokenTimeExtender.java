package token_interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import token_generator.TokenUtilsConsole;

@WebFilter(filterName = "MyFilter", urlPatterns = { "/*" })
public class ServletFilter_TokenTimeExtender implements Filter {

	private HttpServletRequest req;
	private HttpServletResponse res;
	
	private String authHeader;
	private Cookie cookie;
	
	private int timeToExtendToInSeconds = 120;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		req = (HttpServletRequest) request;
		res = (HttpServletResponse) response;

		checkAuthHeader();
		checkCookie();

		// No Header and Cookie
		if ((authHeader == null || !authHeader.startsWith("Bearer")) && cookie == null) {
			System.out.println("--- Servlet Filter - No Header and Cookie ---");
		}

		chain.doFilter(request, response);

	}

	
	// For Authorization header we need to pass token back and forth between a client and the server in EVERY request and response
	private void checkAuthHeader() {
		
		authHeader = req.getHeader("Authorization");
		
		// Check if 'Authorization' header exists and starts with 'Bearer'
		if (authHeader != null && authHeader.startsWith("Bearer")) {
			System.out.println("--- Servlet Filter --- Header!!! " + authHeader);

			String token = authHeader.substring("Bearer".length()).trim();
			long timeToLive = TokenUtilsConsole.tokenTimeToLive(token);
			System.out.println("Header's Token Time to Live: " + timeToLive);

			// Header is not removed like Cookie after its lifetime expires, so we also have to check if time to live is more or equal to 0.
			if (timeToLive < 90 && timeToLive >= 0) {

				String tokenWithAddedTime = TokenUtilsConsole.addTimeToToken(token, timeToExtendToInSeconds);
				System.out.println("Header's Token Time to Live Extended To: " + timeToExtendToInSeconds);

				res.setHeader("Authorization", "Bearer " + tokenWithAddedTime);

			} else {

				if (timeToLive < 0) {
					res.setHeader("Authorization", "expired");	// Token expired
				} else {
					res.setHeader("Authorization", authHeader);	// Token valid but no time extension (setting the same Token)
				}
			}
		}
	}
	
	
	// Cookie is removed after its lifetime expires so we only need to change it when below our time limit (90 secs here)
	// In real world app cookie need to be http-only and secured for security reasons.
	private void checkCookie() {
		// Check if cookies exist and pick the 'bearer' cookie if exists
		Cookie[] cookies = req.getCookies();
		cookie = null;			// Needs to be reseted, otherwise value is kept between requests!
		
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("bearer"))
					cookie = c;
			}
		}
		
		// If cookie 'bearer' exists, and time to expire is less than 90 secs, renew the token expiration to a new value.
		if (cookie != null) {
			String token = cookie.getValue();
			System.out.println("--- Servlet Filter --- Cookie!!! " + token);

			long timeToLive = TokenUtilsConsole.tokenTimeToLive(token);
			System.out.println("Cookie's Token Time to Live: " + timeToLive);

			if (timeToLive < 90 && timeToLive >= 0) {

				String tokenWithAddedTime = TokenUtilsConsole.addTimeToToken(token, timeToExtendToInSeconds);
				System.out.println("Cookie's Token Time to Live Extended To: " + timeToExtendToInSeconds);

				Cookie renewCookie = new Cookie("bearer", tokenWithAddedTime);
				renewCookie.setMaxAge(timeToExtendToInSeconds);

				res.addCookie(renewCookie);
			}
		}
	}
	
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
