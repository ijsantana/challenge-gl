package com.globalLogic.challenge.infra.repository;

import com.globalLogic.challenge.core.model.Phone;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<Phone,Long> {
}
