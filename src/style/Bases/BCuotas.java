package style.Bases;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Matias
 */
public class BCuotas extends BasesDeDatos {
    
    
    /*
     * idcuota int
     * fecha_caducacion Date
     * monto_cuota double
     * pagada bool
     * fk_deuda int
     */
    public void insertarCuotas (int cantidad,java.util.Date fechaInicial,double montoCuota,int fk_deuda){
        String fechaCad;
        Calendar calendario = GregorianCalendar.getInstance();
        
        
        fechaCad = dateToString(calendario.getTime(), 2);
                    
        for(int i = 2;i<=cantidad;i++){
            
            if(calendario.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
                calendario.add(Calendar.DATE,1);
            else
                calendario.add(Calendar.DATE, 2);
            
            fechaCad = dateToString(calendario.getTime(), 2);
            Ejecutar("INSERT INTO cuota (fecha_caducacion, monto_cuota,pagada, fk_deuda,nrocuota) VALUES ('"+fechaCad+"',"+montoCuota+", 0, "+fk_deuda+","+i+");");
        }
       
        if(calendario.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
            calendario.add(Calendar.DATE,1);
        else
            calendario.add(Calendar.DATE, 2);
        
        fechaCad = dateToString(calendario.getTime(), 2);
        Ejecutar("INSERT INTO cuota (fecha_caducacion, monto_cuota,pagada, fk_deuda,nrocuota) VALUES ( '"+fechaCad+"',"+montoCuota+", 0, "+fk_deuda+","+1+");");

        
    }
    
    public void insertarUnaCuota(int idDeuda,double montoCuota){
        String fechaSQL = dateToString(new java.util.Date(), 2);
        Ejecutar ("insert into styles.cuota (fecha_caducacion,monto_cuota,pagada,fk_deuda,nrocuota) values('"+fechaSQL+"',"+montoCuota+",0,'"+idDeuda+"',0);");
    }

    public String getFechaCaducacion(int idCuota,int idDeuda) throws SQLException{
        String str = "";
        ResultSet rs = Consulta("SELECT fecha_caducacion FROM cuota where idcuota='"+idCuota+"' AND fk_deuda='"+idDeuda+"';");
        if(!rs.first())
            rs.first();
        str = rs.getString(1);
        return str;
    }
    
    public double getMontoCuota (int idDeuda) throws SQLException{
        double var = 0;
        ResultSet rs = Consulta ("SELECT monto_cuota FROM cuota where fk_deuda='"+idDeuda+"';");
        if(rs.first())
            
        var = rs.getDouble(1);
        return var;
    }
    
    public int getCantidadCuotasPagadas(int idDeuda) throws SQLException{
        int res = 0;
        ResultSet rs = Consulta("SELECT pagada FROM cuota WHERE fk_deuda='"+idDeuda+"';");
        if(rs.first()){
        do{
            res += rs.getInt(1);

            
        }while(rs.next());
        
        }
        return res;
    }
    
    public int getFkDeuda (int idCuota) throws SQLException{
        ResultSet rs;
        rs = Consulta("SELECT idcuota FROM deudas where fk_deuda='"+idCuota+"';");
        if(!rs.first())
            rs.first();
        int result = rs.getInt(1);
        return result;
    }
     public Date getUltimaFechaCad(int idDeuda) throws SQLException{
        ResultSet rs = Consulta("select cuota.fecha_caducacion from styles.cuota order by cuota.fecha_caducacion ASC; ");
        Date fecha = null;
        if(rs.last()){
            fecha = rs.getDate(1);
        }
        return fecha;
    }
     
     public double getMontoDeuda(int idDeuda) throws SQLException{
         ResultSet rs = Consulta("select cuota.monto_cuota from cuota where fk_deuda='"+idDeuda+"';");
         double monto = 0;
         if (rs.first()){
             do{
                 monto += rs.getDouble(1);
             }while (rs.next());
         }
         return monto;
     }
     
     
}
