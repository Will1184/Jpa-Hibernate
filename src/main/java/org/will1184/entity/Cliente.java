package org.will1184.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    @Column(name = "forma_pago")
    private String formaPago;
//    @Column(name = "creado_en")
//    private LocalDateTime creadoEn;
//
//    @Column(name = "editado_en")
//    private LocalDateTime editadoEn;

    @Embedded
    private Auditoria auditoria= new Auditoria();
    public Cliente() {
    }

    public Cliente(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Cliente(Long id, String nombre, String apellido, String formaPago) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.formaPago = formaPago;
    }
/*    @PrePersist
    public void prePersist(){
        System.out.println("Inicializar algo justo antes del persist");
        this.creadoEn = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        System.out.println("Inicializar algo justo antes del persist");
        this.editadoEn = LocalDateTime.now();
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }


    @Override
    public String toString() {
        return "id: " + id +
                ", nombre: " + nombre+
                ", apellido: " + apellido +
                ", formaPago: " + formaPago +
                ", creado en: "+auditoria.getCreadoEn()+
                ", editado en: "+auditoria.getEditadoEn();
    }
}
