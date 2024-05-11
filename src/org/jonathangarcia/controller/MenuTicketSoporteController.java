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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.model.Cliente;
import org.jonathangarcia.model.TicketSoporte;
import org.jonathangarcia.system.Main;

/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class MenuTicketSoporteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
   private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    
    @FXML
    Button BTNGuardado, BTNVaciar, BTNVolver;
    
    @FXML
    TextField tfTicketId;
    
    @FXML 
    TextArea taDescripcion;
    
    @FXML 
    ComboBox cmbCliente, cmbFactura, cmbEstatus;
    
    @FXML 
    TableView tbTickets;
    
    @FXML 
    TableColumn colTicket, colDescrip, colEstat, colCliente, colFactura;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cargarCmbEstatus();
        cmbCliente.setItems(listarClientes());
        cargarCmbFactura();
    }  
    private Main stage;

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public ObservableList <TicketSoporte> listarTickets(){
        ArrayList <TicketSoporte> tickets = new ArrayList<>();
        
        try{
          conexion = Conexion.getInstance().obtenerConexion();
          String sql = "call listarTicketSoporte();";
          statement = conexion.prepareStatement(sql);
          resultSet = statement.executeQuery();
          
          while(resultSet.next()){
              int ticketId = resultSet.getInt("");
              String descripcion = resultSet.getString("descripcionTicket");
              String estatus = resultSet.getString("estatus");
              String cliente = resultSet.getString("cliente");
              int facturaId = resultSet.getInt("facturaId");
              
              tickets.add(new TicketSoporte(ticketId, descripcion, estatus, cliente, facturaId));
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
    
       return FXCollections.observableList(tickets);
    }
    
    public ObservableList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarTicketSoporte();";
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
                
            }
        }
        
        return FXCollections.observableList(clientes);
    } 
    
   public void cargarDatos(){
       tbTickets.setItems(listarTickets());
       colTicket.setCellFactory(new PropertyValueFactory<TicketSoporte, Integer>("ticketSoporteId")); 
       colDescrip.setCellFactory(new PropertyValueFactory<TicketSoporte, String>("descripcion")); 
       colEstat.setCellFactory(new PropertyValueFactory<TicketSoporte, String>("estatus")); 
       colCliente.setCellFactory(new PropertyValueFactory<TicketSoporte, String>("cliente")); 
       colFactura.setCellFactory(new PropertyValueFactory<TicketSoporte, Integer>("facturaId")); 
   }
   
   public void cargarCmbEstatus(){
       cmbFactura.getItems().add(1);
   }

   public void cargarCmbFactura(){
       cmbEstatus.getItems().add("En proceso");
       cmbEstatus.getItems().add("Finalizado");
   }
   
   @FXML
   public void cargaForm(){
       TicketSoporte ts = (TicketSoporte)tbTickets.getSelectionModel().getSelectedItem();
       if(ts != null){
           tfTicketId.setText(Integer.toString(ts.getTicketSoporteId()));
           taDescripcion.setText(ts.getDescripcion());
           cmbEstatus.getSelectionModel().select(0);
           cmbCliente.getSelectionModel().select(obtenerIndexCliente());
           cmbFactura.getSelectionModel().select(0);
       }
   }
   
   public int obtenerIndexCliente(){
       int index = 0;
       String clienteTbl = (((TicketSoporte)tbTickets.getSelectionModel().getSelectedItems()).getCliente());
       
       for(int i = 0; i <= cmbCliente.getItems().size(); i++){
        String cliemteCmb = cmbCliente.getItems().get(i).toString();
        
        
            if(clienteTbl.equals(cliemteCmb)){
                index = i;
                break;
            }
       }
       return index;
   }
   
   public void agregarTickets(){
       try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_agregarTicketSoporte(?,?,?)";
           
           statement = conexion.prepareStatement(sql);
           
           statement.setString(1, taDescripcion.getText());
           statement.setInt(2, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
           statement.setInt(3, Integer.parseInt(cmbFactura.getSelectionModel().getSelectedItem().toString()));
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
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
   }
   
   public void editarTickets(){
       try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_editarTicketSoporte(?,?,?,?,?)";
           statement = conexion.prepareStatement(sql);
           
           
       }catch (SQLException e){
           System.out.println(e.getMessage());
       }finally{
           
       }
   }
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == BTNVolver){
            stage.menuPrincipalView();
        } else if(event.getSource() == BTNGuardado){
            agregarTickets();
            cargarDatos();
        }else if(event.getSource() == BTNVaciar){
            
        }
    }
    
}
