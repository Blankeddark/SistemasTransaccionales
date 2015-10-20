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

import vos.ClienteValues;
import vos.CuentaValues;
import vos.OficinaValues;
import vos.PrestamoValues;
import vos.TransaccionValues;
import vos.UsuarioValues;

public class ConsultarClienteDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public ConsultarClienteDAO()
	{
		inicializar("./Conexion/conexion.properties");
	}

	public void inicializar(String path)
	{
		try
		{
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream ("C:/Users/Sergio/Documents/Sistrans/Project Sistrans/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PROJECT_Sistrans3/conexion.properties");

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
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexi脙鲁n.");
		}
	}

	/**
	 * M茅todo que retorna la informaci贸n b谩sica de todos los clientes que se 
	 * encuentran dentro de la base de datos.
	 * @param ordenarPor
	 * @param agruparPor
	 * @param descoasc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ArrayList<ClienteValues> darClientes(String ordenarPor, String agruparPor, String descoasc) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList clientes = new ArrayList();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM USUARIOS u JOIN CLIENTES c "
					+ "ON (u.correo = c.correo)");

			while(rs.next())
			{

				String correo = rs.getString("CORREO");
				String tipo = rs.getString("TIPO_PERSONA");
				String nombre = rs.getString("NOMBRE");
				ClienteValues cuentaActual = new ClienteValues(correo, tipo.charAt(0), nombre);
				clientes.add(cuentaActual);
				System.out.println("---------Usuarios-------");			
			}

		}

		finally
		{
			return clientes;
		}

	}

	/**
	 * M茅todo que retorna un usuarioValues a partir del correo con el que 
	 * inicia sesi贸n en el sistema.
	 * @param correo
	 * @return
	 * @throws Exception
	 */
	public UsuarioValues darUsuarioInicioSesion (String correo) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<CuentaValues>  cuentas = new ArrayList<CuentaValues> ();
		UsuarioValues usuarioRetorno = null;

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM USUARIOS WHERE CORREO = '" + correo + "'" );

			String ciudad = "";
			String codPostal = "";
			String contrase馻 = "";
			String departamento = "";
			String direccion = "";
			Date fecha_registro = null;
			String login = "";
			String nacionalidad = "";
			String nombre = "";
			String numeroID = "";
			String telefono = "";
			String tipo_id = "";
			String tipo_usuario = "";

			if(!rs.isBeforeFirst())
			{
				return null;
			}
			

			
			while(rs.next())
			{
				ciudad = rs.getString("CIUDAD");


				codPostal = rs.getString("COD_POSTAL");
				contrase馻 = rs.getString("CONTRASE袮");
				departamento = rs.getString("DEPARTAMENTO");
				direccion = rs.getString("DIRECCION");
				fecha_registro = rs.getTimestamp("FECHA_REGISTRO");
				login = rs.getString("LOGIN");
				nacionalidad = rs.getString("NACIONALIDAD");
				nombre = rs.getString("NOMBRE");
				numeroID = rs.getString("NUMERO_ID");
				telefono = rs.getString("TELEFONO");
				tipo_id = rs.getString("TIPO_ID");
				tipo_usuario = rs.getString("TIPO_USUARIO");
			}

			UsuarioValues usuarioActual = new UsuarioValues(correo, login, 
					contrase馻, numeroID, tipo_id, nombre, nacionalidad, 
					direccion, telefono, ciudad, departamento, codPostal,
					fecha_registro, tipo_usuario);

			return usuarioActual; 
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;

	}

	public int darOficinaEmpleado (String correo) throws Exception
	{
		PreparedStatement prepStat = null;

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT OFICINA FROM EMPLEADOS WHERE CORREO = '" + correo + "'");
			int oficina = 0;
			
			while(rs.next())
			{
				oficina = rs.getInt("OFICINA");
			}
			return oficina; 
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return 0;

	}

	/**
	 * Este m茅todo retorna un arrayList que contiene 5 ArrayList.
	 * El primer arrayList contiene la informaci贸n del cliente en forma de un objeto clienteValues.
	 * El segundo arrayList contiene la informaci贸n de las cuentas del cliente en forma
	 * de cuentaValues.
	 * El tercero contiene la informaci贸n de las oficinas donde tiene una cuenta el usuario
	 * en forma de oficinaValue.
	 * El cuarto contiene informaci贸n de los prestamos del cliente en forma de
	 * prestamoValue.
	 * Y el quinto contiene la informaci贸n de las operaciones del cliente en forma
	 * de transaccionValue.
	 * El cliente se busca a partir de su correo el cual entra por par谩metro.
	 * La informaci贸n que es retornada no est谩 sujetada a ning煤n tipo de filtro
	 * @return
	 * @throws Exception
	 */
	public ArrayList darClienteEspecifico(String pCorreo, String ordenarPor, String descoasc, String agruparPor) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList clientes = new ArrayList();
		ArrayList cuentas = new ArrayList();
		ArrayList oficinas = new ArrayList();
		ArrayList prestamos = new ArrayList();
		ArrayList operaciones = new ArrayList();
		ArrayList informacionCliente = new ArrayList();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT CORREO, TIPO_PERSONA, NOMBRE FROM USUARIOS NATURAL JOIN CLIENTES "
					+ " WHERE CORREO = " + "'" + pCorreo + "'");

			System.out.println("LLego antes del while 1");

			while(rs.next())
			{

				String correo = rs.getString("CORREO");
				String tipo = rs.getString("TIPO_PERSONA");
				String nombre = rs.getString("NOMBRE");
				ClienteValues cuentaActual = new ClienteValues(correo, tipo.charAt(0), nombre);
				clientes.add(cuentaActual);
				System.out.println("---------Usuarios-------");			
			}

			System.out.println("Paso despues del 1mer while");
			ClienteValues temp = (ClienteValues) clientes.get(0);
			System.out.println(temp.getCorreo());


			rs = s.executeQuery("SELECT * FROM CUENTAS WHERE CORREO = " +  "'" + temp.getCorreo() + "'");



			while(rs.next())
			{
				String idCuenta = rs.getString("ID_CUENTA");
				int idCuentax = Integer.parseInt(idCuenta);
				String correo = rs.getString("CORREO");
				String tipo = rs.getString("TIPO_CUENTA");
				String idOficina = rs.getString("OFICINA");
				int oficina = Integer.parseInt(idOficina);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				dateFormat.format(date);
				String estado = rs.getString("ESTADO");
				String monto = rs.getString("SALDO");
				int saldo = Integer.parseInt(monto);
				CuentaValues cuentaActual = new CuentaValues(idCuentax, correo, tipo, oficina, date, saldo, estado);
				cuentas.add(cuentaActual);

			}
			System.out.println(cuentas.size());

			rs = s.executeQuery("SELECT  DIRECCION, GERENTE, c.ID_OFICINA, NOMBRE, c.TELEFONO FROM "
					+ " (SELECT * FROM CUENTAS WHERE CORREO = " +  "'" + temp.getCorreo() + "')"  
					+ "b JOIN OFICINAS c ON (b.oficina = c.id_oficina)");




			while(rs.next())
			{
				String direccion = rs.getString("DIRECCION");
				String gerente = rs.getString("GERENTE");
				int id_oficina = rs.getInt("ID_OFICINA");
				String nombre = rs.getString("NOMBRE");
				String telefono = rs.getString("TELEFONO");
				OficinaValues oficinaActual = new OficinaValues(id_oficina, nombre, direccion, telefono, gerente);
				oficinas.add(oficinaActual);
			}

			System.out.println(oficinas.size());


			rs = s.executeQuery("SELECT * FROM  (SELECT CORREO FROM CUENTAS WHERE CORREO = " 
					+ "'" + temp.getCorreo() + "' )"
					+ " a JOIN PRESTAMOS p ON (a.correo = p.correo_cliente)");



			while(rs.next())
			{
				String correoCliente = rs.getString("CORREO");
				int cuota = rs.getInt("CUOTA");
				int cuotasEfectivas = rs.getInt("CUOTAS_EFECTIVAS");
				int diaPago = rs.getInt("DIA_PAGO_MENSUAL");
				String estado = rs.getString("ESTADO");
				int id = rs.getInt("ID");
				Date fechaPrestamo = rs.getTimestamp("FECHA_PRESTAMO");
				float interes = rs.getFloat("INTERES");
				int montoPrestado = rs.getInt("MONTO_PRESTADO");
				int numeroCuotas = rs.getInt("NUM_CUOTAS");
				int saldoPendiente = rs.getInt("SALDO_PENDIENTE");
				String tipo = rs.getString("TIPO");

				PrestamoValues prestamoActual = new PrestamoValues(id, correoCliente, 
						montoPrestado, tipo, fechaPrestamo, 
						diaPago, cuota, saldoPendiente, estado, 
						numeroCuotas, interes, cuotasEfectivas);
				prestamos.add(prestamoActual);
			}

			System.out.println(prestamos.size());


			rs = s.executeQuery("SELECT  * FROM  (SELECT CORREO AS CORREO_USUARIO FROM CUENTAS WHERE CORREO = " 
					+ "'" + temp.getCorreo() + "'  ) NATURAL JOIN TRANSACCIONES" );



			while(rs.next())
			{
				String correo = rs.getString("CORREO_USUARIO");
				int idTransaccion = rs.getInt("ID_TRANSACCION");
				String tipoTransaccion = rs.getString("TIPO");
				Date fechaTransaccion = rs.getTimestamp("FECHA_TRANSACCION");
				int idPuntoAtencion = rs.getInt("ID_PUNTO_ATENCION");
				TransaccionValues transaccionActual = new 
						TransaccionValues(idTransaccion, 
								correo, tipoTransaccion, 
								fechaTransaccion, idPuntoAtencion);

				operaciones.add(transaccionActual);
			}

			System.out.println(operaciones.size());
			informacionCliente.add(clientes);
			informacionCliente.add(cuentas);
			informacionCliente.add(oficinas);
			informacionCliente.add(prestamos);
			informacionCliente.add(operaciones);


		}

		finally
		{
			return informacionCliente;			
		}

	}
}
