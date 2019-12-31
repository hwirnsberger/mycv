package at.hw.mycv.person;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

import java.time.LocalDate;

public class Person extends PanacheMongoEntity {

    public String name;
    public LocalDate birthDate;

    public static Person findByName(String name) {
        return find("name", name).firstResult();
    }
}