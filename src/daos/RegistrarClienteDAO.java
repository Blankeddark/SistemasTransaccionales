package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

/**
 * Clase RegistroDeClientesDAO, encargada de hacer las consultas basicas para el caso de uso Registro de clientes
 */

public class RegistrarClienteDAO 
{
	    //----------------------------------------------------
	    //Constantes
	    //----------------------------------------------------
	 
	    /**
	     * ruta donde se encuentra el archivo de conexi���n.
	     */
	    private static final String ARCHIVO_CONEXION = "/../conexion.properties";
	 
	 
	    //----------------------------------------------------
	    //Atributos
	    //----------------------------------------------------
	    /**
	     * conexion con la base de datos
	     */
	    public Connection conexion;
	 
	    /**
	     * nombre del usuario para conectarse a la base de datos.
	     */
	    private String usuario;
	 
	    /**
	     * clave de conexi���n a la base de datos.
	     */
	    private String clave;
	 
	    /**
	     * URL al cual se debe conectar para acceder a la base de datos.
	     */
	    private String cadenaConexion;
	 
	    /**
	     * constructor de la clase. No inicializa ningun atributo.
	     */
	    public RegistrarClienteDAO() {
	    }
	 
	    // -------------------------------------------------
	    // M���todos
	    // -------------------------------------------------
	 
	    /**
	     * obtiene ls datos necesarios para establecer una conexion
	     * Los datos se obtienen a partir de un archivo properties.
	     * @param path ruta donde se encuentra el archivo properties.
	     */
	    public void inicializar(String path)
	    {
	        try
	        {
	            File arch= new File(path+ARCHIVO_CONEXION);
	            Properties prop = new Properties();
	            FileInputStream in = new FileInputStream( arch );
	 
	            prop.load( in );
	            in.close( );
	 
	            cadenaConexion = prop.getProperty("url");   // El url, el usuario y passwd deben estar en un archivo de propiedades.
	            // url: "jdbc:oracle:thin:@chie.uniandes.edu.co:1521:chie10";
	            usuario = prop.getProperty("usuario");  // "s2501aXX";
	            clave = prop.getProperty("clave");  // "c2501XX";
	            final String driver = prop.getProperty("driver");
	            Class.forName(driver);
	 
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	 
	        }   
	    }
	 
	    /**
	     * M���todo que se encarga de crear la conexi���n con el Driver Manager
	     * a partir de los parametros recibidos.
	     * @param url direccion url de la base de datos a la cual se desea conectar
	     * @param usuario nombre del usuario que se va a conectar a la base de datos
	     * @param clave clave de acceso a la base de datos
	     * @throws SQLException si ocurre un error generando la conexi���n con la base de datos.
	     */
	    private void establecerConexion(String url, String usuario, String clave) throws SQLException
	    {
	        try
	        {
	            conexion = DriverManager.getConnection(url,usuario,clave);
	        }
	        catch( SQLException exception )
	        {
	            System.out.println(exception.getLocalizedMessage());
	            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	            throw new SQLException( "ERROR: ConsultaDAO obteniendo una conexi���n." );
	        }
	    }
	 
	    /**
	     *Cierra la conexi���n activa a la base de datos. Adem���s, con=null.
	     * @param con objeto de conexi���n a la base de datos
	     * @throws SistemaCinesException Si se presentan errores de conexi���n
	     */
	    public void closeConnection(Connection connection) throws Exception 
	    {        
	        try
	        {
	            connection.close();
	            connection = null;
	        } 
	        catch (SQLException exception)
	        {
	 
	            throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexi���n.");
	        }
	    } 
	 
	    // ---------------------------------------------------
	    // Metodos asociados a los casos de uso: ConsultarEnvios
	    // ---------------------------------------------------
	 
