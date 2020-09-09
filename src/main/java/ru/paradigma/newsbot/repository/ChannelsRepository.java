package ru.paradigma.newsbot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.paradigma.newsbot.domain.Channel;

public interface ChannelsRepository extends MongoRepository<Channel, String> {
}
