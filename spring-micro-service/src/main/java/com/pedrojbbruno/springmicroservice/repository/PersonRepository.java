package com.pedrojbbruno.springmicroservice.repository;

import com.pedrojbbruno.springmicroservice.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
