package ru.paradigma.newsbot.controller;

import ru.paradigma.newsbot.domain.Channel;

import java.util.List;
import java.util.concurrent.Callable;

public interface ChannelsController {
    Callable<List<Channel>> getAllChannels();

    void addNewChannel(Channel channel);

    void deleteChannel(String id);
}
