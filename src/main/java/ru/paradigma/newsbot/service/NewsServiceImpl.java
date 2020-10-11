package ru.paradigma.newsbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.paradigma.newsbot.domain.News;
import ru.paradigma.newsbot.domain.Status;
import ru.paradigma.newsbot.service.newsHandler.NewsHandler;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final List<NewsHandler> handlers;

    @Override
    public List<Status> sendNews(News news) {
        for (NewsHandler handler : handlers) {
            if (handler.getName().equals(news.getMessenger())) {
                try {
                    return handler.sendNews(news);
                } catch (Exception ex) {
                    return List.of(Status.builder()
                            .message("Не найден handler")
                            .from(news.getMessenger())
                            .build());
                }
            }
        }

        return List.of(Status.builder()
                .message("Не найден handler")
                .from(news.getMessenger())
                .build());
    }
}

