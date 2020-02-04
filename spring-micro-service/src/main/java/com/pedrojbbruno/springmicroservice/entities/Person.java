package com.pedrojbbruno.springmicroservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long cpf;
    private String birthday;
    private String adressStreet;
    private String adressCity;
    private String adressNeighbourhood;
    private Integer adressNumber;
    private String adressState;
    private String adressCountry;

    public Person() {
    }

    public Person(String name,
                  Long cpf,
                  String birthday,
                  String adressStreet,
                  String adressCity,
                  String adressNeighbourhood,
                  Integer adressNumber,
                  String adressState,
                  String adressCountry) {
        this.name = name;
        this.cpf = cpf;
        this.birthday = birthday;
        this.adressStreet = adressStreet;
        this.adressCity = adressCity;
        this.adressNeighbourhood = adressNeighbourhood;
        this.adressNumber = adressNumber;
        this.adressState = adressState;
        this.adressCountry = adressCountry;
    }

    public void updatePerson(Person updatePerson) {
        this.setName(updatePerson.getName());
        this.setCpf(updatePerson.getCpf());
        this.setBirthday(updatePerson.getBirthday());
        this.setAdressStreet(updatePerson.getAdressStreet());
        this.setAdressCity(updatePerson.getAdressCity());
        this.setAdressNeighbourhood(updatePerson.getAdressNeighbourhood());
        this.setAdressNumber(updatePerson.getAdressNumber());
        this.setAdressState(updatePerson.getAdressState());
        this.setAdressCountry(updatePerson.getAdressCountry());
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Long getCpf() { return cpf; }

    public void setCpf(Long cpf) { this.cpf = cpf; }

    public String getBirthday() { return birthday; }

    public void setBirthday(String birthday) { this.birthday = birthday; }

    public String getAdressStreet() { return adressStreet; }

    public void setAdressStreet(String adressStreet) { this.adressStreet = adressStreet; }

    public String getAdressCity() { return adressCity; }

    public void setAdressCity(String adressCity) { this.adressCity = adressCity; }

    public String getAdressNeighbourhood() { return adressNeighbourhood; }

    public void setAdressNeighbourhood(String adressNeighbourhood) { this.adressNeighbourhood = adressNeighbourhood; }

    public Integer getAdressNumber() { return adressNumber; }

    public void setAdressNumber(Integer adressNumber) { this.adressNumber = adressNumber; }

    public String getAdressState() { return adressState; }

    public void setAdressState(String adressState) { this.adressState = adressState; }

    public String getAdressCountry() { return adressCountry; }

    public void setAdressCountry(String adressCountry) { this.adressCountry = adressCountry; }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf=" + cpf +
                ", birthday=" + birthday +
                ", adressStreet='" + adressStreet + '\'' +
                ", adressCity='" + adressCity + '\'' +
                ", adressNeighbourhood='" + adressNeighbourhood + '\'' +
                ", adressNumber=" + adressNumber +
                ", adressState='" + adressState + '\'' +
                ", adressContry='" + adressCountry + '\'' +
                '}';
    }
}
