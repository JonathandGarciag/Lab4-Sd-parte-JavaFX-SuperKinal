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
import org.jonathangarcia.dto.DistribuidorDTO;
import org.jonathangarcia.model.Distribuidor;
import org.jonathangarcia.system.Main;

/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class FormDistribuidorController implements Initializable {

    private Main stage;
    private int op;   
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
    @FXML
    TextField tfDistribuidorId, tfNomDistri, tfDirecDistri, tfNitDistri, tfTelDistri, tfWebDistri;
    @FXML
    Button btnGuardarDistri, btnCancelarDistri;
    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(DistribuidorDTO.getDistribuidorDTO().getDistribuidor() != null){
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidor());
        }
    }    
    
    public void cargarDatos(Distribuidor distribuidor){
        tfDistribuidorId.setText(Integer.toString(distribuidor.getDistribuidorId()));
        tfNomDistri.setText(distribuidor.getNombreDistribuidor());
        tfDirecDistri.setText(distribuidor.getDireccionDistribuidor());  
        tfNitDistri.setText(distribuidor.getNitDistribuidor());        
        tfTelDistri.setText(distribuidor.getTelefonoDistribuidor());      
        tfWebDistri.setText(distribuidor.getWeb());
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public void agregarDistribuidores(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDistribuidores(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNomDistri.getText());
            statement.setString(2, tfDirecDistri.getText());
            statement.setString(3, tfNitDistri.getText());
            statement.setString(4, tfTelDistri.getText());
            statement.setString(5, tfWebDistri.getText());
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
    
    public void editarDistribuidores(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDistribuidores(?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNomDistri.getText());
            statement.setString(3, tfDirecDistri.getText());
            statement.setString(4, tfNitDistri.getText());
            statement.setString(5, tfTelDistri.getText());
            statement.setString(6, tfWebDistri.getText());      
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
        if(event.getSource() == btnCancelarDistri){
            DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
            stage.menuDistribuidorView();
        }else if(event.getSource() == btnGuardarDistri){
            if(op == 1){
            agregarDistribuidores(); 
            stage.menuDistribuidorView();
            }else if(op == 2){
                editarDistribuidores();
                DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
                stage.menuDistribuidorView();
            }          
        }
    }

    public void setOp(int op) {
        this.op = op;
    }
}
