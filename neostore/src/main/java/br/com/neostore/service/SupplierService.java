package br.com.neostore.service;

import br.com.neostore.dto.PaginatedResponseDTO;
import br.com.neostore.dto.SupplierDTO;
import br.com.neostore.model.Supplier;
import br.com.neostore.repository.SupplierRepository;
import br.com.neostore.util.JPAUtil;
import jakarta.json.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class SupplierService implements ISupplierService{

    private SupplierRepository supplierRepository = new SupplierRepository(JPAUtil.getEntityManager());

    @Override
    public void createSupplier(SupplierDTO supplierDTO) {
        Supplier existingSupplier = supplierRepository.findByCnpj(supplierDTO.getCnpj());

        if (existingSupplier != null) {
            throw new EntityExistsException("CNPJ fornecido já está registrado em nosso sistema.");
        }

        Supplier newSupplier = convertDTOtoEntity(supplierDTO);
        supplierRepository.add(newSupplier);
    }
    @Override
    public List<JsonValue> createSuppliersInBatch(JsonArray supplierJsonArray) {

        List<JsonValue> notAddedSuppliers = new ArrayList<>();
        List<Supplier> suppliersToBeAdded = new ArrayList<>();

        for(JsonValue jsonValue : supplierJsonArray){
            try {
                Supplier supplier = convertJsonValueToEntity(jsonValue);
                suppliersToBeAdded.add(supplier);
            }catch (Exception e){
                notAddedSuppliers.add(jsonValue);
            }
        }
        supplierRepository.addSuppliers(suppliersToBeAdded);
        return notAddedSuppliers;
    }
    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public PaginatedResponseDTO<Supplier> getPaginatedSuppliers(int itemsPerPage, int page) {
        return new PaginatedResponseDTO<>(getSuppliers(itemsPerPage, page),getTotalSuppliers());
    }

    @Override
    public void updateSupplier(int id, SupplierDTO supplierDTO) {
        Supplier existingSupplier = supplierRepository.findById(id);

        if(existingSupplier == null){
            throw new EntityNotFoundException(String.format("Fornecedor (id = %d) não encontrado em nossos registros.",id));
        }

        verifyIfUpdatedCnpjExists(existingSupplier,supplierDTO);

        Supplier updatedSupplier = convertDTOtoEntity(supplierDTO);
        updatedSupplier.setId(id);

        if(!updatedSupplier.equals(existingSupplier)){
            supplierRepository.update(updatedSupplier);
        }
    }

    private void verifyIfUpdatedCnpjExists(Supplier existingSupplierById, SupplierDTO supplierDTO) {

        if (existingSupplierById.getCnpj().equals(supplierDTO.getCnpj())){
           return;
        }
        Supplier existingSupplierByCnpj = supplierRepository.findByCnpj(supplierDTO.getCnpj());

        if (supplierRepository.findByCnpj(supplierDTO.getCnpj()) != null) {
            throw new EntityExistsException("Não foi possivel realizar a atualização. CNPJ fornecido já está registrado em nosso sistema");
        }
    }

    @Override
    public void deleteSupplierById(int id) {
        Supplier existingSupplier = supplierRepository.findById(id);

        if (existingSupplier == null) {
            throw new EntityNotFoundException(String.format("Fornecedor (id = %d) não encontrado em nossos registros.",id));
        }

        supplierRepository.delete(existingSupplier);
    }
    private List<Supplier> getSuppliers(int itemsPerPage, int page) {

        return supplierRepository.findPaginatedSuppliers(itemsPerPage, page);
    }
    private long getTotalSuppliers() {

        return supplierRepository.findTotalSuppliers();
    }
    private Supplier convertDTOtoEntity(SupplierDTO supplierDTO) {

        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setDescription(supplierDTO.getDescription());
        supplier.setCnpj(supplierDTO.getCnpj());

        return supplier;
    }
    private Supplier convertJsonValueToEntity(JsonValue jsonValue) {

        JsonObject jsonObject = (JsonObject) jsonValue;
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String description = jsonObject.containsKey("description")? jsonObject.getString("description"):"";
        String cnpj = jsonObject.getString("cnpj");

        SupplierDTO supplierDTO = new SupplierDTO(name, email, description, cnpj);

        return convertDTOtoEntity(supplierDTO);
    }
    private String parseToJson(PaginatedResponseDTO<Supplier> paginatedSuppliers){

        JsonArray jsonSuppliers = convertFornecedoresToJsonArray(paginatedSuppliers);

        // Criando um JsonObject para a resposta final
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("suppliers", jsonSuppliers)
                .add("totalItems", paginatedSuppliers.getTotalItems())
                .build();

        // Convertendo o JsonObject para uma string JSON
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.writeObject(jsonResponse);
        }

        return stringWriter.toString();
    }
    private JsonArray convertFornecedoresToJsonArray(PaginatedResponseDTO<Supplier> paginatedSuppliers) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        List<Supplier> fornecedores = paginatedSuppliers.getData();

        for (Supplier supplier : fornecedores) {
            JsonObject jsonFornecedor = Json.createObjectBuilder()
                    .add("id", supplier.getId())
                    .add("name", supplier.getName())
                    .add("email", supplier.getEmail())
                    .add("description", supplier.getDescription() != null ? supplier.getDescription() : "")
                    .add("cnpj", supplier.getCnpj())
                    .build();
            jsonArrayBuilder.add(jsonFornecedor);
        }
        return jsonArrayBuilder.build();
    }
}
