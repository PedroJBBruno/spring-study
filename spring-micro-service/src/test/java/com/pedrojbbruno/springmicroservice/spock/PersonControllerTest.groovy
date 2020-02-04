package com.pedrojbbruno.springmicroservice.spock

import com.fasterxml.jackson.databind.ObjectMapper
import com.pedrojbbruno.springmicroservice.assemblers.PersonRepresentationModelAssembler
import com.pedrojbbruno.springmicroservice.entities.Person
import com.pedrojbbruno.springmicroservice.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.hateoas.EntityModel
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification
import spock.mock.DetachedMockFactory


@AutoConfigureMockMvc
@WebMvcTest
class PersonControllerTest extends Specification {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonRepresentationModelAssembler assembler;

    def "when i post a new person, it shall return 201 and the person created"() {
        given: "i have a new person"
        Person person = new Person("Pedro",
                46042432800,
                "18/10/1997",
                "rua teste",
                "cidade teste",
                "bairro teste",
                100,
                "estado teste",
                "pais teste")
        repository.save(_) >> person
        assembler.toModel(person) >> new EntityModel<Person>(person)

        expect: "to have the Status 201 and the response is the new person"
        mvc.perform(MockMvcRequestBuilders
            .post("/persons")
            .content(asJsonString(person))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(person.getName()))
    }

    def "when i get all persons, it shall return 200 and the array of persons"() {
        given: "i have a list of persons"
        List<Person> persons = new ArrayList<>([
                new Person("Pedro",
                        46042432800,
                        "18/10/1997",
                        "rua teste",
                        "cidade teste",
                        "bairro teste",
                        100,
                        "estado teste",
                        "pais teste"),
                new Person("Pedro2",
                        46042432800,
                        "18/10/1997",
                        "rua teste",
                        "cidade teste",
                        "bairro teste",
                        100,
                        "estado teste",
                        "pais teste"),
                new Person("Pedro3",
                        46042432800,
                        "18/10/1997",
                        "rua teste",
                        "cidade teste",
                        "bairro teste",
                        100,
                        "estado teste",
                        "pais teste")
        ]).asList()
        repository.findAll() >> persons
        assembler.toModel(persons[0]) >> new EntityModel<Person>(persons[0])
        assembler.toModel(persons[1]) >> new EntityModel<Person>(persons[1])
        assembler.toModel(persons[2]) >> new EntityModel<Person>(persons[2])

        expect: "to have the Status 200 and the response is the person list"
        mvc.perform(MockMvcRequestBuilders
                .get("/persons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("\$._embedded.persons.length()").value(persons.size()))
    }

    def "when i get a person, it shall return 200 and the person"() {
        given: "i have a person"
        Person person = new Person("Pedro",
                46042432800,
                "18/10/1997",
                "rua teste",
                "cidade teste",
                "bairro teste",
                100,
                "estado teste",
                "pais teste")
        repository.findById(1L) >> new Optional<Person>(person)
        assembler.toModel(person) >> new EntityModel<Person>(person)

        expect: "to have the Status 200 and the response is the person"
        mvc.perform(MockMvcRequestBuilders
                .get("/persons/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(person.getName()))
    }

    def "when i update a person, it shall return 200 and the updated person"() {
        given: "i have a person and its updated version"
        Person person = new Person("Pedro",
                46042432800,
                "18/10/1997",
                "rua teste",
                "cidade teste",
                "bairro teste",
                100,
                "estado teste",
                "pais teste")
        Person personUpdated = new Person("Augusto",
                46042432800,
                "18/10/1997",
                "rua teste",
                "cidade teste",
                "bairro teste",
                100,
                "estado teste",
                "pais teste")
        repository.findById(1L) >> new Optional<Person>(person)
        repository.save(_) >> personUpdated
        assembler.toModel(personUpdated) >> new EntityModel<Person>(personUpdated)

        expect: "to have the Status 200 and the response is the new person"
        mvc.perform(MockMvcRequestBuilders
                .put("/persons/1")
                .content(asJsonString(personUpdated))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(personUpdated.getName()))
    }

    def "when i delete a person, it shall return 204"() {
        given: "i have a person"
        Person person = new Person("Pedro",
                46042432800,
                "18/10/1997",
                "rua teste",
                "cidade teste",
                "bairro teste",
                100,
                "estado teste",
                "pais teste")
        repository.findById(1L) >> new Optional<Person>(person)

        expect: "to have the Status 204"
        mvc.perform(MockMvcRequestBuilders
                .delete("/persons/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    def "when i delete a non existent person, it shall return 404"() {
        given: "i dont have a person"
        repository.findById(1L) >> Optional.empty()

        expect: "to have the Status 404 and the response is a custom error"
        mvc.perform(MockMvcRequestBuilders
                .delete("/persons/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not find person 1"))
    }

    def "when i get a non existent person, it shall return 404"() {
        given: "i dont have a person"
        repository.findById(1L) >> Optional.empty()

        expect: "to have the Status 404 and the response is a custom error"
        mvc.perform(MockMvcRequestBuilders
                .get("/persons/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not find person 1"))
    }

    def "when i update a non existent person, it shall return 200 and the new person"() {
        given: "i have a person"
        Person person = new Person("Pedro",
                46042432800,
                "18/10/1997",
                "rua teste",
                "cidade teste",
                "bairro teste",
                100,
                "estado teste",
                "pais teste")
        repository.findById(1L) >> Optional.empty()
        repository.save(_) >> person
        assembler.toModel(person) >> new EntityModel<Person>(person)

        expect: "to have the Status 200 and the response is the new person"
        mvc.perform(MockMvcRequestBuilders
                .put("/persons/1")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(person.getName()))
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @TestConfiguration
    static class MockConfig {
        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        PersonRepository personRepository() {
            return detachedMockFactory.Stub(PersonRepository)
        }

        @Bean
        PersonRepresentationModelAssembler personRepresentationModelAssembler() {
            return detachedMockFactory.Stub(PersonRepresentationModelAssembler)
        }
    }
}
