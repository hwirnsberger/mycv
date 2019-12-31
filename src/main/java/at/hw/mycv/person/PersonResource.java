package at.hw.mycv.person;

import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path(PersonResource.PERSONS_PATH)
@Consumes("application/json")
@Produces("application/json")
public class PersonResource {

    public static final String PERSONS_PATH = "/persons";
    public static final String SEARCH_PATH = "/search/{name}";
    public static final String COUNT_PATH = "/count";

    @GET
    public List<Person> list() {
        return Person.listAll();
    }

    @GET
    @Path("/{id}")
    public Person get(String id) {
        return Person.findById(id);
    }

    @POST
    public Response create(Person person) {
        person.persist();
        return Response.status(201).entity(person).build();
    }

    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") String id, Person person) {
        person.update();
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
        Person person = Person.findById(new ObjectId(id));
        person.delete();
    }

    @GET
    @Path(PersonResource.SEARCH_PATH)
    public Person search(@PathParam("name") String name) {
        return Person.findByName(name);
    }

    @GET
    @Path("/count")
    public Long count() {
        return Person.count();
    }
}