/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Company;
import entity.Hobby;
import entity.Person;
import entity.Phone;
import facade.CompanyFacade;
import facade.ICompanyFacade;
import facade.IPersonFacade;
import facade.PersonFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.codehaus.plexus.util.StringUtils;
import util.PersonConverter;
import util.SmartSearch;

/**
 * REST Web Service
 *
 * @author dennisschmock
 */
@Path("person")
public class PersonService {

    @Context
    private UriInfo context;

    private static IPersonFacade perFacade = new PersonFacade(Persistence.createEntityManagerFactory("PU"));
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static PersonConverter perConv = new PersonConverter();
    private static SmartSearch ss = new SmartSearch();

    /**
     * Creates a new instance of PersonService
     */
    public PersonService() {
    }

    @GET
    @Path("complete")
    @Produces(MediaType.APPLICATION_JSON + ";charset=Cp1252")
    public String getAllPersonsComplete() {
        List<Person> persons = perFacade.getPersons();
        return perConv.personsToJson(persons);
    }

    //I create a single person list, so that I can use the same method in the converter 
    @GET
    @Path("complete/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=Cp1252")
    public String getPersonCompleteWithID(@PathParam("id") int id) {
        return perConv.personToJson(perFacade.getPerson(id));

    }
//    

    @GET
    @Path("contactinfo")
    @Produces(MediaType.APPLICATION_JSON + ";charset=Cp1252")
    public String getAllPersonsContactinfo() {
        List<Person> persons = perFacade.getPersons();
        return perConv.personsContactinfoToJson(persons);
    }
//  

    @GET
    @Path("contactinfo/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=Cp1252")
    public String getPersonContactinfoWithID(@PathParam("id") int id) {

        return perConv.personContactinfoToJson(perFacade.getPerson(id));
    }
//    

    @GET
    @Path("hobby/{hobby}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=Cp1252")
    public String getAllPersonsWithHobby(@PathParam("hobby") String hobby) {
        return perConv.personsToJson(perFacade.getPersons(new Hobby(hobby, "")));
    }

    @GET
    @Path("zip/{zip}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=Cp1252")
    public String getPersonWithZip(@PathParam("zip") String zip) {
        int zipCode = Integer.parseInt(zip);
        List<Person> persons = perFacade.getPersons(zipCode);
        return perConv.personsToJson(persons);
    }

    @GET
    @Path("phone/{phone}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=Cp1252")
    public String getPersonWithPhone(@PathParam("phone") String phone) {
        if (StringUtils.isNumeric(phone)) {
            int phoneNo = Integer.parseInt(phone);
            return perConv.personToJson(perFacade.getPerson(new Phone(phoneNo)));
        }
        else {
            throw new IllegalArgumentException();
        }
        
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public String smartSearchNoParam() {
        List<Person> persons = perFacade.getPersons();
        return perConv.personsToJson(persons);
//        List<Person> persons = ss.search(searchStr);
//        return perConv.personsToJson(persons);
    }

    @GET
    @Path("search/{searchstring}")
    @Produces(MediaType.APPLICATION_JSON)

    public String smartSearch(@PathParam("searchstring") String searchStr) {
        if (searchStr != null || !searchStr.equalsIgnoreCase("")) {
            List<Person> persons = perFacade.searchPersons(searchStr);
            return perConv.personsToJson(persons);
        } else {
            List<Person> persons = perFacade.getPersons();
            return perConv.personsToJson(persons);
        }

//        List<Person> persons = ss.search(searchStr);
//        return perConv.personsToJson(persons);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String editPerson(String json) {
        System.out.println("ASDFSDAFFDSAFDName");

        Person p = gson.fromJson(json, Person.class);
        System.out.println("1111ASDFSDAFFDSAFDName");

        Person p1 = perFacade.editPerson(p);
        System.out.println(p1 + "Name");
        return perConv.personToJson(p1);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePerson(@PathParam("id") int id, String json) {
        Person p = perFacade.deletePerson(id);
        return perConv.personToJson(p);
    }

}
