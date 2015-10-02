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
import conexion.ConexionBd;

public class CerrarCuentaDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public CerrarCuentaDAO()
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

	
	/**
	 * Método que retorna un arrayList con objetos de tipo CuentaValues
	 * que contienen toda la información de las cuentas de un cliente especifico
	 * que fueron creadas en una oficina en particular, lo anterior con el fin de que
	 * si se conoce la oficina en la cual el gerente va a cerrar la cuenta el gerente
	 * pueda saber qué cuentas puede cerrar.
	 * Lo anterior con el fin de saber qué cuentas tiene y el ID de cada una
	 * para poder cerrarlas en caso de que el cliente lo desee.
	 * @param numeroID número del documento de identidad del cliente
	 * @param tipoID tipo de documento de identidad del cliente.
	 * @return
	 * @throws Exception
	 */
	public ArrayList<CuentaValues> darCuentasDeUnClienteEnOficinaParticular(String numeroID, String tipoID, int idOficina) throws Exception
	{   
		
		PreparedStatement prepStat = null;
		ArrayList  cuentas = new ArrayList ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT CORREO FROM USUARIOS WHERE NUMERO_ID = "
					+ "'" + numeroID + "'" + "AND TIPO_ID = " + "'" + tipoID + "'");
			String correoCliente = rs.getString("CORREO");
			
			rs = s.executeQuery("SELECT * FROM CUENTAS WHERE CORREO = " + "'" + correoCliente + "'"
					+ "AND OFICINA = " + "'" + idOficina + "'");
			
			while(rs.next())
			{
			   	int idCuenta = rs.getInt("ID_CUENTA");
			   	String correo = rs.getString("CORREO");
			   	String tipoCuenta = rs.getString("TIPO_CUENTA");
			   	int oficina = rs.getInt("OFICINA");
			   	Date fechaUltimoMovimiento = rs.getDate("FECHA_ULTIMO_MOVIMIENTO");
			   	int saldo = rs.getInt("SALDO");
			   	String estado = rs.getString("ESTADO");
			   	CuentaValues cuentaActual = new CuentaValues(idCuenta, correo, tipoCuenta, oficina, fechaUltimoMovimiento, saldo, estado);
			   	cuentas.add(cuentaActual);
			}
			
		}

		finally
		{
			return cuentas;
		}

	}
	
	/**
	 * Método que retorna un arrayList con objetos de tipo CuentaValues
	 * que contienen toda la información de las cuentas de un cliente especifico.
	 * Lo anterior con el fin de saber qué cuentas tiene y el ID de cada una
	 * para poder cerrarlas en caso de que el cliente lo desee.
	 * @param numeroID número del documento de identidad del cliente
	 * @param tipoID tipo de documento de identidad del cliente.
	 * @return
	 * @throws Exception
	 */
	public ArrayList<CuentaValues> darCuentasDeUnCliente(String numeroID, String tipoID) throws Exception
	{   
		
		PreparedStatement prepStat = null;
		ArrayList  cuentas = new ArrayList ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();

			
			ResultSet rs = s.executeQuery("SELECT CORREO FROM USUARIOS WHERE NUMERO_ID = "
					+ "'" + numeroID + "'" + "AND TIPO_ID = " + "'" + tipoID + "'");
			String correoCliente = rs.getString("CORREO");
			
			rs = s.executeQuery("SELECT * FROM CUENTAS WHERE CORREO = " + "'" + correoCliente + "'");
			
			while(rs.next())
			{
			   	int idCuenta = rs.getInt("ID_CUENTA");
			   	String correo = rs.getString("CORREO");
			   	String tipoCuenta = rs.getString("TIPO_CUENTA");
			   	int oficina = rs.getInt("OFICINA");
			   	Date fechaUltimoMovimiento = rs.getDate("FECHA_ULTIMO_MOVIMIENTO");
			   	int saldo = rs.getInt("SALDO");
			   	String estado = rs.getString("ESTADO");
			   	CuentaValues cuentaActual = new CuentaValues(idCuenta, correo, tipoCuenta, oficina, fechaUltimoMovimiento, saldo, estado);
			   	cuentas.add(cuentaActual);
			}
			
		}

		finally
		{
			return cuentas;
		}

	}

	/**
	 * Método que se encarga de cerrar (deshabilitar) una cuenta dentro del sistema a petición
	 * del cliente. Si el saldo que posee la cuenta es cero entonces simplemente se deshabilita
	 * la cuenta. Sin embargo en el caso de que la cuenta posea un saldo > 0 entonces no solamente
	 * se deshabilita la cuenta y se coloca el saldo de esta en cero, sino que además se
	 * registra un retiro con el fin de actualizar la información de la cuenta deshabilitada.
	 * @param id_eliminar
	 * @return
	 * @throws Exception
	 */
	public boolean registrarCerrarCuentaExistente (int id_eliminar) throws Exception
	{
		PreparedStatement prepStmt = null;

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT SALDO FROM CUENTAS WHERE ID_CUENTA = "
					+ id_eliminar);
			int saldoActual = rs.getInt("SALDO");
			
			rs = s.executeQuery("SELECT ID FROM ( SELECT * FROM RETIROS ORDER BY ID DESC) "
					+ "WHERE ROWNUM = 1");
			
			int idMax = rs.getInt("ID");
		    idMax++;
		    
			if(saldoActual == 0)
			{
				String sentencia1 = "UPDATE CUENTAS SET ESTADO = 'Inactiva' "
						+ "WHERE ID_CUENTA = " + id_eliminar;
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);

				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();

			}

			else
			{
				String sentencia1 = "UPDATE CUENTAS SET ESTADO = 'Inactiva',"
						+ "SALDO = 0  WHERE ID_CUENTA = "+ id_eliminar;
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();
			    
				rs = s.executeQuery("SELECT ID_TRANSACCION FROM "
						+ "( SELECT * FROM TRANSACCIONES ORDER BY ID_TRANSACCION DESC) "
						+ "WHERE ROWNUM = 1");
				
				int idMax2 = rs.getInt("ID_TRANSACCION");
			    idMax2++;
			    
				String sentencia3 = "INSERT INTO TRANSACCIONES(ID_TRANSACCION, CORREO_USUARIO,"
						+ " TIPO, FECHA_TRANSACCION, ID_PUNTO_ATENCION) "
						+ "VALUES (" +  idMax + ","  + id_eliminar + "," + saldoActual + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia3);
				prepStmt = conexion.prepareStatement(sentencia3);
				prepStmt.executeUpdate();
				conexion.commit();
				
				String sentencia2 = "INSERT INTO RETIROS(ID, ID_CUENTA_RETIRO, MONTO) "
						+ "VALUES (" +  idMax + ","  + id_eliminar + "," + saldoActual + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia2);
				prepStmt = conexion.prepareStatement(sentencia2);
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
	
	/**
	 * Método que se encarga de cerrar (deshabilitar) una cuenta dentro del sistema a petición
	 * del cliente. Si el saldo que posee la cuenta es cero entonces simplemente se deshabilita
	 * la cuenta. Sin embargo en el caso de que la cuenta posea un saldo > 0 entonces no solamente
	 * se deshabilita la cuenta y se coloca el saldo de esta en cero, sino que además se
	 * registra un retiro con el fin de actualizar la información de la cuenta deshabilitada.
	 * Además en este método se incluye como parámetro el id de la oficina, lo anterior
	 * con el fin de preservar la seguridad en la manipulación de los datos de tal forma
	 * que un gerente solo pueda tener acceso a las cuentas de la oficina de la cual es gerente.
	 * @param id_eliminar
	 * @return
	 * @throws Exception
	 */
	public boolean registrarCerrarCuentaExistenteEnOficinaEspecifica (int id_eliminar, int oficina) throws Exception
	{
		PreparedStatement prepStmt = null;

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
            
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT SALDO FROM CUENTAS WHERE ID_CUENTA = "
					+ id_eliminar + "AND OFICINA = " + oficina);
			int saldoActual = rs.getInt("SALDO");
			
			rs = s.executeQuery("SELECT ID_TRANSACCION FROM ( SELECT * FROM TRANSACCIONES"
					+ " ORDER BY ID_TRANSACCION DESC) "
					+ "WHERE ROWNUM = 1");
			
			int idMax = rs.getInt("ID_TRANSACCION");
		    idMax++;
		    
			if(saldoActual == 0)
			{
				rs = s.executeQuery("SELECT ID FROM PUNTOS_ATENCION WHERE OFICINA = "
						+ oficina);
				int idPuntoAtencion = rs.getInt("ID");
				
				rs = s.executeQuery("SELECT GERENTE FROM OFICINAS WHERE ID_OFICINA = "
						+ oficina);
				String correoGerente = rs.getString("GERENTE");
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String fecha_registro = dateFormat.format(date) ;
				
				String sentencia0 = "INSERT INTO TRANSACCIONES(ID_TRANSACCION, CORREO_USUARIO,"
						+ "TIPO , FECHA_TRANSACCION, ID_PUNTO_ATENCION )"
						+ "VALUES(" + idMax + "," + "'" + correoGerente + "'" + ","
						+  "'" + "CC" + "'" + "," + "TO_DATE (" + "'" + fecha_registro + "'" 
						+ " , 'yyyy/mm/dd HH24-Mi-SS') " + "," + idPuntoAtencion +  ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia0);
				prepStmt = conexion.prepareStatement(sentencia0);
				prepStmt.executeUpdate();
				conexion.commit();
				
				String sentenciax = "INSERT INTO CIERRE_CUENTAS(ID, ID_CUENTA_CERRADA)"
						+ "VALUES(" + idMax + "," + id_eliminar + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentenciax);
				prepStmt = conexion.prepareStatement(sentenciax);
				prepStmt.executeUpdate();
				conexion.commit();
				
				String sentencia1 = "UPDATE CUENTAS SET ESTADO = 'Inactiva' "
						+ "WHERE ID_CUENTA = " + id_eliminar + "AND OFICINA = " + oficina;
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();

			}

			else
			{
				rs = s.executeQuery("SELECT ID FROM PUNTOS_ATENCION WHERE OFICINA = "
						+ oficina);
				int idPuntoAtencion = rs.getInt("ID");
				
				rs = s.executeQuery("SELECT GERENTE FROM OFICINAS WHERE ID_OFICINA = "
						+ oficina);
				String correoGerente = rs.getString("GERENTE");
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String fecha_registro = dateFormat.format(date) ;
				
				String sentencia3 = "INSERT INTO TRANSACCIONES(ID_TRANSACCION, CORREO_USUARIO,"
						+ "TIPO , FECHA_TRANSACCION, ID_PUNTO_ATENCION )"
						+ "VALUES(" + idMax + "," + "'" + correoGerente + "'" + ","
						+  "'" + "R" + "'" + "," + "TO_DATE (" + "'" + fecha_registro + "'" 
						+ " , 'yyyy/mm/dd HH24-Mi-SS') " + "," + idPuntoAtencion +  ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia3);
				prepStmt = conexion.prepareStatement(sentencia3);
				prepStmt.executeUpdate();
				conexion.commit();
				
				String sentencia2 = "INSERT INTO RETIROS(ID, ID_CUENTA_RETIRO, MONTO) "
						+ "VALUES (" +  idMax + ","  + id_eliminar + "," + saldoActual + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia2);
				prepStmt = conexion.prepareStatement(sentencia2);
				prepStmt.executeUpdate();
				conexion.commit();
				
				
				idMax++;
				
				
				String sentencia0 = "INSERT INTO TRANSACCIONES(ID_TRANSACCION, CORREO_USUARIO,"
						+ "TIPO , FECHA_TRANSACCION, ID_PUNTO_ATENCION )"
						+ "VALUES(" + idMax + "," + "'" + correoGerente + "'" + ","
						+  "'" + "CC" + "'" + "," + "TO_DATE (" + "'" + fecha_registro + "'" 
						+ " , 'yyyy/mm/dd HH24-Mi-SS') " + "," + idPuntoAtencion +  ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia0);
				prepStmt = conexion.prepareStatement(sentencia0);
				prepStmt.executeUpdate();
				conexion.commit();
				
				String sentenciax = "INSERT INTO CIERRE_CUENTAS(ID, ID_CUENTA_CERRADA)"
						+ "VALUES(" + idMax + "," + id_eliminar + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentenciax);
				prepStmt = conexion.prepareStatement(sentenciax);
				prepStmt.executeUpdate();
				conexion.commit();
				
				String sentencia1 = "UPDATE CUENTAS SET ESTADO = 'Inactiva' "
						+ "WHERE ID_CUENTA = " + id_eliminar + "AND OFICINA = " + oficina;
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

}
