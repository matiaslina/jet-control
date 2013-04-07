/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package style.Bases;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author matias
 */
public class Notepad extends BasesDeDatos{
    
    public void guardarTexto(String texto){
        Ejecutar("UPDATE styles.notepad SET texto='"+texto+"';");
    }    
    
    public String getTexto () {
        String resultado = "";
        ResultSet rs = Consulta("select texto from notepad;");
        try {
            if(rs.first()) {
                try {
                    resultado = rs.getString(1);
                } catch (SQLException ex) {
                    Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
    }
    
}
