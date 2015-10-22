package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AsociarCuentaDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public AsociarCuentaDAO()
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
	 * Método que asocia la cuenta de un cliente empresarial a sus empleados.
	 * @param id_eliminar
	 * @return
	 * @throws Exception
	 */
	public boolean registrarAsociarCuenta (String correoEmpleador, int cuentaEmpleador, String correoEmpleado, int cuentaEmpleado, int valorPagar, String frecuencia ) throws Exception
	{
		PreparedStatement prepStmt = null;

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			//conexion.setAutoCommit(false);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CLIENTES WHERE CORREO = " + correoEmpleador);

			while(rs.next())
			{
				String tipoPersona = rs.getString("TIPO_PERSONA");
				if(tipoPersona.charAt(0) == 'N')
				{
					throw new Exception("Una persona natural no puede tener una cuenta asociada a empleados");
				}
			}

			rs = s.executeQuery("SELECT CORREO_EMPLEADOR, ID_CUENTA_EMPLEADOR AS ID FROM CLIENTES_EMPLEADOS_DE_CLIENTE"
					+ " WHERE CORREO_EMPLEADO = " + correoEmpleado);

			int cuentaAsociada = 0;

			while(rs.next())
			{
				cuentaAsociada = rs.getInt("ID");
				String correo = rs.getString("CORREO_EMPLEADOR");

				if(cuentaAsociada != 0 && correo.equals(correoEmpleador))
				{
					throw new Exception("El empledo ya tiene una de sus cuenta asociada");
				}
			}

			String sentencia = "INSERT INTO CLIENTES_EMPLEADOS_DE_CLIENTES(CORREO_EMPLEADOR, CORREO_EMPLEADO,"
					+ " ID_CUENTA_EMPLEADOR, ID_CUENTA_EMPLEADO, MONTO_PAGO, FRECUENCIA_PAGO) "
					+ "VALUES (" + "'" + correoEmpleador + "'" + ","  + "'" + correoEmpleado + "'" + "," 
					+ cuentaEmpleador + "," + cuentaEmpleado + "," + valorPagar + "," + "'" + 
					frecuencia + "'" + ")";
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

}
