package style.Bases;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author matias
 */
public class BasesDeDatos {
    // marcamos usuario y contraseña de mysql (depende siempre de mysql de cada computadora)
    private String usuario = "root"; // root            
    private String password = "1234"; // mi contraseña
    // declaramos las diferentes variables para la coneccion
    private Connection myConnection;    // variable de conexion
    private Statement myStatement;      // variable de declaraciones
    private ResultSet rs;        // variable de resultados
    private String db = "styles";    // String con el nombre de la base de datos
    
    public void ConectarSQL() {
        try{
        Class.forName("com.mysql.jdbc.Driver"); // llamamos al conector entre java y mysql
        String url = "jdbc:mysql://localhost:3306/"+db;  // asignamos la ruta de la base de datos dentro de mysql
        // hacemos la coneccion a la base de datos con el usuario y contraseña
        myConnection = DriverManager.getConnection( url, usuario, password ); 
        }
        catch(Exception ee)
        {
        System.out.println("Error: " + ee.getMessage());
        }
    }
    
    public void CerrarSQL(){
        try {
            myConnection.close();
        } catch (SQLException ex) {
            Logger.getLogger(BasesDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        Ejecutar("QUIT");
    }
    
    /**
     * Este metodo devuelve una tabla de mySQL con las diferentes consultas 
     * que se le quieran hacer. preferentemente deben usarse unicamente en las 
     * clases heredadas de esta misma clase, dado que los errores pueden ser 
     * muy diversos.
     * @param sql Linea de codigo de MySQL.
     * @return Un ResultSet con la consulta requerida.
     */
    public ResultSet Consulta(String sql) {
        String error="";
        try{
            myStatement =(Statement) myConnection.createStatement();
            rs=myStatement.executeQuery(sql);
        }
        catch(Exception ee)
        {
            error = ee.getMessage();
        }
        return(rs);
    }
    /**
     * 
     * Este metodo usa una linea de codigo de mySQL para modificar de alguna
     * forma las tablas de una base de datos. Siempre es para modificacion, si 
     * se quieren hacer consultas deben mirar el metodo Consulta.
     * @see Consulta(String sql)
     * @param sql linea de codigo de mySQL para modificacion
     * @return null si no hay error, el codigo de error si es que lo hay
     */
    public String Ejecutar(String sql){
        /* un metodo para ejecutar los diferentes
         * comandos de mysql. por parametro entra el comando en si 
         * en lenguaje sql.
         */
        String error="";
        try
        {
        myStatement = (Statement) myConnection.createStatement();
        myStatement.execute(sql);
        }
        catch(Exception ex)
        {
        error = ex.getMessage();
        }
        return(error);
     }

    
    public boolean TablaEsNull(ResultSet rs) throws SQLException{
        boolean es;
        if(rs.first())
            es = false;
        else
            es=true;
        return es;
    }
    
    /**
     * 
     * @param table nombre de la tabla que queremos saber el numero de filas en cuestion
     * @return numero de filas 
     * @throws SQLException 
     */
    public int getCantidadFilas(String table) throws SQLException{
        ResultSet res = Consulta ("Select count(*) from "+table+";");
        if(!res.first())
            res.first();
        return res.getInt(1);
    }
    
    /**
     * Pasa una fecha a un String
     * @param fecha la fecha que se quiere convertir
     * @param tipo si es uno la fecha esta en formato dd/MM/yyyy, si es 2 esta en formato
     * yyyy-MM-dd
     * @return String con la fecha
     */
    public String dateToString (java.util.Date fecha, int tipo){
        String resultado = null;
        java.text.SimpleDateFormat sdf;
        if(tipo == 1 || tipo ==2){
            if(tipo == 1){
                sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                resultado = sdf.format(fecha);
            }else{
                sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                resultado = sdf.format(fecha); 
            }
        
        }
        
        return resultado;
    }
    
    

    
    
}
