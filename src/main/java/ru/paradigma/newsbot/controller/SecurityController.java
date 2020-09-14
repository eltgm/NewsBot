package ru.paradigma.newsbot.controller;

import ru.paradigma.newsbot.domain.NewsUser;

import java.util.concurrent.Callable;

public interface SecurityController {
    Callable<NewsUser> registration(String username, String password);
}
