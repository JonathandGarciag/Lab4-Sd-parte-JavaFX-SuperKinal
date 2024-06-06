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
import java.sql.Time;
import java.sql.Date;
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
import org.jonathangarcia.dto.FacturaDTO;
import org.jonathangarcia.model.Factura;
import org.jonathangarcia.system.Main;
import org.jonathangarcia.utlis.SuperKinalAlert;
/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class MenuFacturaController implements Initializable {

    private Main stage;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    Button btnRegresar;
    
    @FXML
    TableView tblDetalle;
    
    @FXML
    TableColumn colFactura,colProducto,colCliente,colFecha;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    public void cargarDatos(){
        tblDetalle.setItems(listarDetalle());
        colFactura.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("factura"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Factura, String>("producto"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Factura, String>("cliente"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Factura, Date>("fecha"));
    }
    
     public ObservableList<DetalleFactura> listarDetalle(){
        ArrayList<DetalleFactura> detalle = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarDetalleFactura()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                String producto = resultSet.getString("nombreProducto");
                String cliente = resultSet.getString("cliente");
                Date fecha = resultSet.getDate("fecha");
                detalle.add(new DetalleFactura(facturaId,producto,cliente,fecha));
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
        
        return FXCollections.observableList(detalle);
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnRegresar){
            stage.menuFacturasView();
        }
    }
}
