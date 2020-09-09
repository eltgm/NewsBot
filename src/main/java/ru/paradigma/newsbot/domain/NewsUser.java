package ru.paradigma.newsbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "user")
@NoArgsConstructor
@AllArgsConstructor
public class NewsUser {
    @Id
    private String id;
    private String username;
    private String password;
    private String token;
}
