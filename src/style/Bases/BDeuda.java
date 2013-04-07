package style.Bases;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Matias
 */

import java.sql.*;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;
public class BDeuda extends BasesDeDatos{
    /*
     * iddeudas int
     * monto_prestamo double
     * cuotas int
     * fecha_generada date
     * fk_cliente int
     */
    
    public void crearDeuda(double monto,int cuotas,int idCliente){
        //Calendar calendario = Calendar.getInstance();
        java.util.Date fecha = new java.util.Date();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        
            if(calendario.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
                calendario.add(Calendar.DATE,1);
            else
                calendario.add(Calendar.DATE, 2);
        
        Date fechaSQL = new Date(calendario.getTimeInMillis());
        //fechaSQL =calendario.get(Calendar.YEAR)+"-"+ calendario.get(Calendar.MONTH)+"-"+calendario.get(Calendar.DAY_OF_MONTH);
        
        
        
        Ejecutar ("INSERT INTO deudas (monto_prestamo, cuotas, fecha_generada, fk_cliente) VALUES ("+monto+","+cuotas+" , '"+fechaSQL+"',"+ idCliente+");");
    }
    
    public void borrarDeuda(int idCliente){
        Ejecutar ("DELETE FROM deudas WHERE fk_cliente='"+idCliente+"';");
    }
    
    public int getIdDeudas(int idCliente) throws SQLException{
        ResultSet rs;
        int result=-1;
        rs = Consulta("SELECT iddeudas from deudas where fk_cliente='"+idCliente+"' order by iddeudas ASC;");
        if(rs.last())
            result= rs.getInt(1);
        return result;
    }
    
    /**
     * 
     * @param idCliente
     * @return el id de la ultima deuda del cliente
     * @throws SQLException 
     */
    
    public int getIdUltimaDeuda(int idCliente) throws SQLException{
        ResultSet rs = Consulta ("Select iddeudas from deudas where fk_cliente = '"+idCliente+"';");
        int result = -1;
        if (rs.first()) {
            do {                
                
            } while (estaTerminada(rs.getInt(1)) && rs.next() );
            
            result = rs.getInt(1);
        }
        
        return result;
    }
    
    /**
     * 
     * @param idCliente 
     * @param cantidad cantidad de deudas a obtener
     * @return los id de las ultimas deudas de un 
     * cliente
     * @exception SQLException
     */
    
    public int[] getIdUltimaDeuda(int idCliente,int cantidad) throws SQLException{
        ResultSet rs = Consulta ("Select iddeudas from deudas where fk_cliente = '"+idCliente+"';");
        ResultSet cant = Consulta("Select count(*) from deudas where fk_cliente = '"+idCliente+"';");
        int[] resultado=null;
        if(cant.first())
            if(cantidad <= cant.getInt(1)){
                resultado = new int[cantidad];
            } else {
                resultado = new int[cant.getInt(1)];
            }
        
        if(rs.last()){
            for(int i = 0;i<resultado.length;i++){
                resultado[i] = rs.getInt(1);
                rs.previous();
            }
        }
        return resultado;
    }
   
    public double getMontoPrestamo(int idDeuda) throws SQLException{
        ResultSet rs;
        double result = 0;
        rs = Consulta("SELECT deudas.monto_prestamo FROM styles.deudas where iddeuda='"+idDeuda+"';");
        if(rs.first())
        result = rs.getDouble(1);
        return result;
    }
    
    public void setMontoPrestamo(int idDeuda,double nuevoMonto) throws SQLException{
        Ejecutar("update styles.deudas set monto_prestamo ='"+nuevoMonto+"' where iddeudas='"+idDeuda+"';");
    }
    
    public int getCuotas(int idDeuda) throws SQLException{
        ResultSet rs;
        int result =0;
        rs = Consulta("SELECT cuotas FROM deudas where iddeudas='"+idDeuda+"';");
        if(rs.first())
            result= rs.getInt(1);
        return result;
    }
    
    public java.util.Date fechaGenerada(int idDeuda) throws SQLException{
        ResultSet rs;
        java.util.Date result = new java.util.Date();
        rs = Consulta("SELECT fecha_generada FROM deudas where iddeudas='"+idDeuda+"';");
        if(rs.first())
        result = rs.getDate(1);
        return result;
        
    }

    public int getIdCliente( int idDeuda) throws SQLException{
        ResultSet rs;
        rs = Consulta("SELECT fk_cliente FROM deudas where iddeudas='"+idDeuda+"';");
        if(!rs.first())
            rs.first();
        int result = rs.getInt(1);
        return result;
    }
    
    public int getCantidadDeudas(int idCliente) throws SQLException{
        ResultSet rs;
        rs = Consulta("select count(*) from deudas where fk_cliente ='"+idCliente+"';");
        rs.first();
        return rs.getInt(1);
    }
    
    public void agregarUnaCuota(int idDeuda) throws SQLException{
        Ejecutar("update deudas set cuotas=cuotas+1 where iddeudas='"+idDeuda+"';");
    }
    
    /**
     * 
     * @param idDeuda
     * @return
     * <ul>
     * <li>[0]: monto de la deuda
     * <li>[1]: Cuotas
     * <li>[2]: Fecha
     * <li>[3]: Id Cliente
     * <ul>
     * @throws SQLException 
     */
    public String[] getDatosDeudas(int idDeuda) throws SQLException{
        String datos[]=new String [5];
        ResultSet rs = Consulta("select monto_prestamo,cuotas,fecha_generada,fk_cliente,monto_interes from deudas where iddeudas='"+idDeuda+"';");
        if(rs.first()){
        
        datos[0]=String.valueOf(rs.getDouble(1));
        datos[1]=String.valueOf(rs.getInt(2));
        datos[2]=String.valueOf(dateToString(rs.getDate(3), 2));
        datos[3]=String.valueOf(rs.getInt(4));
        datos[4]=String.valueOf(rs.getDouble(5));
        }
        return datos;
    
        
        
        /*String[] datos = new String[4];
       
        ResultSet rs = Consulta("select monto_prestamo,cuotas,fecha_generada,fk_cliente from deudas where iddeudas = '"+idDeuda+"';");
        if(!rs.first()){
            rs.first();
            datos[0]= Double.toString(rs.getDouble(1));
            datos[1] = Integer.toString(rs.getInt(2));
            datos[2] = dateToString(rs.getDate(3),1);
            datos[3] = Integer.toString(rs.getInt(4));
        }
        
        
        return datos;*/
    }
    
    public String[][] getTodasDeudas (int idCliente) throws SQLException{
        ResultSet rs = Consulta ("SELECT deudas.* FROM styles.deudas INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes) WHERE (clientes.idclientes=' "+idCliente+"');");
        ResultSet cantidadSQL = Consulta ("SELECT count(*) FROM styles.deudas INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes) WHERE (clientes.idclientes='"+idCliente+"');");
        int cantidad = 0;
        if(cantidadSQL.first())
            cantidad = cantidadSQL.getInt(1);
        
        String[][] resultado = new String [5][cantidad];
        
        if(rs.first()){
            for(int i=0; i<cantidad;i++){
                resultado[0][i] = Double.toString(rs.getDouble(2));
                resultado[1][i] = Integer.toString(rs.getInt(3));
                resultado[2][i] = dateToString(rs.getDate(4), 1);
                resultado[3][i] = Integer.toString(rs.getInt(5));
                resultado[4][i]=String.valueOf(rs.getDouble(5));
                rs.next();
            }
        }
        
        return resultado;
    }
    
    public String getFechaCaducacion(int idDeuda) throws SQLException{
        ResultSet rs = Consulta("select fecha_caducacion from styles.cuota where fk_deuda = '"+idDeuda+"' order by fecha_caducacion ASC;");
        String fecha = "";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");;
        
        if (rs.last()){
            fecha = sdf.format(rs.getDate(1));
            
        }
        
        return fecha;
    }
    
    /**
     * revisa que la deuda esta terminada.
     * @param idDeuda
     * @return en caso que este completa devuelve TRUE, caso contrario FALSE
     * @throws SQLException 
     */
    public boolean estaTerminada(int idDeuda) throws SQLException{
        ResultSet rs = Consulta("SELECT `cuota`.`pagada` , `deudas`.`iddeudas` FROM `styles`.`cuota` INNER JOIN `styles`.`deudas`  ON (`cuota`.`fk_deuda` = `deudas`.`iddeudas`) WHERE (`deudas`.`iddeudas` = "+idDeuda+");");
        boolean terminada = true;
        
        if (rs.first()){
            do {
                terminada = terminada && rs.getBoolean(1); 
            } while (rs.next());
        }
    
    return terminada;
    }
    
    public double getInteres(int idDeuda)throws SQLException{
        ResultSet rs = Consulta("select monto_interes from deudas where iddeudas='"+idDeuda+"';");
        double interes = 0;
        
        if(rs.first())
            interes = rs.getDouble(1);
        
        return interes;
    }
    
    public void setInteres(int idDeuda,double monto) {
        Ejecutar("UPDATE styles.deudas SET monto_interes='"+monto+"' WHERE  iddeudas ='"+idDeuda+"';");
    }
    
    public void llenarPagos(int idDeuda, javax.swing.JTable tabla) throws SQLException{

	ResultSet rs1 = Consulta("SELECT `pago`.`monto` , `pago`.`fecha` FROM `styles`.`cuota` INNER JOIN `styles`.`deudas` ON (`cuota`.`fk_deuda` = `deudas`.`iddeudas`) INNER JOIN `styles`.`pago` ON (`pago`.`fk_cuota` = `cuota`.`idcuota`) WHERE (`deudas`.`iddeudas` = "+idDeuda+") ORDER BY fecha DESC ;");
	ResultSet rs2 = Consulta("SELECT `pago`.`monto` , `pago`.`fecha` FROM `styles`.`cuota` INNER JOIN `styles`.`deudas` ON (`cuota`.`fk_deuda` = `deudas`.`iddeudas`) INNER JOIN `styles`.`pago` ON (`pago`.`fk_cuota` = `cuota`.`idcuota`) WHERE (`deudas`.`iddeudas` = "+idDeuda+") ORDER BY fecha DESC ;");
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        Object[]array = new Object[2];
        double monto = 0;
        if(rs1.first() && rs2.first()){
            monto = 0;
            
            if(rs2.next()){
                
                do{
                    if((rs1.getDate(2).equals(rs2.getDate(2)))) {
                        monto += rs1.getDouble(1);
                    }
                    else{
                        monto +=rs1.getDouble(1);
                        array[0] = monto;
                        array[1] = rs1.getDate(2);
                        model.addRow(array);

                        monto = 0;
                    }

                }while(rs1.next() && rs2.next());
              }
            monto +=rs1.getDouble(1);
            array[0] = monto;
            array[1] = rs1.getDate(2);
            model.addRow(array);
        }
        else {
            model.setRowCount(0);
        }
    }
    

    
    
}
