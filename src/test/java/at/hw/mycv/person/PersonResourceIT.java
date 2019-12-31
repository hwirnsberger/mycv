package at.hw.mycv.person;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.MatcherAssert.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.bind.JsonbBuilder;
import java.time.LocalDate;

public class PersonResourceIT {

    public static Person testPerson = new Person();

    @BeforeAll
    public static void setup() {
        RestAssured.basePath = PersonResource.PERSONS_PATH;

        testPerson.name = "Herbert Wirnsberger";
        testPerson.birthDate = LocalDate.of(1985, 6, 16);
    }

    @Test
    public void testCreatePerson() {

        Person personCreated = createPerson();
        cleanUp(personCreated);

        assertThat(personCreated.id,CoreMatchers.notNullValue());
        assertThat(personCreated.name, CoreMatchers.equalTo(testPerson.name));
        assertThat(personCreated.birthDate, CoreMatchers.equalTo(testPerson.birthDate));

    }

    private Person createPerson() {
        String json = JsonbBuilder.newBuilder().build().toJson(testPerson);
        return RestAssured.given().contentType(ContentType.JSON).body(json).post().then().statusCode(201).extract().as(Person.class);
    }

    private void cleanUp(Person personCreated) {
        RestAssured.given().pathParam("id", personCreated.id.toString()).when().delete("{id}").then()
                .statusCode(204);
    }

    @Test
    public void testSearchPerson() {
        Person personCreated = createPerson();

        Person personSearched = RestAssured.given().pathParam("name", personCreated.name).when().get(PersonResource.SEARCH_PATH).then()
                .statusCode(200).extract().as(Person.class);

        assertThat(personSearched.id,CoreMatchers.equalTo(personCreated.id));

        cleanUp(personCreated);
    }

    @Test
    public void testDeletePerson() {
        cleanUp(createPerson());
        RestAssured.given().pathParam("name", testPerson.name).when().get(PersonResource.SEARCH_PATH).then()
                .statusCode(204);
    }
}