package org.will1184;

import jakarta.persistence.EntityManager;
import org.will1184.entity.Cliente;
import org.will1184.util.JpaUtil;

import javax.swing.*;

public class HibernateEditar {
    public static void main(String[] args) {
        EntityManager manager= JpaUtil.getEntityManager();
        try {
            Long id= Long.valueOf(JOptionPane.showInputDialog("Ingrese el id del cliente a modificar"));
            Cliente c = manager.find(Cliente.class,id);
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre: ",c.getNombre());
            String apellido = JOptionPane.showInputDialog("Ingrese el apellido: ",c.getApellido());
            String pago = JOptionPane.showInputDialog("Ingrese la forma de pago: ",c.getFormaPago());
            manager.getTransaction().begin();
            c.setNombre(nombre);
            c.setApellido(apellido);
            c.setFormaPago(pago);
            manager.merge(c);
            manager.getTransaction().commit();
            c=manager.find(Cliente.class,id);
            System.out.println(c);
        }catch (Exception e){
            manager.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            manager.close();
        }

    }
}
