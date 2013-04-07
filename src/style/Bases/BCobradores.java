/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package style.Bases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Matias
 */
public class BCobradores extends BasesDeDatos{
    
        
    public void insertarCobrador (String nombre,String apellido,String celular,String telefono,String direccion,String correo){
        Ejecutar("insert into cobradores (nombre,apellido,celular,telefono,direccion,mail) values ('"+nombre+"','"+apellido+"','"+celular+"','"+telefono+"','"+direccion+"','"+correo+"');");
    }
    
    public void borrarCobrador(int id){
        Ejecutar ("delete from cobradores where idcobradores = '"+id+"';");
    }
    
    public void setNombreCobrador(int id,String nombre){
        Ejecutar("UPDATE cobradores SET nombre='"+nombre+"' WHERE idcobradores='"+id+"';");
    }
    
    public void setApellidoCobrador(int id,String apellido){
        Ejecutar("UPDATE cobradores SET apellido='"+apellido+"' WHERE idcobradores='"+id+"';");
    }
    
    public void setCelularCobrador(int id,String celular){
        Ejecutar("UPDATE cobradores SET celular='"+celular+"' WHERE idcobradores='"+id+"';");
    }
    public void setTelefonoCobrador(int id,String telefono){
        Ejecutar("UPDATE cobradores SET telefono='"+telefono+"' WHERE idcobradores='"+id+"';");
    }
    public void setDireccionCobrador(int id,String direccion){
        Ejecutar("UPDATE cobradores SET direccion='"+direccion+"' WHERE idcobradores='"+id+"';");
    }

    public void setCorreoCobrador(int id,String correo){
        Ejecutar("UPDATE cobradores SET correo='"+correo+"' WHERE idcobradores='"+id+"';");
    }
    
    /**
     * 
     * @param columna se refiere a la columna donde queremos buscar. puede ser "nombre","apellido","celular","telefono","direccion" o "mail", en otro caso retorna -1
     * @param parametro denota el parametro de busqueda, si no existe en la tabla, retorna -1
     * @return id del Cobrador si existe, sino -1
     * @throws SQLException 
     */
    public int getIdCobrador(String columna,String parametro) throws SQLException{
        int resultado = -1;
        if("nombre".equals(columna)||"apellido".equals(columna)||"celular".equals(columna)||"telefono".equals(columna)||"direccion".equals(columna)||"correo".equals(columna)){
            ResultSet rs;
            rs = Consulta("SELECT idcobradores from cobradores where "+columna+"='"+parametro+"';");
            if(rs.next()){
                if(!rs.first())
                    rs.first();
                resultado = rs.getInt(1);
            }
        }
        
        return resultado;
    }

     public void getNombreApellido (JComboBox jcb) throws SQLException{
        ResultSet rs = Consulta("select nombre,apellido from styles.cobradores;");
        String result = "";
        if(rs.first()){
            do{
                result = rs.getString(1)+" "+ rs.getString(2);
                jcb.addItem(result);
            } while (rs.next());
        } else {
            jcb.addItem(result);
        }
     }
     
     public void getIndexCobrador (JComboBox jcb,int idCobrador) throws SQLException{
         ResultSet rs = Consulta("select idcobrador from styles.cobradores;");
         if(rs.first()){
             do{
                 if(rs.getInt(1) == idCobrador)
                     jcb.setSelectedIndex(rs.getRow()-1);
                     break;
             }while(rs.next());
         }
     }

