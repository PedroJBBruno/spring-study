package com.pedrojbbruno.microservice.personservice.controller;

import com.pedrojbbruno.microservice.personservice.entities.Person;
import com.pedrojbbruno.microservice.personservice.assemblers.PersonRepresentationModelAssembler;
import com.pedrojbbruno.microservice.personservice.exceptions.PersonNotFoundException;
import com.pedrojbbruno.microservice.personservice.repository.PersonRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PersonController {

    private final PersonRepository repository;
    private final PersonRepresentationModelAssembler assembler;

    public PersonController(PersonRepository repository, PersonRepresentationModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Cacheable(value = "persons")
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Person>> all() {
        return new CollectionModel<>(repository.findAll().stream()
                .map(assembler::toModel).collect(Collectors.toList()),
                linkTo(methodOn(PersonController.class).all()).withSelfRel());
    }

    @CacheEvict(value = "persons", allEntries = true)
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Person> newPerson(@RequestBody Person person) {
        return assembler.toModel(repository.save(person));
    }

    @Cacheable(value = "person", key = "#id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Person> one(@PathVariable Long id) {
        return assembler.toModel(repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id)));
    }


    @CacheEvict(value = "person", key = "#id")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Person> replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return repository.findById(id)
                .map(person -> {
                    person.updatePerson(newPerson);
                    System.out.println(person);
                    return assembler.toModel(repository.save(person));
                })
                .orElseGet(() -> {
                    return assembler.toModel(repository.save(newPerson));
                });
    }

    @Caching(evict = {
            @CacheEvict(value = "person", key = "#id"),
            @CacheEvict(value = "persons", allEntries = true)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long id) {
        repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        repository.deleteById(id);
    }
}
