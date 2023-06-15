package org.will1184;

import jakarta.persistence.EntityManager;
import org.will1184.entity.Cliente;
import org.will1184.service.ClienteService;
import org.will1184.service.ClienteServiceImpl;
import org.will1184.util.JpaUtil;

import java.util.List;
import java.util.Optional;

public class HibernateCrudService {
    public static void main(String[] args) {
        EntityManager manager= JpaUtil.getEntityManager();
        ClienteService service = new ClienteServiceImpl(manager);
        System.out.println("=======Listar======");
        List<Cliente>clientes = service.listar();
        clientes.forEach(System.out::println);

        System.out.println("=======Obtener Por Id======");
        Optional<Cliente> optionalCliente = service.porId(1L);
        optionalCliente.ifPresent(System.out::println);

        System.out.println("=======Insertar Nuevo Cliente======");
        Cliente cliente = new Cliente();
        cliente.setNombre("Luci");
        cliente.setApellido("Mena");
        cliente.setFormaPago("Paypal");
        service.guardar(cliente);
        System.out.println("Cliente guardado con exito");
        service.listar().forEach(System.out::println);

        System.out.println("=======Editar Cliente======");
        Long id = cliente.getId();
        optionalCliente = service.porId(id);
        optionalCliente.ifPresent(c->{
            c.setFormaPago("Mercado Pago");
            service.guardar(c);
            System.out.println("Cliente Editado con Exito!");
            service.listar().forEach(System.out::println);
        });

        System.out.println("=======Eliminar Cliente======");
        id = cliente.getId();
        optionalCliente = service.porId(id);
        optionalCliente.ifPresent(c->{
            service.eliminar(c.getId());
            System.out.println("Cliente Eliminado con Exito!");
            service.listar().forEach(System.out::println);
        });
    }
}
