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
import java.sql.Time;
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
import org.jonathangarcia.model.Empleados;
import org.jonathangarcia.system.Main;
/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class MenuEmpleadosController implements Initializable {

    @FXML
    Button btnAgregarEmple, btnEditarEmple, btnEliminarEmple, btnRegresarEmple, btnBuscarEmple;
    @FXML
    TextField tfEmpleadoId;
    @FXML
    TableView tblEmpleados;
    @FXML
    TableColumn colId, colNombre, colApellido, colSueldo, colHoraEntrada, colHoraSalida, colCargo, colEncargado;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    private static int op;
    private Main stage;
    
    public void cargarDatos(){
        if(op == 3){
            tblEmpleados.getItems().add(buscarEmpleado());
            op = 0;
            
        }else{
            tblEmpleados.setItems(listarEmpleados()); 

            colId.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("empleadoId"));
            colNombre.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
            colApellido.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
            colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
            colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleados, Time>("horaEntrada"));
            colHoraSalida.setCellValueFactory(new PropertyValueFactory<Empleados, Time>("horaSalida"));
            colCargo.setCellValueFactory(new PropertyValueFactory<Empleados, String>("cargo"));
            colEncargado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("Encargado"));
        }
        
        
    }
    
    public ObservableList<Empleados> listarEmpleados(){
        ArrayList<Empleados> empleados = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " call sp_listarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time horaentrada = resultSet.getTime("horaentrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                String cargoId = resultSet.getString("cargo");
                String encargadoId = resultSet.getString("Encargado");
            
                empleados.add(new Empleados(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaentrada, horaSalida, cargoId, encargadoId));
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
        
        
        return FXCollections.observableList(empleados);
    }
    
    public void eliminarEmpleado(int empId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarEmpleados(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,empId);
            statement.execute();
            
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
    }
    
    public Empleados buscarEmpleado(){
        Empleados empleado = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarEmpleados(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfEmpleadoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time horaentrada = resultSet.getTime("horaentrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                String cargoId = resultSet.getString("cargo");
                String encargadoId = resultSet.getString("Encargado");
            
                empleado = new Empleados(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaentrada, horaSalida, cargoId, encargadoId);

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
        return empleado;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnAgregarEmple){
            stage.formEmpleadosView(1);
        }else if(event.getSource() == btnEditarEmple){
            stage.formEmpleadosView(2);
        }else if(event.getSource() == btnEliminarEmple){
                eliminarEmpleado(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
                cargarDatos();
        }else if(event.getSource() == btnBuscarEmple){
            tblEmpleados.getItems().clear();
            if(tfEmpleadoId.getText().equals("")){
                cargarDatos();
            }else {
                op = 3;
                cargarDatos();
            }
        }else if(event.getSource() == btnRegresarEmple){
            stage.menuPrincipalView();
        }
    }
}
