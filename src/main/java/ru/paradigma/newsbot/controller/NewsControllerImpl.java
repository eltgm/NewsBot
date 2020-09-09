package ru.paradigma.newsbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.paradigma.newsbot.domain.News;
import ru.paradigma.newsbot.service.NewsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class NewsControllerImpl implements NewsController {
    private final NewsService newsService;

    @SneakyThrows
    @Override
    @PostMapping("/api/news")
    public void createNews(HttpServletRequest request, HttpServletResponse response) {
        var fileList = new ArrayList<File>();
        String newsText = null;
        if (request != null) {
            newsText = request.getParameter("text");
        }

        var multipart = (MultipartHttpServletRequest) request;
        var fileNames = multipart.getFileNames();
        while (fileNames.hasNext()) {
            var fileContent = multipart.getFile(fileNames.next());

            var file = new File("D:\\test\\" + fileContent.getOriginalFilename());
            fileContent.transferTo(file);
            fileList.add(file);
        }

        newsService.createNews(News.builder()
                .date(new Date())
                .photos(fileList)
                .text(newsText)
                .build());
    }
}
