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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.dto.ClienteDTO;
import org.jonathangarcia.model.Cliente;
import org.jonathangarcia.system.Main;
/**
* FXML Controller class
*
* @author informatica
*/
public class MenuClienteController implements Initializable {
 
    /**
     * Initializes the controller class.
     */
    private Main stage;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    //Cliente cliente = new Cliente();
    @FXML
    Button salirClid, btnAgregar, btnEditar, btnEliminar, btnBuscar;
    @FXML
    TableView tblClientes;
    @FXML
    TableColumn colClienteId, colNombre, colApellido, colTelefono, colDireccion, colNit;
    @FXML
    TextField tfClienteId;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }   
    public void cargarDatos(){
        if(op == 3){
            tblClientes.getItems().add(buscarCliente());
            op = 0;
        }else{
            tblClientes.setItems(listarClientes());
        }
        colClienteId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("clienteId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
        colNit.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nit"));
    }
    public ObservableList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listaClientes();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                clientes.add(new Cliente(clienteId, nombre, apellido, telefono, direccion, nit));
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
        return FXCollections.observableList(clientes);
    }    

 
    public Main getStage() {
        return stage;
    }
 
    public void setStage(Main stage) {
        this.stage = stage;
    }

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == salirClid){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formClienteView(1);
        }else if(event.getSource() == btnEditar){
            ClienteDTO.getClienteDTO().setCliente((Cliente)tblClientes.getSelectionModel().getSelectedItem());
            stage.formClienteView(2);
        }else if(event.getSource() == btnEliminar){
            eliminarCliente(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getClienteId());
            cargarDatos();
        }else if(event.getSource() == btnBuscar){ 
            tblClientes.getItems().clear();
            if(tfClienteId.getText().equals("")){
                cargarDatos();
            }else{
                op = 3;
                cargarDatos();
            }
        }
           
    }
    
    public void eliminarCliente(int clidId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarClientes(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, clidId);
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
    
    public Cliente buscarCliente(){
        Cliente cliente = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarClientes(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfClienteId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                
                cliente = new Cliente(clienteId, nombre, apellido, telefono, direccion, nit);
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
        
        return cliente;
    }
}
