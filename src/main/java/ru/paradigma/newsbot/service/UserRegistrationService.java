package ru.paradigma.newsbot.service;

import ru.paradigma.newsbot.domain.NewsUser;

public interface UserRegistrationService {
    NewsUser registration(NewsUser newsUser);

}
