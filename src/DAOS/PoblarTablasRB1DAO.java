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
import java.util.Date;
import java.util.Properties;

public class PoblarTablasRB1DAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public PoblarTablasRB1DAO()
	{
		inicializar("./Conexion/conexion.properties");
	}

	public void inicializar(String path)
	{
		try
		{
			File arch = new File(path+ARCHIVO_CONEXION);
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

	public boolean insertarUsuario (String tipo_usuario, String correo, String login, String contrase�a, String numero_id, String tipo_id, String nombre, String nacionalidad, String direccion, String telefono, String ciudad, String departamento, String cod_postal,
			String tipoPersona, String tipoEmpleado, int oficina) throws Exception
	{
		PreparedStatement prepStmt = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String fecha_registro = dateFormat.format(date) ;

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO USUARIOS (CORREO, LOGIN, CONTRASEÑA, "
					+ "NUMERO_ID, TIPO_ID, NOMBRE, NACIONALIDAD, DIRECCION, TELEFONO,"
					+ "CIUDAD, DEPARTAMENTO, COD_POSTAL, FECHA_REGISTRO, TIPO_USUARIO) "+
					"VALUES (" + "'" + correo + "'" + "," + "'" + login + "'" + "," + 
					"'" + contrase�a + "'" + ","  + "'" + numero_id + "'"   + ","
					+  "'" + tipo_id + "'" + "," + "'" + nombre + "'" + "," + 
					"'" + nacionalidad + "'" + "," + "'" + direccion + "'" + ","
					+ "'" + telefono + "'" + "," + "'" + ciudad + "'" +  "," + 
					"'" + departamento + "'" + "," 
					+ "'" + cod_postal + "'" +"," + "TO_DATE ("+
					"'" + fecha_registro + "' , 'yyyy/mm/dd HH24-Mi-SS')" + "," + "'" + tipo_usuario + "'" + ")";
			System.out.println("--------------------------------------------------------------------------");
			System.out.println(sentencia);
			prepStmt = conexion.prepareStatement(sentencia);
			prepStmt.executeUpdate();
			conexion.commit();
			
			if(tipo_usuario.equals("Cliente"))
			{
				String sentencia1 = "INSERT INTO CLIENTES(CORREO, TIPO_PERSONA) "
						+ "VALUES (" + "'" + correo + "'" + "," + "'" + tipoPersona + "'" + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();
			}
			
			else
			{
				String sentencia1 = "INSERT INTO EMPLEADOS (CORREO, TIPO, OFICINA) "
						+ "VALUES (" + "'" + correo + "'" + "," + "'" + tipoEmpleado + "'" + ","
						+ oficina + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();
			}
			
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
				try 
				{
					prepStmt.close();
				}

				catch (SQLException exception) 
				{

					throw new Exception("ERROR: RegistroDePaquetesDAO: loadRow() =  cerrando una conexiÔøΩÔøΩÔøΩn.");
				}
			}

			closeConnection(conexion);
		} 

		return true;
	}

	public boolean insertarOficina (String nombre, String direccion, String telefono, String gerente, String ciudad, String departamento) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			int idMax = 0;
			Statement s = conexion.createStatement();

			ResultSet rs = s.executeQuery("SELECT ID_OFICINA "
					+ "FROM (SELECT * FROM OFICINAS ORDER BY ID_OFICINA DESC) WHERE ROWNUM = 1");

			while(rs.next())
			{
				idMax = rs.getInt("ID_OFICINA");
			}

			idMax++;

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO OFICINAS (ID_OFICINA, NOMBRE, DIRECCION, "
					+ "TELEFONO, GERENTE, CIUDAD, DEPARTAMENTO) "+
					"VALUES (" + idMax + "," + "'" + nombre + "'" + "," + "'" + direccion 	+ "'" 
					+ "," + telefono  + ","   + "'" + gerente + "'"  + "," 
					+ "'" + ciudad + "'" + "," + "'" + departamento + "'" + ")";
			prepStmt = conexion.prepareStatement(sentencia);
			System.out.println("--------------------------------------------------------------------------");
			System.out.println(sentencia);
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
				try 
				{
					prepStmt.close();
				}

				catch (SQLException exception) 
				{

					throw new Exception("ERROR: RegistroDePaquetesDAO: loadRow() =  cerrando una conexiÔøΩÔøΩÔøΩn.");
				}
			}

			closeConnection(conexion);
		} 

		return true;
	}

	public boolean insertarPuntosAtencion (String tipo, int oficina, String correoCajero, String direccion, String ciudad, String departamento) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			int idMax = 0;
			Statement s = conexion.createStatement();

			ResultSet rs = s.executeQuery("SELECT ID "
					+ "FROM (SELECT * FROM PUNTOS_ATENCION ORDER BY ID DESC) WHERE ROWNUM = 1");

			while(rs.next())
			{
				idMax = rs.getInt("ID");
			}



			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO PUNTOS_ATENCION (ID, TIPO, OFICINA,"
					+ "CORREO_CAJERO, DIRECCION, CIUDAD, DEPARTAMENTO) " +
					"VALUES ("  + idMax + "," +  "'" + tipo + "'" + "," 
					+  oficina + "," + "'" + correoCajero + "'" + "," 
					+  "'" + direccion + "'" + "," + "'" + ciudad + "'" + "," 
					+  "'" + departamento + "'" + ")";
			prepStmt = conexion.prepareStatement(sentencia);
			System.out.println("--------------------------------------------------------------------------");
			System.out.println(sentencia);
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
				try 
				{
					prepStmt.close();
				}

				catch (SQLException exception) 
				{

					throw new Exception("ERROR: RegistroDePaquetesDAO: loadRow() =  cerrando una conexiÔøΩÔøΩÔøΩn.");
				}
			}

			closeConnection(conexion);
		} 

		return true;
	}

	public boolean insertarCuenta (String correo, String tipoCuenta, int oficina, int saldo, String estado) throws Exception
	{
		PreparedStatement prepStmt = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String fecha_ultimo_movimiento = dateFormat.format(date) ;

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			int idMax = 0;
			Statement s = conexion.createStatement();

			ResultSet rs = s.executeQuery("SELECT ID_CUENTA "
					+ "FROM (SELECT * FROM CUENTAS ORDER BY ID_CUENTA DESC) WHERE ROWNUM = 1");

			while(rs.next())
			{
				idMax = rs.getInt("ID_CUENTA");
			}

			idMax++;

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO CUENTAS (ID_CUENTA, CORREO, TIPO_CUENTA, "
					+ "OFICINA, FECHA_ULTIMO_MOVIMIENTO, SALDO, ESTADO) "+
					"VALUES (" + idMax + "," + "'" + correo + "'" + "," + "'" + tipoCuenta 	+ "'" 
					+ "," + oficina  + ","   + "TO_DATE ("+
					"'" + fecha_ultimo_movimiento + "' , 'yyyy/mm/dd HH24-Mi-SS')" + "," 
					+ saldo + "," + "'" + estado + "'" + ")";
			prepStmt = conexion.prepareStatement(sentencia);
			System.out.println("--------------------------------------------------------------------------");
			System.out.println(sentencia);
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
				try 
				{
					prepStmt.close();
				}

				catch (SQLException exception) 
				{

					throw new Exception("ERROR: RegistroDePaquetesDAO: loadRow() =  cerrando una conexiÔøΩÔøΩÔøΩn.");
				}
			}

			closeConnection(conexion);
		} 

		return true;
	}

	public boolean insertarPrestamo (String correoCliente, int montoPrestado, 
			String tipo, int diaPagoMensual, int cuota, int saldoPendiente,
			String estado, int numCuotas, float interes, int cuotasEfectivas) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String fecha_prestamo = dateFormat.format(date) ;

			int idMax = 0;
			Statement s = conexion.createStatement();

			ResultSet rs = s.executeQuery("SELECT ID "
					+ "FROM (SELECT * FROM PRESTAMOS ORDER BY ID DESC) WHERE ROWNUM = 1");

			while(rs.next())
			{
				idMax = rs.getInt("ID");
			}

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO PRESTAMOS (ID, CORREO_CLIENTE, MONTO_PRESTADO,"
					+ "TIPO, FECHA_PRESTAMO, DIA_PAGO_MENSUAL, CUOTA, SALDO_PENDIENTE, ESTADO,"
					+ "NUM_CUOTAS, INTERES, CUOTAS_EFECTIVAS) " +
					"VALUES ("  + idMax + "," +  "'" + correoCliente + "'" + "," 
					+  montoPrestado + "," + "'" + tipo + "'" + "," 
					+  "TO_DATE ("+
					"'" + fecha_prestamo + "' , 'yyyy/mm/dd HH24-Mi-SS')" + ","
					+ diaPagoMensual + "," + cuota + "," + saldoPendiente + ","
					+ "'" + estado + "'" + "," + numCuotas + "," + interes + "," 
					+ cuotasEfectivas + ")";
			prepStmt = conexion.prepareStatement(sentencia);
			System.out.println("--------------------------------------------------------------------------");
			System.out.println(sentencia);
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
				try 
				{
					prepStmt.close();
				}

				catch (SQLException exception) 
				{

					throw new Exception("ERROR: RegistroDePaquetesDAO: loadRow() =  cerrando una conexiÔøΩÔøΩÔøΩn.");
				}
			}

			closeConnection(conexion);
		} 

		return true;
	}

}

