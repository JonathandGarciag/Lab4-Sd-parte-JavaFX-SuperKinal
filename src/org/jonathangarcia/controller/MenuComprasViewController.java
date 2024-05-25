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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.dto.ComprasDTO;
import org.jonathangarcia.model.Compras;
import org.jonathangarcia.system.Main;

/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class MenuComprasViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private Main stage;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
     Button btnSalirComp, btnAgregarComp, btnEditarComp, btnEliminarComp, btnBuscarComp;
    @FXML
    TableView tblCompras;
    @FXML
     TableColumn colComprasId, colfechaCompra, cototalCompra;
    @FXML
     TextField tfComprasId;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         cargarDatos();
    }    
    
    public void cargarDatos(){
        if(op == 3){
            tblCompras.getItems().add(buscarCompras());
            op = 0;
        }else{
            tblCompras.setItems(listarCompras());
        }
        colComprasId.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("comprasId"));      
        colfechaCompra.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaCompra"));
        cototalCompra.setCellValueFactory(new PropertyValueFactory<Compras, String>("totalCompra"));
    }
    
    public ObservableList<Compras> listarCompras(){
        ArrayList<Compras> compras = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCompra();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
                String fechaCompra = resultSet.getString("fechaCompra");
                double totalCompra = resultSet.getDouble("totalCompra");
                compras.add(new Compras(compraId, fechaCompra, totalCompra));
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
        return FXCollections.observableList(compras);
    }    
    
    public void eliminarCompras(int compId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, compId);
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
    
    public Compras buscarCompras(){
        Compras compras = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfComprasId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
                String fechaCompra = resultSet.getString("fechaCompra");
                double totalCompra = resultSet.getDouble("totalCompra");
                
                compras = new Compras(compraId, fechaCompra, totalCompra);
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
        
        return compras;
    }
    
    public Main getStage() {
        return stage;
    }
 
    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnSalirComp){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregarComp){
            stage.formComprasView(1);
        }else if(event.getSource() == btnEditarComp){
            ComprasDTO.getComprasDTO().setCompras((Compras)tblCompras.getSelectionModel().getSelectedItem());
            stage.formComprasView(2);
        }else if(event.getSource() == btnEliminarComp){
            eliminarCompras(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getComprasId());
            cargarDatos();
        }else if(event.getSource() == btnBuscarComp){ 
            tblCompras.getItems().clear();
            if(tfComprasId.getText().equals("")){
                cargarDatos();
            }else{
                op = 3;
                cargarDatos();
            }
        }
           
    }
    
}
