
package com.mycompany.integrador_02.logica;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Ingrediente {
    private int id;
    private String nombre;
    private String procedencia;
    private String cantidad;
    private Date fechaVenc;
    @ManyToOne
    @JoinColumn(name="ingrediente_id")
    private Producto producto;

    public Ingrediente() {
    }

    public Ingrediente(int id, String nombre, String procedencia, String cantidad, Date fechaVenc, Producto producto) {
        this.id = id;
        this.nombre = nombre;
        this.procedencia = procedencia;
        this.cantidad = cantidad;
        this.fechaVenc = fechaVenc;
        this.producto = producto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }
    
    
}
