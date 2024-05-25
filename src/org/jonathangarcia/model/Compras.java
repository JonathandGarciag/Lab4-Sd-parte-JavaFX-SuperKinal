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
public class Compras {
    private int comprasId;
    private String fechaCompra;
    private double totalCompra;
    
    public Compras(){
    }

    public Compras(int comprasId, String fechaCompra, double totalCompra) {
        this.comprasId = comprasId;
        this.fechaCompra = fechaCompra;
        this.totalCompra = totalCompra;
    }

    public int getComprasId() {
        return comprasId;
    }

    public void setComprasId(int comprasId) {
        this.comprasId = comprasId;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString() {
        return "Compras{" + "comprasId=" + comprasId + ", fechaCompra=" + fechaCompra + ", totalCompra=" + totalCompra + '}';
    }

}
