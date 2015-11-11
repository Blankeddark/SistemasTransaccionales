package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBd {
	
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";
	
	public Connection conexion;

	private String usuario;
	
	private String clave;
	
	private String cadenaConexion;
	
	public ConexionBd()
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
	    	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
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
		try{
			connection.close();
			connection = null;
			
		}
		catch (SQLException exception)
		{
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexiÃ³n.");
		}
	}
	
//	public ArrayList<UsuarioValues> darUsuarios() throws Exception
//	{
//		PreparedStatement prepStat = null;
//		
//		ArrayList <UsuarioValues> usuarios = new ArrayList <UsuarioValues>();
//		
//		try{
//			establecerConexion(cadenaConexion, usuario, clave);
//			
//			Statement s = conexion.createStatement();
//			ResultSet rs = s.executeQuery(" SELECT * FROM USUARIOS");
//			while(rs.next()){
//				String correo = rs.getString("CORREO");
//				String login = rs.getString("LOGIN");
//				String contraseña = rs.getString("CONTRASEÑA");
//				String numero_id = rs.getString("NUMERO_ID");
//				String tipo_id = rs.getString("TIPO_ID");
//				String nombre = rs.getString("NOMBRE");
//				
//				UsuarioValues nuevoU = new UsuarioValues(nombre, idP, login, rol, clave, tipoId);
//				
//				System.out.println("---------Usuarios-------");
//			}
//		}
//		finally
//		{
//			return usuarios;
//		}
//		
//		
//	}
	
	public static void main(String[] args)
	{
		System.out.println("main");
		ConexionBd conexion = new ConexionBd();
		
	}
	
}
