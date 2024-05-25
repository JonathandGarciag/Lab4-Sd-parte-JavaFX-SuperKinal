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
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
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
public class FormPromocionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Main stage;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;

    @FXML
    TextField tfPromocionId, tfProductosId, tfFechaInicio, tfFechaFinal, tfPrecio;   
    @FXML
    TextArea taDescriPromo;
    @FXML
    Button btnGuardarPromo, btnCancelarPromo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(PromocionesDTO.getPromocionesDTO().getPromociones() != null){
            cargarDatos(PromocionesDTO.getPromocionesDTO().getPromociones());
        }
    }    
    
    public void cargarDatos(Promociones promocion){
        tfPromocionId.setText(Integer.toString(promocion.getPromocionId()));
        tfProductosId.setText(Integer.toString(promocion.getProductoId()));
        tfFechaInicio.setText(promocion.getFechaInicio());
        tfFechaFinal.setText(promocion.getFechaFinal());
        tfPrecio.setText(Double.toString(promocion.getPrecioPromocion()));
        taDescriPromo.setText(promocion.getDescripcionPromocion());
    }
    
    public void agregarPromociones(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarPromocion(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setDouble(1, Double.parseDouble(tfPrecio.getText()));
            statement.setString(2, taDescriPromo.getText());
            statement.setString(3, tfFechaInicio.getText());
            statement.setString(4, tfFechaFinal.getText());
            statement.setInt(5, Integer.parseInt(tfProductosId.getText()));
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
    
    public void editarPromociones(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarPromocion(?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfPromocionId.getText()));
            statement.setDouble(2, Double.parseDouble(tfPrecio.getText()));
            statement.setString(3, taDescriPromo.getText());
            statement.setString(4, tfFechaInicio.getText());
            statement.setString(5, tfFechaFinal.getText());
            statement.setInt(6, Integer.parseInt(tfProductosId.getText()));
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
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public int getOp() {
        return op;
    }
    
    public void setOp(int op) {
        this.op = op;
    }
    
    public void handleButtonAction(ActionEvent event){
       if(event.getSource() == btnGuardarPromo){
            if(op == 1){
                
                if(!tfPrecio.getText().equals("") && !tfFechaInicio.getText().equals("") && !tfFechaFinal.getText().equals("") && !taDescriPromo.getText().equals("")){
                    agregarPromociones();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(600);
                    stage.menuPromociones();
                }else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    if(tfPrecio.getText().equals("")){
                        tfPrecio.requestFocus();
                    } else if(tfFechaInicio.getText().equals("")) {
                        tfFechaInicio.requestFocus();
                    } else if(tfFechaFinal.getText().equals("")){
                        tfFechaFinal.requestFocus();
                    }else if(taDescriPromo.getText().equals("")){
                        taDescriPromo.requestFocus();
                    }
                }
            }else if(op == 2){
                
               if(!tfPrecio.getText().equals("") && !tfFechaInicio.getText().equals("") && !tfFechaFinal.getText().equals("") && !taDescriPromo.getText().equals("")){  
                   
                   if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(800).get() == ButtonType.OK){
                       editarPromociones();
                       SuperKinalAlert.getInstance().mostrarAlertaInfo(500);
                       PromocionesDTO.getPromocionesDTO().setPromociones(null);
                       stage.menuPromociones();
                   }else{
                       stage.menuPromociones();
                   }
                }else{
                   SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    if(tfPrecio.getText().equals("")){
                        tfPrecio.requestFocus();
                    } else if(tfFechaInicio.getText().equals("")) {
                        tfFechaInicio.requestFocus();
                    } else if(tfFechaFinal.getText().equals("")){
                        tfFechaFinal.requestFocus();
                    }else if(taDescriPromo.getText().equals("")){
                        taDescriPromo.requestFocus();
                    }
               }   
            }
        } else if(event.getSource()== btnCancelarPromo){
            stage.menuPromociones();
            PromocionesDTO.getPromocionesDTO().setPromociones(null);
        }        
    }
    
}
