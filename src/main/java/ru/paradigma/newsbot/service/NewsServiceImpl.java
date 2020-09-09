package ru.paradigma.newsbot.service;

import chat.tamtam.botapi.TamTamBotAPI;
import chat.tamtam.botapi.TamTamUploadAPI;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.model.*;
import chat.tamtam.botapi.queries.SendMessageQuery;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InputMedia;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import com.pengrad.telegrambot.request.SendMediaGroup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.mail.im.botapi.BotApiClientController;
import ru.mail.im.botapi.api.entity.SendFileRequest;
import ru.mail.im.botapi.api.entity.SendTextRequest;
import ru.paradigma.newsbot.domain.Channel;
import ru.paradigma.newsbot.domain.News;
import ru.paradigma.newsbot.repository.ChannelsRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

@Async
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final ChannelsRepository channelsRepository;
    private final TelegramBot telegramBot;
    private final BotApiClientController icqBot;
    private final TamTamBotAPI tamTamBot;
    private final TamTamClient tamTamClient;

    @Override
    public void createNews(News news) {
        sendNewsToTelegram(news);
        sendNewsToTamTam(news);
        sendNewsToICQ(news);
    }

    private void sendNewsToTelegram(News news) {
        channelsRepository
                .findAll()
                .stream()
                .filter(channel -> channel.getMessenger().equals("telegram"))
                .forEach(channel -> {
                    final var photos = news.getPhotos();
                    final var inputMedia = new InputMedia[photos.size()];

                    final var firstPhoto = new InputMediaPhoto(photos.get(0));
                    firstPhoto.caption(news.getText());
                    inputMedia[0] = firstPhoto;
                    for (int i = 1; i < photos.size(); i++) {
                        inputMedia[i] = new InputMediaPhoto(photos.get(i));
                    }

                    final var newsPost = new SendMediaGroup(channel.getChatId(), inputMedia);
                    telegramBot.execute(newsPost);
                });
    }

    @SneakyThrows
    private void sendNewsToTamTam(News news) {
        channelsRepository
                .findAll()
                .stream()
                .filter(channel -> channel.getMessenger().equals("tamtam"))
                .forEach(channel -> sendMessageToTamTam(channel, news));
    }

    @SneakyThrows
    private void sendMessageToTamTam(Channel channel, News news) {
        final var photos = news.getPhotos();
        final var photoBody = new ArrayList<AttachmentRequest>();

        photos.forEach(new Consumer<File>() {
            @SneakyThrows
            @Override
            public void accept(File file) {
                final var photoAttachmentRequestPayload = new PhotoAttachmentRequestPayload();

                UploadEndpoint endpoint = tamTamBot.getUploadUrl(UploadType.IMAGE).execute();
                String uploadUrl = endpoint.getUrl();
                TamTamUploadAPI uploadAPI = new TamTamUploadAPI(tamTamClient);
                var photoTokens = uploadAPI.uploadImage(uploadUrl, file).execute();

                photoTokens.getPhotos().forEach(photoAttachmentRequestPayload::putPhotosItem);

                photoBody.add(new PhotoAttachmentRequest(photoAttachmentRequestPayload));
            }
        });
        NewMessageBody body = new NewMessageBody(news.getText(), photoBody
                , null);
        SendMessageQuery sendMessageQuery = tamTamBot.sendMessage(body).chatId(Long.valueOf(channel.getChatId()));

        sendMessageQuery.execute();
    }

    private void sendNewsToICQ(News news) {
        channelsRepository
                .findAll()
                .stream()
                .filter(channel -> channel.getMessenger().equals("icq"))
                .forEach(channel -> {
                    final var photos = news.getPhotos();

                    for (File photo : photos) {
                        final var sendFileRequest = new SendFileRequest();
                        sendFileRequest.setFile(photo);
                        sendFileRequest.setChatId(channel.getChatId());

                        try {
                            icqBot.sendFile(sendFileRequest);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    final var sendTextRequest = new SendTextRequest();
                    sendTextRequest.setChatId(channel.getChatId());
                    sendTextRequest.setText(news.getText());
                    try {
                        icqBot.sendTextMessage(sendTextRequest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
