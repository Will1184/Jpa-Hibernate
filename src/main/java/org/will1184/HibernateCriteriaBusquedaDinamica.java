package org.will1184;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.will1184.entity.Cliente;
import org.will1184.exception.CondicionException;
import org.will1184.exception.LecturaException;
import org.will1184.util.JpaUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HibernateCriteriaBusquedaDinamica {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Filtro para nombre");
        String nombre = s.nextLine();

        System.out.println("Filtro para apellido");
        String apellido = s.nextLine();

        System.out.println("Filtro para la forma de pago ");
        String formaPago = s.nextLine();
        EntityManager manager= JpaUtil.getEntityManager();

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
        Root<Cliente> from = query.from(Cliente.class);


            List<Predicate> condiciones = new ArrayList<>();
            if (nombre!= null && !nombre.isEmpty()){
                condiciones.add(builder.equal(from.get("nombre"),nombre));
            }
            if (apellido!= null && !apellido.isEmpty()){
                condiciones.add(builder.equal(from.get("apellido"),apellido));
            }
            if (formaPago!= null && !formaPago.equals("")){
                condiciones.add(builder.equal(from.get("formaPago"),formaPago));
            }
            if (condiciones.isEmpty()){
                manager.getTransaction().rollback();
                manager.close();
                throw new CondicionException("No puede dejar ningun campo vacio");
            }
            for (Predicate c: condiciones){
               if (c.getExpressions().isEmpty()){
                   manager.getTransaction().rollback();
                   manager.close();
                   throw new CondicionException("Tiene uno o varios campos vacio");
               }
            }

            query.select(from).where(builder.and(condiciones.toArray(new Predicate[condiciones.size()])));
            List<Cliente> clientes = manager.createQuery(query).getResultList();

            if (clientes.isEmpty()) {
                manager.getTransaction().rollback();
                manager.close();
                throw new LecturaException("No existe el cliente");
            }
            clientes.forEach(System.out::println);
            manager.close();

    }
}
