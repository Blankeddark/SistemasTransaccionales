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

public class RegistrarOperacionCuentaDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public RegistrarOperacionCuentaDAO()
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

	public String darCorreoUsuario(String numeroId, String tipoId)
	{
		String correo = "";
		PreparedStatement prepStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
			Statement state = conexion.createStatement();
			ResultSet rs = state.executeQuery("SELECT CORREO FROM CUENTAS WHERE NUMERO_ID = " 
					+ "'" + numeroId + "'" + " AND TIPO_ID = " + "'" + tipoId +"'");
			correo = rs.getString("CORREO");

		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return correo;
	}

	
	/**
	 * Esté metodo se encarga de resolver el RF6, no necesita apoyo de ningún otro
	 * método externo, simplemente de las entradas que ofrece el navegador.
	 * @param tipo
	 * @param correo_cliente
	 * @param id_cuenta_origen
	 * @param valor
	 * @param puesto_atencion
	 * @param cajero
	 * @return
	 * @throws Exception
	 */
	public boolean registrarOperacionSobreCuentaExistente (String tipo, String correo_cliente, int idCuentaDestino, int valor, int puesto_atencion, String cajero, int id_cuenta_origen) throws Exception
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

			ResultSet rs1 = state.executeQuery("SELECT ESTADO FROM CUENTAS WHERE ID_CUENTA = "
					+ id_cuenta_origen);
			String estadoActual = "";
			
			while(rs1.next())
			{
				estadoActual = rs1.getString("ESTADO");
			}
			
            
			rs1 = state.executeQuery("SELECT TIPO_CUENTA FROM CUENTAS WHERE ID_CUENTA = "
					+ id_cuenta_origen);
			String tipo_cuenta = "";
			while(rs1.next())
			{
				
				tipo_cuenta= rs1.getString("TIPO_CUENTA");
			}
			
			
			if(estadoActual.equals("Inactiva"))
			{
				throw new Exception("La cuenta esta inactiva, no se pueden registrar operaciones");	
			}

			rs1 = state.executeQuery("SELECT ID_TRANSACCION "
					+ "FROM (SELECT ID_TRANSACCION FROM TRANSACCIONES ORDER BY ID_TRANSACCION DESC)"
					+ " WHERE ROWNUM = 1");
			
			while (rs1.next())
			{
				idTransaccion= rs1.getInt("ID_TRANSACCION");		
			}
			idTransaccion++;


			String sentencia = "INSERT INTO TRANSACCIONES (ID_TRANSACCION, CORREO_USUARIO, TIPO, FECHA_TRANSACCION, ID_PUNTO_ATENCION) "+
					"VALUES (" + idTransaccion + "," + "'" + correo_cliente + "'"  + "," + 
					"'" + tipo + "'" + "," + "TO_DATE ("+
					"'" + fecha_registro + "' , 'yyyy/mm/dd HH24-Mi-SS')" + "," 
				     + puesto_atencion + ")";
			System.out.println("--------------------------------------------------------------------------");
			System.out.println(sentencia);
			prepStmt = conexion.prepareStatement(sentencia);
			prepStmt.executeUpdate();
			conexion.commit();

			if(tipo.equals("Consignacion") || tipo.equals("C"))
			{
				
				int saldoActual = 0;
				Statement s = conexion.createStatement();
				ResultSet rs = s.executeQuery("SELECT SALDO FROM CUENTAS WHERE ID_CUENTA = " + idCuentaDestino);
				
				while(rs.next())
				{
					saldoActual = rs.getInt("SALDO");
				}
				
				saldoActual += valor;

				if(id_cuenta_origen != 0)
				{
					String sentencia1 = "INSERT INTO CONSIGNACIONES (ID, ID_CUENTA_ORIGEN,"
							+ " ID_CUENTA_DESTINO, MONTO) "+
							"VALUES (" + idTransaccion + "," + id_cuenta_origen + "," + idCuentaDestino + "," + valor +")";
					System.out.println("--------------------------------------------------------------------------");
					System.out.println(sentencia1);
					prepStmt = conexion.prepareStatement(sentencia1);
					prepStmt.executeUpdate();
					conexion.commit();
					
					int saldoOrigen = 0;
					
					ResultSet rs3 = s.executeQuery("SELECT SALDO FROM CUENTAS WHERE ID_CUENTA = " + id_cuenta_origen);
					
					while(rs3.next())
					{
						saldoOrigen = rs3.getInt("SALDO");
					}
					
					String sentencia2 = "UPDATE CUENTAS SET SALDO = " + (saldoOrigen - valor) + " WHERE ID_CUENTA = " + id_cuenta_origen;
					System.out.println("--------------------------------------------------------------------------");
					System.out.println(sentencia2);
					prepStmt = conexion.prepareStatement(sentencia2);
					prepStmt.executeUpdate();
					conexion.commit();
				}
				
				else
				{
					String sentencia1 = "INSERT INTO CONSIGNACIONES (ID, "
							+ " ID_CUENTA_DESTINO, MONTO) "+
							"VALUES (" + idTransaccion + "," + idCuentaDestino + "," + valor +")";
					System.out.println("--------------------------------------------------------------------------");
					System.out.println(sentencia1);
					prepStmt = conexion.prepareStatement(sentencia1);
					prepStmt.executeUpdate();
					conexion.commit();
					
			}
				
				String sentencia0 = "UPDATE CUENTAS SET SALDO = " + saldoActual 
						+ " WHERE ID_CUENTA = " + idCuentaDestino;
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia0);
				
				prepStmt = conexion.prepareStatement(sentencia0);
				prepStmt.executeUpdate();
				conexion.commit();
			}

			
			else if(tipo.equals("Retiro") || tipo.equals("R"))
			{
				
				int saldoActual = 0;
				Statement s = conexion.createStatement();
				ResultSet rs = s.executeQuery("SELECT SALDO FROM CUENTAS WHERE ID_CUENTA = " + id_cuenta_origen);
				saldoActual = rs.getInt("SALDO");
				saldoActual-= valor;

				String sentencia1 = "INSERT INTO RETIROS (ID, ID_CUENTA_RETIRO, MONTO) "+
						"VALUES (" + idTransaccion + "," + id_cuenta_origen + "," + valor + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia1);
				prepStmt = conexion.prepareStatement(sentencia1);
				prepStmt.executeUpdate();
				conexion.commit();

				String sentencia0 = "UPDATE CUENTAS SET SALDO = " + saldoActual 
						+ "WHERE ID_CUENTA = " + id_cuenta_origen;
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia0);
				prepStmt = conexion.prepareStatement(sentencia0);
				prepStmt.executeUpdate();
				conexion.commit();
			}

			else if(tipo.equals("Apertura cuenta"))
			{
				
				int idMaxCuenta = 0;
				Statement s = conexion.createStatement();
				ResultSet rs = s.executeQuery("SELECT ID_CUENTA"
						+ "FROM (SELECT ID_CUENTA FROM CUENTAS ORDER BY ID_CUENTA DESC)"
						+ "WHERE ROWNUM = 1");
				idMaxCuenta = rs.getInt("ID_CUENTA");
				idMaxCuenta++;

				rs = s.executeQuery("SELECT OFICINA FROM PUNTOS_ATENCION WHERE ID =" + puesto_atencion);
				int idOficina = rs.getInt("OFICINA");

				String sentencia2 = "INSERT INTO CUENTAS (ID_CUENTA, CORREO, TIPO_CUENTA, OFICINA, FECHA_ULTIMO_MOVIMIENTO, SALDO, ESTADO) "+
						"VALUES (" + idMaxCuenta + "," + "'" + correo_cliente + "'" + "," 
						+ "'" + tipo_cuenta + "'" + "," + idOficina + "," +  "TO_DATE ("+
						"'" + fecha_registro + "' , 'yyyy/mm/dd HH24-Mi-SS')"  + "," 
						+ valor + ", 'Activa' " + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia2);

				prepStmt = conexion.prepareStatement(sentencia2);
				prepStmt.executeUpdate();
				conexion.commit();

				String sentencia3 = "INSERT INTO APERTURA_CUENTAS (ID, CORREO_CUENTA_ABIERTA, TIPO_CUENTA_ABIERTA) "+
						"VALUES (" + idTransaccion +"," + "'" + correo_cliente + "'" + ","
						+ "'" + tipo_cuenta + "'" + ")";
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(sentencia3);
				prepStmt = conexion.prepareStatement(sentencia3);
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
