/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.dto;

import org.jonathangarcia.model.Cargos;

/**
 *
 * @author Ernesto Lopez
 */
public class CargoDTO {
 private static CargoDTO instance;
    private Cargos cargos;

    public Cargos getCargos() {
        return cargos;
    }

    public void setCargos(Cargos cargos) {
        this.cargos = cargos;
    }
    
    
    
    private CargoDTO (){
        
    }
    
    public static CargoDTO getCargoDTO(){
        if(instance == null){
            instance = new CargoDTO();
        }
        return instance;
    }
}

