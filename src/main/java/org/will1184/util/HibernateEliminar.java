package org.will1184.util;

import jakarta.persistence.EntityManager;
import org.will1184.entity.Cliente;

import java.util.Scanner;

public class HibernateEliminar {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Ingrese el id del cliente a eliminar");
        Long id = s.nextLong();
        EntityManager manager= JpaUtil.getEntityManager();
        try {
            Cliente cliente= manager.find(Cliente.class,id);
            manager.getTransaction().begin();
            manager.remove(cliente);
            manager.getTransaction().commit();
            System.out.println("Dato Eliminado");

        }catch (Exception e){
            manager.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            manager.close();
        }
    }
}
