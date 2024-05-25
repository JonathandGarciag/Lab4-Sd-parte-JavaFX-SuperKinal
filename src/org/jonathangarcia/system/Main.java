 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jonathangarcia.controller.FormCargosController;
import org.jonathangarcia.controller.FormClientesController;
import org.jonathangarcia.controller.MenuCargosController;
import org.jonathangarcia.controller.MenuClienteController;
import org.jonathangarcia.controller.MenuDistribuidorController;
import org.jonathangarcia.controller.MenuPrincipalController;
import org.jonathangarcia.controller.MenuProductosController;
import org.jonathangarcia.controller.MenuTicketSoporteController;
import org.jonathangarcia.controller.FormDistribuidorController;
import org.jonathangarcia.controller.MenuComprasViewController;
import org.jonathangarcia.controller.FormsComprasController;
import org.jonathangarcia.controller.MenuCategoriaProductosController;
import org.jonathangarcia.controller.FormCategoriaProductoController;
import org.jonathangarcia.controller.MenuPromocionesController;
import org.jonathangarcia.controller.FormPromocionController;
import org.jonathangarcia.controller.MenuEmpleadosController;
import org.jonathangarcia.controller.FormEmpleadoController;


/**
 *
 * @author Ernesto Lopez
 */
public class Main extends Application {
    
    private Stage stage;
    private Scene scene;
    private final String URLVIEW = "/org/jonathangarcia/view/";
    
    @Override
    public void start(Stage stage){
        this.stage = stage;
        stage.setTitle("Super Kinal APP");
        menuPrincipalView();
        stage.show();
    }
    
    public Initializable switchScene(String fxmlName, int width, int height) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        scene = new Scene((AnchorPane)loader.load(file), width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        return resultado;
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml", 950, 700);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuClienteView(){
        try{
            MenuClienteController menuClienteView = (MenuClienteController)switchScene("MenuClienteView.fxml", 1200, 750);
            menuClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formClienteView(int op){
        try{
            FormClientesController formClienteView = (FormClientesController)switchScene("FormClientesView.fxml", 500, 700);
            formClienteView.setOp(op);
            formClienteView.setStage(this);
            formClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuTicketView(){
        try{
            MenuTicketSoporteController menuTicketView = (MenuTicketSoporteController) switchScene("MenuTicketSoporteView.fxml", 1200, 750);
            menuTicketView.setStage(this);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuProductosView(){
        try{
            MenuProductosController menuProductosView = (MenuProductosController) switchScene("MenuProductosView.fxml", 1600, 850);
            menuProductosView.setStage(this);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formCargosView(int op){
        try{
            FormCargosController formCargosView = (FormCargosController) switchScene("FormCargosView.fxml", 500, 400);
            formCargosView.setOp(op);
            formCargosView.setStage(this);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCargosView(){
        try{
            MenuCargosController menuCargosView = (MenuCargosController) switchScene("MenuCargosView.fxml", 500, 400);
            menuCargosView.setStage(this);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuDistribuidorView(){
        try{
            MenuDistribuidorController menuDistribuidorView = (MenuDistribuidorController) switchScene("MenuDistribuidores.fxml", 1200, 750);
            menuDistribuidorView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formDistribuidorView(int op){
        try{
            FormDistribuidorController formDistribuidorView = (FormDistribuidorController)switchScene("FormDistribuidor.fxml", 500, 800);
            formDistribuidorView.setOp(op);
            formDistribuidorView.setStage(this);
            formDistribuidorView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuComprasView(){
        try{
            MenuComprasViewController menuComprasView = (MenuComprasViewController) switchScene("MenuComprasView.fxml", 600, 500);
            menuComprasView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formComprasView(int op){
        try{
            FormsComprasController formComprasView = (FormsComprasController) switchScene("FormsComprasView.fxml", 400, 500);
            formComprasView.setOp(op);
            formComprasView.setStage(this);
            formComprasView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCategoriaProductosView() {
        try{
            MenuCategoriaProductosController menuCategoriaProductosView = (MenuCategoriaProductosController)switchScene("MenuCategoriaProductosView.fxml",500,400);
            menuCategoriaProductosView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    
    public void formCategoriaProductosView(int op) {
        try{
            FormCategoriaProductoController formCategoriaProductosView = (FormCategoriaProductoController)switchScene("FormCategoriaProductoView.fxml",500,400);
            formCategoriaProductosView.setOp(op);
            formCategoriaProductosView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    
    public void menuPromociones(){
        try{
            MenuPromocionesController menuPromociones = (MenuPromocionesController)switchScene("MenuPromocionesView.fxml", 1200,750);
            menuPromociones.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void formPromociones(int op){
        try{
            FormPromocionController formPromociones = (FormPromocionController) switchScene("FormPromocionView.fxml", 1200, 800);
            formPromociones.setOp(op);
            formPromociones.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuEmpleadosView(){
        try{
            MenuEmpleadosController menuEmpleadosView = (MenuEmpleadosController) switchScene("MenuEmpleadosView.fxml", 1400, 750);
            menuEmpleadosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formEmpleadosView(int op){
        try{
            FormEmpleadoController FormEmpleadoView = (FormEmpleadoController)switchScene("FormEmpleadoView.fxml", 850, 750);
            FormEmpleadoView.setOp(op);
            FormEmpleadoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
