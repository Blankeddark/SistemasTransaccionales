package DAOS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vos.UsuarioActivoValues;

public class ConsultaUsuarioMasActivoDAO 
{

	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;
	
	
	private void establecerConexion (String url, String usuario, String clave) throws SQLException
	{
		System.out.println("------- Oracle Connection Testing ------- ");
		try{
			Class.forName("oracle.jdbc.OracleDriver");

		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Oracle JDBC driver is missing");
			e.printStackTrace();
			return;
		}

		System.out.println("Oracle JDBC Driver is Registered");

		try
		{

			conexion = DriverManager.getConnection( "jdbc:oracle:thin:@fn3.oracle.virtual.uniandes.edu.co:1521:prod", "ISIS2304211520", "fyJEyG3u7wXA");
		}

		catch (SQLException e)
		{
			System.out.println("Connection Failed");
			e.printStackTrace();
			return;
		}
		if(conexion != null)
		{
			System.out.println("You access to your dataBase");
		}
		else
		{
			System.out.println("Connection Fail");
		}
	}
	
	/**
	 * Método que retorna un arrayList con la información del usuario (o usuarios) más activos 
	 * de la BD a partir de un tipo de transacción en particular. Un usuario activo values solo
	 * contiene la información que 
	 * @param tipoTransaccion
	 * @return
	 * @throws Exception
	 */
	public ArrayList<UsuarioActivoValues> darUsuarioActivoGeneral(String tipoTransaccion) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<UsuarioActivoValues>  cuentas = new ArrayList<UsuarioActivoValues> ();
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM "
					+ "(SELECT NOMBRE, NUMERO_ID, TIPO_ID, CORREO, TIPO_USUARIO, COUNT(ID_TRANSACCION) AS NUM_TRANSACCIONES"
					+ " FROM USUARIOS c JOIN TRANSACCIONSES t ON (c.CORREO = t.CORREO_USUARIO ) )"
					+ "WHERE TIPO = " + tipoTransaccion + "  HAVING MAX (COUNT(ID_TRANSACCION))" );

			while(rs.next())
			{		
               String correo = rs.getString("CORREO");
               String nombre = rs.getString("NOMBRE");
               String numeroID = rs.getString("NUMERO_ID");
               String tipoID = rs.getString("TIPO_ID");
               String tipoUsuario = rs.getString("TIPO_USUARIO");
               int numTransacciones = rs.getInt("NUM_TRANSACCIONES");
               UsuarioActivoValues usuarioActual = new UsuarioActivoValues(nombre, numeroID, tipoID, numTransacciones, tipoUsuario, correo);
               cuentas.add(usuarioActual);
			}
			
			

		}

		finally
		{
			return cuentas;
		}

	}
	
	
	/**
	 * Método que retorna un arrayList con la información del usuario (o usuarios) más activos 
	 * de una oficina a partir de un tipo de transacción en particular. Un usuario activo values solo
	 * contiene la información que 
	 * @param tipoTransaccion
	 * @return
	 * @throws Exception
	 */
	public ArrayList<UsuarioActivoValues> darUsuarioActivoOficina(String tipoTransaccion, int idOficina) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<UsuarioActivoValues>  cuentas = new ArrayList<UsuarioActivoValues> ();
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery(" SELECT * FROM (SELECT * FROM "
					+ "(SELECT ID_PUNTO_ATENCION, NOMBRE, NUMERO_ID, TIPO_ID, CORREO, TIPO_USUARIO, COUNT(ID_TRANSACCION) AS NUM_TRANSACCIONES"
					+ " FROM USUARIOS c JOIN TRANSACCIONSES t ON (c.CORREO = t.CORREO_USUARIO ) ) b "
					+ "JOIN PUNTOS_ATENCION p ON (b.id_punto_atencion = p.id) ) "
					+ "WHERE TIPO = " + tipoTransaccion + " AND OFICINA = " + idOficina + "  HAVING MAX (COUNT(ID_TRANSACCION))" );

			while(rs.next())
			{		
               String correo = rs.getString("CORREO");
               String nombre = rs.getString("NOMBRE");
               String numeroID = rs.getString("NUMERO_ID");
               String tipoID = rs.getString("TIPO_ID");
               String tipoUsuario = rs.getString("TIPO_USUARIO");
               int numTransacciones = rs.getInt("NUM_TRANSACCIONES");
               UsuarioActivoValues usuarioActual = new UsuarioActivoValues(nombre, numeroID, tipoID, numTransacciones, tipoUsuario, correo);
               cuentas.add(usuarioActual);
			}
			
		}

		finally
		{
			return cuentas;
		}

	}
}
