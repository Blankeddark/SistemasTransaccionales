package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import vos.TransaccionValues;

public class ConsultarOperacionesDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public ConsultarOperacionesDAO()
	{
		inicializar("./Conexion/conexion.properties");
	}

	public void inicializar(String path)
	{
		try
		{
			File arch = new File(path+ARCHIVO_CONEXION);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream ("C:/Users/Sergio/git/PROJECT_Sistrans/SistemasTransaccionales/WebContent/conexion.properties");

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
	 * Método que retorna la información en todas las operaciones de un cliente en particular
	 * en forma de transaccionValues
	 * @param ordenarPor
	 * @param agruparPor
	 * @param descoasc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList<TransaccionValues> darOperacionesCliente(String ordenarPor, String filtroTipo, String descoasc, String correoCliente) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList operaciones = new ArrayList();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			if(filtroTipo.trim().equals("") && ordenarPor.trim().equals("") )
			{
				Statement s = conexion.createStatement();
				ResultSet rs = s.executeQuery("SELECT * FROM TRANSACCIONES WHERE CORREO_USUARIO = '" 
						+ correoCliente + "'" );

				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					String tipo = rs.getString("TIPO");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, tipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}	
			}

			else if(!filtroTipo.equals(" ") && ordenarPor.equals(" "))
			{
				Statement s = conexion.createStatement();
				ResultSet rs = s.executeQuery("SELECT * FROM TRANSACCIONES WHERE CORREO_USUARIO = " 
						+ correoCliente + " AND TIPO =  '" + filtroTipo + "'");

				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, filtroTipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}
			}

			else if(filtroTipo.equals(" ") && !ordenarPor.equals(" "))
			{
				Statement s = conexion.createStatement();
				ResultSet rs = s.executeQuery("SELECT * FROM TRANSACCIONES WHERE CORREO_USUARIO = " 
						+ correoCliente  + " ORDER BY " + ordenarPor +  "  " + descoasc);

				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					String tipo = rs.getString("TIPO");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, tipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}	
			}

			else 
			{
				Statement s = conexion.createStatement();
				ResultSet rs = s.executeQuery("SELECT * FROM TRANSACCIONES WHERE CORREO_USUARIO = " 
						+ correoCliente + " AND TIPO =  " + filtroTipo + " ORDER BY " 
						+ ordenarPor + " " + descoasc);

				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, filtroTipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}
			}



		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		finally
		{
			return operaciones;
		}

	}


	/**
	 * Método que devuelve la información de todas las operaciones realizadas en una oficina
	 * en específico cuyo ID entra por parámetro. Toda la información anterior se devuelve en forma de
	 * un arrayList de transaccionesValues
	 * @return
	 * @throws Exception
	 */
	public ArrayList<TransaccionValues> darOperacionesOficina(int idOficina, String ordenarPor, String descoasc, String filtrarPor) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList <TransaccionValues> operaciones = new ArrayList<TransaccionValues>();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();

			if(filtrarPor.trim().equals("") && ordenarPor.trim().equals(""))
			{
				ResultSet rs = s.executeQuery("SELECT * FROM (SELECT ID AS ID_PUNTO_ATENCION, OFICINA "
						+ "FROM PUNTOS_ATENCION) NATURAL JOIN TRANSACCIONES WHERE OFICINA = " + idOficina);

				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					String tipo = rs.getString("TIPO");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					String correoCliente = rs.getString("CORREO_USUARIO");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, tipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}	
			}

			else if(filtrarPor.equals(" ") && !ordenarPor.equals(" "))
			{
				ResultSet rs = s.executeQuery("SELECT * FROM (SELECT ID AS ID_PUNTO_ATENCION, OFICINA "
						+ "FROM PUNTOS_ATENCION) NATURAL JOIN TRANSACCIONES WHERE OFICINA = " + idOficina 
						+ " ORDER BY " + ordenarPor + " " + descoasc);

				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					String tipo = rs.getString("TIPO");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					String correoCliente = rs.getString("CORREO_USUARIO");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, tipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}	
			}

			else if(!filtrarPor.equals(" ") && ordenarPor.equals(" "))
			{
				ResultSet rs = s.executeQuery("SELECT * FROM (SELECT ID AS ID_PUNTO_ATENCION, OFICINA "
						+ "FROM PUNTOS_ATENCION) NATURAL JOIN TRANSACCIONES WHERE OFICINA = " + idOficina
						+ " AND TIPO = '" + filtrarPor + "'");

				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					String tipo = rs.getString("TIPO");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					String correoCliente = rs.getString("CORREO_USUARIO");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, tipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}	
			}


			else 
			{
				ResultSet rs = s.executeQuery("SELECT * FROM (SELECT ID AS ID_PUNTO_ATENCION, OFICINA "
						+ "FROM PUNTOS_ATENCION) NATURAL JOIN TRANSACCIONES WHERE OFICINA = " + idOficina 
						+ " AND TIPO = " + filtrarPor + " ORDER BY " + ordenarPor + " " + descoasc);

				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					String tipo = rs.getString("TIPO");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					String correoCliente = rs.getString("CORREO_USUARIO");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, tipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}	
			}


		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return operaciones;			
		}

	}

	/**
	 * Método que devuelve la información de todas las operaciones realizadas dentro
	 * del sistema bancandes en forma de un arrayList de TransaccionValues
	 * @return
	 * @throws Exception
	 */
	public ArrayList<TransaccionValues> darOperacionesGeneral(String ordenarPor, String descoasc, String filtrarPor) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList <TransaccionValues> operaciones = new ArrayList<TransaccionValues>();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();

			if(filtrarPor.trim().equals("") && ordenarPor.trim().equals(""))
			{
				ResultSet rs = s.executeQuery("SELECT * FROM TRANSACCIONES");
				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					String tipo = rs.getString("TIPO");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					String correoCliente = rs.getString("CORREO_USUARIO");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, tipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}	
			}

			else if(filtrarPor.trim().equals("") && !ordenarPor.trim().equals(""))
			{
				ResultSet rs = s.executeQuery("SELECT * FROM TRANSACCIONES ORDER BY " + ordenarPor + " " + descoasc);

				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					String tipo = rs.getString("TIPO");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					String correoCliente = rs.getString("CORREO_USUARIO");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, tipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}	
			}

			else if(!filtrarPor.trim().equals("") && ordenarPor.trim().equals(""))
			{
				ResultSet rs = s.executeQuery("SELECT * FROM TRANSACCIONES WHERE TIPO = "  + filtrarPor);

				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					String tipo = rs.getString("TIPO");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					String correoCliente = rs.getString("CORREO_USUARIO");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, tipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}	
			}


			else 
			{
				ResultSet rs = s.executeQuery("SELECT * FROM TRANSACCIONES WHERE TIPO = '" + filtrarPor
						+ "'  ORDER BY  " + ordenarPor + "   " + descoasc);
				while(rs.next())
				{

					int idTransaccion = rs.getInt("ID_TRANSACCION");
					String tipo = rs.getString("TIPO");
					Date fecha = rs.getDate("FECHA_TRANSACCION");
					String correoCliente = rs.getString("CORREO_USUARIO");
					TransaccionValues transaccionActual = new TransaccionValues(idTransaccion, correoCliente, tipo, fecha, 0);
					operaciones.add(transaccionActual);
					System.out.println("---------Usuarios-------");			
				}	
			}


		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		finally
		{
			return operaciones;			
		}

	}

	/**
	 * Retorna el valor de la consignación cuyo id es ingresado por parámetro.
	 * @param idConsignaciones id de la consignación cuyo valor es requiere.
	 * @return
	 * @throws SQLException 
	 */
	public int darValorConsignacion(int idConsignacion) throws SQLException
	{
		establecerConexion(cadenaConexion, usuario, clave);
		Statement s = conexion.createStatement();


		int rta = 0;
		ResultSet rs = s.executeQuery("SELECT MONTO FROM CONSIGNACIONES WHERE ID =" + idConsignacion);
		while(rs.next())
		{
			rta = rs.getInt("MONTO");
		}


		return rta;
	}
}
