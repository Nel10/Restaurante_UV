/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author holberton
 */
public class Producto {
    private int id_producto; 
    private String nombre;
    private double precio;
    private String descripcion;
    private String categoria;
    
    // Constructor mejorado
    public Producto(int id_producto, String nombre, double precio, String descripcion, String categoria) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public int getId() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Método para representar el producto como un String (opcional, para depuración)
    @Override
    public String toString() {
        return "Producto [nombre=" + nombre + ", precio=" + precio + ", descripcion=" + descripcion + ", categoria=" + categoria + "]";
    }
}

