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

public class RegistrarOperacionSobrePrestamoDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public RegistrarOperacionSobrePrestamoDAO()
	{
		inicializar("./Conexion/conexion.properties");
	}

	public void inicializar(String path)
	{
		try
		{
			File arch = new File(path + ARCHIVO_CONEXION);
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

	public ArrayList<String> darOperacionesPrestamoClienteEspecifico(String idCliente) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList  cuentas = new ArrayList ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT ");
			while(rs.next())
			{
				String idP = rs.getString("CUENTA");
				cuentas.add(idP);
				System.out.println("---------Usuarios-------");			
			}

		}

		finally
		{
			return cuentas;
		}

	}

	public boolean registrarOperacionSobrePrestamoExistente (String tipo, String correo_cliente, int id_cuenta, int valor, int puesto_atencion, String cajero, String tipo_prestamo, int id_solicitud,
			int numCuotas, int idPrestamo, int cuentaOrigen) throws Exception
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
			ResultSet rs1 = state.executeQuery("SELECT ID_TRANSACCION"
					+ "FROM (SELECT ID_TRANSACCION FROM TRANSACCIONES ORDER BY ID_TRANSACCION DESC)"
					+ "WHERE ROWNUM = 1");

			idTransaccion = rs1.getInt("ID_TRANSACCION");
			idTransaccion++;
            
            String tipoX = "";
			
            if(tipo.equals("Solicitar")) tipoX = "SP";
            else if(tipo.equals("Aprobar")) tipoX = "AP";
            else if(tipo.equals("Rechazar")) tipoX = "RP";
            else if(tipo.equals("Pagar cuota")) tipoX = "PP";
            else if(tipo.equals("Pagar cuota extraordinaria")) tipoX = "PPE";
            
                
			String sentencia = "INSERT INTO TRANSACCIONES (ID_TRANSACCION, CORREO_USUARIO, TIPO, FECHA_TRANSACCION, ID_PUNTO_ATENCION) "+
					"VALUES (" + idTransaccion + "," + "'" + correo_cliente + "'"  + "," 
					+ "'" + tipoX + "'"  + "," + "TO_DATE (" + "'" + fecha_registro 
					+ "' , 'yyyy/mm/dd HH24-Mi-SS')" + "," + puesto_atencion + ")";
			System.out.println("--------------------------------------------------------------------------");
			System.out.println(sentencia);
			prepStmt = conexion.prepareStatement(sentencia);
			prepStmt.executeUpdate();
			conexion.commit();


			if(tipo.equals("Solicitar"))
			{
				Statement s = conexion.createStatement();	

				String sentencia1 = "INSERT INTO SOLICITUDES_PRESTAMO (ID, MONTO_SOLICITADO,"
						+ " TIPO, ESTADO) "+
						"VALUES (" + idTransaccion + "," + valor + "," + "'" + tipo_prestamo + "'" + "," 
						+ "'" + "EnEspera" + "'" + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();

			}

			else if(tipo.equals("Aprobar"))
			{
				Statement s = conexion.createStatement();	
				String sentencia1 = "INSERT INTO APROBACIONES_PRESTAMO"
						+ " (ID, SOLICITUD_APROBADA) "+
						"VALUES (" + idTransaccion + "," + id_solicitud + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);

				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();
				
				
				

				sentencia1 = "UPDATE SOLICITUDES_PRESTAMO SET ESTADO = 'Aprobada' "
						+ "WHERE ID = " + idTransaccion;
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();

				int idPrestamoMax = 0;
				s = conexion.createStatement();
			    ResultSet rs = s.executeQuery("SELECT ID"
						+ "FROM (SELECT ID FROM PRESTAMOS ORDER BY ID DESC)"
						+ "WHERE ROWNUM = 1");

				idPrestamoMax = rs.getInt("ID");
				idPrestamoMax++;

				int cuota = (valor/numCuotas);
				double random = Math.random();
				while (random == 0)
				{
					random= Math.random();
				}
				
				int diaPago = (int) random*29;
				
				sentencia1 = "INSERT INTO PRESTAMOS (ID, CORREO_CLIENTE, MONTO_PRESTADO, "
						+ "TIPO, FECHA_PRESTAMO, DIA_PAGO_MENSUAL, CUOTA, SALDO_PENDIENTE,"
						+ "ESTADO, NUM_CUOTAS, INTERES, CUOTAS_EFECTIVAS) VALUES ("
						+ idPrestamoMax + "," + "'" + correo_cliente + "'" + "," + valor + ","
						+ "'" + tipo_prestamo + "'" + "," + "TO_DATE (" + "'" + fecha_registro 
						+ "' , 'yyyy/mm/dd HH24-Mi-SS')" + "," + diaPago + ","
						+ cuota + "," + valor + "," + "'Abierto', " + numCuotas + "0.0," 
						+ "0)";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();

			}

			else if(tipo.equals("Rechazar"))
			{
				int idMax = 0;
				Statement s = conexion.createStatement();
				

				String sentencia1 = "INSERT INTO RECHAZOS_PRESTAMO"
						+ " (ID, SOLICITUD_RECHAZADA) "+
						"VALUES (" + idTransaccion + "," + id_solicitud + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();
			
				sentencia1 = "UPDATE SOLICITUDES_PRESTAMO SET ESTADO = 'Rechazada' "
						+ "WHERE ID = " + idMax;
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();
			}


			else if(tipo.equals("Pagar cuota"))
			{
				Statement s = conexion.createStatement();
				ResultSet rs = s.executeQuery("SELECT SALDO_PENDIENTE FROM PRESTAMOS WHERE ID = " + idPrestamo);
				int saldoPendiente = rs.getInt("SALDO_PENDIENTE");
				saldoPendiente -= (valor/numCuotas);
				rs = s.executeQuery("SELECT CUOTAS_EFECTIVAS FROM PRESTAMOS WHERE ID = " + idPrestamo);
				int cuotasEfectivas = rs.getInt("CUOTAS_EFECTIVAS");
				cuotasEfectivas++;
				String sentencia1 = "UPDATE PRESTAMOS SET SALDO_PENDIENTE = " + saldoPendiente + "," 
						+ " CUOTAS_EFECTIVAS = " + cuotasEfectivas + "WHERE ID = " + idPrestamo;
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();
				sentencia1 = "INSERT INTO PAGOS_PRESTAMO(ID_PAGO, ID_PRESTAMO_PAGADO, MONTO_PAGADO, ID_CUENTA_ORIGEN) "
						+ "VALUES (" + idTransaccion + "," + idPrestamo + "," + (valor/numCuotas) +  cuentaOrigen + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();
			}

			else if(tipo.equals("Pagar cuota extraordinaria"))
			{
				Statement s = conexion.createStatement();
				ResultSet rs = s.executeQuery("SELECT SALDO_PENDIENTE FROM PRESTAMOS WHERE ID = " + idPrestamo);
				int saldoPendiente = rs.getInt("SALDO_PENDIENTE");
				saldoPendiente -= valor;
				rs = s.executeQuery("SELECT CUOTAS_EFECTIVAS FROM PRESTAMOS WHERE ID = " + idPrestamo);
				int cuotasEfectivas = rs.getInt("CUOTAS_EFECTIVAS");
				cuotasEfectivas++;
				String sentencia1 = "UPDATE PRESTAMOS SET SALDO_PENDIENTE = " + saldoPendiente + "," 
						+ " CUOTAS_EFECTIVAS = " + cuotasEfectivas + "WHERE ID = " + idPrestamo;
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();
				sentencia1 = "INSERT INTO PAGOS_PRESTAMO(ID_PAGO, ID_PRESTAMO_PAGADO, MONTO_PAGADO, ID_CUENTA_ORIGEN) "
						+ "VALUES (" + idTransaccion + "," + idPrestamo + "," + valor + cuentaOrigen + ")";
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
