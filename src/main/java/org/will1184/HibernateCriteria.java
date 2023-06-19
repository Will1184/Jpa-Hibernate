package org.will1184;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.will1184.entity.Cliente;
import org.will1184.util.JpaUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

        System.out.println("======= Filtrar usando predicados mayor que o mayo igual que =========");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(criteria.gt(from.get("id"),2L));
        clientes= manager.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======= Consulta con los predicados conjucion and y disyunsion or =========");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        Predicate porNombre = criteria.equal(from.get("nombre"),"Andres");
        Predicate porFormaPago = criteria.equal(from.get("formaPago"),"debito");
        Predicate p3 = criteria.ge(from.get("id"),4L);
        query.select(from).where(criteria.and(p3,criteria.or(porNombre,porFormaPago)));
        clientes= manager.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======= Consulta con order by asc desc =========");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).orderBy(criteria.desc(from.get("nombre")),criteria.asc(from.get("apellido")));
        clientes= manager.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======= Consulta por id =========");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<Long> idParam = criteria.parameter(Long.class,"id");
        query.select(from).where(criteria.equal(from.get("id"),idParam));

        Cliente cliente = manager.createQuery(query)
                .setParameter("id",1L)
                .getSingleResult();

        System.out.println(cliente);

        System.out.println("======= Consulta solo el nombre de los clientes =========");
        CriteriaQuery<String> criteriaString = criteria.createQuery(String.class);
        from =criteriaString.from(Cliente.class);
        criteriaString.select(from.get("nombre"));
        List<String> nombres = manager.createQuery(criteriaString).getResultList();
        System.out.println(nombres);

        System.out.println("======= Consulta solo el nombre de los clientes unicos con distinct=========");
        criteriaString = criteria.createQuery(String.class);
        from= criteriaString.from(Cliente.class);
        criteriaString.select(criteria.upper(from.get("nombre"))).distinct(true);
        nombres= manager.createQuery(criteriaString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("======= Consulta por nombre y apellidos concatenados=========");
        criteriaString = criteria.createQuery(String.class);
        from= criteriaString.from(Cliente.class);
        criteriaString.select(criteria.concat(criteria.concat(from.get("nombre"),""),from.get("apellido")));
        nombres= manager.createQuery(criteriaString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("======= Consulta por nombres y apellidos concatenados upper o lower =========");
        criteriaString = criteria.createQuery(String.class);
        from= criteriaString.from(Cliente.class);
        criteriaString.select(criteria.upper(criteria.concat(criteria.concat(from.get("nombre"),""),from.get("apellido"))));
        nombres= manager.createQuery(criteriaString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("======= Consulta de campos personalizados del entity cliente =========");
        CriteriaQuery<Object[]>criteriaObject = criteria.createQuery(Object[].class);
        from= criteriaObject.from(Cliente.class);
        criteriaObject.multiselect(from.get("id"),from.get("nombre"),from.get("apellido"));
        List<Object[]> registros =manager.createQuery(criteriaObject).getResultList();
        registros.forEach(reg ->{
            Long id = (Long) reg[0];
            String nombre = (String) reg[1];
            String apellido = (String) reg[2];
            System.out.println("id: " +id+", nombre: "+nombre+",apellido: "+apellido);
        });

        System.out.println("======= Consulta de campos personalizados del entity cliente con where =========");
       criteriaObject = criteria.createQuery(Object[].class);
        from= criteriaObject.from(Cliente.class);
        criteriaObject.multiselect(from.get("id"),from.get("nombre"),from.get("apellido"))
                .where(criteria.like(from.get("nombre"),"%lu%"));

        registros =manager.createQuery(criteriaObject).getResultList();
        registros.forEach(reg ->{
            Long id = (Long) reg[0];
            String nombre = (String) reg[1];
            String apellido = (String) reg[2];
            System.out.println("id: " +id+", nombre: "+nombre+",apellido: "+apellido);
        });

        System.out.println("======= Consulta de campos personalizados del entity cliente con where id =========");
        criteriaObject = criteria.createQuery(Object[].class);
        from= criteriaObject.from(Cliente.class);
        criteriaObject.multiselect(from.get("id"),from.get("nombre"),from.get("apellido"))
                .where(criteria.equal(from.get("id"),2L));

        Object[] registro =manager.createQuery(criteriaObject).getSingleResult();
        registros.forEach(reg ->{
            Long id = (Long) reg[0];
            String nombre = (String) reg[1];
            String apellido = (String) reg[2];
            System.out.println("id: " +id+", nombre: "+nombre+",apellido: "+apellido);
        });

        System.out.println("======= Contar registros de la consulta con count =========");
        CriteriaQuery<Long>queryLong= criteria.createQuery(Long.class);
        from= queryLong.from(Cliente.class);
       queryLong.select(criteria.count(from.get("id")));
       Long count= manager.createQuery(queryLong).getSingleResult();
        System.out.println(count);

        System.out.println("======= Sumar algun campo de la tabla =========");
       queryLong = criteria.createQuery(Long.class);
       from =queryLong.from(Cliente.class);
       queryLong.select(criteria.sum(from.get("id")));
       Long sum = manager.createQuery(queryLong).getSingleResult();
        System.out.println(sum);

        System.out.println("======= Consultas con el maximo id =========");
        queryLong = criteria.createQuery(Long.class);
        from =queryLong.from(Cliente.class);
        queryLong.select(criteria.max(from.get("id")));
        Long max= manager.createQuery(queryLong).getSingleResult();
        System.out.println(max);

        System.out.println("======= Consultas con el minimo id =========");
        queryLong = criteria.createQuery(Long.class);
        from =queryLong.from(Cliente.class);
        queryLong.select(criteria.min(from.get("id")));
        Long min= manager.createQuery(queryLong).getSingleResult();
        System.out.println(min);

        System.out.println("======= Ejemplo varios resultados de funciones de agregacion en una sola consulta =========");
        criteriaObject = criteria.createQuery(Object[].class);
        from =criteriaObject.from(Cliente.class);
        criteriaObject.multiselect(criteria.count(from.get("id"))
                ,criteria.sum(from.get("id"))
                ,criteria.max(from.get("id"))
                ,criteria.min(from.get("id"))
        );
        registro = manager.createQuery(criteriaObject).getSingleResult();
        count = (Long) registro[0];
        sum = (Long) registro[1];
        max = (Long) registro[2];
        min = (Long) registro[3];
        System.out.println("Count: "+count+ " Sum: "+sum+" Max: "+max+" Min: "+min);

        manager.close();
    }
}
