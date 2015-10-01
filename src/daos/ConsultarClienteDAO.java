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
	 * Método que retorna la información básica de todos los clientes que se 
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
	 * Este método retorna un arrayList que contiene 5 ArrayList.
	 * El primer arrayList contiene la información del cliente en forma de un objeto clienteValues.
	 * El segundo arrayList contiene la información de las cuentas del cliente en forma
	 * de cuentaValues.
	 * El tercero contiene la información de las oficinas donde tiene una cuenta el usuario
	 * en forma de oficinaValue.
	 * El cuarto contiene información de los prestamos del cliente en forma de
	 * prestamoValue.
	 * Y el quinto contiene la información de las operaciones del cliente en forma
	 * de transaccionValue.
	 * El cliente se busca a partir de su correo el cual entra por parámetro.
	 * La información que es retornada no está sujetada a ningún tipo de filtro
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ClienteValues> darClienteEspecifico(String pCorreo, String ordenarPor, String descoasc, String agruparPor) throws Exception
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
			ResultSet rs = s.executeQuery("SELECT * FROM (SELECT * FROM USUARIOS u JOIN CLIENTES c "
					+ "ON (u.correo = c.correo) ) WHERE CORREO = " + pCorreo);

			while(rs.next())
			{

				String correo = rs.getString("CORREO");
				String tipo = rs.getString("TIPO_PERSONA");
				String nombre = rs.getString("NOMBRE");
				ClienteValues cuentaActual = new ClienteValues(correo, tipo.charAt(0), nombre);
				clientes.add(cuentaActual);
				System.out.println("---------Usuarios-------");			
			}

			ClienteValues temp = (ClienteValues) clientes.get(0);
			if(ordenarPor.equalsIgnoreCase("CUENTA"))
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() 
						+ "ORDER BY " + ordenarPor);
			}

			else if(!agruparPor.equalsIgnoreCase("CUENTA"))
			{
				rs = s.executeQuery("SELECT COUNT(*) FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() );
			}
			
			else
			{
				rs = s.executeQuery("SELECT * FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() );
			}


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
				String estado = rs.getString("ESTADO");
				String monto = rs.getString("SALDO");
				int saldo = Integer.parseInt(monto);
				CuentaValues cuentaActual = new CuentaValues(idCuentax, correo, tipo, oficina, date, saldo, estado);
				cuentas.add(cuentaActual);

			}

			if(ordenarPor.equalsIgnoreCase("OFICINA"))
			{
				rs = s.executeQuery("  SELECT  * FROM ( (SELECT * FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() + ")"
						+ "b JOIN OFICINAS c ON (b.oficina = c.id_oficina) ) ORDER BY "
						+ ordenarPor);
			}

			else if(!agruparPor.equalsIgnoreCase("OFICINA"))
			{
				rs = s.executeQuery("  SELECT  COUNT(*) FROM ( (SELECT * FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() + ")"
						+ "b JOIN OFICINAS c ON (b.oficina = c.id_oficina) )" );
			}
			
			else
			{
				rs = s.executeQuery("  SELECT  * FROM ( (SELECT * FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() + ")"
						+ "b JOIN OFICINAS c ON (b.oficina = c.id_oficina) )" );
			}



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

			if(ordenarPor.equalsIgnoreCase("PRESTAMO"))
			{
				rs = s.executeQuery(" SELECT * FROM ( (SELECT CORREO FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() 
						+ ") a JOIN PRESTAMOS p ON (a.correo = p.correo_cliente) ) ORDER BY "
						+ ordenarPor);
			}

			else if(!agruparPor.equalsIgnoreCase("PRESTAMO"))
			{
				rs = s.executeQuery(" SELECT COUNT( *) FROM ( (SELECT CORREO FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() 
						+ ") a JOIN PRESTAMOS p ON (a.correo = p.correo_cliente) )");
			}
			
			else
			{
				rs = s.executeQuery(" SELECT * FROM ( (SELECT CORREO FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() 
						+ ") a JOIN PRESTAMOS p ON (a.correo = p.correo_cliente) )");
			}


			while(rs.next())
			{
				String correoCliente = rs.getString("CORREO");
				int cuota = rs.getInt("CUOTA");
				int cuotasEfectivas = rs.getInt("CUOTAS_EFECTIVAS");
				int diaPago = rs.getInt("DIA_PAGO_MENSUAL");
				String estado = rs.getString("ESTADO");
				int id = rs.getInt("ID");
				Date fechaPrestamo = rs.getDate("FECHA_PRESTAMO");
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
            
			if(ordenarPor.equalsIgnoreCase("OPERACION"))
			{
				rs = s.executeQuery("  SELECT  * FROM ( (SELECT CORREO FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() + ")"
						+ "b JOIN TRANSACCIONES t ON (b.correo = t.correo_usuario) ) ORDER BY"
						+ ordenarPor );
			}
			
			else if(!agruparPor.equalsIgnoreCase("OPERACION"))
			{
				rs = s.executeQuery("  SELECT  COUNT (*) FROM ( (SELECT CORREO FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() + ")"
						+ "b JOIN TRANSACCIONES t ON (b.correo = t.correo_usuario) )" );
			}
			
			else
			{
				rs = s.executeQuery("  SELECT  * FROM ( (SELECT CORREO FROM CUENTAS WHERE CORREO = " +  temp.getCorreo() + ")"
						+ "b JOIN TRANSACCIONES t ON (b.correo = t.correo_usuario) )" );
			}
			

			while(rs.next())
			{
				String correo = rs.getString("CORREO_USUARIO");
				int idTransaccion = rs.getInt("ID_TRANSACCION");
				String tipoTransaccion = rs.getString("TIPO");
				Date fechaTransaccion = rs.getDate("FECHA_TRANSACCION");
				int idPuntoAtencion = rs.getInt("ID_PUNTO_ATENCION");
				TransaccionValues transaccionActual = new 
						TransaccionValues(idTransaccion, 
								correo, tipoTransaccion, 
								fechaTransaccion, idPuntoAtencion);

				operaciones.add(transaccionActual);
			}

			informacionCliente.add(clientes);
			informacionCliente.add(cuentas);
			informacionCliente.addAll(oficinas);
			informacionCliente.add(prestamos);
			informacionCliente.addAll(operaciones);


		}

		finally
		{
			return informacionCliente;			
		}

	}
}
