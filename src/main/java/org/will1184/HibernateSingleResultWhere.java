package org.will1184;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.will1184.entity.Cliente;
import org.will1184.util.JpaUtil;

import java.util.List;
import java.util.Scanner;

public class HibernateSingleResultWhere {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        EntityManager manager = JpaUtil.getEntityManager();
        Query query = manager.createQuery("select c from  Cliente c where c.formaPago=?1",
                Cliente.class);
        System.out.println("Ingrese una forma de pago: ");
        String pago= s.next();
        query.setParameter(1,pago);
        query.setMaxResults(1);
//        query.setParameter(2,4L);

        Cliente cliente = (Cliente) query.getSingleResult();
        System.out.println(cliente);
        manager.close();
    }
}
