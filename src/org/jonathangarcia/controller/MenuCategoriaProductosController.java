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
import org.jonathangarcia.dto.CategoriaProductosDTO;
import org.jonathangarcia.model.CategoriaProductos;
import org.jonathangarcia.system.Main;

/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class MenuCategoriaProductosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private Main stage;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    Button btnAgregarCatePro, btnEditarCatePro, btnEliminarCatePro, btnBuscarCatePro, btnRegresarCtPr;
    @FXML
    TextField tfCateProId;
    @FXML
    TableView tblCateProductos; 
    @FXML
    TableColumn colcatgProductosId, colnomCatePro, coldescripcionCatePro;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    public void cargarDatos(){
        if(op == 3){ 
            tblCateProductos.getItems().add(BuscarCategoriaProductos());
            op = 0;
        }else {
            tblCateProductos.setItems(listarCategoriaProductos());
        }
        colcatgProductosId.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, Integer> ("categoriaProductosId"));
        colnomCatePro.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String> ("nombreCategoria"));
        coldescripcionCatePro.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String> ("descripcionCategoria"));
    }
    
    public ObservableList<CategoriaProductos> listarCategoriaProductos(){
        ArrayList<CategoriaProductos> CategoriaProducto = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProductos();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                CategoriaProducto.add(new CategoriaProductos(categoriaProductosId, nombreCategoria, descripcionCategoria));
            }
            
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(conexion != null){
                    conexion.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        
        return FXCollections.observableList(CategoriaProducto);
    } 
    
    public void eliminarCargos(int catProId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCategoriaProductos(?);";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, catProId);
            statement.execute();     
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(conexion != null){
                    conexion.close();
                }
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public CategoriaProductos BuscarCategoriaProductos(){
        CategoriaProductos categoriaProducto = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCategoriaProductos(?);";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCateProId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categoriaProducto = new CategoriaProductos(categoriaProductosId, nombreCategoria, descripcionCategoria);
            }
            
        }catch(SQLException e ){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(conexion != null){
                    conexion.close();
                }
                if(statement != null){
                    statement.close();
                }
            }catch (Exception e){
               System.out.println(e.getMessage()); 
            }
        }
        return categoriaProducto;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresarCtPr){
            stage.menuPrincipalView();
        } else if(event.getSource()== btnAgregarCatePro){
            stage.formCategoriaProductosView(1);
        } else if(event.getSource()== btnEditarCatePro){
            CategoriaProductosDTO.getCategoriaProductosDTO().setCategoriaProducto((CategoriaProductos)tblCateProductos.getSelectionModel().getSelectedItem());
            stage.formCategoriaProductosView(2);
            cargarDatos();
        }else if(event.getSource()== btnEliminarCatePro){
                eliminarCargos(((CategoriaProductos)tblCateProductos.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
                cargarDatos();         
        }else if(event.getSource() == btnBuscarCatePro){
            tblCateProductos.getItems().clear();
            if(tfCateProId.getText().equals("")){
                cargarDatos();
            }else {
                op = 3;
                cargarDatos();
            }
        }
        
    }  
    
}
