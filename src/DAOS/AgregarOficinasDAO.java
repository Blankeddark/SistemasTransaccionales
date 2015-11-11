package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import _ASTools.UsaRandom;

public class AgregarOficinasDAO extends UsaRandom {

	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	UsaRandom usaRandom;

	int maxOficinas;

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public AgregarOficinasDAO()
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

	public void agregarOficinaRandomDebug()
	{
		Random random = new Random();
		String nombre = usaRandom.generarNombreRandom(random);
		String direccion = usaRandom.generarDireccionRandom(random);
		String[] ubicacion = usaRandom.generarUbicacionRandom(random);

		String ciudad = ubicacion[0];
		String departamento = ubicacion[1];

		System.out.println("nombre: " + nombre);
		System.out.println("direccion: " + direccion);
		System.out.println("ciudad: " + ciudad);
		System.out.println("departamento: " + departamento);
		System.out.println("------------------------------");
	}

	public void agregarOficinaRandom(Random random) throws SQLException, InterruptedException
	{
		String nombre = usaRandom.generarNombreRandom(random);
		String direccion = usaRandom.generarDireccionRandom(random);
		String telefono = usaRandom.generarTelefonoRandom(random);
		String[] ubicacion = usaRandom.generarUbicacionRandom(random);

		String ciudad = ubicacion[0];
		String departamento = ubicacion[1];

		agregarOficina(nombre, direccion, telefono, ciudad, departamento);
	}

	public void agregarOficinasRandom(int cantidadOficinas) throws SQLException, InterruptedException
	{
		establecerConexion(cadenaConexion, usuario, clave);
		Random random = new Random();

		for (int i = 0; i < cantidadOficinas; i++)
		{
			agregarOficinaRandom(random);
		}
	}

	public static void main(String[] args)
	{
		AgregarOficinasDAO ag = new AgregarOficinasDAO();
		for (int i = 0; i < 30; i++)
		{
			ag.agregarOficinaRandomDebug();
		}
	}

	public String generarIdOficina() throws SQLException
	{
		if (maxOficinas == 0)
		{
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT MAX(ID_OFICINA) FROM OFICINAS");

			while (rs.next())
			{
				maxOficinas = rs.getInt(1) + 1;
			}
			
			s.close();
			rs.close();
		}
		return String.valueOf(maxOficinas);
	}

	public void agregarOficina(String nombre, String direccion, String telefono, String ciudad, String departamento) throws SQLException, InterruptedException
	{
		Statement s = conexion.createStatement();

		String idOficina = generarIdOficina();

		String sql = "INSERT INTO OFICINAS(ID_OFICINA, NOMBRE, DIRECCION, TELEFONO, CIUDAD, DEPARTAMENTO)"
				+ ""
				+ " VALUES (" + idOficina + ", '" + nombre + "', '" + direccion + "', '"
				+ telefono + "', '" + ciudad + "', '" + departamento + "')";

		s.executeUpdate(sql);	
		
		s.close();
		
		System.out.println(sql);
		System.out.println("------------------------------------------------------");
		maxOficinas++;
	}

}
