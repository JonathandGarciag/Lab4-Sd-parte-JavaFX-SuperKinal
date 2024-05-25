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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class FormCargosController implements Initializable {

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
    Button btnCancelar, btnAceptar;
    
    @FXML
    TextField tfCargoId, tfNombreCargo;
    
    @FXML
    TextArea taDescripcion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(CargoDTO.getCargoDTO().getCargos() != null){
        cargarDatos(CargoDTO.getCargoDTO().getCargos());
        }
    }    
    
    public void cargarDatos(Cargos cargo){
        tfCargoId.setText(Integer.toString(cargo.getCargoId()));
        tfNombreCargo.setText(cargo.getNombreCargo());
        taDescripcion.setText(cargo.getDescripcionCargo());
    }
    
    
    public void agregarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCargo(?, ?);";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1, tfNombreCargo.getText());
            statement.setString(2, taDescripcion.getText());
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
    
    public void editarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCargo(?, ? ,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombreCargo.getText());
            statement.setString(3, taDescripcion.getText());
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
    

    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuCargosView();
        }else if(event.getSource() == btnAceptar){
            if(op == 1){
                
                if(!tfNombreCargo.getText().equals("") && !taDescripcion.getText().equals("")){
                    agregarCargo();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(600);
                    stage.menuCargosView();
                }else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                        if(tfNombreCargo.getText().equals("")) {
                            tfNombreCargo.requestFocus();
                        }else if(taDescripcion.getText().equals("")){
                            taDescripcion.requestFocus();
                        }
                    }
                }else if(op == 2){
                
                if(!tfNombreCargo.getText().equals("") && !taDescripcion.getText().equals("")){  

                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(900).get() == ButtonType.OK){
                        editarCargo();
                        SuperKinalAlert.getInstance().mostrarAlertaInfo(700);
                        CargoDTO.getCargoDTO().setCargos(null);
                        stage.menuCargosView();
                    }else{
                        stage.menuCargosView();
                    }
                 }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                     if(tfNombreCargo.getText().equals("")) {
                        tfNombreCargo.requestFocus();
                     } else if(taDescripcion.getText().equals("")){
                        taDescripcion.requestFocus();
                    }
                }
            }
        }
    }
    
    public void setOp(int op) {
        this.op = op;
    }  
    
}
