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

/**
 * Controlador para operações relacionadas aos fornecedores.
 * Fornece endpoints para leitura, criação, atualização de fornecedores.
 */
@Path("supplier")
public class SupplierController {

    private SupplierService supplierService = new SupplierService();

    /**
     * Cria um novo fornecedor.
     *
     * @param supplierDTO As informações do fornecedor a ser criado.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
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

    /**
     * Cria vários fornecedores em lote.
     *
     * @param supplierJsonArray Lista de objetos JSON representando fornecedores.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
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

    /**
     * Obtém todos os fornecedores.
     *
     * @return Resposta HTTP contendo a lista de fornecedores.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<Supplier> fornecedores = supplierService.getAllSuppliers();
        return Response.status(Response.Status.OK).entity(fornecedores).build();
    }

    /**
     * Obtém uma lista paginada de fornecedores.
     *
     * @param itemsPerPage Número de itens por página.
     * @param page Número da página desejada.
     * @return Resposta HTTP contendo a lista paginada de fornecedores e total de fornecedores em nosso banco de dados.
     */
    @GET
    @Path("/paginated")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaginatedSuppliers(
            @QueryParam("itemsPerPage") int itemsPerPage,
            @QueryParam("page") int page){

        PaginatedResponseDTO<Supplier> paginatedSuppliers = supplierService.getPaginatedSuppliers(itemsPerPage, page);
        return Response.ok(paginatedSuppliers).build();
    }

    /**
     * Endpoint para atualizar um fornecedor existente.
     *
     * @param id O ID do fornecedor a ser atualizado.
     * @param supplierDTO O SupplierDTO contendo as informações atualizadas.
     * @return Resposta com o status HTTP indicando sucesso ou falha.
     */
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

    /**
     * Endpoint para excluir um fornecedor por ID.
     *
     * @param id O ID do fornecedor a ser excluído.
     * @return Resposta com o status HTTP indicando sucesso ou falha.
     */
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
