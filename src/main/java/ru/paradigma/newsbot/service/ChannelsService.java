package ru.paradigma.newsbot.service;

import ru.paradigma.newsbot.domain.Channel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ChannelsService {
    CompletableFuture<List<Channel>> getAllChannels();

    void addChannel(Channel channel);

    void deleteChannel(String id);
}
