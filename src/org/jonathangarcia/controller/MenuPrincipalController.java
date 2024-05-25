/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.jonathangarcia.system.Main;

/**
 *
 * @author Ernesto Lopez
 */
public class MenuPrincipalController implements Initializable{
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientes,btnMenuTickets, btnMenuProductos, btnMenuCargos, btnMenuDistribuidor, btnMenuCompras, btnCategoriaProductos, 
            btnPromocion, btnEmpleados;
    
    
    @Override 
    public void initialize(URL location, ResourceBundle resources){
        
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuClientes){
            stage.menuClienteView();
        }else if(event.getSource() == btnMenuTickets){
            stage.menuTicketView();
        }else if(event.getSource() == btnMenuProductos){
            stage.menuProductosView();
        }else if(event.getSource() == btnMenuCargos){
            stage.menuCargosView();
        }else if(event.getSource() == btnMenuDistribuidor){
            stage.menuDistribuidorView();
        }else if(event.getSource() == btnMenuCompras){
            stage.menuComprasView();
        }else if(event.getSource()== btnCategoriaProductos){
            stage.menuCategoriaProductosView();
        }else if(event.getSource() == btnPromocion){
            stage.menuPromociones();
        }else if(event.getSource() == btnEmpleados){
            stage.menuEmpleadosView();
        }
    }
}
