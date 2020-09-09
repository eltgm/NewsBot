package ru.paradigma.newsbot.conf.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.paradigma.newsbot.service.UserService;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final UserService userService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {

    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        final var token = (String) usernamePasswordAuthenticationToken.getCredentials();
        return userService.findByToken(token);
    }
}