      public void llenarTablaCobradores (JTable tabla,int idCobrador) {
           	try {
        	ResultSet rs = Consulta("SELECT clientes.idclientes FROM styles.pago INNER JOIN styles.cobradores ON (pago.fk_cobrador = cobradores.idcobradores) INNER JOIN styles.cuota ON (pago.fk_cuota = cuota.idcuota) INNER JOIN styles.deudas ON (cuota.fk_deuda = deudas.iddeudas) INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes) WHERE (cobradores.idcobradores='" + idCobrador + "' ) ORDER BY clientes.idclientes ASC;");
        	
                DefaultTableModel modeloFilas = (DefaultTableModel) tabla.getModel();
        	modeloFilas.setRowCount(0);
                
                // cambiar los anchos de la tabla
                tabla.getColumnModel().getColumn(0).setWidth(30);

                // inicializacion de variables
                // QUE SUME EL TOTAL DE ATRASO,MONTO Y PAGO ANTERIOR
                float totalAtraso = 0;
                float totalPagoAnterior = 0;
                float totalRestante = 0;
                float totalMontoCuota = 0;
                int idCliente = -1;
        	int idClienteAnterior;
        	String[] arr = new String[11];

                BClientes dbcli = new BClientes();
        	BDeuda dbd = new BDeuda();
        	BCuotas dbcuo = new BCuotas();
        	BPago dbp = new BPago();
        	dbcli.ConectarSQL();
        	dbd.ConectarSQL();
        	dbcuo.ConectarSQL();
        	dbp.ConectarSQL();
                
        	if (rs.first()) {
            	
            	
            	do {
                        idClienteAnterior = rs.getInt(1);
                	if (idCliente != idClienteAnterior) {
                        idCliente = rs.getInt(1);

                        //1 PAGO, 2 MONTO, 3 CUOTA, 4 NOMBRE CLIENTE, 5 DOMICILIO,  6 ATRASO, 
                        //7 PAGO ANTERIOR, 8 PAGADO, 9 RESTANTE, 10 VENCIMIENTO.-
                    	int idDeuda = dbd.getIdDeudas(idCliente);
                        arr[0] = dbcli.getDatosCliente(idCliente)[6]; // orden
                        arr[1] = "0";  // cobro!
                        arr[2] = String.valueOf(dbcuo.getMontoCuota(idDeuda));  // monto cuota
                        arr[3] = String.valueOf(dbd.getCuotas(idDeuda));    // cuota
                    	arr[4] = dbcli.getDatosCliente(idCliente)[1] + ", " + dbcli.getDatosCliente(idCliente)[0]; //nombre
                    	arr[5] = dbcli.getDatosCliente(idCliente)[4];   // direccion
                        arr[6] = String.valueOf((dbp.contadorPagosVacios(idDeuda) * dbcuo.getMontoCuota(idDeuda)) + dbd.getInteres(idDeuda) - dbp.getMontoCliente(idCliente,idDeuda)); // atraso
                        arr[7] = String.valueOf(dbp.getPagoAnterior(idCliente)); // pago anterior
                    	arr[8] = String.valueOf(dbp.getMontoCliente(idCliente,idDeuda));    //pagado
                    	arr[9] = String.valueOf((dbcuo.getMontoDeuda(idDeuda))  - dbp.getMontoCliente(idCliente,idDeuda)); // restante
                    	arr[10] = dbd.getFechaCaducacion(idDeuda);   // fecha caducacion
                        
                        
                    	
                        //if (dbcuo.getCantidadCuotasPagadas(idDeuda) != Double.valueOf(arr[4]))
                        if (!dbd.estaTerminada(idDeuda)){
                            totalAtraso += Float.valueOf(arr[6]);
                            totalPagoAnterior += Float.valueOf(arr[7]);
                            totalMontoCuota += Float.valueOf(arr[2]);
                            totalRestante += Float.valueOf(arr[9]);
                             modeloFilas.addRow(arr);
                        }
                        
                        idClienteAnterior = idCliente;
                    	idCliente = rs.getInt(1);
                	}

            	} while (rs.next());
                
                modeloFilas.addRow(new Object[]{"","",totalMontoCuota,"","","",totalAtraso,totalPagoAnterior,"",totalRestante,""});
        	}
                rs.close();
        	dbcli.CerrarSQL();
        	dbd.CerrarSQL();
        	dbcuo.CerrarSQL();
        	dbp.CerrarSQL();
        	} catch (SQLException ex) {
        	Logger.getLogger(BCobradores.class.getName()).log(Level.SEVERE, null, ex);

        	}

    }

    public int getIdFromNomAp(String nombre,String apellido) throws SQLException{
         ResultSet rs = Consulta("SELECT idcobradores from styles.cobradores where nombre='"+nombre+"' and apellido = '"+apellido+"';");
         int index=-1;
            if(rs.first())
                index= rs.getInt(1);

         return index;


    }
    /**
     * 
     * @param id ingresa el id del Cobrador del cual obtendremos los datos.
     * @return un array de tamaño 6 que contiene los datos del Cobrador en cuestion:
     * <ul>
     * <li>[0]: nombre
     * <li>[1]: apellido
     * <li>[2]: celular
     * <li>[3]: telefono
     * <li>[4]: direccion
     * <li>[5]: mail
     * <ul>
     * @throws SQLException 
     */
    public String[] getDatosCobrador(int id) throws SQLException{
        String datos[]=new String [6];
        ResultSet rs = Consulta("select nombre,apellido,celular,telefono,direccion,mail from cobradores where idcobradores='"+id+"';");
        if(!rs.first()){
            rs.first();
        }    
            for (int i=1;i<=6;i++){
                datos[i-1]=rs.getString(i);
            }
        return datos;
    }
}
