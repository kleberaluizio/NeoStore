package br.com.neostore.service;

import br.com.neostore.dto.PaginatedResponseDTO;
import br.com.neostore.dto.SupplierDTO;
import br.com.neostore.model.Supplier;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface ISupplierService {

    void createSupplier(SupplierDTO supplierDTO);
    List<JsonValue> createSuppliersInBatch(JsonArray supplierJsonArray);
    List<Supplier> getAllSuppliers();
    PaginatedResponseDTO<Supplier> getPaginatedSuppliers(int itemsPerPage, int page);
    void updateSupplier(int id, SupplierDTO supplierDTO);
    void deleteSupplierById(int id);
}
