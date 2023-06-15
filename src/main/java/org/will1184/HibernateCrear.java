package org.will1184;

import jakarta.persistence.EntityManager;
import org.will1184.entity.Cliente;
import org.will1184.util.JpaUtil;

import javax.swing.*;

public class HibernateCrear {
    public static void main(String[] args) {
        EntityManager manager= JpaUtil.getEntityManager();
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese Nombre");
            String apellido = JOptionPane.showInputDialog("Ingrese Apellido");
            String pago = JOptionPane.showInputDialog("Ingrese Forma de pago");

            manager.getTransaction().begin();
            Cliente c = new Cliente();
            c.setNombre(nombre);
            c.setApellido(apellido);
            c.setFormaPago(pago);
            manager.persist(c);
            manager.getTransaction().commit();
            System.out.println("El id del cliente registrado es: "+c.getId());
            c = manager.find(Cliente.class,c.getId());
            System.out.println(c);
        }catch (Exception e){
            manager.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            manager.close();
        }
    }
}
