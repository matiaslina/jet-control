package style.Bases;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Matias
 */
public class BClientes extends BasesDeDatos{
    
    public void insertarCliente (String nombre,String apellido,String celular,String telefono,String direccion,String particular,String correo){
        Ejecutar("insert into clientes (nombre,apellido,celular,telefono,direccion,mail,particular) values ('"+nombre+"','"+apellido+"','"+celular+"','"+telefono+"','"+direccion+"','"+correo+"','"+particular+"');");
    }
    
    public void borrarCliente(int id){
        Ejecutar ("delete from clientes where idclientes = '"+id+"';");
    }
    
    public void setNombreCliente(int id,String nombre){
        Ejecutar("UPDATE styles.clientes SET nombre='"+nombre+"' WHERE  idclientes ='"+id+"';");
    }    
    public void setApellidoCliente(int id,String apellido){
        Ejecutar("UPDATE styles.clientes SET apellido='"+apellido+"' WHERE idclientes='"+id+"';");
    }
    
    public void setCelularCliente(int id,String celular){
        Ejecutar("UPDATE styles.clientes SET celular='"+celular+"' WHERE idclientes='"+id+"';");
    }
    public void setTelefonoCliente(int id,String telefono){
        Ejecutar("UPDATE styles.clientes SET telefono='"+telefono+"' WHERE idclientes='"+id+"';");
    }
    public void setDireccionCliente(int id,String direccion){
        Ejecutar("UPDATE styles.clientes SET direccion='"+direccion+"' WHERE idclientes='"+id+"';");
    }
    
    public void setDirParticularCliente(int id,String direccion){
        Ejecutar("UPDATE styles.clientes SET particular='"+direccion+"' WHERE idclientes='"+id+"';");
    }

        public void setCorreoCliente(int id,String correo){
        Ejecutar("UPDATE styles.clientes SET correo='"+correo+"' WHERE idclientes='"+id+"';");
    }
    
    public void setOrden (int id,int orden){
        Ejecutar("UPDATE styles.clientes SET orden="+orden+" WHERE idclientes='"+id+"';");
    }


    /**
     * 
     * @param columna se refiere a la columna donde queremos buscar. puede ser "nombre","apellido","celular","telefono","direccion" o "mail", en otro caso retorna -1
     * @param parametro denota el parametro de busqueda, si no existe en la tabla, retorna -1
     * @return id del cliente si existe, sino -1
     * @throws SQLException 
     */
    public int getIdCliente(String columna,String parametro) throws SQLException{
        int resultado = -1;
        if("nombre".equals(columna)||"apellido".equals(columna)||"celular".equals(columna)||"telefono".equals(columna)||"direccion".equals(columna)||"correo".equals(columna)){
            ResultSet rs;
            rs = Consulta("SELECT idclientes from clientes where "+columna+"='"+parametro+"';");
            if(rs.next()){
                if(rs.first())
                    resultado = rs.getInt(1);
            }
        }
        
        return resultado;
    }
    
    public int getIdFromNomAp(String nombre,String apellido) throws SQLException{
         ResultSet res;
         res = Consulta("SELECT idclientes from clientes where nombre='"+nombre+"' and apellido = '"+apellido+"';");
         
         int index=-1; 
        if(res.first()) 
            index= res.getInt(1);
         return index;
         
         
    }


    public void llenarLista(java.awt.List lb,String parametro) throws SQLException{
        ResultSet rs = Consulta("select idclientes, nombre,apellido from styles.clientes;");
        int cantidad = getCantidadFilas("clientes");
        lb.removeAll();
        if(rs.first()){
            for(int i=1;i<=cantidad;i++){
                String nomAp = rs.getInt(1)+" "+ rs.getString(2)+" "+rs.getString(3);

                if(nomAp.contains(parametro)){
                    lb.add(nomAp);
                }
                rs.next();
            }
        // rs.close();
        }
    }
    
    /**
     * 
     * @param id ingresa el id del cliente del cual obtendremos los datos.
     * @return un array de tamaño 6 que contiene los datos del cliente en cuestion:
     * <ul>
     * <li>[0]: nombre
     * <li>[1]: apellido
     * <li>[2]: celular
     * <li>[3]: telefono
     * <li>[4]: direccion
     * <li>[5]: mail
     * <li>[6]: orden
     * <li>[7]: particular
     * <ul>
     * @throws SQLException 
     */
    public String[] getDatosCliente(int id) throws SQLException{
        String datos[]=new String [8];
        ResultSet rs = Consulta("select nombre,apellido,celular,telefono,direccion,mail,orden,particular from clientes where idclientes='"+id+"';");
        if(rs.first()){

            for(int i=1;i<=8;i++){
                datos[i-1]=rs.getString(i);
            }
        }
        return datos;
    }
    
    public int getCantidadFilas() throws SQLException{
        ResultSet res = Consulta ("Select count(*) from clientes;");
        int resultado = 0;
        if(res.first())
          resultado= res.getInt(1);
    
        return resultado;
    }
    
    public int getIdCobrador(int idCliente) throws SQLException{
        ResultSet res = Consulta ("SELECT cobradores.idcobradores FROM styles.deudas INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes) INNER JOIN styles.cuota ON (cuota.fk_deuda = deudas.iddeudas) INNER JOIN styles.pago ON (pago.fk_cuota = cuota.idcuota) INNER JOIN styles.cobradores ON (pago.fk_cobrador = cobradores.idcobradores) WHERE (clientes.idclientes ='"+idCliente+"');");
        int resultado=-1;
        if(res.last());
            resultado=res.getInt(1);
            
        return resultado;
    }
    
    public boolean cambiarCobrador(int idCliente, int newIdCobrador) throws SQLException{
        ResultSet res = Consulta ("SELECT pago.idpago,pago.fk_cobrador FROM styles.deudas INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes) INNER JOIN styles.cuota ON (cuota.fk_deuda = deudas.iddeudas) INNER JOIN styles.pago ON (pago.fk_cuota = cuota.idcuota) INNER JOIN styles.cobradores ON (pago.fk_cobrador = cobradores.idcobradores) WHERE (clientes.idclientes ='"+idCliente+"');");
        
        if (res.first()){
            do{
                if (res.getInt(2)!=newIdCobrador){
                    Ejecutar("UPDATE pago SET fk_cobrador='"+newIdCobrador+"' WHERE idpago="+res.getInt(1) +";");
                }
            }while(res.next());
        }
        
        return true;
    }
    
        
    public boolean clientePago(int index) throws SQLException{
        ResultSet rs = Consulta ("SELECT count(pago.idpago) FROM styles.deudas INNER JOIN styles.clientes ON (deudas.fk_cliente = clientes.idclientes) INNER JOIN styles.cuota ON (cuota.fk_deuda = deudas.iddeudas)INNER JOIN styles.pago ON (pago.fk_cuota = cuota.idcuota)INNER JOIN styles.cobradores ON (pago.fk_cobrador = cobradores.idcobradores) WHERE (clientes.idclientes ='"+index+"');");
        rs.first();
        if (rs.getInt(1)==0)
            return false;
        else
            return true;
    }
    
    
                
            
            
            
    }

