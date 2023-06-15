package org.will1184.service;

import jakarta.persistence.EntityManager;
import org.will1184.entity.Cliente;
import org.will1184.repository.ClienteRepository;
import org.will1184.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements ClienteService{
    private EntityManager manager;
    private CrudRepository<Cliente> repository;

    public ClienteServiceImpl(EntityManager manager) {
        this.manager = manager;
        this.repository = new ClienteRepository(manager);
    }

    @Override
    public List<Cliente> listar() {
        return repository.listar();
    }

    @Override
    public Optional<Cliente> porId(Long id) {
        return Optional.ofNullable(repository.porId(id));
    }

    @Override
    public void guardar(Cliente cliente) {
        try {
            manager.getTransaction().begin();
            repository.guardar(cliente);
            manager.getTransaction().commit();
        }catch (Exception e){
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            manager.getTransaction().begin();
            repository.eliminar(id);
            manager.getTransaction().commit();

        }catch (Exception e){
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
