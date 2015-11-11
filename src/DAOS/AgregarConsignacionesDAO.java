package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Properties;
import java.util.Random;

import _ASTools.UsaRandom;

public class AgregarConsignacionesDAO {

	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	int maxConsignaciones; 

	int maxTransacciones;

	UsaRandom usaRandom;
	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public AgregarConsignacionesDAO()
	{
		inicializar("./Conexion/conexion.properties");
		usaRandom = new UsaRandom();
	}

	public void inicializar(String path)
	{
		try
		{
			File arch = new File(path+ARCHIVO_CONEXION);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream ("C:/Users/Sergio/git/PROJECT_Sistrans/SistemasTransaccionales/WebContent/conexion.properties");

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

	public void agregarConsignacionesRandom(int cantidadConsignaciones) throws SQLException
	{
		establecerConexion(cadenaConexion, usuario, clave);
		Random random = new Random();
		for (int i = 0; i < cantidadConsignaciones; i++)
		{
			try
			{
				agregarConsignacionRandom(random);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				cantidadConsignaciones++;
				continue;
			}
		}
	}

	public void agregarConsignacionRandom(Random random) throws SQLException, ParseException
	{
		String monto = String.valueOf( random.nextInt(3000) + 1);

		int idOrigen = 0;

		if (random.nextInt(3) == 0)
		{
			idOrigen = generarIdCuentaRandom();
		}

		int idCuentaDestino = generarIdCuentaRandom() + 1;

		while (idOrigen == idCuentaDestino)
		{
			idOrigen = generarIdCuentaRandom();
		}

		if (idOrigen == 0)
		{
			agregarConsignacionSinOrigen( String.valueOf(idCuentaDestino) , monto);
		}

		else
		{
			agregarConsignacionConOrigen(String.valueOf(idOrigen), String.valueOf(idCuentaDestino), monto);
		}
	}

	public void agregarConsignacionConOrigen(String idOrigen, String idDestino, String monto) throws SQLException, ParseException
	{
		Statement s = conexion.createStatement();

		String id = generarIdConsignacion();
		String idTransaccion = generarIdTransaccion();
		
		Random random = new Random();
		String correoOrigen = "";
		
		ResultSet rs= s.executeQuery("SELECT CORREO FROM CUENTAS WHERE ID_CUENTA=" + idOrigen);
		
		while (rs.next())
		{
			correoOrigen = rs.getString(1);
		}
		
		rs.close();
		String fecha_transaccion = usaRandom.generarFechaRandom(random);
		String puntoAtencion = generarIdPuntoAtencionRandom();

		String sql1 = "INSERT INTO TRANSACCIONES VALUES("
		+ idTransaccion + ", '" + correoOrigen + "', 'C', TO_DATE('" + fecha_transaccion + "', 'dd/MM/yyyy'), " +  puntoAtencion + ")";
		
		s.executeUpdate(sql1);
		System.out.println(sql1);
		System.out.println("------------------------------------------------------");
		maxTransacciones++;


		String sql = "INSERT INTO CONSIGNACIONES VALUES(" + id + ", " + idOrigen + ", " + idDestino+ ", " + monto + ")";

		s.executeUpdate(sql);
		System.out.println(sql);
		System.out.println("------------------------------------------------------");
		maxConsignaciones++;
		s.close();
	}

	public void agregarConsignacionSinOrigen(String idDestino, String monto) throws SQLException, ParseException
	{
		Statement s = conexion.createStatement();

		String id = generarIdConsignacion();
		String idTransaccion = generarIdTransaccion();

		System.out.println("id generado: " + id);
		System.out.println("id cuenta destino generado: " + idDestino);

		Random random = new Random();
		String fecha_transaccion = usaRandom.generarFechaRandom(random);
		String puntoAtencion = generarIdPuntoAtencionRandom();

		String sql1 = "INSERT INTO TRANSACCIONES(ID_TRANSACCION, TIPO, FECHA_TRANSACCION, ID_PUNTO_ATENCION) VALUES("
		+ idTransaccion + ", 'C', TO_DATE('" + fecha_transaccion + "', 'dd/MM/yyyy'), " +  puntoAtencion + ")";
		
		s.executeUpdate(sql1);
		maxTransacciones++;
		System.out.println(sql1);
		System.out.println("------------------------------------------------------");

		
				String sql = "INSERT INTO CONSIGNACIONES(ID, ID_CUENTA_DESTINO, MONTO) VALUES(" + id + ", " + idDestino+ ", " + monto + ")";
			
		s.executeUpdate(sql);
		System.out.println(sql);
		System.out.println("------------------------------------------------------");
		maxConsignaciones++;
		s.close();
	}

	public int generarIdCuentaRandom() throws SQLException
	{
		Statement s = conexion.createStatement();
		int rta = 0;
		ResultSet rs = s.executeQuery("SELECT ID_CUENTA FROM  ( SELECT ID_CUENTA FROM CUENTAS ORDER BY dbms_random.value) WHERE rownum = 1");
		while(rs.next())
		{
			rta = rs.getInt("ID_CUENTA");
		}
		s.close();
		rs.close();
		return rta;
	}



	public String generarIdPuntoAtencionRandom() throws SQLException
	{
		Statement s = conexion.createStatement();
		int rta = 0;
		ResultSet rs = s.executeQuery("SELECT ID FROM  ( SELECT ID FROM PUNTOS_ATENCION ORDER BY dbms_random.value) WHERE rownum = 1");
		while(rs.next())
		{
			rta = rs.getInt("ID");
		}
		s.close();
		rs.close();
		if (rta < 151)
		{
			rta = 151;
		}
		return String.valueOf(rta);

	}
	
	public String generarIdConsignacion() throws SQLException
	{
		if (maxConsignaciones == 0)
		{
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT MAX(ID) FROM CONSIGNACIONES");

			while (rs.next())
			{
				maxConsignaciones = rs.getInt(1) + 1;
			}
			rs.close();
		}

		return String.valueOf(maxConsignaciones);
	}

	public String generarIdTransaccion() throws SQLException
	{
		if (maxTransacciones == 0)
		{
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT MAX(ID_TRANSACCION) FROM TRANSACCIONES");

			while (rs.next())
			{
				maxTransacciones = rs.getInt(1) + 1;
			}
			rs.close();
		}

		return String.valueOf(maxTransacciones);
	}


}
