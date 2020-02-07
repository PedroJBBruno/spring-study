package com.pedrojbbruno.microservice.personservice.repository;

import com.pedrojbbruno.microservice.personservice.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
