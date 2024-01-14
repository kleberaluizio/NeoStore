package br.com.neostore.repository;

import br.com.neostore.model.Supplier;

import java.util.Optional;

public interface ISupplierRepository {

    Optional<Supplier> findByCnpj(String cnpj);
    void add(Supplier supplier);
}
