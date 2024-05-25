/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.dto;

import org.jonathangarcia.model.Empleados;
/**
 *
 * @author Ernesto Lopez
 */
public class EmpleadosDTO {
    private static EmpleadosDTO instance;
    private Empleados empleado;

    public Empleados getEmpleados() {
        return empleado;
    }

    public void setEmpleados(Empleados cargos) {
        this.empleado = cargos;
    }  
    private EmpleadosDTO (){
        
    }
 
    public static EmpleadosDTO getEmpleadosDto(){
        if(instance == null){
            instance = new EmpleadosDTO();
        }
        return instance;
    }
}
