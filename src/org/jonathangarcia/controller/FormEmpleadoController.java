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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.dto.EmpleadosDTO;
import org.jonathangarcia.model.Cargos;
import org.jonathangarcia.model.Empleados;
import org.jonathangarcia.system.Main;
import org.jonathangarcia.utlis.SuperKinalAlert;
/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class FormEmpleadoController implements Initializable {
    
    @FXML
    Button BtnGuardarEmple, BtnCancelarEmple;
    @FXML
    TextField tfEmpId , tfNombre, tfApellido, tfSueldo, tfHoraEntrada, tfHoraSalida;
    @FXML
    ComboBox cmbCargId, cmbEncargados;
   
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    private Main stage;
    private int op;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(EmpleadosDTO.getEmpleadosDto().getEmpleados() != null){
            cargarDatos(EmpleadosDTO.getEmpleadosDto().getEmpleados());
        }
        cmbCargId.setItems(listarCargos());
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
    
    public void cargarDatos(Empleados empleado){
        tfEmpId.setText(Integer.toString(empleado.getEmpleadoId()));
        tfNombre.setText(empleado.getNombreEmpleado());
        tfApellido.setText(empleado.getApellidoEmpleado());
        tfSueldo.setText(Double.toString(empleado.getSueldo()));
        tfHoraEntrada.setText(empleado.getHoraEntrada().toString());
        tfHoraSalida.setText(empleado.getHoraSalida().toString());
    }
    
    public void agregarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarEmpleados(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setString(3, tfSueldo.getText());
            statement.setString(4, tfHoraEntrada.getText());
            statement.setString(5, tfHoraSalida.getText());
            statement.setInt(6,((Cargos)(cmbCargId.getSelectionModel().getSelectedItem())).getCargoId());
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
    
    public void editarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarEmpleados(?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfApellido.getText());
            statement.setString(4, tfSueldo.getText());
            statement.setString(5, tfHoraEntrada.getText());
            statement.setString(6, tfHoraSalida.getText());
            statement.setInt(7,((Cargos)cmbCargId.getSelectionModel().getSelectedItem()).getCargoId());
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
                String encargadoId = resultSet.getString("nombreEncargado");
            
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
    
    public void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == BtnCancelarEmple){
            EmpleadosDTO.getEmpleadosDto().setEmpleados(null);
            stage.menuEmpleadosView();
        }else if(event.getSource() == BtnGuardarEmple){
            if(op == 1){
                if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("") && !tfSueldo.getText().equals("") && !tfHoraEntrada.getText().equals("") && !tfHoraSalida.getText().equals("")){
                    agregarEmpleado();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(600);
                    stage.menuEmpleadosView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    if(tfNombre.getText().equals("")){
                        tfNombre.requestFocus();
                    }else if(tfApellido.getText().equals("")){
                        tfApellido.requestFocus();
                    }else if(tfSueldo.getText().equals("")){
                        tfSueldo.requestFocus();
                    }else if(tfHoraEntrada.getText().equals("")){
                        tfHoraEntrada.requestFocus();
                    }else if(tfHoraSalida.getText().equals("")){
                        tfHoraSalida.requestFocus();
                    }
                }
                
               
            }else if(op == 2){
                if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("") && !tfSueldo.getText().equals("") && !tfHoraEntrada.getText().equals("") && !tfHoraSalida.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(800).get() == ButtonType.OK){
                        editarEmpleado();
                        EmpleadosDTO.getEmpleadosDto().setEmpleados(null);
                        SuperKinalAlert.getInstance().mostrarAlertaInfo(500);
                        stage.menuEmpleadosView();
                    }else{
                        stage.menuEmpleadosView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    if(tfNombre.getText().equals("")){
                        tfNombre.requestFocus();
                    }else if(tfApellido.getText().equals("")){
                        tfApellido.requestFocus();
                    }else if(tfSueldo.getText().equals("")){
                        tfSueldo.requestFocus();
                    }else if(tfHoraEntrada.getText().equals("")){
                        tfHoraEntrada.requestFocus();
                    }else if(tfHoraSalida.getText().equals("")){
                        tfHoraSalida.requestFocus();
                    }
                }
                
            }
        }
    }
    
}
