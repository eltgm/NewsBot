package ru.paradigma.newsbot.controller;

import java.util.concurrent.Callable;

public interface SecurityController {
    Callable<String> registration(String username, String password);

    Callable<String> auth(String username, String password);
}
