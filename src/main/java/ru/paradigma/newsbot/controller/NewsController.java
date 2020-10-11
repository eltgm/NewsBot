package ru.paradigma.newsbot.controller;

import ru.paradigma.newsbot.domain.Status;
import ru.paradigma.newsbot.domain.dto.NewsDto;

import java.util.List;

public interface NewsController {
    List<Status> createNews(NewsDto newsDto);
}
