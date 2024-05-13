/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.dto;
import org.jonathangarcia.model.CategoriaProductos;

/**
 *
 * @author Ernesto Lopez
 */
public class CategoriaProductosDTO {
    private static CategoriaProductosDTO instance;
    private CategoriaProductos CategoriaProducto;

    public CategoriaProductosDTO() {
        
    }
    
    public static CategoriaProductosDTO getCategoriaProductosDTO(){
        if(instance == null){
            instance = new CategoriaProductosDTO();
        }
        return instance;
    }

    public CategoriaProductos getCategoriaProducto() {
        return CategoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProductos CategoriaProducto) {
        this.CategoriaProducto = CategoriaProducto;
    }
}
