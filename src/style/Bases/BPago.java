/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package style.Bases;

import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *
 * @author Matias
 */
public class BPago extends BasesDeDatos {
    /*
     * idpago
     * monto
     * fecha
     * fk_cobrador
     * fk_cuota
     */
    
    public void agregarPago (int idCliente,int idDeuda,double monto, java.util.Date fecha,int idCobrador) throws SQLException{
        String fechaSQL=dateToString(fecha, 2);
        ResultSet rsCuotas = Consulta("SELECT cuota.pagada , cuota.idcuota,cuota.monto_cuota FROM styles.deudas INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes)INNER JOIN styles.cuota ON (cuota.fk_deuda = deudas.iddeudas) WHERE (clientes.idclientes='"+idCliente+"' and deudas.iddeudas = '"+idDeuda+"' and cuota.pagada ='0') ORDER BY cuota.pagada,cuota.idcuota ASC;");

        if(rsCuotas.first()){
            int indexCuota = rsCuotas.getInt(2);
            double montoCuota = rsCuotas.getDouble(3);
            double sumPagosCuota = getMontoCuota(indexCuota);
            double loQueFalta = montoCuota-sumPagosCuota;


                if(monto < loQueFalta ){
                    Ejecutar("insert into styles.pago (monto,fecha,fk_cuota,fk_cobrador) values ("+monto+",'"+fechaSQL+"',"+indexCuota+","+idCobrador+");");
                    if(getMontoCuota(indexCuota) == montoCuota)
                        Ejecutar("UPDATE styles.cuota SET  pagada = 1 WHERE idcuota ='"+indexCuota+"' and fk_deuda='"+idDeuda +"';");
                }
                else {
                    double sobrante = monto - loQueFalta;
                    Ejecutar("insert into styles.pago (monto,fecha,fk_cuota,fk_cobrador) values ("+loQueFalta+",'"+fechaSQL+"',"+indexCuota+","+idCobrador+");");
                    Ejecutar("UPDATE styles.cuota SET  pagada = 1 WHERE idcuota ='"+indexCuota+"' and fk_deuda='"+idDeuda +"';");
                    agregarPago(idCliente,idDeuda,sobrante,fecha,idCobrador);

                }
        }
        else{
                    Ejecutar("insert into styles.pago (monto,fecha,fk_cobrador) values ("+monto+",'"+fechaSQL+"',"+idCobrador+");");
        }
    }
    
    
    
    public double getMontoCuota(int idCuota) throws SQLException{
        double result=0;

        ResultSet rs = Consulta("Select monto from pago where fk_cuota = "+idCuota+";");
        if(rs.first()){
            do{
                result += rs.getDouble(1);
            }while (rs.next());
        }
        return result;
    }
    
    public double getMontoTotal(int idCobrador) throws SQLException{
        double res = 0;
        ResultSet rs = Consulta("Select monto from pago where fk_cobrador='"+idCobrador+"';");
        if(!rs.first())
            rs.first();
        do{
            res += rs.getDouble(1);
        }while(rs.next());
        return res; 
    }
    
    public double getMontoCliente(int index, int indexDeuda) throws SQLException{
        double res=0;
        ResultSet rs = Consulta("SELECT pago.monto FROM styles.cuota INNER JOIN styles.deudas ON (cuota.fk_deuda = deudas.iddeudas) INNER JOIN styles.pago ON (pago.fk_cuota = cuota.idcuota) INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes) WHERE (clientes.idclientes='"+index+"' and deudas.iddeudas = '"+indexDeuda+"');");
        if (rs.first()){
            do{
                res += rs.getDouble(1);
            } while (rs.next());
        }
        return res;
    }


    public double getMontoTotal(int idCobrador,int mes) throws SQLException, ParseException{
    	double res = 0;
        String fechaSQL;
        String[] arrFecha ;
        int mesFecha;
    	ResultSet rs = Consulta("Select monto,fecha from pago where fk_cobrador='"+idCobrador+"';");

        if(rs.first()){
            do{
                fechaSQL =String.valueOf(rs.getDate(2));
                arrFecha = fechaSQL.split("-");
                mesFecha = Integer.parseInt(arrFecha[1]) - 1; 
                
                if(mesFecha == mes){
                    res += rs.getDouble(1);
                }
            }while(rs.next());
        }
    	return res;
	}
    
    public boolean todasCuotasPagadas(int idCliente,int idDeuda) throws SQLException{
        boolean bool;
            ResultSet rs = Consulta("SELECT cuota.pagada FROM styles.deudas INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes)INNER JOIN styles.cuota ON (cuota.fk_deuda = deudas.iddeudas) WHERE (clientes.idclientes='"+idCliente+"' and cuota.pagada='0' and deudas.iddeudas = '"+idDeuda+"') ORDER BY cuota.pagada,cuota.idcuota ASC;");
        if(rs.first())
            bool = false;
        else
            bool = true;
        
        return bool;
    }

