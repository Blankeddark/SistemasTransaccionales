package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import _ASTools.UsaRandom;

public class AgregarCuentasDAO {

	int idCuentaMax;

	UsaRandom usaRandom;

	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	/**
	 * Modela el saldo máximo que puede tener una cuenta aleatoriamente generada.
	 */
	public final static int SALDO_MAXIMO = 1000000;

	/**
	 * Modela el saldo mínimo que pueda tener una cuenta aleatoriamente generada.
	 */
	public final static int SALDO_MINIMO = 10000;

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	ArrayList<String> orderByPermitidos;

	ArrayList<String> tipoCuentaPermitidos;

	public AgregarCuentasDAO()
	{
		inicializar("./Conexion/conexion.properties");
		inicializarTipoCuentaPermitidos();
		inicializarOrderBy();
		usaRandom = new UsaRandom();
		idCuentaMax = 0;
	}

	public void inicializarTipoCuentaPermitidos()
	{
		tipoCuentaPermitidos = new ArrayList<String>();
		tipoCuentaPermitidos.add("Corriente");
		tipoCuentaPermitidos.add("Ahorros");
	}

	public void inicializarOrderBy()
	{
		orderByPermitidos = new ArrayList<String>();
		orderByPermitidos.add("CORREO");
		orderByPermitidos.add("NUMERO_ID");
		orderByPermitidos.add("NOMBRE");
		orderByPermitidos.add("TELEFONO");
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
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexiÃƒÂ³n.");
		}
	}

	public void agregarCuentasRandom(int numeroCuentas) throws SQLException, ParseException
	{
		establecerConexion(cadenaConexion, usuario, clave);
		Random random = new Random();

		for (int i = 0; i < numeroCuentas; i++)
		{
			try
			{
				agregarCuentaRandom(random);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				numeroCuentas++;
				continue;
			}
		}
	}
	
	public void agregarCuentasRandomThread(int numeroCuentas) throws SQLException, ParseException
	{
		establecerConexion(cadenaConexion, usuario, clave);
		Random random = new Random();

		ThreadAgregarCuentasDAO thread = new ThreadAgregarCuentasDAO(numeroCuentas/3, conexion);
		thread.start();
		
		ThreadAgregarCuentasDAO thread2 = new ThreadAgregarCuentasDAO(numeroCuentas/3, conexion);
		thread2.start();
		
		for (int i = 0; i < numeroCuentas/3; i++)
		{
			try
			{
				agregarCuentaRandom(random);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				numeroCuentas++;
				continue;
			}
		}
	}


	public void agregarCuentaRandom(Random random) throws SQLException, ParseException
	{
		String correo = seleccionarCorreoRandom();
		String tipo_cuenta = seleccionarTipoCuentaRandom(random);
		String idOficina = seleccionarIdOficinaRandom();
		String fechaUltMov = usaRandom.generarFechaRandom(random);
		int saldoInt = random.nextInt(SALDO_MAXIMO) + SALDO_MINIMO;

		if (saldoInt > SALDO_MAXIMO)
		{
			saldoInt = SALDO_MAXIMO;
		}

		String saldo = String.valueOf(saldoInt);

		agregarCuenta(correo, tipo_cuenta, idOficina, fechaUltMov, saldo);
	}
	
	public void agregarCuentaRandomCompartirConnection(Random random, Connection con) throws SQLException, ParseException
	{
		String correo = seleccionarCorreoRandomCompartirConnection(con);
		String tipo_cuenta = seleccionarTipoCuentaRandom(random);
		String idOficina = seleccionarIdOficinaRandomCompartirConnection(con);
		String fechaUltMov = usaRandom.generarFechaRandom(random);
		int saldoInt = random.nextInt(SALDO_MAXIMO) + SALDO_MINIMO;

		if (saldoInt > SALDO_MAXIMO)
		{
			saldoInt = SALDO_MAXIMO;
		}

		String saldo = String.valueOf(saldoInt);

		agregarCuentaCompartirConnection(con, correo, tipo_cuenta, idOficina, fechaUltMov, saldo);
	}


	public String seleccionarIdOficinaRandom() throws SQLException
	{
		Statement s = conexion.createStatement();
		String rta = "";
		ResultSet rs = s.executeQuery("SELECT ID_OFICINA FROM  ( SELECT ID_OFICINA FROM OFICINAS ORDER BY dbms_random.value) WHERE rownum = 1");
		while(rs.next())
		{
			rta = rs.getString("ID_OFICINA");
		}

		s.close();
		//Esto nunca pasará
		return rta;
	}
	
	public String seleccionarIdOficinaRandomCompartirConnection(Connection con) throws SQLException
	{
		Statement s = con.createStatement();
		String rta = "";
		ResultSet rs = s.executeQuery("SELECT ID_OFICINA FROM  ( SELECT ID_OFICINA FROM OFICINAS ORDER BY dbms_random.value) WHERE rownum = 1");
		while(rs.next())
		{
			rta = rs.getString("ID_OFICINA");
		}

		s.close();
		//Esto nunca pasará
		return rta;
	}

	public String seleccionarTipoCuentaRandom(Random random)
	{
		return tipoCuentaPermitidos.get( random.nextInt( tipoCuentaPermitidos.size() ));
	}

	public String seleccionarCorreoRandom() throws SQLException
	{
		Statement s = conexion.createStatement();
		String rta = "";
		ResultSet rs = s.executeQuery("SELECT CORREO FROM  ( SELECT CORREO FROM USUARIOS WHERE TIPO_USUARIO = 'Cliente' ORDER BY dbms_random.value) WHERE rownum = 1");
		while(rs.next())
		{
			rta = rs.getString("CORREO");
		}
		s.close();
		return rta;
	}
	
	public String seleccionarCorreoRandomCompartirConnection(Connection con) throws SQLException
	{
		Statement s = con.createStatement();
		String rta = "";
		ResultSet rs = s.executeQuery("SELECT CORREO FROM  ( SELECT CORREO FROM USUARIOS WHERE TIPO_USUARIO = 'Cliente' ORDER BY dbms_random.value) WHERE rownum = 1");
		while(rs.next())
		{
			rta = rs.getString("CORREO");
		}
		s.close();
		return rta;
	}

	/**
	 * Agrega una nueva cuenta. El id es generado automáticamente.
	 * @throws SQLException 
	 */
	public void agregarCuenta(String correo, String tipo_cuenta, String idOficina, String fechaUltMov, String saldo) throws SQLException
	{
		Statement s = conexion.createStatement();
		int id = generarIdCuenta(s);

		String sql = "INSERT INTO CUENTAS VALUES(" + id + ", '" + correo+"', '" 
				+ tipo_cuenta+"', " + idOficina + ", TO_DATE('" + fechaUltMov + "', 'dd/MM/yyyy'), " + saldo + ", 'Activa')";

		s.executeUpdate(sql);
		System.out.println(sql);
		System.out.println("------------------------------------------------------");
		s.close();
		idCuentaMax++;
	}
	
	public void agregarCuentaCompartirConnection(Connection con, String correo, String tipo_cuenta, String idOficina, String fechaUltMov, String saldo) throws SQLException
	{
		Statement s = con.createStatement();
		int id = generarIdCuentaCompartirConnection(s, con);

		String sql = "INSERT INTO CUENTAS VALUES(" + id + ", '" + correo+"', '" 
				+ tipo_cuenta+"', " + idOficina + ", TO_DATE('" + fechaUltMov + "', 'dd/MM/yyyy'), " + saldo + ", 'Activa')";

		s.executeUpdate(sql);
		System.out.println(sql);
		System.out.println("------------------------------------------------------");
		s.close();
		idCuentaMax+=2;
	}

	public int generarIdCuenta(Statement s) throws SQLException
	{
		if(idCuentaMax == 0)
		{
			s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT MAX(ID_CUENTA) FROM CUENTAS");
			while (rs.next())
			{
				idCuentaMax = rs.getInt(1) + 1;
			}
			s.close();
			rs.close();
		}

		return idCuentaMax;
	}
	
	public int generarIdCuentaCompartirConnection(Statement s, Connection con) throws SQLException
	{
		if(idCuentaMax == 0)
		{
			s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT MAX(ID_CUENTA) FROM CUENTAS");
			while (rs.next())
			{
				idCuentaMax = rs.getInt(1) + 2;
			}
			s.close();
			rs.close();
		}

		return idCuentaMax;
	}

}
