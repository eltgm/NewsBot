package ru.paradigma.newsbot.exception;

public class UserAlreadyRegisteredException extends RuntimeException{
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
