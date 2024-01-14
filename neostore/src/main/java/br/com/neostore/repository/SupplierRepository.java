package br.com.neostore.repository;

import br.com.neostore.model.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Optional;

public class SupplierRepository implements ISupplierRepository{

    private EntityManager entityManager;
    public SupplierRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void add(Supplier supplier) {
        entityManager.persist(supplier);
    }
    @Override
    @Transactional
    public Optional<Supplier> findByCnpj(String cnpj) {
        String JPQL = "SELECT p FROM Supplier p WHERE p.cnpj = :cnpj";

        return Optional.of(entityManager.createQuery(JPQL, Supplier.class)
                .setParameter("cnpj", cnpj)
                .getSingleResult());

    }

}
