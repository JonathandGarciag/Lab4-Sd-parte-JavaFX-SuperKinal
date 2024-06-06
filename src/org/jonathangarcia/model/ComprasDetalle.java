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
public class ComprasDetalle {
    private int detalleCompraId;
    private int cantidadCompra;
    private int productoId;
    private int compraId;
    private String compra;
    private String producto;
    
    public ComprasDetalle() {
    }

    public ComprasDetalle(int detalleCompraId, int cantidadCompra, String producto,String compra) {
        this.detalleCompraId = detalleCompraId;
        this.producto = producto;
        this.cantidadCompra = cantidadCompra;
        this.compra = compra;
    }

    public int getDetalleCompraId() {
        return detalleCompraId;
    }

    public void setDetalleCompraId(int detalleCompraId) {
        this.detalleCompraId = detalleCompraId;
    }

    public int getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }
    
    @Override
    public String toString(){
        return "";
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCompra() {
        return compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }
}
