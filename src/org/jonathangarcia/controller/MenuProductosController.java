 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.model.CategoriaProductos;
import org.jonathangarcia.model.Distribuidor;
import org.jonathangarcia.model.Productos;
import org.jonathangarcia.system.Main;
import org.jonathangarcia.utlis.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Ernesto Lopez
 */
public class MenuProductosController implements Initializable {
    /**
     * Initializes the controller class.
     */
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    private List<File> files = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private Main stage;

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    @FXML
    Button btnRegresar, btnVaciar, btnAgregarProd, btnEditarProd, btnEliminarProd, btnBuscarProd;
    
    @FXML
    TextField  tfDistribuidorId, tfCategoriaProdId, tfProductoId, tfNombreProd, tfCantidadStock, tfVentaUni, tfVentaMay, tfPrecioComp;
    
    @FXML
    TextArea taDescripcionProd;
    
    @FXML
    ComboBox cmbCatego, cmbDistribu;
    
    @FXML
    ImageView imgCargarImg;
    
    @FXML
    public void handleOnDrag(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    
    @FXML
    public void handleOnDrop(DragEvent event){
        try{
            files = event.getDragboard().getFiles();
            FileInputStream file = new FileInputStream(files.get(0));
            Image image = new Image(file);
            imgCargarImg.setImage(image);
        }catch(Exception e){
           e.printStackTrace();
        }
    }
    
    public void agregarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarProducto(?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1, tfNombreProd.getText());
            statement.setString(2, taDescripcionProd.getText());
            statement.setInt(3, Integer.parseInt(tfCantidadStock.getText()));
            statement.setDouble(4, Double.parseDouble(tfVentaUni.getText()));
            statement.setDouble(5, Double.parseDouble(tfVentaMay.getText()));
            statement.setDouble(6, Double.parseDouble(tfPrecioComp.getText()));
            InputStream img = new FileInputStream(files.get(0));
            statement.setBinaryStream(7, img);
            statement.setInt(8, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setInt(9, Integer.parseInt(tfCategoriaProdId.getText()));
            statement.execute();
            
        }catch(Exception e){
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
    
    public Productos buscarProducto(){
        Productos producto = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            
            resultSet = statement.executeQuery();
            
                if(resultSet.next()){
                   int productoId = resultSet.getInt("productoId"); 
                   String nombreProducto = resultSet.getString("nombreProducto"); 
                   String descripcionProducto = resultSet.getString("descripcionProducto"); 
                   Integer cantidadStock = resultSet.getInt("cantidadStock"); 
                   Double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario"); 
                   Double precioVentaMayor = resultSet.getDouble("precioVentaMayor"); 
                   Double precioCompra = resultSet.getDouble("precioCompra"); 
                   Blob imagenProducto = resultSet.getBlob("imagenProducto"); 
                   int distribuidorId = resultSet.getInt("distribuidorId"); 
                   int categoriaId = resultSet.getInt("categoriaProductosId");
                   
                   producto = new Productos(productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaId);
                }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            
        }
        return producto;
    }
    
    public void eliminarProducto(int prodId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql =  "call sp_eliminarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, prodId);
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
    
    public ObservableList<CategoriaProductos> listarCategoriaProductos(){
        ArrayList<CategoriaProductos> CategoriaProducto = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProductos();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                CategoriaProducto.add(new CategoriaProductos(categoriaProductosId, nombreCategoria, descripcionCategoria));
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
        
        return FXCollections.observableList(CategoriaProducto);
    } 
    
    public ObservableList<Distribuidor> listarDistribuidor(){
        ArrayList<Distribuidor> distribuidor = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidores();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                distribuidor.add(new Distribuidor(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web));
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
        return FXCollections.observableList(distribuidor);
    }    
    
    public int obtenerIndexCategoria() {
        int index = -1;
        int categoriaId = Integer.parseInt(tfProductoId.getText()); 

        for (int i = 0; i < cmbCatego.getItems().size(); i++) {
            CategoriaProductos categoria = (CategoriaProductos) cmbCatego.getItems().get(i);
            if (categoria.getCategoriaProductosId() == categoriaId) {
                index = i;
                break;
            }
        }

        return index;
    }
    
    public int obtenerIndexDistribuidor() {
        int index = -1;
        int distribuidorId = Integer.parseInt(tfProductoId.getText()); 
        
        for (int i = 0; i < cmbDistribu.getItems().size(); i++) {
            Distribuidor distribuidor = (Distribuidor) cmbDistribu.getItems().get(i);
            if (distribuidor.getDistribuidorId() == distribuidorId) {
                index = i;
                break;
            }
        }

        return index;
    }
    
    public void vaciarProd(){
        tfNombreProd.clear();
        tfProductoId.clear();
        tfDistribuidorId.clear();
        tfCategoriaProdId.clear();
        tfCantidadStock.clear();
        tfVentaUni.clear();
        tfVentaMay.clear();
        tfPrecioComp.clear();
        taDescripcionProd.clear();
        imgCargarImg.setImage(null);
    }
    
    
    public void handleButtonAction(ActionEvent event){  
        try{
            if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
            } else if(event.getSource() == btnVaciar){
                vaciarProd();
            }else if(event.getSource() == btnAgregarProd){
                
                if(tfNombreProd.getText().equals("") && tfDistribuidorId.getText().equals("") && tfCategoriaProdId.getText().equals("") && tfCantidadStock.getText().equals("") && tfVentaUni.getText().equals("")&& tfVentaMay.getText().equals("") && tfPrecioComp.getText().equals("") && taDescripcionProd.getText().equals("")){
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                }else if(!tfProductoId.getText().equals("")){
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(200);
                }else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(600);
                    agregarProducto();
                }
            }else if(event.getSource() == btnEditarProd){

            }else if(event.getSource() == btnEliminarProd){

            }else if(event.getSource() == btnBuscarProd){
                Productos producto = buscarProducto();
                    if(producto != null){
                        tfNombreProd.setText(producto.getNombreProducto());
                        InputStream file = producto.getImagenProducto().getBinaryStream();
                        Image image = new Image(file);
                        imgCargarImg.setImage(image);
                        tfDistribuidorId.setText(Integer.toString(producto.getDistribuidorId()));
                        tfCategoriaProdId.setText(Integer.toString(producto.getcategoriaProductosId()));
                        tfCantidadStock.setText(Integer.toString(producto.getCantidadStock()));
                        tfVentaUni.setText(Double.toString(producto.getPrecioVentaUnitario()));
                        tfVentaMay.setText(Double.toString(producto.getPrecioVentaMayor()));
                        tfPrecioComp.setText(Double.toString(producto.getPrecioCompra()));
                        taDescripcionProd.setText(producto.getDescripcionProducto());
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    
}

