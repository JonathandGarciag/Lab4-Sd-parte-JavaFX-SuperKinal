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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.jonathangarcia.dao.Conexion;
import org.jonathangarcia.model.Usuario;
import org.jonathangarcia.system.Main;
import org.jonathangarcia.utlis.Contrasenia;
import org.jonathangarcia.utlis.SuperKinalAlert;
import org.jonathangarcia.model.Password;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class LoginKinalSuperController implements Initializable {

    private Main stage;
    private int op = 0;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;   
    
    @FXML
    TextField tfUser;
    @FXML
    PasswordField tfPasword;
    @FXML
    Button btnIniciar, btnAgregar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }       
 
    public Usuario buscarUsuario(){
        Usuario user = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarUsuario(?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1,tfUser.getText());
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int usuarioId = resultSet.getInt("usuarioId");
                String usuario = resultSet.getString("usuario");
                String contrasenia = resultSet.getString("contrasenia");
                int nivelAcceso = resultSet.getInt("nivelAccesoId");
                int empleadoId = resultSet.getInt("empleadoId");
                user = new Usuario(usuarioId,usuario,contrasenia,nivelAcceso,empleadoId);
            }
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
        return user;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){  
        Usuario usuario = buscarUsuario();
        if(event.getSource() == btnIniciar){
            if(op == 0){
                if(usuario != null){
                    if(Contrasenia.getInstance().checkPassword(tfPasword.getText(), usuario.getPassword())){
                        SuperKinalAlert.getInstance().alertWelcome(usuario.getUsuario());
                        if(usuario.getNivelesAccesoId() == 1){
                            btnAgregar.setDisable(false);
                            btnIniciar.setText("Ir al menu");
                            op = 1;
                        }else if(usuario.getNivelesAccesoId() == 2){
                            stage.menuPrincipalView();
                        }    
                    }else{
                        SuperKinalAlert.getInstance().mostrarAlertaInfo(005);
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(602);
                } 
                }else{
                    stage.menuPrincipalView();
                }    
        }else if(event.getSource() == btnAgregar){
            stage.formRegistroView();
        }
    }  
    
    public Main getStage() {
        return stage;
    }
 
    public void setStage(Main stage) {
        this.stage = stage;
    }
}

