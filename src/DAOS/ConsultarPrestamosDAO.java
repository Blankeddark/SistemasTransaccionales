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

import vos.ClienteValues;
import vos.CuentaValues;
import vos.OficinaValues;
import vos.PrestamoValues;
import vos.TransaccionValues;

public class ConsultarPrestamosDAO 
{
	
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public ConsultarPrestamosDAO()
	{
		inicializar("./Conexion/conexion.properties");
	}

	public void inicializar(String path)
	{
		try
		{
			File arch = new File("C:/Users/Sergio/git/PROJECT_Sistrans/SistemasTransaccionales/WebContent/conexion.properties");
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream (arch);

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
	 * Método que retorna los prestamos de un cliente en específico en forma
	 * de un arrayList de PrestamoValues 
	 * @param pCorreo
	 * @param ordenarPor
	 * @param descoasc
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PrestamoValues> darPrestamosCliente(String correoCliente, String ordenarPor, String descoasc, String filtrarTipo) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList prestamos = new ArrayList();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM USUARIOS");
			
			if(filtrarTipo.trim().equals("") && ordenarPor.trim().equals(""))
			{
				rs = s.executeQuery("SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ "FROM PRESTAMOS) u NATURAL JOIN USUARIOS WHERE CORREO = " + correoCliente );
			}
			
			else if(!filtrarTipo.trim().equals("") && ordenarPor.trim().equals(""))
			{
				rs = s.executeQuery("SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ "FROM PRESTAMOS) u NATURAL JOIN USUARIOS WHERE CORREO = " + correoCliente 
						+ " AND TIPO = " + filtrarTipo);
			}
			
			else if(filtrarTipo.trim().equals("") && !ordenarPor.trim().equals(""))
			{
				rs = s.executeQuery("SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ "FROM PRESTAMOS) u NATURAL JOIN USUARIOS WHERE CORREO = " + correoCliente 
						+ " ORDER BY " + ordenarPor + " " + descoasc);
			}
			
