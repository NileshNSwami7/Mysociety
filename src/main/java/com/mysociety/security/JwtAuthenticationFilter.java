package com.mysociety.security;

import java.io.IOException;
import java.nio.file.PathMatcher;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mysociety.service.SaveRefreshTokenService;
import com.mysociety.serviceimpl.UserSecurityServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtutils;

	@Autowired
	private UserSecurityServiceImpl userservicefilter;

	@Autowired
	private PathSecuritProperties pathsecurityproperties;
	
	@Autowired
	private SaveRefreshTokenService refreshTokenService;

	private final AntPathMatcher pathmathcer = new AntPathMatcher();

	public JwtAuthenticationFilter(JwtUtils jwtutils, UserSecurityServiceImpl userservicefilter) {
		super();
		this.jwtutils = jwtutils;
		this.userservicefilter = userservicefilter;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// check requestPath and redirect to the controller
		String path = request.getRequestURI();
		// ✅ Skip JWT filter for public/authentication endpoints
//		if (path.startsWith("/generate-token") || path.startsWith("/register") || path.startsWith("/public")
//				|| path.startsWith("/refresh-access-token") || path.startsWith("/mysociety/api/user/**")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
			List<String> paths = pathsecurityproperties.getPublicpaths();
		if (paths.stream().anyMatch(p -> pathmathcer.match(p, path))) {
			filterChain.doFilter(request, response);
			return;
		}

		String username = null;
		String token = null;

		// when you set the header by using local storage then you can use this code
		/*
		 * final String authheader = request.getHeader("Authorization"); String username
		 * = null; String token=null;
		 * 
		 * if(authheader != null && authheader.startsWith("Bearer ")) { token =
		 * authheader.substring(7);
		 * 
		 * try { username = jwtutils.extractUsername(token); }catch(ExpiredJwtException
		 * e) { e.printStackTrace(); System.out.println("Token has expired");
		 * }catch(Exception e) { e.printStackTrace(); System.out.println("error"); } }
		 * else { System.out.println("Invalid token not started with Bearer string"); }
		 */

		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("access_token".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}

		if (token != null)
			{
			try {
				username = jwtutils.extractUsername(token);
				if(!jwtutils.validateToken(token, username)) {
					addCorsHeaders(response);
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
				
			} catch (ExpiredJwtException e) {
//				this.refreshTokenService.isrefreshTokenValidate(token);
				System.out.println("Acces Token has expired");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				addCorsHeaders(response);

//				response.getWriter().write("Access token expired");
				return;
			} catch (Exception e) {
				System.out.println("Invalid token");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				addCorsHeaders(response);

//				response.getWriter().write("Invalid token");
				return;
			}
		} else {
			System.out.println("No token found in cookies");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			addCorsHeaders(response);
			return ;
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userdtails = this.userservicefilter.loadUserByUsername(username);
			if (jwtutils.validateToken(token, userdtails.getUsername())) {

				UsernamePasswordAuthenticationToken usernampasswordauthetication = new UsernamePasswordAuthenticationToken(
						userdtails, null, userdtails.getAuthorities());

				usernampasswordauthetication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernampasswordauthetication);
			} else {
				 System.out.println("Token is not valid");
			        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			        response.getWriter().write("Invalid token");
			        return;
			    }
			} else if (username == null) {
			    System.out.println("Username is null — token invalid or extraction failed");
			    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			    response.getWriter().write("Token invalid or missing username");
			    return;
			}

			filterChain.doFilter(request, response);
	}
	
	private void addCorsHeaders(HttpServletResponse response) {
	    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
	}

}
