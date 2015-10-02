package conexion;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import DAOS.RegistrarClienteDAO;


public class ConexionBd 
{	
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";
	
	public Connection conexion;

	private String usuario;
	
	private String clave;
	
	private String cadenaConexion;
	
	public ConexionBd()
	{
		inicializar("./Conexion/conexion.properties");
	}
	
	public Connection darConexion()
	{
		return conexion;
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
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexión.");
		}
	}
	
	public ArrayList<String> darUsuarios() throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList  usuarios = new ArrayList ();
		
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CUENTAS");
			while(rs.next())
			{
				String idP = rs.getString("CORREO");
				usuarios.add(idP);
				System.out.println("---------Usuarios-------");			
			}
		}
		
		finally
		{
			return usuarios;
		}
			
	}
	
	public static void main(String[] args)
	{
		System.out.println("main");
		ConexionBd conexion = new ConexionBd();
	
		try
		{
			ArrayList a = conexion.darUsuarios();
			for (int i = 0; i < a.size(); i++) 
			{
			   System.out.println(a.get(i));	
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}
