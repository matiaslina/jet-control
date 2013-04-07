/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package style.Bases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author matias
 */
public class BCaja extends BasesDeDatos{
    
    public void insertarIngreso(String concepto,double debe){
        Date fecha = new Date();
        
        Ejecutar("insert into caja (concepto,debe,haber,fecha) values('"+concepto+"',"+debe+",0,'"+dateToString(fecha,2)+"');");
    }
    
    public void insertarEgreso(String concepto,double haber){
        Date fecha = new Date();
        Ejecutar("insert into caja (concepto,debe,haber,fecha) values('"+concepto+"',0,"+haber+",'"+dateToString(fecha, 2)+"');");
    }
    
    public void eliminarRegistros(){
        Ejecutar ("truncate caja;");
    }
    
    public void llenarTablaCaja(JTable jt) throws SQLException{
        ResultSet rs = Consulta("select * from styles.caja;");
        DefaultTableModel model = (DefaultTableModel) jt.getModel();
        
        model.setRowCount(0);
        
        Object[] arr = new Object[4];
        if(rs.first()){
            do{
                arr[0]=rs.getString(1);
                arr[1] =  rs.getDouble(2) ;
                arr[2] =  rs.getDouble(3) ;
                arr[3] = rs.getDate(4);
                
                model.addRow(arr);     
            }while(rs.next());   
        }
        
    }
    
    public void llenarTablaCaja(JTable jt,int mes) throws SQLException{
        ResultSet rs = Consulta("select * from styles.caja where month(fecha) = '"+mes+"';");
        DefaultTableModel model = (DefaultTableModel) jt.getModel();
        
        model.setRowCount(0);
        
        Object[] arr = new Object[4];
        if(mes != 0){
            if(rs.first()){
                do{
                    arr[0]=rs.getString(1);
                    arr[1] =  rs.getDouble(2) ;
                    arr[2] =  rs.getDouble(3) ;
                    arr[3] = rs.getDate(4);

                    model.addRow(arr);  

                }while(rs.next());   
            } else{
                model.setRowCount(0);
            }
        } else {
            llenarTablaCaja(jt);
        }
    }


}
