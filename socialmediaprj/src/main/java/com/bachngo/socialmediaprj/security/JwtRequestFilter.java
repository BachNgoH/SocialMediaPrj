package com.bachngo.socialmediaprj.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.service.AppUserDetailsService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter{
	
	private AppUserDetailsService userDetailsService;
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		String email = null;
		String jwt = null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
			jwt = authorizationHeader.substring(7);
			email = jwtUtil.extractEmail(jwt);
		}
		
		if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			AppUser userDetails = this.userDetailsService.loadUserByUsername(email);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}
	

}
