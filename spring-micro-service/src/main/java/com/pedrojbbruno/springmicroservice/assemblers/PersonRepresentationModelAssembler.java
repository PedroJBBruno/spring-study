package com.pedrojbbruno.springmicroservice.assemblers;

import com.pedrojbbruno.springmicroservice.controller.PersonController;
import com.pedrojbbruno.springmicroservice.entities.Person;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonRepresentationModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {

    @Override
    public EntityModel<Person> toModel(Person person) {
        return new EntityModel<>(person,
                linkTo(methodOn(PersonController.class).one(person.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).all()).withRel("persons"));
    }
}
