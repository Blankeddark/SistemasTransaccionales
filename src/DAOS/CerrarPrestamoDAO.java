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

import vos.PrestamoValues;

public class CerrarPrestamoDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public CerrarPrestamoDAO()
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
	 * Método que retorna un arrayList con todos los prestamos de la base de datos
	 * en forma de PrestamoValues
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PrestamoValues> darPrestamos() throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList  prestamos = new ArrayList ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM PRESTAMOS");
			while(rs.next())
			{
				int id = rs.getInt("ID");
				int montoPrestado = rs.getInt("MONTO_PRESTADO");
				String tipoPrestamo = rs.getString("TIPO");
				String correoCliente = rs.getString("CORREO_CLIENTE");
				int saldoPendiente = rs.getInt("SALDO_PENDIENTE");
				Date fechaPrestamo = rs.getDate("FECHA_PRESTAMO");
				int diaPagoMensual = rs.getInt("DIA_PAGO_MENSUAL");
                int cuota = rs.getInt("CUOTA");
                String estado = rs.getString("ESTADO");
                int numCuotas = rs.getInt("NUM_CUOTAS");
                float interes = rs.getFloat("INTERES");
                int cuotasEfectivas = rs.getInt("CUOTAS_EFECTIVAS");
                PrestamoValues prestamoActual = new PrestamoValues(id,                
                correoCliente, montoPrestado, tipoPrestamo, fechaPrestamo, 
                diaPagoMensual, cuotasEfectivas, 0, estado, numCuotas, interes, 
                cuotasEfectivas);
                prestamos.add(prestamoActual);
			}
		}

		finally
		{
			return prestamos;
		}

	}

	/**
	 * Método que retorna un arrayList con todos los prestamos pagados de la base de datos
	 * en forma de PrestamoValues
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PrestamoValues> darTodosLosPrestamosPagados() throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList  prestamos = new ArrayList ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM PRESTAMOS WHERE SALDO_PENDIENTE = 0");
			while(rs.next())
			{
				int id = rs.getInt("ID");
				int montoPrestado = rs.getInt("MONTO_PRESTADO");
				String tipoPrestamo = rs.getString("TIPO");
				String correoCliente = rs.getString("CORREO_CLIENTE");
				int saldoPendiente = rs.getInt("SALDO_PENDIENTE");
				Date fechaPrestamo = rs.getDate("FECHA_PRESTAMO");
				int diaPagoMensual = rs.getInt("DIA_PAGO_MENSUAL");
                int cuota = rs.getInt("CUOTA");
                String estado = rs.getString("ESTADO");
                int numCuotas = rs.getInt("NUM_CUOTAS");
                float interes = rs.getFloat("INTERES");
                int cuotasEfectivas = rs.getInt("CUOTAS_EFECTIVAS");
                PrestamoValues prestamoActual = new PrestamoValues(id,                
                correoCliente, montoPrestado, tipoPrestamo, fechaPrestamo, 
                diaPagoMensual, cuotasEfectivas, 0, estado, numCuotas, interes, 
                cuotasEfectivas);
                prestamos.add(prestamoActual);
			}
		}

		finally
		{
			return prestamos;
		}

	}

	/**
	 * Método que retorna todos los prestamos de un cliente específico
	 * en formato de PrestamoValues.
	 * @param correoCliente
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PrestamoValues> darPrestamosClienteEspecifico(String numID, String tipoID) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList  prestamos = new ArrayList ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT CORREO_CLIENTE FROM USUARIOS WHERE NUMERO_ID = " 
					+ "'" + numID + "'" + " AND TIPO_ID = " + "'" + tipoID + "'");
			String correoCliente = rs.getString("CORREO_CLIENTE");
			rs = s.executeQuery("SELECT * FROM PRESTAMOS WHERE CORREO_CLIENTE = " 
					+ "'" + correoCliente + "'" );
			while(rs.next())
			{
				int id = rs.getInt("ID");
				int montoPrestado = rs.getInt("MONTO_PRESTADO");
				String tipoPrestamo = rs.getString("TIPO");
				correoCliente = rs.getString("CORREO_CLIENTE");
				int saldoPendiente = rs.getInt("SALDO_PENDIENTE");
				Date fechaPrestamo = rs.getDate("FECHA_PRESTAMO");
				int diaPagoMensual = rs.getInt("DIA_PAGO_MENSUAL");
                int cuota = rs.getInt("CUOTA");
                String estado = rs.getString("ESTADO");
                int numCuotas = rs.getInt("NUM_CUOTAS");
                float interes = rs.getFloat("INTERES");
                int cuotasEfectivas = rs.getInt("CUOTAS_EFECTIVAS");
                PrestamoValues prestamoActual = new PrestamoValues(id,                
                correoCliente, montoPrestado, tipoPrestamo, fechaPrestamo, 
                diaPagoMensual, cuotasEfectivas, 0, estado, numCuotas, interes, 
                cuotasEfectivas);
                prestamos.add(prestamoActual);
			}
		}

		finally
		{
			return prestamos;
		}

	}
	
	/**
	 * Método que retorna todos los prestamos que ya han sido pagados
	 * por un cliente en particular en forma de prestamoValues.
	 * @param numID
	 * @param tipoID
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PrestamoValues> darPrestamosPagadosClienteEspecifico(String numID, String tipoID) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList  prestamos = new ArrayList ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT CORREO_CLIENTE FROM USUARIOS WHERE NUMERO_ID = " 
					+ "'" + numID + "'" + " AND TIPO_ID = " + "'" + tipoID + "'");
			String correoCliente = rs.getString("CORREO_CLIENTE");
			rs = s.executeQuery("SELECT * FROM PRESTAMOS WHERE CORREO_CLIENTE = " 
					+ "'" + correoCliente + "'" + " AND SALDO_PENDIENTE = 0");
			while(rs.next())
			{
				int id = rs.getInt("ID");
				int montoPrestado = rs.getInt("MONTO_PRESTADO");
				String tipoPrestamo = rs.getString("TIPO");
				Date fechaPrestamo = rs.getDate("FECHA_PRESTAMO");
				int diaPagoMensual = rs.getInt("DIA_PAGO_MENSUAL");
                int cuota = rs.getInt("CUOTA");
                String estado = rs.getString("ESTADO");
                int numCuotas = rs.getInt("NUM_CUOTAS");
                float interes = rs.getFloat("INTERES");
                int cuotasEfectivas = rs.getInt("CUOTAS_EFECTIVAS");
                PrestamoValues prestamoActual = new PrestamoValues(id,                
                correoCliente, montoPrestado, tipoPrestamo, fechaPrestamo, 
                diaPagoMensual, cuotasEfectivas, 0, estado, numCuotas, interes, 
                cuotasEfectivas);
                prestamos.add(prestamoActual);
			}
		}

		finally
		{
			return prestamos;
		}

	}
	
	/**
	 * Método que cierra un prestamo una vez que este ha sido pagado.
	 * Si se ingresa por parametro el id de un prestamo que no exista o que no esté
	 * pagado entonces se lanzará una excepción.
	 * @param id_eliminar
	 * @return
	 * @throws Exception
	 */
	public boolean cerrarPrestamoExistentePagado (int id_eliminar, int oficina) throws Exception
	{
		PreparedStatement prepStmt = null;

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT SALDO_PENDIENTE FROM PRESTAMOS WHERE ID = "
					+ id_eliminar );
			int saldoPendiente = 0;
			while(rs.next())
			{
				saldoPendiente = rs.getInt("SALDO_PENDIENTE");
			}
			
			rs = s.executeQuery("SELECT ID_TRANSACCION FROM(SELECT * FROM TRANSACCIONES"
					+ " ORDER BY ID_TRANSACCION DESC) WHERE ROWNUM = 1");
			int idTransaccionMax = 0;
			while(rs.next())
			{
				idTransaccionMax = rs.getInt("ID_TRANSACCION");
			}
		
			idTransaccionMax++;
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String fecha_registro = dateFormat.format(date) ;
			
			
			if(saldoPendiente != 0)
			{
				throw new Exception("Este prestamo no puede ser cerrado, a&uacute;n se debe dinero");
			}
			
			else if (saldoPendiente == 0)
			{
				rs = s.executeQuery("SELECT CORREO_CLIENTE FROM PRESTAMOS WHERE ID = " + id_eliminar);
				String correo_cliente = "";
				while(rs.next())
				{
					correo_cliente = rs.getString("CORREO_CLIENTE");
				}
				
				rs = s.executeQuery("SELECT ID FROM PUNTOS_ATENCION WHERE OFICINA = " + oficina);
				int puesto_atencion = 0;
				while(rs.next())
				{
					puesto_atencion = rs.getInt("ID");
				}
				
				String sentencia = "INSERT INTO TRANSACCIONES (ID_TRANSACCION, CORREO_USUARIO, TIPO, FECHA_TRANSACCION, ID_PUNTO_ATENCION) "+
						"VALUES (" + idTransaccionMax + "," + "'" + correo_cliente + "'"  + "," + 
						"'" + "CP" + "'" + "," + "TO_DATE ("+
						"'" + fecha_registro + "' , 'yyyy/mm/dd HH24-Mi-SS')" + "," 
					     + puesto_atencion + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia);
				prepStmt = conexion.prepareStatement(sentencia);
				prepStmt.executeUpdate();
				conexion.commit();	
				
				sentencia = "INSERT INTO CIERRES_PRESTAMOS (ID_CIERRE, ID_PRESTAMO_CERRADO) "+
						"VALUES (" + idTransaccionMax + "," + id_eliminar + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia);
				prepStmt = conexion.prepareStatement(sentencia);
				prepStmt.executeUpdate();
				conexion.commit();	
				
				String sentencia1 = "UPDATE PRESTAMOS SET ESTADO = 'Cerrado' WHERE ID = "
						+ id_eliminar;
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();		
			}
			
			else
			{
				throw new Exception("Este prestamo no existe, ingrese una ID de prestamo válida");
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