    /**
     *
     * @param idDeuda
     * @return Cantidad de pagos con respecto a las fechas de determinada deuda
     * @throws SQLException
     */
    public int contadorPagosVacios(int idDeuda) throws SQLException{
        int contador = 0;
        ResultSet rs = Consulta("SELECT `pago`.`monto` , `pago`.`fecha` FROM `styles`.`cuota` INNER JOIN `styles`.`deudas` ON (`cuota`.`fk_deuda` = `deudas`.`iddeudas`) INNER JOIN `styles`.`pago` ON (`pago`.`fk_cuota` = `cuota`.`idcuota`) WHERE (`deudas`.`iddeudas` = "+idDeuda+");");
        ResultSet cantidad = Consulta("SELECT count(*) FROM `styles`.`cuota` INNER JOIN `styles`.`deudas` ON (`cuota`.`fk_deuda` = `deudas`.`iddeudas`) INNER JOIN `styles`.`pago` ON (`pago`.`fk_cuota` = `cuota`.`idcuota`) WHERE (`deudas`.`iddeudas` = "+idDeuda+");");
        java.util.Date fechahoy = new java.util.Date();
        Date segundaFecha = new Date(fechahoy.getTime());
        
        if(rs.first() && cantidad.first()){
            if(cantidad.getInt(1) != 1){
                do {

                    if(!rs.getDate(2).equals(segundaFecha)){
                        contador++;
                    }
                      segundaFecha = rs.getDate(2);
                }while(rs.next());
            }
            
        }
        
        
        return contador;
    }
    
    public double getPagoAnterior(int idCliente) throws SQLException{
        double pago=0;
        Calendar calendario = Calendar.getInstance();
        
        
        ResultSet pagosDiaActual = Consulta("SELECT pago.monto FROM styles.cuota INNER JOIN styles.deudas ON (cuota.fk_deuda = deudas.iddeudas) INNER JOIN styles.pago ON (pago.fk_cuota = cuota.idcuota) INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes) WHERE (clientes.idclientes='"+idCliente+"' AND YEAR(fecha)="+calendario.get(Calendar.YEAR) +" AND MONTH(fecha)="+(calendario.get(Calendar.MONTH)+1)+" AND DAYOFMONTH(fecha)="+calendario.get(Calendar.DAY_OF_MONTH)+");");
        
        if(pagosDiaActual.first()){
            do{
                pago += pagosDiaActual.getDouble(1);
            }while(pagosDiaActual.next());
        } 
        else {
            if(Calendar.DAY_OF_WEEK != Calendar.TUESDAY){
                calendario.add(Calendar.DATE, -1);
            } else {
                calendario.add(Calendar.DATE, -2);
            }
            
            ResultSet pagosDiaAnterior = Consulta("SELECT pago.monto FROM styles.cuota INNER JOIN styles.deudas ON (cuota.fk_deuda = deudas.iddeudas) INNER JOIN styles.pago ON (pago.fk_cuota = cuota.idcuota) INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes) WHERE (clientes.idclientes='"+idCliente+"' AND YEAR(fecha)="+calendario.get(Calendar.YEAR) +" AND MONTH(fecha)="+(calendario.get(Calendar.MONTH)+1)+" AND DAYOFMONTH(fecha)="+calendario.get(Calendar.DAY_OF_MONTH)+");");
            
            if(pagosDiaAnterior.first()){
                do{
                    pago += pagosDiaAnterior.getDouble(1);
                }while(pagosDiaAnterior.next());
            } 
        }
        
        return pago;
    }

    public boolean pagoHoy(int idCliente) throws SQLException {
        Calendar calendario = Calendar.getInstance();
        boolean pago;
        
        ResultSet pagosDiaActual = Consulta("SELECT pago.monto FROM styles.cuota INNER JOIN styles.deudas ON (cuota.fk_deuda = deudas.iddeudas) INNER JOIN styles.pago ON (pago.fk_cuota = cuota.idcuota) INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes) WHERE (clientes.idclientes='"+idCliente+"' AND YEAR(fecha)="+calendario.get(Calendar.YEAR) +" AND MONTH(fecha)="+(calendario.get(Calendar.MONTH)+1)+" AND DAYOFMONTH(fecha)="+calendario.get(Calendar.DAY_OF_MONTH)+");");
        
        if(pagosDiaActual.first()){
            pago = true;
        } 
        else
            pago=false;
        
        
        return pago;
        
    }

}
