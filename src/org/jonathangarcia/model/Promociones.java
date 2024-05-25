/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.model;

/**
 *
 * @author Ernesto Lopez
 */
public class Promociones {
    private int promocionId;
    private double precioPromocion;
    private String descripcionPromocion;
    private String fechaInicio;
    private String fechaFinal;
    private int productoId;
    
    public Promociones(){
        
    }

    public Promociones(int promocionId,double precioPromocion, String descripcionPromocion, String fechaInicio, String fechaFinal, int productoId) {
        this.promocionId = promocionId;
        this.precioPromocion = precioPromocion;
        this.descripcionPromocion = descripcionPromocion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.productoId = productoId;
    }
    
    public int getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(int promocionId) {
        this.promocionId = promocionId;
    }

    public double getPrecioPromocion() {
        return precioPromocion;
    }

    public void setPrecioPromocion(double precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    @Override
    public String toString() {
        return "Promociones{" + "promocionId=" + promocionId + ", precioPromocion=" + precioPromocion + ", descripcionPromocion=" + descripcionPromocion + ", fechaInicio=" + fechaInicio + ", fechaFinal=" + fechaFinal + ", productoId=" + productoId + '}';
    }
    
    
}
