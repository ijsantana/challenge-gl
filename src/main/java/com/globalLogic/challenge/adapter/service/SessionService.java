package com.globalLogic.challenge.adapter.service;

import com.globalLogic.challenge.core.dto.LoginDto;
import com.globalLogic.challenge.core.dto.LoginResponse;
import com.globalLogic.challenge.core.dto.SessionDto;
import com.globalLogic.challenge.core.exception.LoginException;
import com.globalLogic.challenge.core.exception.SessionException;
import com.globalLogic.challenge.core.exception.UserException;
import com.globalLogic.challenge.adapter.mapper.SessionMapper;
import com.globalLogic.challenge.core.model.Session;
import com.globalLogic.challenge.core.model.User;
import com.globalLogic.challenge.infra.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserService userService;


    /**
     * Verifica que el usuario esté con el Login activo
     * En caso que el Login haya expirado, se modificará el status previo a devolverlo.
     * Para Ello se comparan las fechas de vencimiento con la hora actual.
     *
     * @param token -> Session Token.
     * @return Devuelve el DTO de logueo y si el mismo está activo o no.
     *
     * @author ijmsantana@gmail.com
     */
    public SessionDto isAuthenticated(String token) {
        Session session = sessionRepository.findBySessionToken(token)
                .orElseThrow(() -> new SessionException("token do not exist"));

        if (session.getDueDate().isBefore(LocalDateTime.now())) {
            session.setActive(false);
            sessionRepository.save(session);
        }

        return SessionMapper.mapSessionToDto(session);
    }


    /**
     * Creación de un nuevo Login por parte del usuario
     *
     * @param loginDto -> DTO de logueo (Mail y Password.
     * @return loginResponse -> Devuelve el resultado del login con el Session Token.
     * @throws UserException -> En caso que la password o el user sean incorrectos.
     * @throws LoginException -> En caso que falle la creación del login.
     * @author ijmsantana@gmail.com
     */
    public LoginResponse generateNewLogin(LoginDto loginDto) {

        User user = userService.getUser(loginDto)
                .orElseThrow(() -> new UserException("User do not exist or password is incorrect"));

        try {
            this.expireAllOldSessions(user);
            Optional<Session> lastSession = sessionRepository.getLastSession(user.getId());
            Session session = new Session();
            session.setActive(true);
            session.setCreated(LocalDateTime.now());
            session.setDueDate(LocalDateTime.now().plusMinutes(5));
            session.setSessionToken(this.createNewSessionToken());
            session.setUser(user);
            LocalDateTime lastLogin = lastSession.isPresent() ? lastSession.get().getCreated() : session.getCreated();
            sessionRepository.save(session);
            return SessionMapper.mapSessionToLoginResponse(session, lastLogin);
        } catch(Exception e){
            throw new LoginException("Error detected trying to login user: " + e);
        }
    }

    private String createNewSessionToken(){
        return UUID.randomUUID().toString();
    }

    private void expireAllOldSessions(User user){
        List<Session> sessions = sessionRepository.findAllByIsActiveAndUser(true, user)
                .stream()
                .peek(s-> s.setActive(false))
                .toList();
        sessionRepository.saveAll(sessions);
    }


    /**
     * Método que se encarga de validar que el login continúe estando activo.
     * Para ello corre un proceso batch cada un minuto donde se van actualizando los estados.
     *
     * @author ijmsantana@gmail.com
     */
    public void updateActivesSessions() {

        List<Session> sessions = sessionRepository.findAllByIsActive(true)
                .stream()
                .filter(s->s.getDueDate().isBefore(LocalDateTime.now()))
                .peek(s -> s.setActive(false))
                .toList();

        if(!sessions.isEmpty())
            sessionRepository.saveAll(sessions);

    }
}
