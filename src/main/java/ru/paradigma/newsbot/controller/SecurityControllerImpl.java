package ru.paradigma.newsbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.paradigma.newsbot.domain.NewsUser;
import ru.paradigma.newsbot.service.UserService;

import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
public class SecurityControllerImpl implements SecurityController {
    private final UserService userService;

    @Override
    @PostMapping("/api/user/registration")
    public Callable<String> registration(String username, String password) {
        return () -> userService.registration(NewsUser.builder()
                .username(username)
                .password(password)
                .build());
    }

    @Override
    @PostMapping("/api/user/auth")
    public Callable<String> auth(String username, String password) {
        return () -> userService.login(NewsUser.builder()
                .username(username)
                .password(password)
                .build());
    }
}
