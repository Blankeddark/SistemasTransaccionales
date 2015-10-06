package Test;

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

public class TestDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public TestDAO()
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

	public boolean insertarUsuario (String tipo_usuario, String correo, String login, String contrase�a, String numero_id, String tipo_id, String nombre, String nacionalidad, String direccion, String telefono, String ciudad, String departamento, String cod_postal) throws Exception
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

			int idTransaccion = 0;
			Statement state = conexion.createStatement();

			String sentencia = "INSERT INTO USUARIOS (CORREO, LOGIN, CONTRASE�A, "
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

	/**
	 * Este método  no maneja las ID´s de forma secuencial sino que entran por parámetro.
	 * @param tipo_usuario
	 * @param correo
	 * @param login
	 * @param contraseña
	 * @param numero_id
	 * @param tipo_id
	 * @param nombre
	 * @param nacionalidad
	 * @param direccion
	 * @param telefono
	 * @param ciudad
	 * @param departamento
	 * @param cod_postal
	 * @return
	 * @throws Exception
	 */
	public boolean insertarCuenta (int indice, String correo, String tipoCuenta, int oficina, int saldo, String estado) throws Exception
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

			if(indice != 0)
			{
				Statement state = conexion.createStatement();
				String sentencia = "INSERT INTO CUENTAS (ID_CUENTA, CORREO, TIPO_CUENTA, "
						+ "OFICINA, FECHA_ULTIMO_MOVIMIENTO, SALDO, ESTADO) "+
						"VALUES (" + indice + "," + "'" + correo + "'" + "," + "'" + tipoCuenta 	+ "'" 
						+ "," + oficina  + ","   + "TO_DATE ("+
						"'" + fecha_ultimo_movimiento + "' , 'yyyy/mm/dd HH24-Mi-SS')" + "," 
						+ saldo + "," + "'" + estado + "'" + ")";
				prepStmt = conexion.prepareStatement(sentencia);
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia);
				prepStmt.executeUpdate();
				conexion.commit();
			}

			else
			{
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



			//            else
			//            {
			//            	Statement state = conexion.createStatement();
			//    			String sentencia = "INSERT INTO CUENTAS (ID_CUENTA, CORREO, TIPO_CUENTA, "
			//    					+ "OFICINA, FECHA_ULTIMO_MOVIMIENTO, SALDO, ESTADO) "+
			//    					"VALUES (" + idMax + "," + "'" + correo + "'" + "," + "'" + tipoCuenta 	+ "'" 
			//    					+ "," + oficina  + ","   + "TO_DATE ("+
			//    					"'" + fecha_ultimo_movimiento + "' , 'yyyy/mm/dd HH24-Mi-SS')" + "," 
			//    					+ saldo + "," + "'" + estado + "'" + ")";
			//    			prepStmt = conexion.prepareStatement(sentencia);
			//    			prepStmt.executeUpdate();
			//    			conexion.commit();
			//            }





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

	public boolean insertarOficina (int indice, String nombre, String direccion, String telefono, String gerente, String ciudad, String departamento) throws Exception
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

			if (indice != 0)
			{
				Statement state = conexion.createStatement();
				String sentencia = "INSERT INTO OFICINAS (ID_OFICINA, NOMBRE, DIRECCION, "
						+ "TELEFONO, GERENTE, CIUDAD, DEPARTAMENTO) "+
						"VALUES (" + indice + "," + "'" + nombre + "'" + "," + "'" + direccion 	+ "'" 
						+ "," + telefono  + ","   + "'" + gerente + "'"  + "," 
						+ "'" + ciudad + "'" + "," + "'" + departamento + "'" + ")";
				prepStmt = conexion.prepareStatement(sentencia);
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia);
				prepStmt.executeUpdate();
				conexion.commit();
			}

			else
			{
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

	public boolean insertarCliente (int indice, String correo, String tipo) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO CLIENTES (CORREO, TIPO_PERSONA) "+
					"VALUES ("  + "'" + correo + "'" + "," + "'" + tipo.charAt(0) 	+ "'"  + ")";
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

	public boolean insertarEmpleado (int indice, String correo, String tipo, int oficina) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			if(indice == 0)
			{
				Statement state = conexion.createStatement();
				String sentencia = "INSERT INTO EMPLEADOS (CORREO, TIPO) " +
						"VALUES ("  + "'" + correo + "'" + "," + "'" + tipo 	+ "'" + ")";
				prepStmt = conexion.prepareStatement(sentencia);
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia);
				prepStmt.executeUpdate();
				conexion.commit();
			}

			else if (indice == 1000)
			{
				Statement state = conexion.createStatement();
				String sentencia = "UPDATE EMPLEADOS SET  OFICINA = " + oficina
						+"WHERE CORREO = " + "'" + correo + "'";
				prepStmt = conexion.prepareStatement(sentencia);
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia);
				prepStmt.executeUpdate();
				conexion.commit();
			}

			else
			{
				Statement state = conexion.createStatement();
				String sentencia = "INSERT INTO EMPLEADOS (CORREO, TIPO, OFICINA) " +
						"VALUES ("  + "'" + correo + "'" + "," + "'" + tipo 	+ "'" + ","
						+ oficina + ")";
				prepStmt = conexion.prepareStatement(sentencia);
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia);
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

	public boolean insertarTipoPrestamo (int indice, String tipo) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO TIPOS_PRESTAMOS (TIPO_PRESTAMO) " +
					"VALUES ("  + "'" + tipo + "'" + ")";
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


	public boolean insertarPrestamo (int indice, String correoCliente, int montoPrestado, 
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

			if(indice != 0)
			{
				Statement state = conexion.createStatement();
				String sentencia = "INSERT INTO PRESTAMOS (ID, CORREO_CLIENTE, MONTO_PRESTADO,"
						+ "TIPO, FECHA_PRESTAMO, DIA_PAGO_MENSUAL, CUOTA, SALDO_PENDIENTE, ESTADO,"
						+ "NUM_CUOTAS, INTERES, CUOTAS_EFECTIVAS) " +
						"VALUES ("  + indice + "," +  "'" + correoCliente + "'" + "," 
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

			else
			{
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

	public boolean insertarPuntosAtencion (int indice, String tipo, int oficina, 
			String correoCajero, String direccion, String ciudad, String departamento) throws Exception
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

			if(indice != 0)
			{
				Statement state = conexion.createStatement();
				String sentencia = "INSERT INTO PUNTOS_ATENCION (ID, TIPO, OFICINA,"
						+ "CORREO_CAJERO, DIRECCION, CIUDAD, DEPARTAMENTO) " +
						"VALUES ("  + indice + "," +  "'" + tipo + "'" + "," 
						+  oficina + "," + "'" + correoCajero + "'" + "," 
						+  "'" + direccion + "'" + "," + "'" + ciudad + "'" + "," 
						+  "'" + departamento + "'" + ")";
				prepStmt = conexion.prepareStatement(sentencia);
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia);
				prepStmt.executeUpdate();
				conexion.commit();
			}

			else
			{
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

	public boolean insertarTransacciones (int indice, String correoUsuario, String tipo, 
			int idPuntoAtencion) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String fecha_transaccion = dateFormat.format(date) ;

			int idMax = 0;
			Statement s = conexion.createStatement();

			ResultSet rs = s.executeQuery("SELECT ID_TRANSACCION "
					+ "FROM (SELECT * FROM TRANSACCIONES ORDER BY ID_TRANSACCION DESC) WHERE ROWNUM = 1");

			while(rs.next())
			{
				idMax = rs.getInt("ID_TRANSACCION");
			}

			if( indice != 0)
			{
				Statement state = conexion.createStatement();
				String sentencia = "INSERT INTO TRANSACCIONES (ID_TRANSACCION, CORREO_USUARIO, "
						+ "TIPO, FECHA_TRANSACCION, ID_PUNTO_ATENCION) " +
						"VALUES ("  + indice + "," +  "'" + correoUsuario + "'" + "," 
						+  "'" + tipo + "'" + ","  + "TO_DATE ("+
						"'" + fecha_transaccion + "' , 'yyyy/mm/dd HH24-Mi-SS')" + "," 
						+  idPuntoAtencion + ")";
				prepStmt = conexion.prepareStatement(sentencia);
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia);
				prepStmt.executeUpdate();
				conexion.commit();
			}

			else
			{
				Statement state = conexion.createStatement();
				String sentencia = "INSERT INTO TRANSACCIONES (ID_TRANSACCION, CORREO_USUARIO, "
						+ "TIPO, FECHA_TRANSACCION, ID_PUNTO_ATENCION) " +
						"VALUES ("  + idMax + "," +  "'" + correoUsuario + "'" + "," 
						+  "'" + tipo + "'" + ","  + "TO_DATE ("+
						"'" + fecha_transaccion + "' , 'yyyy/mm/dd HH24-Mi-SS')" + "," 
						+  idPuntoAtencion + ")";
				prepStmt = conexion.prepareStatement(sentencia);
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia);
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

	public boolean insertarAperturaCuenta (int indice, int id, String correoCuentaAbierta,
			String tipoCuentaAbierta) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO APERTURA_CUENTAS (ID, CORREO_CUENTA_ABIERTA, "
					+ "TIPO_CUENTA_ABIERTA) " +
					"VALUES ("  + id + "," +  "'" + correoCuentaAbierta + "'" + "," 
					+ "'" + tipoCuentaAbierta + "'" + ")";
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

	public boolean insertarCierreCuenta (int indice, int id, int idCuentaCerrada) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO CIERRE_CUENTAS (ID, ID_CUENTA_CERRADA) " +
					"VALUES ("  + id + "," +  idCuentaCerrada + ")";
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

	public boolean insertarConsignacion (int indice, int id, int idOrigen, int idDestino,
			int monto) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO CONSIGNACIONES (ID, ID_CUENTA_ORIGEN, "
					+ "ID_CUENTA_DESTINO, MONTO) " +
					"VALUES ("  + id + "," +  idOrigen  + "," + idDestino + "," + monto + ")";
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


	public boolean insertarRetiro (int indice, int id, int idCuentaRetiro, int monto) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO RETIROS (ID, ID_CUENTA_RETIRO, "
					+ "MONTO ) " +
					"VALUES ("  + id + "," +  idCuentaRetiro + "," + monto + ")";
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


	public boolean insertarSolicitudPrestamo (int indice, int id, int montoSolicitado, 
			String tipo, String estado) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO SOLICITUDES_PRESTAMO (ID, MONTO_SOLICITADO, "
					+ "TIPO, ESTADO ) " +
					"VALUES ("  + id + "," + montoSolicitado + "," +  "'" + tipo + "'" + ","
					+ "'" + estado + "'" + ")";
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

	public boolean insertarAprobacionesPrestamo (int indice, int id, int solicitudAprobada) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO APROBACIONES_PRESTAMO (ID, SOLICITUD_APROBADA) " +
					"VALUES ("  + id + "," + solicitudAprobada + ")";
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

	public boolean insertarRechazoPrestamo (int indice, int id, int solicitudRechazada) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO RECHAZOS_PRESTAMO (ID, SOLICITUD_RECHAZADA) " +
					"VALUES ("  + id + "," + solicitudRechazada + ")";
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


	public boolean insertarCierePrestamo (int indice, int idCierre, 
			int idPrestamoCerrado) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO CIERRES_PRESTAMOS (ID_CIERRE, ID_PRESTAMO_CERRADO ) " 
					+ "VALUES ("  + idCierre + "," + idPrestamoCerrado + ")";
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

	public boolean insertarPagosPrestamo (int indice, int idPago, int idPrestamoPagado,
			int montoPagado) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "INSERT INTO PAGOS_PRESTAMO (ID_PAGO, ID_PRESTAMO_PAGADO, "
					+ "MONTO_PAGADO ) " +
					"VALUES ("  + idPago + "," + idPrestamoPagado + "," + montoPagado + ")";
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

	public void borrarDeTablas (String tabla, String condicion) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "DELETE FROM " + tabla +  "WHERE" + condicion;
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

	}

	public void ponerNullEmpleados ( ) throws Exception
	{
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement state = conexion.createStatement();
			String sentencia = "UPDATE EMPLEADOS SET OFICINA = NULL WHERE CORREO = 'ac.zuleta100@uniandes.edu.co' ";
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

	}
}
