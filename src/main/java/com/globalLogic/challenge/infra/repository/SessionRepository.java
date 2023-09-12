package com.globalLogic.challenge.infra.repository;

import com.globalLogic.challenge.core.model.Session;
import com.globalLogic.challenge.core.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session,Long> {

    Optional<Session> findBySessionToken(String sessionToken);

    List<Session> findAllByIsActiveAndUser(boolean isActive, User user);

    List<Session> findAllByIsActive(boolean isActive);

    @Query(value = "select s from Session as s join User as u on u.id = s.user.id where u.id = :idUser and s.created IN (SELECT max(s.created) FROM Session as s join User as u on u.id = s.user.id where u.id = :idUser)")
    Optional<Session> getLastSession(@Param("idUser") Long idUser);
}
