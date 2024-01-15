package br.com.neostore.repository;

import br.com.neostore.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface ISupplierRepository {

    void add(Supplier supplier);
    void addSuppliers(List<Supplier> suppliersToBeAdded);
    List<Supplier> findAll();
    Supplier findById(int id);
    Supplier findByCnpj(String cnpj);
    List<Supplier> findPaginatedSuppliers(int itemsPerPage, int page);
    long findTotalSuppliers();
    void delete(Supplier supplier);
    void update(Supplier supplier);

}
