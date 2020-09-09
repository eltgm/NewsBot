package ru.paradigma.newsbot.controller;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface NewsController {
    void createNews(HttpServletRequest request, HttpServletResponse response);
}
