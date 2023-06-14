package org.will1184;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.will1184.entity.Cliente;
import org.will1184.util.JpaUtil;

import java.util.Scanner;

public class HibernatePorId {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.println("Ingrese id");
        Long id = s.nextLong();
        EntityManager manager= JpaUtil.getEntityManager();
        Cliente cliente = manager.find(Cliente.class,id);
        System.out.println(cliente);

        System.out.println("Ingrese id");
        Long id2=s.nextLong();
        Cliente cliente2 = manager.find(Cliente.class,id2);
        System.out.println(cliente2);
        manager.close();
    }
                
}
