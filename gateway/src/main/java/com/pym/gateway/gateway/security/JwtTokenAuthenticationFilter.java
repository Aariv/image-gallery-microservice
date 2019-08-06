package com.pym.gateway.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filter to validate the tokens
 *
 * OncePerRequestFilter is guarntees a single exectuion per request
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //. 1 Get the authenticaction header. Tokens are supposed to be passed in authentication header
        String header = request.getHeader(jwtConfig.getHeader());

        // 2. validate the header and checks the prefix

        if(header == null || !header.startsWith(jwtConfig.getPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        // If there is no token provided and hence  the user won't be authenticated
        // It's Ok May be user is accessing the public path or asking for a token
        // All secured path needs to have a token that is configured in the security configuration
        // If user tries to access without authentication token then he won't be authenticated and exeception is thrown.

        // 3. Get the token
        String token = header.replace(jwtConfig.getPrefix(), "");

        try { // Excetions might be throwing when creating claim
            // 4. Validate the token
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();
            if(username != null) {
                List<String> authorities = (List<String>) claims.get("authorities");

                //5. Create auth object
                // UsernamePasswordAuthenticationToken: A built-in object used by spring to represent the current authenticated
                // or being authenticated user
                // It needs list of authorities, which has type of GrantedAuthority interface where SimpleGrantedAuthority is an
                // implementation of the interface

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                // 6. Authenticate the user
                // Now the user is authenticated
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (Exception e) {
            // Incase of failire we have to clear the context
            SecurityContextHolder.clearContext();
        }

        // go to the next filter in the filter chain
        filterChain.doFilter(request, response);
    }
}
