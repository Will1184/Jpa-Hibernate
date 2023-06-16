package org.will1184;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.will1184.entity.Cliente;
import org.will1184.util.JpaUtil;

import java.util.Arrays;
import java.util.List;

public class HibernateCriteria {
    public static void main(String[] args) {
        EntityManager manager = JpaUtil.getEntityManager();

        CriteriaBuilder criteria = manager.getCriteriaBuilder();
        CriteriaQuery<Cliente> query = criteria.createQuery(Cliente.class);
        Root<Cliente> from = query.from(Cliente.class);

        query.select(from);
        List<Cliente> clientes = manager.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("==========Listar Where Equals=========");
        query =criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<String> nombreParam= criteria.parameter(String.class,"nombre");

        query.select(from).where(criteria.equal(from.get("nombre"),nombreParam));
        clientes= manager.createQuery(query).setParameter("nombre","Andres").getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======= usando where like para buscar clientes por nombres=======");
        query =criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<String> nombreParamLike= criteria.parameter(String.class,"nombre");

        query.select(from).where(criteria.like(criteria.upper(from.get("nombre"))
                ,criteria.upper(nombreParamLike)));
        clientes= manager.createQuery(query).setParameter("nombre","%jo%")
                .getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======= usando where between para rangos========");
        query =criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);

        query.select(from).where(criteria.between(from.get("id"),2L,6L));
        clientes= manager.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======= Consulta Where in=========");
        query =criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<List> listParam= criteria.parameter(List.class,"nombres");

        query.select(from).where((from.get("nombre").in(listParam)));
        clientes= manager.createQuery(query)
                .setParameter("nombres", Arrays.asList("Andres","John","Lou"))
                .getResultList();
        clientes.forEach(System.out::println);
    }
}
