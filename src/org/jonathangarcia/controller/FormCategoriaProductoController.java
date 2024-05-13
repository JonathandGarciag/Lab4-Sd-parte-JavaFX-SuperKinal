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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.dto.CategoriaProductosDTO;
import org.jonathangarcia.model.CategoriaProductos;
import org.jonathangarcia.system.Main;
import org.jonathangarcia.utlis.SuperKinalAlert;
/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class FormCategoriaProductoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private int op;
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;

    @FXML
    Button btnGuardarCatePro, btnCancelarCatePr;
    @FXML    
    TextField tfCategoriaProductosId, tfnombreCatePro;
    @FXML
    TextArea tfdescripcionCatePro;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(CategoriaProductosDTO.getCategoriaProductosDTO().getCategoriaProducto()!= null){
            cargarDatos(CategoriaProductosDTO.getCategoriaProductosDTO().getCategoriaProducto());
        }
    }    
    
    
    public void cargarDatos(CategoriaProductos categoriaProducto){
        tfCategoriaProductosId.setText(Integer.toString(categoriaProducto.getCategoriaProductosId()));
        tfnombreCatePro.setText(categoriaProducto.getNombreCategoria());
        tfdescripcionCatePro.setText(categoriaProducto.getDescripcionCategoria());
    }
    
       public void agregarCategoriaProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCategoriaProducto(?, ?);";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1, tfnombreCatePro.getText());
            statement.setString(2, tfdescripcionCatePro.getText());
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
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void editarCategoriaProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCategoriaProductos(?, ? ,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaProductosId.getText()));
            statement.setString(2, tfnombreCatePro.getText());
            statement.setString(3, tfdescripcionCatePro.getText());
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
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    } 
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws SQLException{
        if(event.getSource()== btnCancelarCatePr){
            stage.menuCategoriaProductosView();
        }if(event.getSource()== btnGuardarCatePro){
            if(op==1){
                if( !tfnombreCatePro.getText().equals("") && !tfdescripcionCatePro.getText().equals("")){
                    agregarCategoriaProductos();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    stage.menuCategoriaProductosView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(600);
                    tfnombreCatePro.requestFocus();
                }
                
            }else if(op ==2){
                if( !tfnombreCatePro.getText().equals("") && !tfdescripcionCatePro.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(550).get() == ButtonType.OK){
                        editarCategoriaProductos();
                        SuperKinalAlert.getInstance().mostrarAlertaInfo(500);
                        stage.menuCategoriaProductosView();
                        CategoriaProductosDTO.getCategoriaProductosDTO().setCategoriaProducto(null);
                    }else{
                        stage.menuCategoriaProductosView();
                    }     
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(600);
                    tfnombreCatePro.requestFocus();
                }
                
            }
            
        }
    }
    
}
