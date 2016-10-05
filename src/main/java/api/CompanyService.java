/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Company;
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
import util.JSONConverter;

/**
 * REST Web Service
 *
 * @author dennisschmock
 */
@Path("company")
public class CompanyService {

    @Context
    private UriInfo context;
    private ICompanyFacade comFacade = new CompanyFacade(Persistence.createEntityManagerFactory("PU"));
    private IPersonFacade perFacade = new PersonFacade(Persistence.createEntityManagerFactory("PU"));
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private JSONConverter jsc = new JSONConverter();

    /**
     * Creates a new instance of CompanyService
     */
    public CompanyService() {
    }

    @GET
    @Path("complete")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCompanies() {
        List<Company> companies;
        companies = comFacade.getCompanies();
        return jsc.companiesJson(companies);
    }

    @GET
    @Path("complete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCompanyComplete(@PathParam("id") int id) {
        Company com = comFacade.getCompany(id);

        return jsc.companyJson(com);
    }

    @GET
    @Path("contactinfo")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCompaniesContactInfo() {
        return jsc.companiesContactInfo(comFacade.getCompanies());
    }
    
    
    @GET
    @Path("contactinfo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCompaniesContactInfo(@PathParam("id")int id) {
        return jsc.companyContactInfo(comFacade.getCompany(id));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createCompany() {
        return "";
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteCompany(@PathParam("id") int id) {
        return jsc.companyJson(comFacade.deleteCompany(id));
    }

}