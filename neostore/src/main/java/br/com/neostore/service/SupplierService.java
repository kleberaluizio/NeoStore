package br.com.neostore.service;

import br.com.neostore.dto.PaginatedResponseDTO;
import br.com.neostore.dto.SupplierDTO;
import br.com.neostore.model.Supplier;
import br.com.neostore.repository.SupplierRepository;
import br.com.neostore.util.JPAUtil;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public class SupplierService implements ISupplierService{

    private SupplierRepository supplierRepository = new SupplierRepository(JPAUtil.getEntityManager());

    @Override
    public void createSupplier(SupplierDTO supplierDTO) {
        Supplier existingSupplier = supplierRepository.findByCnpj(supplierDTO.getCnpj());

        if (existingSupplier != null) {
            throw new EntityExistsException("Erro: CNPJ fornecido já está registrado em nosso sistema");
        }

        Supplier newSupplier = convertDTOtoEntity(supplierDTO);
        supplierRepository.add(newSupplier);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public PaginatedResponseDTO<Supplier> getPaginatedSuppliers(int itemsPerPage, int page) {

        List<Supplier> suppliers = getSuppliers(itemsPerPage, page);
        long totalSupplier = getTotalSuppliers();
        return new PaginatedResponseDTO<>(suppliers,totalSupplier);
    }

    @Override
    public void updateSupplier(int id, SupplierDTO supplierDTO) {
        Supplier existingSupplier = supplierRepository.findById(id);

        if(existingSupplier == null){
            throw new EntityNotFoundException(String.format("Erro: Fornecedor (id = %d) não encontrado em nossos registros.",id));
        }

        Supplier updatedSupplier = convertDTOtoEntity(supplierDTO);
        updatedSupplier.setId(id);

        if(!updatedSupplier.equals(existingSupplier)){
            supplierRepository.update(updatedSupplier);
        }
    }
    @Override
    public void deleteSupplierById(int id) {
        Supplier existingSupplier = supplierRepository.findById(id);

        if (existingSupplier == null) {
            throw new EntityNotFoundException(String.format("Erro: Fornecedor (id = %d) não encontrado em nossos registros.",id));
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
}
