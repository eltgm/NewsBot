package ru.paradigma.newsbot.service;

import ru.paradigma.newsbot.domain.News;
import ru.paradigma.newsbot.domain.Status;

import java.util.List;

public interface NewsService {
    List<Status> sendNews(News news);
}