	    /**
	     * 
	     * @param placaVehiculo
	     * @return
	     * @throws Exception
	     */
	    public boolean registrarClienteExistente( String correo ) throws Exception
	    {
	        PreparedStatement prepStmt = null;
	        try
	        {
	            establecerConexion(cadenaConexion, usuario, clave);
	            conexion.setAutoCommit(false);
	            conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	             
	            String sentencia = "update vehiculo set disponible = 'Y' where placa = '"+correo+"'";
	            System.out.println("--------------------------------------------------------------------------");
	            System.out.println(sentencia);
	            prepStmt = conexion.prepareStatement(sentencia);
	            prepStmt.executeUpdate();
	            conexion.commit();
	 
	        } 
	        
	        catch (Exception e) 
	        {
	            conexion.rollback();
	            e.printStackTrace();
	            throw new Exception("ERROR = DAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
	        }
	        
	        finally
	        {
	            if (prepStmt != null) 
	            {
	                try {
	                    prepStmt.close();
	                } catch (SQLException exception) {
	 
	                    throw new Exception("ERROR: RegistroDePaquetesDAO: loadRow() =  cerrando una conexiÔøΩÔøΩÔøΩn.");
	                }
	            }
	            closeConnection(conexion);
	        }       
	        return true;
	    }
	     
	    /**
	     * 
	     * @param placa
	     * @param cupo
	     * @param id_oficina
	     * @param id_centro
	     * @param peso_max
	     * @param tamano_max
	     * @param id_terreno
	     * @param id_tipo_envio
	     * @return
	     * @throws Exception
	     */
	    public boolean registrarClienteNoExistente (String correo, String login, String contraseña, String numero_id, String tipo_id, String nombre, String nacionalidad, String direccion, String telefono, String ciudad, String departamento, String cod_postal, String tipo_persona) throws Exception
	    {
	        PreparedStatement prepStmt = null;
	        
	        try
	        {
	            establecerConexion(cadenaConexion, usuario, clave);
	            //conexion.setAutoCommit(false);
	            conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	            
	            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		Date date = new Date();
	    		String fecha_registro = dateFormat.format(date) ;
	            
	            String sentencia = "insert into usuarios (correo, login, contraseña, numero_id, tipo_id, nombre, nacionalidad, direccion, telefono, ciudad, departamento, cod_postal, fecha_registro) "+
	                    "values ('"+correo+"', "+login+", "+contraseña+","+numero_id+","+tipo_id+", "+(nombre.equals("")?"null":"'"+nombre+"'" )+", "+(nacionalidad.equals("")?"null":"'"+nacionalidad+"'" )+", "+(direccion.equals("")?"null":"'"+direccion+"'" )+", "+(telefono.equals("")?"null":"'"+telefono+"'" )+","
	                    		+ ""+(ciudad.equals("")?"null":"'"+ciudad+"'" )+",  "+(departamento.equals("")?"null":"'"+departamento+"'" )+" , "+(cod_postal.equals("")?"null":"'"+cod_postal+"'" )+", "+fecha_registro+")";
	            System.out.println("--------------------------------------------------------------------------");
	            System.out.println(sentencia);
	             
	            String sentencia1 = "insert into clientes (correo, tipo_persona) "+
	                    "values ('"+correo+"', "+tipo_persona+" "+")";
	            System.out.println("--------------------------------------------------------------------------");
	            System.out.println(sentencia1);
	            
	            prepStmt = conexion.prepareStatement(sentencia);
	            prepStmt.executeUpdate();
	            conexion.commit();
	            
	            prepStmt = conexion.prepareStatement(sentencia1);
	            prepStmt.executeUpdate();
	            conexion.commit();
	             
	 
	        } 
	        
	        catch (Exception e) 
	        {
	            conexion.rollback();
	            e.printStackTrace();
	            throw new Exception("ERROR = DAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
	        }
	        
	        finally
	        {
	            if (prepStmt != null) 
	            {
	                try {
	                    prepStmt.close();
	                } catch (SQLException exception) {
	 
	                    throw new Exception("ERROR: RegistroDePaquetesDAO: loadRow() =  cerrando una conexiÔøΩÔøΩÔøΩn.");
	                }
	            }
	            closeConnection(conexion);
	        }       
	        return true;
	    }
	     
	    /**
	     * 
	     * @return
	     * @throws Exception
	     */
	    public ArrayList<String> darTodosLosCorreos ( ) throws Exception
	    {
	        PreparedStatement prepStmt = null;
	        try
	        {
	            establecerConexion(cadenaConexion, usuario, clave);
	            conexion.setAutoCommit(false);
	            conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	 
	            ArrayList<String> placas = new ArrayList<String>();
	 
	            String sentencia = "SELECT correo from usuarios order by correo";
	            prepStmt = conexion.prepareStatement(sentencia);
	            ResultSet rs = prepStmt.executeQuery();
	             
	            while (rs.next())
	            {
	                placas.add(rs.getString("placa"));
	            }
	             
	            return placas;
	 
	        } 
	        catch (Exception e) 
	        {
	            conexion.rollback();
	            e.printStackTrace();
	            throw new Exception("ERROR = DAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
	        }
	        
	        finally
	        {
	            if (prepStmt != null) 
	            {
	                try {
	                    prepStmt.close();
	                } catch (SQLException exception) {
	 
	                    throw new Exception("ERROR: RegistroDePaquetesDAO: loadRow() =  cerrando una conexiÔøΩÔøΩÔøΩn.");
	                }
	            }
	            closeConnection(conexion);
	        }       
	         
	    }
		 
}
