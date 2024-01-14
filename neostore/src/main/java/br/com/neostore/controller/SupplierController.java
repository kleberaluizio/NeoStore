package br.com.neostore.controller;

import br.com.neostore.dto.SupplierDTO;
import br.com.neostore.model.Supplier;
import br.com.neostore.service.SupplierService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.persistence.EntityExistsException;
import java.util.List;

@Path("supplier")
public class SupplierController {

    private SupplierService supplierService = new SupplierService();

    //CREATE
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid SupplierDTO supplierDTO){

        try{
            supplierService.createSupplier(supplierDTO);
        }catch(EntityExistsException errorMessage){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
        }catch (Exception e){
            String errorMessage = "Error: " + e.getMessage();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
        }
        return Response.ok().build();
    }

    /*
    //READ
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<Supplier> fornecedores = supplierService.getAllSuppliers();
        return Response.status(Response.Status.OK).entity(fornecedores).build();
    }

    //UPDATE
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, SupplierDTO supplierDTO){
        return supplierService.updateSupplier(id, supplierDTO);
    }

    //DELETE
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFornecedor(@PathParam("id") int id) {
        return supplierService.deleteSupplierById(id);
    }

     */

}
