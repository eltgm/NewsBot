package ru.paradigma.newsbot.service;

import org.springframework.security.core.userdetails.User;
import ru.paradigma.newsbot.domain.NewsUser;

public interface UserService {
    User findByToken(String token);

    String login(NewsUser newsUser);

    String registration(NewsUser newsUser);
}
