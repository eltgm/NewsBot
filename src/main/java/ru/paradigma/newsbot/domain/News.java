package ru.paradigma.newsbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    private String id;
    private String text;
    private List<File> photos;
    private Date date;
}
