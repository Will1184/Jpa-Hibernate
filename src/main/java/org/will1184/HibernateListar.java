package org.will1184;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.will1184.entity.Cliente;
import org.will1184.util.JpaUtil;

import java.util.List;


public class HibernateListar {
    public static void main(String[] args) {
        EntityManager manager = JpaUtil.getEntityManager();
        List<Cliente> clientes = manager.createQuery("select c from Cliente c",
                Cliente.class).getResultList();
        clientes.forEach(System.out::println);
        manager.close();
    }
}
