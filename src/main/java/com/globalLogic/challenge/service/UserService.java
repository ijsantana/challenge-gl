package com.globalLogic.challenge.service;

import com.globalLogic.challenge.dto.UserDto;
import com.globalLogic.challenge.model.Phone;
import com.globalLogic.challenge.model.User;
import com.globalLogic.challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Object createNewUser(UserDto userDto) {
        log.info("Creating new User with name {} and email {}", userDto.getName(), userDto.getEmail());
        userRepository.save(new User()
                .withName(userDto.getName())
                .withEmail(userDto.getEmail())
                .withPassword(userDto.getPassword())
                .withUserToken(this.createNewUserToken())
                .withPhones(this.createPhoneList(userDto)));
    }

    private List<Phone> createPhoneList(UserDto userDto) {
        return null;
    }

    private String createNewUserToken(){
        return UUID.randomUUID().toString();
    }

    public Page<User> getAllUsers(Integer page) {
        return null;
    }

    public Object isAuthenticated(String token) {
        return null;
    }

    public Object generateNewLogin(UserDto userDto) {
        return null;
    }


}
