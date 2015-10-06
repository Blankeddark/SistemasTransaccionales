package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import vos.CuentaValues;
import vos.UsuarioActivoValues;
import vos.UsuarioValues;

public class ConsultaUsuarioMasActivoDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public ConsultaUsuarioMasActivoDAO()
	{
		inicializar("./Conexion/conexion.properties");
	}

	public void inicializar(String path)
	{
		try
		{
			File arch = new File(path+ARCHIVO_CONEXION);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream ("C:/Users/Sergio/Documents/Sistrans/Project Sistrans/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PROJECT_Sistrans3/conexion.properties");

			prop.load(in);
			in.close();

			cadenaConexion = prop.getProperty("url");
			usuario = prop.getProperty("usuario");
			clave = prop.getProperty("clave");
			@SuppressWarnings ("unused")
			final String driver = prop.getProperty("driver");

		}

		catch (Exception e)
		{
			e.printStackTrace(); 
		}

	}

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

	public void closeConnection (Connection connection) throws Exception
	{
		try
		{
			connection.close();
			connection = null;

		}

		catch (SQLException exception)
		{
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexiÃ³n.");
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
			ResultSet rs = s.executeQuery("  SELECT NOMBRE, CORREO, NUMERO_ID, TIPO_ID, TIPO_USUARIO, NUMERO_OPERACIONES "
					+ " FROM (SELECT * FROM (SELECT CORREO, COUNT(CORREO) AS NUMERO_OPERACIONES FROM (SELECT NOMBRE, NUMERO_ID, "
					+ " TIPO_ID, CORREO, TIPO_USUARIO, TIPO_TRANSACCION, OFICINA FROM ( SELECT * "
					+ " FROM USUARIOS u JOIN (SELECT ID_TRANSACCION, TIPO AS TIPO_TRANSACCION, "
					+ " CORREO_USUARIO , ID_PUNTO_ATENCION FROM TRANSACCIONES) "
					+ " t ON(u.CORREO = t.CORREO_USUARIO) ) b JOIN PUNTOS_ATENCION p ON "
					+ " (b.id_punto_atencion = p.id) WHERE TIPO_TRANSACCION = " + "'" + tipoTransaccion +"'" +  
					") f   GROUP BY CORREO ORDER BY COUNT(CORREO) DESC) WHERE ROWNUM = 1 ) "
					+ "NATURAL JOIN USUARIOS" );

			while(rs.next())
			{		
				String correo = rs.getString("CORREO");
				String nombre = rs.getString("NOMBRE");
				String numeroID = rs.getString("NUMERO_ID");
				String tipoID = rs.getString("TIPO_ID");
				String tipoUsuario = rs.getString("TIPO_USUARIO");
				int numTransacciones = rs.getInt("NUMERO_OPERACIONES");
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
			ResultSet rs = s.executeQuery("  SELECT NOMBRE, CORREO, NUMERO_ID, TIPO_ID, TIPO_USUARIO, NUMERO_OPERACIONES "
					+ " FROM (SELECT * FROM (SELECT CORREO, COUNT(CORREO) AS NUMERO_OPERACIONES FROM (SELECT NOMBRE, NUMERO_ID, "
					+ " TIPO_ID, CORREO, TIPO_USUARIO, TIPO_TRANSACCION, OFICINA FROM ( SELECT * "
					+ " FROM USUARIOS u JOIN (SELECT ID_TRANSACCION, TIPO AS TIPO_TRANSACCION, "
					+ " CORREO_USUARIO , ID_PUNTO_ATENCION FROM TRANSACCIONES) "
					+ " t ON(u.CORREO = t.CORREO_USUARIO) ) b JOIN PUNTOS_ATENCION p ON "
					+ " (b.id_punto_atencion = p.id) WHERE TIPO_TRANSACCION = " + "'" + tipoTransaccion + "'" +  
					" AND OFICINA = " + idOficina + " ) f "
					+ " GROUP BY CORREO ORDER BY COUNT(CORREO) DESC) WHERE ROWNUM = 1 ) "
					+ "NATURAL JOIN USUARIOS" );

			while(rs.next())
			{		
				String correo = rs.getString("CORREO");
				String nombre = rs.getString("NOMBRE");
				String numeroID = rs.getString("NUMERO_ID");
				String tipoID = rs.getString("TIPO_ID");
				String tipoUsuario = rs.getString("TIPO_USUARIO");
				int numTransacciones = rs.getInt("NUMERO_OPERACIONES");
				UsuarioActivoValues usuarioActual = new UsuarioActivoValues(nombre, numeroID, tipoID, numTransacciones, tipoUsuario, correo);
				cuentas.add(usuarioActual);
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}


		finally
		{
			return cuentas;
		}

	}
}
