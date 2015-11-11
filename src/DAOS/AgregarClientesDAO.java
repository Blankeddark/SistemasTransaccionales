package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import vos.UsuarioValues;
import _ASTools.UsaRandom;

public class AgregarClientesDAO {

	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	UsaRandom usaRandom;
	
	int maxId;

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	private ArrayList<String> finalesCorreoPermitidos;



	private ArrayList<String> tiposIdPermitidos;

	public AgregarClientesDAO()
	{
		inicializar("./Conexion/conexion.properties");
		inicializarCorreosPermitidos();
		inicializarIdPermitidos();
		usaRandom = new UsaRandom();
	}



	public void inicializarIdPermitidos()
	{
		tiposIdPermitidos = new ArrayList<String>();
		tiposIdPermitidos.add("CC");
		tiposIdPermitidos.add("CE");
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
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexiÃƒÂ³n.");
		}
	}

	public void inicializarCorreosPermitidos()
	{
		finalesCorreoPermitidos = new ArrayList<String>();
		finalesCorreoPermitidos.add("@gmail.com");
		finalesCorreoPermitidos.add("@knows.com");
		finalesCorreoPermitidos.add("@bancandes.com");
		finalesCorreoPermitidos.add("@hotmail.com");
		finalesCorreoPermitidos.add("@uniandes.edu.co");
		finalesCorreoPermitidos.add("@outlook.com");
		finalesCorreoPermitidos.add("@outlook.es");
		finalesCorreoPermitidos.add("@hotmail.es");
	}

	public void agregarClientes(int cantidadClientes) throws SQLException, ParseException, InterruptedException
	{
		establecerConexion(cadenaConexion, usuario, clave);
		Random random = new Random();
		//conexion.setAutoCommit(false);
		conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		Statement s = conexion.createStatement();
		for (int i = 0; i < cantidadClientes/2; i++)
		{
//			if (i % 30 == 0)
//			{
//				TimeUnit.MILLISECONDS.sleep(500);
//			}
			try
			{
			agregarUsuarioRandom(random);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				cantidadClientes++;
				continue;
			}
		}
	}
	
	public void agregarClientesConThread(int cantidadClientes) throws SQLException, ParseException, InterruptedException
	{
		establecerConexion(cadenaConexion, usuario, clave);
		Random random = new Random();
		//conexion.setAutoCommit(false);
		conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		Statement s = conexion.createStatement();
		
		ThreadAgregarClientesDAO thread = new ThreadAgregarClientesDAO(cantidadClientes/5, conexion);
		ThreadAgregarClientesDAO thread2 = new ThreadAgregarClientesDAO(cantidadClientes/5, conexion);
		ThreadAgregarClientesDAO thread3 = new ThreadAgregarClientesDAO(cantidadClientes/5, conexion);
		ThreadAgregarClientesDAO thread4 = new ThreadAgregarClientesDAO(cantidadClientes/5, conexion);

		thread.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		for (int i = 0; i < cantidadClientes/5; i++)
		{
//			if (i % 30 == 0)
//			{
//				TimeUnit.MILLISECONDS.sleep(500);
//			}
			try
			{
			agregarUsuarioRandom(random);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				cantidadClientes++;
				continue;
			}
		}
	}

	public UsuarioValues generarUsuarioRandom(Random random) throws ParseException
	{
		String correoRandom = generarCorreoRandom(random);
		String login = correoRandom.split("@")[0];

		if (login.length() > 15)
		{
			login = login.substring(0, 14);
		}
		String contraseña = generarContraseñaRandom();
		String numero_id = generarNumeroRandom(random, 15);
		String tipoId = generarTipoId(random);
		String nombre = usaRandom.generarNombreRandom(random);
		String nacionalidad = usaRandom.generarNacionalidadRandom(random);
		String direccion = usaRandom.generarDireccionRandom(random);
		String telefono = usaRandom.generarTelefonoRandom(random);

		String[] ubicacion = usaRandom.generarUbicacionRandom(random);


		String ciudad = ubicacion[0];
		String departamento = ubicacion[1];

		String cod_postal = generarNumeroRandom(random, 10);

		Date fechaRegistro = usaRandom.darFormato().parse( usaRandom.generarFechaRandom(random) );
		String tipo_usuario = "Cliente";

		UsuarioValues rta = new UsuarioValues(correoRandom, login, contraseña, numero_id, tipoId, nombre, nacionalidad, direccion, telefono, ciudad, departamento, cod_postal, fechaRegistro, tipo_usuario);

		return rta;

	}

	public String generarNumeroRandom(Random random, int maximaLongitud)
	{
		int longitud = random.nextInt(maximaLongitud) + 1;
		return usaRandom.generarSucesionDigitosRandom(random, longitud);
	}

	public String generarTipoId(Random random)
	{
		return tiposIdPermitidos.get( random.nextInt(tiposIdPermitidos.size() ) );
	}

	public void agregarUsuarioRandom(Random random) throws ParseException, SQLException
	{
		Statement s = conexion.createStatement();
		UsuarioValues usuario = generarUsuarioRandom(random);

		String correo = usuario.getCorreo();
		String login = usuario.getLogin();
		String contraseña = usuario.getContraseña();
		String numero_id = usuario.getNumero_id();
		String tipo_id = usuario.getTipo_id();
		String nombre = usuario.getNombre();
		String nacionalidad = usuario.getNacionalidad();
		String direccion = usuario.getDireccion();
		String telefono = usuario.getTelefono();
		String ciudad = usuario.getCiudad();
		String departamento = usuario.getDepartamento();
		String cod_postal = usuario.getCod_postal();
		String fecha_registro = usaRandom.darFormato().format(usuario.getFecha_registro());

		String sql = "INSERT INTO USUARIOS VALUES('" + correo + "', '"+login+"', '"+contraseña+"', '"
				+ numero_id + "', '" + tipo_id + "', '" + nombre + "', '" + nacionalidad + "', '"+
				direccion + "', '" + telefono + "', '" + ciudad+"', '" + departamento+"', '" + cod_postal
				+"', TO_DATE('" + fecha_registro + "', 'dd/MM/yyyy'), 'Cliente')"; 

		s.executeUpdate(sql);
		
		System.out.println(sql);
		System.out.println("-----------------------------------");
		
		String tipoPersona = random.nextInt(2) == 0? "N" : "J";

		agregarCliente(correo, tipoPersona, s);
	}

	public void agregarUsuarioRandomCompartirConnection(Random random, Connection con) throws ParseException, SQLException
	{
		Statement s = con.createStatement();
		UsuarioValues usuario = generarUsuarioRandom(random);

		String correo = usuario.getCorreo();
		String login = usuario.getLogin();
		String contraseña = usuario.getContraseña();
		String numero_id = usuario.getNumero_id();
		String tipo_id = usuario.getTipo_id();
		String nombre = usuario.getNombre();
		String nacionalidad = usuario.getNacionalidad();
		String direccion = usuario.getDireccion();
		String telefono = usuario.getTelefono();
		String ciudad = usuario.getCiudad();
		String departamento = usuario.getDepartamento();
		String cod_postal = usuario.getCod_postal();
		String fecha_registro = usaRandom.darFormato().format(usuario.getFecha_registro());

		String sql = "INSERT INTO USUARIOS VALUES('" + correo + "', '"+login+"', '"+contraseña+"', '"
				+ numero_id + "', '" + tipo_id + "', '" + nombre + "', '" + nacionalidad + "', '"+
				direccion + "', '" + telefono + "', '" + ciudad+"', '" + departamento+"', '" + cod_postal
				+"', TO_DATE('" + fecha_registro + "', 'dd/MM/yyyy'), 'Cliente')"; 

		s.executeUpdate(sql);
		
		System.out.println(sql);
		System.out.println("-----------------------------------");
		
		String tipoPersona = random.nextInt(2) == 0? "N" : "J";

		agregarCliente(correo, tipoPersona, s);
	}
	public String generarCorreoRandom(Random random)
	{
		String correoRandom = "";

		//Se obtiene el final del correo a partir de la posición generada por el número aleatorio.
		int posicionFinal = random.nextInt( finalesCorreoPermitidos.size() );
		String finalCorreo = finalesCorreoPermitidos.get(posicionFinal);

		//Se obtiene la longitud máxima posible para el nombre del correo, asumiendo el valor 
		//máximo para uno de 40 como fue definido en el diseño de la base de datos.
		int numDigitos = random.nextInt(7); 
		int longitudMaximaNombre = 40 - finalCorreo.length() - numDigitos;

		//Se decide aleatoriamente la longitud del nombre del correo.
		int longitudEfectivaNombre = random.nextInt(longitudMaximaNombre) + 1;

		//Se genera aleatoriamente cada letra del nombre del correo
		String nombre = usaRandom.generarStringRandom(random, longitudEfectivaNombre) + usaRandom.generarSucesionDigitosRandom(random, numDigitos);

		return nombre + finalCorreo;

	}

	public String generarContraseñaRandom()
	{
		Random random = new Random();

		//Genera una longitud aleatoria asumiendo una longitud máxima de 15 carácteres.
		int longitudAleatoria = random.nextInt(15) + 1;

		return usaRandom.generarStringRandom(random, longitudAleatoria);
	}

	public void agregarCliente(String correo, String tipoPersona, Statement s) throws SQLException
	{
		String sql = "INSERT INTO CLIENTES VALUES('" + correo + "', '" +tipoPersona+"')";
		boolean loHizo = false;
		while(!loHizo)
		{
			try
			{
				s.executeUpdate(sql);
				loHizo = true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if (e.getMessage().startsWith("ORA-08177")){
				System.err.println(sql);
				System.err.println("-----------------------------------");
				}
				else
				{
					return;
				}
			}
		}
		System.out.println(sql);
		System.out.println("-----------------------------------");
		s.close();
	}
	
	public static void main(String[] args) throws ParseException
	{
		AgregarClientesDAO ag = new AgregarClientesDAO();
		UsuarioValues usuarioActual = null;
		Random random = new Random();
		for ( int i = 0; i < 30; i++)
		{
			usuarioActual = ag.generarUsuarioRandom(random);
			System.out.println("correo: " + usuarioActual.getCorreo());
			System.out.println("login: " + usuarioActual.getLogin());
			System.out.println("contraseña: " + usuarioActual.getContraseña());
			System.out.println("id: " + usuarioActual.getNumero_id());
			System.out.println("Tipo id: " + usuarioActual.getTipo_id());
			System.out.println("Nombre: " + usuarioActual.getNombre());
			System.out.println("Nacionalidad: " + usuarioActual.getNacionalidad());
			System.out.println("Direccion: " + usuarioActual.getDireccion());
			System.out.println("teléfono: " + usuarioActual.getTelefono());

			System.out.println("ciudad: " + usuarioActual.getCiudad());
			System.out.println("departamento: " + usuarioActual.getDepartamento());
			System.out.println("cod_postal: "+ usuarioActual.getCod_postal());
			//System.out.println("fecha registro: " + usaRandom.darFormato().format(usuarioActual.getFecha_registro()));
			System.out.println("-------------------------------------------------------");
		}
	}

}
