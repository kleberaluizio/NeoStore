package br.com.neostore.service;

import br.com.neostore.dto.SupplierDTO;
import br.com.neostore.model.Supplier;
import br.com.neostore.repository.SupplierRepository;
import br.com.neostore.util.JPAUtil;
import jakarta.ws.rs.core.Response;

import javax.persistence.EntityExistsException;
import java.util.Optional;

public class SupplierService implements ISupplierService{

    private SupplierRepository supplierRepository = new SupplierRepository(JPAUtil.getEntityManager());

    public void createSupplier(SupplierDTO supplierDTO) {
        Optional<Supplier> existingSupplier = supplierRepository.findByCnpj(supplierDTO.getCnpj());

        if (existingSupplier.isPresent()) {
            throw new EntityExistsException("Error: CNPJ supplier already registered in our system.");
        }

        Supplier newSupplier = convertDTOtoEntity(supplierDTO);
        supplierRepository.add(newSupplier);
    }

    private Supplier convertDTOtoEntity(SupplierDTO supplierDTO) {

        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setComment(supplierDTO.getComment());
        supplier.setCnpj(supplierDTO.getCnpj());

        return supplier;
    }
}
