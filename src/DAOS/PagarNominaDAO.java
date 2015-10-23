package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class PagarNominaDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public PagarNominaDAO()
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
	 * Método que paga la nómina de un cliente de tipo ejecutivo, se utilizan savepoints
	 * para asegurar la transaccionalidad del mpetodo y todo eso.
	 * @param id_eliminar
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList registrarPagarNomina (int cuenta, String correoCliente) throws Exception
	{
		PreparedStatement prepStmt = null;
		ArrayList cuentasEmpleadosPorPagar = new ArrayList();
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			//Desactivamos el autoCommit para tener total control sobre la transacción
			conexion.setAutoCommit(false);

			//Garantizamos que no habrá problemas de transaccionalidad poniendo el nivel de aislamiento
			// de la transacción en serializable
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			//Creamos nuestro primer savePoint para retornarnos al inicio de la transaccion
			Savepoint savepoint1 = conexion.setSavepoint("Savepoint1");

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CUENTAS WHERE ID_CUENTA = " + cuenta);
			int c = 0;

			while(rs.next())
			{
				c = rs.getInt("ID_CUENTA");
				if(c == 0)
				{
					throw new Exception ("La cuenta ingresada no existe");
				}
			}

			rs = s.executeQuery("SELECT * FROM CLIENTES_EMPLEADOS_DE_CLIENTES WHERE "
					+ " ID_CUENTA_EMPLEADOR = " + cuenta);
			int cantidadEmpleados = 0;

			while(rs.next())
			{   
				int cuentaEmpleadoActual = rs.getInt("ID_CUENTA_EMPLEADO");
				cuentasEmpleadosPorPagar.add(cuentaEmpleadoActual);
				cantidadEmpleados++;
			}

			//Creamos otro savePoint antes de que entrar al for
			Savepoint savepoint2 = conexion.setSavepoint("Savepoint2");
			RegistrarOperacionCuentaDAO dao1 = new RegistrarOperacionCuentaDAO();

			//El for está acotado por el número de cuentas de empleados asociadas a la cuenta que entra
			//Por parámetro
			for (int i = 0; i < cuentasEmpleadosPorPagar.size(); i++) 
			{	
				rs = s.executeQuery("SELECT * FROM CLIENTES_EMPLEADOS_DE_CLIENTES WHERE "
						+ " ID_CUENTA_EMPLEADO = " + (Integer) cuentasEmpleadosPorPagar.get(i) 
						+ " AND ID_CUENTA_EMPLEADOR = " + cuenta);

				int valor = 0;

				while(rs.next())
				{
					valor = rs.getInt("MONTO_PAGO");
				}

				try
				{   
					//Utilizamos como subtransaccion el RF10
					dao1.registrarOperacionSobreCuentaExistente("C", correoCliente, (Integer) cuentasEmpleadosPorPagar.get(i), 
							valor, 1, "null", cuenta);
					//Eliminamos al empleado de la lista de porPagar una vez se le ha pagado
					cuentasEmpleadosPorPagar.remove(i);
				}

				catch(Exception e)
				{   

					System.out.println(e.getMessage());
					if(e.getMessage().equals("La cuenta actual no cuenta con suficiente dinero para hacer la transferencia"))
					{   
						return cuentasEmpleadosPorPagar;
					}

					else
					{
						conexion.rollback(savepoint2);
					}

					e.printStackTrace();
				}

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
		
		return cuentasEmpleadosPorPagar;
	}
}
