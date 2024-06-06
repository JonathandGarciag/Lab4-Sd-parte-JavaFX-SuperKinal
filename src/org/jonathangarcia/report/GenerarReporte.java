/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.report;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.jonathangarcia.dao.Conexion;
import win.zqxu.jrviewer.JRViewerFX;

/**
 *
 * @author Ernesto Lopez
 */
public class GenerarReporte {
    private static GenerarReporte instance;
    private static Connection conexion = null;
    private GenerarReporte(){
    }
    
    public static GenerarReporte getInstance(){
        if(instance == null){
            instance = new GenerarReporte();
        }
        return instance;
    }
    public void generarFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("factId", 43);
            Stage reportStage = new Stage();
            JasperPrint reporte = JasperFillManager.fillReport(
                    GenerarReporte.class.getResourceAsStream("/org/jonathangarcia/report/Factura.jasper"), 
                    parametros, conexion);
            JRViewerFX reportView = new JRViewerFX(reporte);
            //Pane root = new JRViewerFX();
            //root.getChildren().add(reporteView);
            //reporteView.setPrefSize(1000, 800);
            //Scene scene = new Scene(root);
            //reportStage.setScene(scene);
            reportStage.setTitle("");
            reportStage.show();
        }catch( Exception e){
            e.printStackTrace();
        }
    }
}
