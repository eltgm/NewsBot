package ru.paradigma.newsbot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.paradigma.newsbot.domain.NewsUser;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<NewsUser, String> {
    Optional<NewsUser> findByUsername(String username);

    Optional<NewsUser> findByToken(String token);
}
