package ru.paradigma.newsbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.paradigma.newsbot.domain.NewsUser;
import ru.paradigma.newsbot.service.UserRegistrationService;

import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
public class SecurityControllerImpl implements SecurityController {
    private final UserRegistrationService userService;

    @Override
    @PostMapping("/api/user/registration")
    public Callable<NewsUser> registration(String username, String password) {
        return () -> userService.registration(NewsUser.builder()
                .username(username)
                .password(password)
                .build());
    }
}
