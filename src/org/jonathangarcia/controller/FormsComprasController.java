/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.dto.ComprasDTO;
import org.jonathangarcia.model.Compras;
import org.jonathangarcia.system.Main;

/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class FormsComprasController implements Initializable {

    private Main stage;
    private int op;   
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
    @FXML
    TextField tfComprasId, tfechaCompra, tftotalCompra;
    @FXML
    Button btnGuardarComp, btnCancelarComp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(ComprasDTO.getComprasDTO().getCompras() != null){
            cargarDatos(ComprasDTO.getComprasDTO().getCompras());
        } 
    }    
    
    public void cargarDatos(Compras compras){
        tfComprasId.setText(Integer.toString(compras.getComprasId()));
        tfechaCompra.setText(compras.getFechaCompra());
        tftotalCompra.setText(Double.toString(compras.getTotalCompra()));  
    }
    
    public void agregarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCompra(?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfechaCompra.getText());
            statement.setString(2, tftotalCompra.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{            
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCompra(?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfComprasId.getText()));
            statement.setString(2, tfechaCompra.getText());
            statement.setString(3, tftotalCompra.getText());   
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException e){
            System.out.println(e.getMessage());                
            }
        }
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelarComp){
            ComprasDTO.getComprasDTO().setCompras(null);
            stage.menuComprasView();
        }else if(event.getSource() == btnGuardarComp){
            if(op == 1){
            agregarCompra(); 
            stage.menuComprasView();
            }else if(op == 2){
                editarCompra();
                ComprasDTO.getComprasDTO().setCompras(null);
                stage.menuComprasView();
            }          
        }
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
