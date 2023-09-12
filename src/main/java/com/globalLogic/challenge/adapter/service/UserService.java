package com.globalLogic.challenge.adapter.service;

import com.globalLogic.challenge.adapter.Utils.PasswordValidations;
import com.globalLogic.challenge.core.dto.LoginDto;
import com.globalLogic.challenge.core.dto.UserDto;
import com.globalLogic.challenge.adapter.mapper.PhoneMapper;
import com.globalLogic.challenge.core.exception.AttributeException;
import com.globalLogic.challenge.core.exception.UserException;
import com.globalLogic.challenge.core.model.Phone;
import com.globalLogic.challenge.core.model.User;
import com.globalLogic.challenge.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Creación de un nuevo usuario. Se genera un User_Token único para cada user dado de alta.
     * Validaciones:
     * - Si es un usuario existente (a través del mail)
     * - Validación del formato de mail. Que contenga "@" y "."
     * - Validación de la Password: Un Caracter en Mayúscula y al menos dos caracteres numéricos.
     *
     * @param userDto -> dto con información del Usuario a dar de alta.
     * @return user -> Devuelve el usuario generado.
     *
     * @throws UserException -> En caso de que el usuario ya exista.
     * @throws AttributeException -> En caso de que la Password o el mail estén incorrectos.
     *
     * @author ijmsantana@gmail.com
     */
    public User createNewUser(UserDto userDto) {
        log.info("Creating new User with name {} and email {}", userDto.getName(), userDto.getEmail());

        log.info("Validating existing user with email {}", userDto.getEmail());
        if(userRepository.existsByMail(userDto.getEmail()))
            throw new UserException("User already registered with mail " + userDto.getEmail());

        log.info("Validating mail");
        if(!PasswordValidations.validateMail(userDto.getEmail()))
            throw new AttributeException("The mail format is incorrect");

        log.info("Validating password");
        if(!PasswordValidations.validatePassword(userDto.getPassword()))
            throw new AttributeException("The password format is incorrect");

        User user = new User()
                .withName(userDto.getName())
                .withEmail(userDto.getEmail())
                .withPassword(userDto.getPassword())
                .withUserToken(this.createNewUserToken());

        List<Phone> phones = userDto.getPhones()
                .stream()
                .map(PhoneMapper::mapDtoToPhone)
                .peek(p->p.setUser(user))
                .toList();

        user.setPhones(phones);

        return userRepository.save(user);

    }

    private String createNewUserToken(){
        return UUID.randomUUID().toString();
    }

    public Page<User> getAllUsers(Integer page) {
        return userRepository.findAll(PageRequest.of(page, 10));
    }

    public Optional<User> getUser(LoginDto loginDto) {
        return userRepository.findByMailAndPassword(loginDto.getEmail(), loginDto.getPassword());
    }

}
