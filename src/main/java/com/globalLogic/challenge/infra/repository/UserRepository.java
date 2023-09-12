package com.globalLogic.challenge.infra.repository;

import com.globalLogic.challenge.core.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long>  {

    Page<User> findAll(Pageable page);

    Optional<User> findByMailAndPassword(String name, String passowrd);

    boolean existsByMail(String mail);

}
