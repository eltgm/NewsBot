package ru.paradigma.newsbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.paradigma.newsbot.domain.NewsUser;
import ru.paradigma.newsbot.exception.UserAlreadyRegisteredException;
import ru.paradigma.newsbot.exception.UserNotFoundException;
import ru.paradigma.newsbot.repository.UsersRepository;

import java.util.UUID;

@Async
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByToken(String token) {
        final var userOptional = usersRepository.findByToken(token);
        if (userOptional.isEmpty()) {
            throw new BadCredentialsException("Пользователь с таким токеном не найден!");
        }
        final var newsUser = userOptional.get();
        return new User(newsUser.getUsername(), newsUser.getPassword(), true, true, true, true,
                AuthorityUtils.createAuthorityList("USER"));
    }

    @Override
    public String login(NewsUser newsUser) {
        final var userOptional = usersRepository.findByUsername(newsUser.getUsername());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Пользователь с указанным логином не найден!");
        }
        var userFromDB = userOptional.get();

        if (!bCryptPasswordEncoder.matches(newsUser.getPassword(), userFromDB.getPassword())) {
            throw new UserNotFoundException("Неверный пароль!");
        }

        var token = UUID.randomUUID().toString();
        userFromDB.setToken(token);
        usersRepository.save(userFromDB);

        return token;
    }

    @Override
    public String registration(NewsUser newsUser) {
        var userFromDB = usersRepository.findByUsername(newsUser.getUsername());

        if (userFromDB.isPresent()) {
            throw new UserAlreadyRegisteredException("Пользователь уже зарегистрирован!");
        }

        newsUser.setPassword(bCryptPasswordEncoder.encode(newsUser.getPassword()));
        var token = UUID.randomUUID().toString();
        newsUser.setToken(token);
        usersRepository.save(newsUser);
        return token;
    }
}
