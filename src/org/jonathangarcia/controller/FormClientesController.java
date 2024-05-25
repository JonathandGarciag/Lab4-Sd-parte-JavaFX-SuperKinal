/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.dto.ClienteDTO;
import org.jonathangarcia.model.Cliente;
import org.jonathangarcia.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FormClientesController implements Initializable {

    private Main stage;
    private int op;   
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
    @FXML
    TextField tfCienteId, tfNom, tfApe, tfTele, tfDire, tfNit;
    @FXML
    Button btnGuardar, btnCancelar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(ClienteDTO.getClienteDTO().getCliente() != null){
            cargarDatos(ClienteDTO.getClienteDTO().getCliente());
        }           
    }  
    
    public void cargarDatos(Cliente cliente){
        tfCienteId.setText(Integer.toString(cliente.getClienteId()));
        tfNom.setText(cliente.getNombre());
        tfApe.setText(cliente.getApellido());  
        tfTele.setText(cliente.getTelefono());        
        tfDire.setText(cliente.getDireccion());      
        tfNit.setText(cliente.getNit());
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public void agregarCliente(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarClientes(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNom.getText());
            statement.setString(2, tfApe.getText());
            statement.setString(3, tfTele.getText());
            statement.setString(4, tfDire.getText());
            statement.setString(5, tfNit.getText());
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
    
    public void editarCliente(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarClientes(?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCienteId.getText()));
            statement.setString(2, tfNom.getText());
            statement.setString(3, tfApe.getText());
            statement.setString(4, tfTele.getText());
            statement.setString(5, tfDire.getText());
            statement.setString(6, tfNit.getText());      
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
        if(event.getSource() == btnCancelar){
            ClienteDTO.getClienteDTO().setCliente(null);
            stage.menuClienteView();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
            agregarCliente(); 
            stage.menuClienteView();
            }else if(op == 2){
                editarCliente();
                ClienteDTO.getClienteDTO().setCliente(null);
                stage.menuClienteView();
            }          
        }
    }

    public void setOp(int op) {
        this.op = op;
    }
    
}
