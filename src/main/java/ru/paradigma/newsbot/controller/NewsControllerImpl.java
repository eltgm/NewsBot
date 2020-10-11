package ru.paradigma.newsbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.paradigma.newsbot.conf.integration.NewsGateway;
import ru.paradigma.newsbot.domain.Status;
import ru.paradigma.newsbot.domain.dto.NewsDto;
import ru.paradigma.newsbot.util.DtoParser;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsControllerImpl implements NewsController {
    private final DtoParser dtoParser;
    private final NewsGateway newsGateway;

    @Override
    @PostMapping("/api/news")
    public List<Status> createNews(NewsDto newsDto) {
        return newsGateway.sendNews(dtoParser.newsDtoToPojo(newsDto));
    }
}
