/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jonathangarcia.utlis;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Ernesto Lopez
 */
public class Contrasenia {
    private static Contrasenia instance;
    
    private int nivelccesoId;
    private String nivelAcceso;

    public Contrasenia() {
    }
    
    public static Contrasenia getInstance(){
        if(instance == null){
            instance = new Contrasenia();
        }
        return instance;
    }
    
    public String encryptedPassword(String passWord){
        return BCrypt.hashpw(passWord, BCrypt.gensalt());
    }
    
    public boolean checkPassword(String password, String passwordEncrypted){
        return BCrypt.checkpw(password, passwordEncrypted);
    }

    @Override
    public String toString() {
        return "ID " + nivelccesoId + " | " + nivelAcceso;
    }  
}
