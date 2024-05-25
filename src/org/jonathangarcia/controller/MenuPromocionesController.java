/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.controller;

import java.net.URL;
import javafx.fxml.Initializable;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.dto.PromocionesDTO;
import org.jonathangarcia.model.Promociones;
import org.jonathangarcia.system.Main;
import org.jonathangarcia.utlis.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class MenuPromocionesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    Button btnBuscarPromo, btnAgregarPromo, btnEditarPromo, btnEliminarPromo, btnRegresarPromo;     
    @FXML
    TextField tfBuscarPromo; 
    @FXML
    TableView tblPromociones;
    @FXML
    TableColumn colPromocionId, colPrecioPromo, coldescriPromo, colfechaInicio, colfechaFinal, colIdProducto; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    public void cargarDatos(){
        if(op == 3){ 
            tblPromociones.getItems().add(buscarPromociones());
            op = 0;
        }else {
            tblPromociones.setItems(listarPromociones());
        }
        colPromocionId.setCellValueFactory(new PropertyValueFactory<Promociones, Integer> ("promocionId"));
        colPrecioPromo.setCellValueFactory(new PropertyValueFactory<Promociones, Double> ("precioPromocion"));
        coldescriPromo.setCellValueFactory(new PropertyValueFactory<Promociones, String> ("descripcionPromocion"));
        colfechaInicio.setCellValueFactory(new PropertyValueFactory<Promociones, String> ("fechaInicio"));
        colfechaFinal.setCellValueFactory(new PropertyValueFactory<Promociones, String> ("fechaFinal"));
        colIdProducto.setCellValueFactory(new PropertyValueFactory<Promociones, Integer> ("productoId"));
    }
    
    public ObservableList<Promociones> listarPromociones(){
        ArrayList<Promociones> promocion = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarPromocion();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                double precioPromocion = resultSet.getDouble("precioPromocion");
                String descripcionPromocion = resultSet.getString("descripcionPromocion");
                String fechaInicio = resultSet.getString("fechaInicio");
                String fechaFinal = resultSet.getString("fechaFinal");
                int productoId = resultSet.getInt("productoId");           
                promocion.add(new Promociones(promocionId, precioPromocion, descripcionPromocion, fechaInicio, fechaFinal, productoId));
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
        
        return FXCollections.observableList(promocion);
    } 
    
    public void eliminarPromocion(int promoId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarPromocion(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, promoId);
            statement.execute();
            
            
        }catch(SQLException e){
            e.printStackTrace();
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
    
    public Promociones buscarPromociones(){
        Promociones promocion = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarPromocion(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscarPromo.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                double precioPromocion = resultSet.getDouble("precioPromocion");
                String descripcionPromocion = resultSet.getString("descripcionPromocion");
                String fechaInicio = resultSet.getString("fechaInicio");
                String fechaFinal = resultSet.getString("fechaFinal");
                int productoId = resultSet.getInt("productoId");
                
                promocion = new Promociones(promocionId, precioPromocion, descripcionPromocion, fechaInicio,fechaFinal, productoId);
            }
            
            
        }catch(SQLException e){
            e.printStackTrace();
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
           }catch(Exception e){
               e.printStackTrace();
           }
        }
        
        return promocion;
    } 
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresarPromo){
            stage.menuPrincipalView();
        }else if(event.getSource()== btnAgregarPromo){
            stage.formPromociones(1);
        } else if(event.getSource()== btnEditarPromo){
            PromocionesDTO.getPromocionesDTO().setPromociones((Promociones)tblPromociones.getSelectionModel().getSelectedItem());
            stage.formPromociones(2);
            cargarDatos();
        }else if(event.getSource()== btnEliminarPromo){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(700).get() == ButtonType.OK){
                eliminarPromocion(((Promociones)tblPromociones.getSelectionModel().getSelectedItem()).getPromocionId());
                cargarDatos();
            }
        }else if(event.getSource() == btnBuscarPromo){
            tblPromociones.getItems().clear();
            if(tfBuscarPromo.getText().equals("")){
                cargarDatos();
            }else {
                op = 3;
                cargarDatos();
            }
        }
    }
    
}
