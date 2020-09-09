package ru.paradigma.newsbot.conf.security;


import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ru.paradigma.newsbot.domain.Response;
import ru.paradigma.newsbot.exception.TokenNotFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public AuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        final var tokenParam = Optional.ofNullable(httpServletRequest.getHeader(AUTHORIZATION));
        if (tokenParam.isEmpty()) {
            throw new TokenNotFoundException("Не найден токен!");
        }
        var token = tokenParam.get();
        token = token.substring(7);
        final var requestAuthentication = new UsernamePasswordAuthenticationToken(token, token);

        return getAuthenticationManager().authenticate(requestAuthentication);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        sendError(response, HttpStatus.UNAUTHORIZED.value(), failed.getLocalizedMessage(), failed);
    }

    private void sendError(HttpServletResponse response, int code, String message, Exception e) throws IOException {
        SecurityContextHolder.clearContext();

        Response<String> exceptionResponse =
                new Response<>(Response.STATUES_FAILURE, message);

        exceptionResponse.send(response, code);
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
