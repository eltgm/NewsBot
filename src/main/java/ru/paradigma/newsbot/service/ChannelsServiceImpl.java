package ru.paradigma.newsbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paradigma.newsbot.domain.Channel;
import ru.paradigma.newsbot.repository.ChannelsRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Async
@Service
@RequiredArgsConstructor
public class ChannelsServiceImpl implements ChannelsService {
    private final ChannelsRepository channelsRepository;

    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<Channel>> getAllChannels() {
        return CompletableFuture.completedFuture(channelsRepository.findAll());
    }

    @Override
    @Transactional
    public void addChannel(Channel channel) {
        channelsRepository.save(channel);
    }

    @Override
    @Transactional
    public void deleteChannel(String id) {
        channelsRepository.delete(Channel.builder()
                .id(id)
                .build());
    }
}
