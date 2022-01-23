package io.github.igormateus.repertapp.api.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.github.igormateus.repertapp.common.AppProperties;
import io.github.igormateus.repertapp.domain.model.User;
import io.github.igormateus.repertapp.domain.model.UserDetailsImpl;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static int TOKEN_EXPIRATION = AppProperties.JWT.Token.expiration * 60 * 1000;
    public static final String TOKEN_SECRET = AppProperties.JWT.Token.secret;
    static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>() // Permissions
                    ));
        } catch (IOException e) {
            throw new RuntimeException("Authentication fail", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userImpl = (UserDetailsImpl) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(userImpl.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(TOKEN_SECRET));

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
        response.getWriter().write(token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}