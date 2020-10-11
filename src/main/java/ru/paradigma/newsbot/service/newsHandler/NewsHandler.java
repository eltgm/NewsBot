package ru.paradigma.newsbot.service.newsHandler;


import ru.paradigma.newsbot.domain.News;
import ru.paradigma.newsbot.domain.Status;

import java.util.List;

public interface NewsHandler {
    String getName();

    List<Status> sendNews(News news);
}
