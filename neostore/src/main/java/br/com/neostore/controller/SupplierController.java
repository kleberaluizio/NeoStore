package br.com.neostore.controller;

import br.com.neostore.dto.PaginatedResponseDTO;
import br.com.neostore.dto.SupplierDTO;
import br.com.neostore.model.Supplier;
import br.com.neostore.service.SupplierService;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
        }catch(EntityExistsException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }catch (Exception e){
            String errorMessage = "Error: " + e.getMessage();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }
    @POST
    @Path("/batch")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSuppliers(JsonArray supplierJsonArray){

        List<JsonValue> notAddedSuppliers = supplierService.createSuppliersInBatch(supplierJsonArray);

        if (!notAddedSuppliers.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(notAddedSuppliers)
                    .build();
        }

        return Response.status(Response.Status.CREATED).build();
    }

    //READ
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<Supplier> fornecedores = supplierService.getAllSuppliers();
        return Response.status(Response.Status.OK).entity(fornecedores).build();
    }

    @GET
    @Path("/paginated")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaginatedSuppliers(
            @QueryParam("itemsPerPage") int itemsPerPage,
            @QueryParam("page") int page){

        PaginatedResponseDTO<Supplier> paginatedSuppliers = supplierService.getPaginatedSuppliers(itemsPerPage, page);
        return Response.ok(paginatedSuppliers).build();
    }
    //UPDATE
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, SupplierDTO supplierDTO){
        try{
            supplierService.updateSupplier(id, supplierDTO);
        } catch (EntityNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    //DELETE
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {

        try{
            supplierService.deleteSupplierById(id);
        } catch (EntityNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e){
            String errorMessage = "Error: " + e.getMessage();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
        }
        return Response.status(Response.Status.OK).build();
    }
}
