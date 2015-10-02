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

public class ConsultarCuentasDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public ConsultarCuentasDAO()
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
	 * Método que retorna un arrayList con objetos tipo cuentaValues que contiene todas
	 * las cuentas que se encuentran dentro de la base de datos.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList<CuentaValues> darCuentas(String ordenarPor, String agruparPor, String descoasc) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<CuentaValues>  cuentas = new ArrayList<CuentaValues> ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CUENTAS");

			if(agruparPor.equals("") && !ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS ORDER BY" + ordenarPor + descoasc);
			}

			else if(!agruparPor.equals("") && ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS GROUP BY" + agruparPor);
			}

			else if(!agruparPor.equals("") && !ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS GROUP BY" + agruparPor
						+ "ORDER BY " + ordenarPor);
			}

			while(rs.next())
			{
				int idCuenta = rs.getInt("ID_CUENTA");
				String correo = rs.getString("CORREO");
				String tipo = rs.getString("TIPO_CUENTA");
				int idOficina = rs.getInt("OFICINA");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String estado = rs.getString("ESTADO");
				int saldo = rs.getInt("SALDO");
				CuentaValues cuentaActual = new CuentaValues(idCuenta, correo, tipo, idOficina, date, saldo, estado);
				cuentas.add(cuentaActual);
				System.out.println("---------Usuarios-------");			
			}

		}

		finally
		{
			return cuentas;
		}

	}

	/**
	 * Método que retorna un arrayList con objetos tipo cuentaValues que contiene todas
	 * las cuentas que se encuentran dentro de la base de datos
	 * que son del tipo de cuenta que entra por parámetro (este es para consultas
	 * del gerente general).
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList<CuentaValues> darCuentasPorTipo(String tipo, String ordenarPor, String agruparPor, String descoasc) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<CuentaValues>  cuentas = new ArrayList<CuentaValues> ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CUENTAS WHERE TIPO_CUENTA = " + tipo);

			if(agruparPor.equals("") && !ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE TIPO_CUENTA = " + tipo 
						+ " ORDER BY" + ordenarPor + descoasc);
			}

			else if(!agruparPor.equals("") && ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE TIPO_CUENTA = " + tipo +
						"GROUP BY" + agruparPor);
			}

			else if(!agruparPor.equals("") && !ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE TIPO_CUENTA = " + tipo
						+ "GROUP BY" + agruparPor + "ORDER BY " + ordenarPor);
			}

			while(rs.next())
			{

				int idCuenta = rs.getInt("ID_CUENTA");
				String correo = rs.getString("CORREO");
				//String tipo = rs.getString("TIPO_CUENTA");
				int idOficina = rs.getInt("OFICINA");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String estado = rs.getString("ESTADO");
				int saldo = rs.getInt("SALDO");
				CuentaValues cuentaActual = new CuentaValues(idCuenta, correo, tipo, idOficina, date, saldo, estado);
				cuentas.add(cuentaActual);
				System.out.println("---------Usuarios-------");			

			}

		}

		finally
		{
			return cuentas;
		}

	}


	/**
	 * Método que retorna un arrayList con objetos tipo cuentaValues que contiene todas
	 * las cuentas que se encuentran dentro de la base de datos
	 * que poseen un saldo que se encuentra dentro del rango que entra
	 * por parámetro (este es para consultas
	 * del gerente general).
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList<CuentaValues> darCuentasPorRangoSaldo(int desde, int hasta, String ordenarPor, String agruparPor, String descoasc) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<CuentaValues>  cuentas = new ArrayList<CuentaValues> ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CUENTAS WHERE SALDO BETWEEN "
					+ desde + " AND" + hasta);

			if(agruparPor.equals("") && !ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE SALDO BETWEEN "
						+ desde + " AND" + hasta  + " ORDER BY" + ordenarPor + descoasc);
			}

			else if(!agruparPor.equals("") && ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE SALDO BETWEEN "
						+ desde + " AND" + hasta + "GROUP BY" + agruparPor);
			}

			else if(!agruparPor.equals("") && !ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE SALDO BETWEEN "
						+ desde + " AND" + hasta + "GROUP BY" + agruparPor 
						+ "ORDER BY " + ordenarPor);
			}

			while(rs.next())
			{

				int idCuenta = rs.getInt("ID_CUENTA");
				String correo = rs.getString("CORREO");
				String tipo = rs.getString("TIPO_CUENTA");
				int idOficina = rs.getInt("OFICINA");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String estado = rs.getString("ESTADO");
				int saldo = rs.getInt("SALDO");
				CuentaValues cuentaActual = new CuentaValues(idCuenta, correo, tipo, idOficina, date, saldo, estado);
				cuentas.add(cuentaActual);
				System.out.println("---------Usuarios-------");			

			}

		}

		finally
		{
			return cuentas;
		}

	}

	/**
	 * Método que retorna un arrayList con objetos tipo cuentaValues que contiene todas
	 * las cuentas que se encuentran dentro de la base de datos
	 * cuya ultima actividad haya sido dentro de la fecha que entra por parámetro
	 * por parámetro (este es para consultas
	 * del gerente general).
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList<CuentaValues> darCuentasPorFechaUltimoMovimiento(String fecha, String ordenarPor, String agruparPor, String descoasc) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<CuentaValues>  cuentas = new ArrayList<CuentaValues> ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CUENTAS WHERE "
					+ "FECHA_ULTIMO_MOVIMIENTO = " + "'" + fecha + "'");

			if(agruparPor.equals("") && !ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE "
						+ "FECHA_ULTIMO_MOVIMIENTO = " + "'" + fecha + "'"
						+ " ORDER BY" + ordenarPor + descoasc);
			}

			else if(!agruparPor.equals("") && ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE "
						+ "FECHA_ULTIMO_MOVIMIENTO = " + "'" + fecha + "'"
						+ "GROUP BY" + agruparPor);
			}

			else if(!agruparPor.equals("") && !ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE "
						+ "FECHA_ULTIMO_MOVIMIENTO = " + "'" + fecha + "'" 
						+ "GROUP BY" + agruparPor 
						+ "ORDER BY " + ordenarPor);
			}

			while(rs.next())
			{

				int idCuenta = rs.getInt("ID_CUENTA");
				String correo = rs.getString("CORREO");
				String tipo = rs.getString("TIPO_CUENTA");
				int idOficina = rs.getInt("OFICINA");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String estado = rs.getString("ESTADO");
				int saldo = rs.getInt("SALDO");
				CuentaValues cuentaActual = new CuentaValues(idCuenta, correo, tipo, idOficina, date, saldo, estado);
				cuentas.add(cuentaActual);
				System.out.println("---------Usuarios-------");			

			}

		}

		finally
		{
			return cuentas;
		}

	}

	/**
	 * Método que retorna un arrayList con objetos tipo cuentaValues que contiene todas
	 * las cuentas que se encuentran dentro de una oficina en particular. 
	 * Es decir, se retornan todas las cuentas que están registradas dentro de la oficina
	 * donde labora el gerente de la oficina. Se debe entregar por parámetro el id de la oficina.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList<CuentaValues> darCuentasOficinaParticular(int idOficina, String ordenarPor, String agruparPor, String descoasc) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<CuentaValues>  cuentas = new ArrayList<CuentaValues> ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina);
			if(!ordenarPor.equals("") && agruparPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "GROUP BY" + agruparPor);
			}

			else if(ordenarPor.equals("") && !agruparPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "ORDER BY" + ordenarPor + descoasc);
			}

			else if(!ordenarPor.equals("") && !agruparPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "GROUP BY" + agruparPor + "ORDER BY" + ordenarPor + descoasc);
			}

			while(rs.next())
			{
				String idCuenta = rs.getString("ID_CUENTA");
				int idCuentax = Integer.parseInt(idCuenta);
				String correo = rs.getString("CORREO");
				String tipo = rs.getString("TIPO_CUENTA");
				String idOficinax = rs.getString("OFICINA");
				int oficina = Integer.parseInt(idOficinax);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String estado = rs.getString("ESTADO");
				String monto = rs.getString("SALDO");
				int saldo = Integer.parseInt(monto);
				CuentaValues cuentaActual = new CuentaValues(idCuentax, correo, tipo, oficina, date, saldo, estado);
				cuentas.add(cuentaActual);
				System.out.println("---------Usuarios-------");			
			}

		}

		finally
		{
			return cuentas;
		}

	}

	/**
	 * Método que retorna un arrayList con objetos tipo cuentaValues que contiene todas
	 * las cuentas que se encuentran dentro de una oficina en particular
	 * y sean del tipo de cuenta específicado. 
	 * Es decir, se retornan todas las cuentas que están registradas dentro de la oficina
	 * donde labora el gerente de la oficina. Se debe entregar por parámetro el id de la oficina.
	 * Además entra por parámetro el criterio de agrupamiento (si lo hay) y el criterio de ordenamiento
	 * (si lo hay)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList<CuentaValues> darCuentasOficinaParticularPorTipoDeCuenta(int idOficina, String tipo, String descoasc, String agruparPor, String ordenarPor) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<CuentaValues>  cuentas = new ArrayList<CuentaValues> ();

		try
		{   
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CUENTAS WHERE ROWNUM = 1");

			if(ordenarPor.equals(" ") && agruparPor.equals(" "))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina 
						+ " AND TIPO_CUENTA = " + tipo);	
			}

			else if(ordenarPor.equals(" ") && !agruparPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina 
						+ " AND TIPO_CUENTA = " + tipo + " GROUP BY " + agruparPor);
			}

			else if(!ordenarPor.equals(" ") && agruparPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina 
						+ " AND TIPO_CUENTA = " + tipo + " ORDER BY " + ordenarPor + descoasc);
			}

			else
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina 
						+ " AND TIPO_CUENTA = " + tipo + " GROUP BY " + agruparPor +  " ORDER BY " 
						+ ordenarPor + descoasc );
			}

			while(rs.next())
			{

				int idCuenta = rs.getInt("ID_CUENTA");
				String correo = rs.getString("CORREO");
				//String tipo = rs.getString("TIPO_CUENTA");
				//int idOficina = rs.getInt("OFICINA");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String estado = rs.getString("ESTADO");
				int saldo = rs.getInt("SALDO");
				CuentaValues cuentaActual = new CuentaValues(idCuenta, correo, tipo, idOficina, date, saldo, estado);
				cuentas.add(cuentaActual);
				System.out.println("---------Usuarios-------");			

			}

		}

		finally
		{
			return cuentas;
		}

	}

	/**
	 * Método que retorna un arrayList con objetos tipo cuentaValues que contiene todas
	 * las cuentas que se encuentran dentro de una oficina en particular
	 * y se encuentran dentro de un rango de saldo que entra por parámetro. 
	 * Es decir, se retornan todas las cuentas que están registradas dentro de la oficina
	 * donde labora el gerente de la oficina. Se debe entregar por parámetro el id de la oficina.
	 * Se debe entregar por parámetro el id de la oficina.
	 * Además entra por parámetro el criterio de agrupamiento (si lo hay) y el criterio 
	 * de ordenamiento (si lo hay)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList<CuentaValues> darCuentasOficinaParticularPorRangoDeSaldo(int idOficina, int desde, int hasta, String descoasc, String ordenarPor, String agruparPor) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<CuentaValues>  cuentas = new ArrayList<CuentaValues> ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CUENTAS WHERE ROWNUM = 1");

			if(agruparPor.equals("") && ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "AND SALDO BETWEEN " + desde + "AND " + hasta);
			}

			else if(agruparPor.equals("") && !ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "AND SALDO BETWEEN " + desde + "AND " + hasta 
						+ " GROUP BY " + agruparPor);
			}

			else if(!agruparPor.equals("") && ordenarPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "AND SALDO BETWEEN " + desde + "AND " + hasta
						+ " ORDER BY" + ordenarPor + descoasc);
			}

			else
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "AND SALDO BETWEEN " + desde + "AND " + hasta
						+  " GROUP BY " + agruparPor + " ORDER BY" + ordenarPor + descoasc);
			}

			while(rs.next())
			{
				int idCuenta = rs.getInt("ID_CUENTA");
				String correo = rs.getString("CORREO");
				String tipo = rs.getString("TIPO_CUENTA");
				//int idOficina = rs.getInt("OFICINA");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String estado = rs.getString("ESTADO");
				int saldo = rs.getInt("SALDO");
				CuentaValues cuentaActual = new CuentaValues(idCuenta, correo, tipo, idOficina, date, saldo, estado);
				cuentas.add(cuentaActual);
				System.out.println("---------Usuarios-------");				
			}

		}

		finally
		{
			return cuentas;
		}

	}



	/**
	 * Método que retorna un arrayList con objetos tipo cuentaValues que contiene todas
	 * las cuentas que se encuentran dentro de una oficina en particular
	 * y se encuentran dentro de una fecha que entra por parámetro. 
	 * Es decir, se retornan todas las cuentas que están registradas dentro de la oficina
	 * donde labora el gerente de la oficina. Se debe entregar por parámetro el id de la oficina.
	 * Se debe entregar por parámetro el id de la oficina.
	 * Además entra por parámetro el criterio de agrupamiento (si lo hay) y 
	 * el criterio de ordenamiento (si lo hay)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList<CuentaValues> darCuentasOficinaParticularPorFechaUltimoMovimiento(int idOficina, String fecha, String desoasc, String agruparPor, String ordenarPor) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<CuentaValues>  cuentas = new ArrayList<CuentaValues> ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CUENTAS WHERE ROWNUM = 1");

			if(ordenarPor.equals("") && agruparPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "AND FECHA_ULTIMO_MOVIMIENTO = " +  "'" + fecha + "'" );
			}

			else if(!ordenarPor.equals("") && agruparPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "AND FECHA_ULTIMO_MOVIMIENTO = " +  "'" + fecha + "'"  
						+ " GROUP BY " + agruparPor);
			}

			else if(ordenarPor.equals("") && !agruparPor.equals(""))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "AND FECHA_ULTIMO_MOVIMIENTO = " +  "'" + fecha + "'" 
						+ " ORDER BY" + ordenarPor + desoasc);
			}

			else
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE OFICINA = " + idOficina
						+ "AND FECHA_ULTIMO_MOVIMIENTO = " +  "'" + fecha + "'" 
						+ " GROUP BY " + agruparPor + " ORDER BY " + ordenarPor + desoasc);
			}

			while(rs.next())
			{
				int idCuenta = rs.getInt("ID_CUENTA");
				String correo = rs.getString("CORREO");
				String tipo = rs.getString("TIPO_CUENTA");
				//int idOficina = rs.getInt("OFICINA");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String estado = rs.getString("ESTADO");
				int saldo = rs.getInt("SALDO");
				CuentaValues cuentaActual = new CuentaValues(idCuenta, correo, tipo, idOficina, date, saldo, estado);
				cuentas.add(cuentaActual);
				System.out.println("---------Usuarios-------");		
			}

		}

		finally
		{
			return cuentas;
		}

	}

}
