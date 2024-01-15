package br.com.neostore.repository;

import br.com.neostore.model.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class SupplierRepository implements ISupplierRepository{

    private EntityManager entityManager;
    public SupplierRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Supplier supplier) {
        openConnection();
        entityManager.persist(supplier);
        closeConnection();
    }

    @Override
    public void addSuppliers(List<Supplier> suppliers) {
        if (!suppliers.isEmpty()) {
            openConnection();
            for (Supplier supplier : suppliers) {
                this.entityManager.persist(supplier);
            }
            closeConnection();
        }
    }

    @Override
    public List<Supplier> findAll() {
        String JPQL = "SELECT s FROM Supplier s";
        return entityManager.createQuery(JPQL,Supplier.class).getResultList();
    }

    @Override
    public Supplier findById(int id) {

        try {
            return entityManager.find(Supplier.class,id);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Supplier findByCnpj(String cnpj) {
        String JPQL = "SELECT s FROM Supplier s WHERE s.cnpj = :cnpj";

        try {
            return entityManager.createQuery(JPQL, Supplier.class)
                    .setParameter("cnpj", cnpj)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<Supplier> findPaginatedSuppliers(int itemsPerPage, int page) {
        String JPQL = "SELECT s FROM Supplier s ORDER BY s.name";

        Query query = entityManager.createQuery(JPQL, Supplier.class);

        query.setFirstResult((page - 1) * itemsPerPage);
        query.setMaxResults(itemsPerPage);

        return query.getResultList();
    }

    public long findTotalSuppliers() {
        String JPQL = "SELECT COUNT(s) FROM Supplier s";

        TypedQuery<Long> query = entityManager.createQuery(JPQL, Long.class);

        return query.getSingleResult();
    }

    @Override
    public void update(Supplier supplier) {
        openConnection();
        this.entityManager.merge(supplier);
        closeConnection();
    }
    @Override
    public void delete(Supplier supplier) {
        openConnection();
        supplier = entityManager.merge(supplier);
        this.entityManager.remove(supplier);
        closeConnection();
    }
    public void openConnection(){
        entityManager.getTransaction().begin();
    }
    public void closeConnection(){
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
