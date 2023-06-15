package org.will1184.repository;

import jakarta.persistence.EntityManager;
import org.will1184.entity.Cliente;

import java.util.List;

public class ClienteRepository implements CrudRepository<Cliente>{
    private EntityManager manager;

    public ClienteRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<Cliente> listar() {
        return manager.createQuery("select c from Cliente c", Cliente.class).getResultList();
    }

    @Override
    public Cliente porId(Long id) {
        return manager.find(Cliente.class,id);
    }

    @Override
    public void guardar(Cliente cliente) {
        if (cliente.getId()!= null && cliente.getId()>0){
            manager.merge(cliente);
        }else {
            manager.persist(cliente);
        }
    }

    @Override
    public void eliminar(Long id) {
        Cliente cliente= porId(id);
        manager.remove(cliente);
    }
}
