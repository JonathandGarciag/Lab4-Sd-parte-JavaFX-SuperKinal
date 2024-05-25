/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.dto.DistribuidorDTO;
import org.jonathangarcia.model.Distribuidor;
import org.jonathangarcia.system.Main;
/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class MenuDistribuidorController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    private Main stage;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    Button regresarClid, btnAgregardistri, btnEditardistri, btnEliminardistri, btnBuscardistri;
    @FXML
    TableView tbldistri;
    @FXML
    TableColumn coldistribuidorId, colNombreDistribuidor, colDireccionionDistribuidor, colNitDistribuidor, colTelefonoDistribuidor, colweb;
    @FXML
    TextField tfDistribuidorId;
    @Override
    
    public void initialize(URL location, ResourceBundle resources) {
         cargarDatos();
    }    
    
    public void cargarDatos(){
        if(op == 3){
            tbldistri.getItems().add(buscarDistribuidor());
            op = 0;
        }else{
            tbldistri.setItems(listarDistribuidor());
        }
        coldistribuidorId.setCellValueFactory(new PropertyValueFactory<Distribuidor, Integer>("distribuidorId"));
        colNombreDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("nombreDistribuidor"));
        colDireccionionDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("direccionDistribuidor"));
        colNitDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("nitDistribuidor"));
        colTelefonoDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("telefonoDistribuidor"));
        colweb.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("web"));
    }
    
    public ObservableList<Distribuidor> listarDistribuidor(){
        ArrayList<Distribuidor> distribuidor = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidores();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                distribuidor.add(new Distribuidor(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return FXCollections.observableList(distribuidor);
    }    

 
    public Main getStage() {
        return stage;
    }
 
    public void setStage(Main stage) {
        this.stage = stage;
    }

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == regresarClid){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregardistri){
             stage.formDistribuidorView(1);
        }else if(event.getSource() == btnEditardistri){
             DistribuidorDTO.getDistribuidorDTO().setDistribuidor((Distribuidor)tbldistri.getSelectionModel().getSelectedItem());
             stage.formDistribuidorView(2);
        }else if(event.getSource() == btnEliminardistri){
            eliminarDistribuidores(((Distribuidor)tbldistri.getSelectionModel().getSelectedItem()).getDistribuidorId());
            cargarDatos();
        }else if(event.getSource() == btnBuscardistri){ 
            tbldistri.getItems().clear();
            if(tfDistribuidorId.getText().equals("")){
                cargarDatos();
            }else{
                op = 3;
                cargarDatos();
            }
        }
           
    }
    
    public void eliminarDistribuidores(int distriId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarDistribuidores(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, distriId);
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
    
    public Distribuidor buscarDistribuidor(){
        Distribuidor distribuidor = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarDistribuidores(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                distribuidor = new Distribuidor(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web);
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
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
        
        return distribuidor;
    }
}