			else
			{
				rs = s.executeQuery("SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ "FROM PRESTAMOS) u NATURAL JOIN USUARIOS WHERE CORREO = " + correoCliente 
						+ " AND TIPO = " + filtrarTipo + " ORDER BY " + ordenarPor + " " + descoasc);	
			}
			
			while(rs.next())
			{
                
				int id = rs.getInt("ID");
				String correo = rs.getString("CORREO");
				int saldoPendiente = rs.getInt("SALDO_PENDIENTE");
				String nombre = rs.getString("NOMBRE");
				int cuotasPagadas = rs.getInt("CUOTAS_EFECTIVAS");
				String tipo = rs.getString("TIPO");
				Date fecha = rs.getDate("FECHA_PRESTAMO");
				
				PrestamoValues prestamoActual = new PrestamoValues(id, correo, saldoPendiente, nombre, cuotasPagadas, tipo, fecha);
				prestamos.add(prestamoActual);
				System.out.println("---------Usuarios-------");			
			}			


		}

		finally
		{
			return prestamos;			
		}

	}
	

	/**
	 * Método que retorna todos los prestamos de una oficina en forma de PrestamoValues
	 * @param idOficina
	 * @param ordenarPor
	 * @param descoasc
	 * @param filtrarTipo
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PrestamoValues> darPrestamosOficina(int idOficina, String ordenarPor, String descoasc, String filtrarTipo) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList prestamos = new ArrayList();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM USUARIOS");
			
			if(filtrarTipo.trim().equals("") && ordenarPor.trim().equals(""))
			{
				rs = s.executeQuery("SELECT * FROM (SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ "FROM PRESTAMOS) u NATURAL JOIN USUARIOS) NATURAL JOIN PUNTOS_ATENCION WHERE OFICINA = " + idOficina );
			}
			
			else if(!filtrarTipo.trim().equals("") && ordenarPor.trim().equals(""))
			{
				rs = s.executeQuery("SELECT * FROM (SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ "FROM PRESTAMOS) u NATURAL JOIN USUARIOS) NATURAL JOIN PUNTOS_ATENCION WHERE OFICINA = " + idOficina 
						+ " AND TIPO = " + filtrarTipo);
			}
			
			else if(filtrarTipo.trim().equals("") && !ordenarPor.trim().equals(""))
			{
				rs = s.executeQuery("SELECT * FROM (SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ "FROM PRESTAMOS) u NATURAL JOIN USUARIOS) NATURAL JOIN PUNTOS_ATENCION WHERE OFICINA = " + idOficina 
						+  " ORDER BY " + ordenarPor + " " + descoasc);
			}
			
			else
			{
				rs = s.executeQuery("SELECT * FROM (SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ "FROM PRESTAMOS) u NATURAL JOIN USUARIOS) NATURAL JOIN PUNTOS_ATENCION WHERE OFICINA = " + idOficina 
						+ " AND TIPO = " + filtrarTipo + " ORDER BY  " + ordenarPor + " " + descoasc);
			}
			
			while(rs.next())
			{
                
				int id = rs.getInt("ID");
				String correo = rs.getString("CORREO");
				int saldoPendiente = rs.getInt("SALDO_PENDIENTE");
				String nombre = rs.getString("NOMBRE");
				int cuotasPagadas = rs.getInt("CUOTAS_EFECTIVAS");
				String tipo = rs.getString("TIPO");
				Date fecha = rs.getDate("FECHA_PRESTAMO");
				
				PrestamoValues prestamoActual = new PrestamoValues(id, correo, saldoPendiente, nombre, cuotasPagadas, tipo, fecha);
				prestamos.add(prestamoActual);
				System.out.println("---------Usuarios-------");			
			}			


		}

		finally
		{
			return prestamos;			
		}

	}
	
	/**
	 * Método que retorna todos los prestamos del banco 
	 * @param correoCliente
	 * @param ordenarPor
	 * @param descoasc
	 * @param filtrarTipo
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PrestamoValues> darPrestamosGeneral(String ordenarPor, String descoasc, String filtrarTipo) throws Exception
	{
		System.out.println("comenzó a dar préstamos generales en COnsultarPrestamoGeneralDAO");
		PreparedStatement prepStat = null;
		ArrayList prestamos = new ArrayList();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM USUARIOS");
			
			System.out.println("llegó antes del query");
		
			rs = s.executeQuery("SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ " FROM PRESTAMOS) u NATURAL JOIN USUARIOS" );
			
			System.out.println("Pasó despues deel query");
			
			if(!filtrarTipo.trim().equals("") && !ordenarPor.trim().equals("") )
			{
				rs = s.executeQuery("SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ " FROM PRESTAMOS) u NATURAL JOIN USUARIOS WHERE " 
						+ " TIPO = '" + filtrarTipo + "' ORDER BY " + ordenarPor + " " + descoasc);	
			}
			
			else if(!filtrarTipo.trim().equals(""))
			{
				rs = s.executeQuery("SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ " FROM PRESTAMOS) u NATURAL JOIN USUARIOS WHERE "  
						+ "TIPO = '" + filtrarTipo + "'");
			}
			
			else if(!ordenarPor.trim().equals(""))
			{
				rs = s.executeQuery("SELECT * FROM (SELECT ID, CORREO_CLIENTE AS CORREO, SALDO_PENDIENTE, CUOTAS_EFECTIVAS, TIPO, FECHA_PRESTAMO"
						+ " FROM PRESTAMOS) u NATURAL JOIN USUARIOS" 
						+ " ORDER BY " + ordenarPor + " " + descoasc);
			}
			
			
			int contador = 0;
			
			while(rs.next())
			{
                try
                {
				int id = rs.getInt("ID");
				String correo = rs.getString("CORREO");
				int saldoPendiente = rs.getInt("SALDO_PENDIENTE");
				String nombre = rs.getString("NOMBRE");
				int cuotasPagadas = rs.getInt("CUOTAS_EFECTIVAS");
				String tipo = rs.getString("TIPO");
				Date fecha = rs.getDate("FECHA_PRESTAMO");
				
				PrestamoValues prestamoActual = new PrestamoValues(id, correo, saldoPendiente, nombre, cuotasPagadas, tipo, fecha);
				prestamos.add(prestamoActual);
				System.out.println("---------Usuarios-------");	
				contador++;
                }
                catch (Exception e2)
                {
                	e2.printStackTrace();
                }
			}			

			System.out.println(contador);

		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}

		finally
		{
			return prestamos;			
		}

	}
}
