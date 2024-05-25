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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.dto.CargoDTO;
import org.jonathangarcia.model.Cargos;
import org.jonathangarcia.system.Main;
import org.jonathangarcia.utlis.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class MenuCargosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    
    private Main stage;
    private int op;
    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    Button btnAgregar, btnEditar, btnEliminar, btnBuscar, btnRegresar;
    
    @FXML
    TextField tfBarra;
    
    @FXML
    TableView tblCargos;
    
    @FXML
    TableColumn colCargoId, colNombreCargo, colDescripcion;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }   
    
    public void cargarDatos(){
        if(op == 3){ 
            tblCargos.getItems().add(buscarCargo());
            op = 0;
        }else {
            tblCargos.setItems(listarCargos());
        }
        colCargoId.setCellValueFactory(new PropertyValueFactory<Cargos, Integer> ("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String> ("nombreCargo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Cargos, String> ("descripcionCargo"));
    }
    
    public ObservableList<Cargos> listarCargos(){
        ArrayList<Cargos> cargos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargo();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId"); 
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargos(cargoId, nombreCargo, descripcionCargo));
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
        
        return FXCollections.observableList(cargos);
    } 
    
    public Cargos buscarCargo(){
        Cargos cargos = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCargo(?);";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBarra.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos = new Cargos(cargoId, nombreCargo, descripcionCargo);
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
        return cargos;
    }
    
    public void eliminarCargos(int cargId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCargo(?);";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, cargId);
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
    
    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        } else if(event.getSource()== btnAgregar){
            stage.formCargosView(1);
        } else if(event.getSource()== btnEditar){
            CargoDTO.getCargoDTO().setCargos((Cargos)tblCargos.getSelectionModel().getSelectedItem());
            stage.formCargosView(2);
            cargarDatos();
        }else if(event.getSource()== btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(700).get() == ButtonType.OK){
                eliminarCargos(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCargoId());
                cargarDatos();
            }
        }else if(event.getSource() == btnBuscar){
            tblCargos.getItems().clear();
            if(tfBarra.getText().equals("")){
                cargarDatos();
            }else {
                op = 3;
                cargarDatos();
            }
        }
        
    }
}
