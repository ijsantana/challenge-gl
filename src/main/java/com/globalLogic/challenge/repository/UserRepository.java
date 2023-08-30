package com.globalLogic.challenge.repository;

import com.globalLogic.challenge.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long>  {

}
