package DAOS;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import vos.ConsignacionValues;
import vos.TransaccionValues;
import vos.UsuarioActivoValues;
import vos.UsuarioValues;

public class Iter4DAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;

	public Iter4DAO()
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
	 * Método que se encarga de resolver el RFC9, hace la consulta sobre toda la base de datos
	 * debido a que es una operación realizada por el gereten general.
	 * El método retorna un arrayList de consignaciones values que contiene toda
	 * la información de las consignaciones con un monto superior al que entra por parámetro
	 * y que fueron realizadas por clientes que poseen cierto tipo de oréstamo que también
	 * ingresa por parámetro.
	 * @param monto
	 * @param tipoPrestamo
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ConsignacionValues> darConsignacionesIter4(int monto, String tipoPrestamo) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<ConsignacionValues>  consignaciones = new ArrayList<ConsignacionValues> ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM (SELECT ID_TRANSACCION AS ID  "
					+ "FROM (SELECT CORREO AS CORREO_USUARIO "
					+ "FROM (SELECT CORREO_CLIENTE AS CORREO FROM PRESTAMOS "
					+ "WHERE TIPO = " + tipoPrestamo + ") NATURAL JOIN CLIENTES ) "
					+ "NATURAL JOIN TRANSACCIONES WHERE TIPO = 'C') NATURAL JOIN CONSIGNACIONES"
					+ " WHERE MONTO >= " + monto);

			while(rs.next())
			{		
				int id = rs.getInt("ID");
				int idCuentaOrigen = rs.getInt("ID_CUENTA_ORIGEN");
				int idCuentaDestino = rs.getInt("ID_CUENTA_DESTINO");
				int monto2 = rs.getInt("MONTO");
				ConsignacionValues consginacionActual = new ConsignacionValues(id, idCuentaOrigen, idCuentaDestino, monto2);
				consignaciones.add(consginacionActual);
			}


		}

		finally
		{
			return consignaciones;
		}

	}


	public ArrayList<TransaccionValues> darOperacionesV2(String fechaInicial, String fechaFinal, 
			String tipoOperacion, int monto) throws Exception
			{
		PreparedStatement prepStat = null;
		ArrayList<TransaccionValues>  transacciones = new ArrayList<TransaccionValues> ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();
			String[] inicialArray = fechaInicial.split("/");
			String[] finalArray = fechaFinal.split("/");	
			@SuppressWarnings("deprecation")
			Date inicial = new Date(Integer.parseInt(inicialArray[2]), Integer.parseInt(inicialArray[1]), Integer.parseInt(inicialArray[0]));
			@SuppressWarnings("deprecation")
			Date finalFecha = new Date(Integer.parseInt(finalArray[2]), Integer.parseInt(finalArray[1]), Integer.parseInt(finalArray[0]));
			ResultSet rs = s.executeQuery("SELECT * FROM CLIENTES WHERE ROWNUM = 1");

			if(!tipoOperacion.trim().equals(" "))
			{
				rs = s.executeQuery("SELECT * FROM TRANSACCION WHERE TIPO = " + tipoOperacion 
						+ " AND FECHA_TRANSACCION BETWEEN TO_DATE ('" + inicial + "' , 'yyyy/mm/dd/') "
						+ " AND TO_DATE ('" + finalFecha + "' , 'yyyy/mm/dd/') " );
			}

			else if(monto != 0)
			{
				rs = s.executeQuery("SELECT * FROM TRANSACCION WHERE MONTO  >= " + monto 
						+ " AND FECHA_TRANSACCION BETWEEN TO_DATE ('" + inicial + "' , 'yyyy/mm/dd/') "
						+ " AND TO_DATE ('" + finalFecha + "' , 'yyyy/mm/dd/') " );
			}


			while(rs.next())
			{		
				int id = rs.getInt("ID_TRANSACCION");
				String correoUsuario = rs.getString("CORREO_USUARIO");
				String tipo = rs.getString("TIPO");
				Date fechaTransaccion = rs.getDate("FECHA_TRANSACCION");
				int idPuntoAtencion = rs.getInt("ID_PUNTO_ATENCION");
				TransaccionValues operacionActual = new TransaccionValues(id, correoUsuario, tipo, fechaTransaccion, idPuntoAtencion);
				transacciones.add(operacionActual);
			}


		}

		finally
		{
			return transacciones;
		}

			}

	public ArrayList<TransaccionValues> darOperacionesV3(String fechaInicial, String fechaFinal, 
			String tipoOperacion, int monto) throws Exception
			{
		PreparedStatement prepStat = null;
		ArrayList<TransaccionValues>  transacciones = new ArrayList<TransaccionValues> ();

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();
			String[] inicialArray = fechaInicial.split("/");
			String[] finalArray = fechaFinal.split("/");	
			@SuppressWarnings("deprecation")
			Date inicial = new Date(Integer.parseInt(inicialArray[2]), Integer.parseInt(inicialArray[1]), Integer.parseInt(inicialArray[0]));
			@SuppressWarnings("deprecation")
			Date finalFecha = new Date(Integer.parseInt(finalArray[2]), Integer.parseInt(finalArray[1]), Integer.parseInt(finalArray[0]));
			ResultSet rs = s.executeQuery("SELECT * FROM CLIENTES WHERE ROWNUM = 1");

			if(!tipoOperacion.trim().equals(" "))
			{
				rs = s.executeQuery("SELECT * FROM TRANSACCION WHERE TIPO != " + tipoOperacion 
						+ " AND FECHA_TRANSACCION BETWEEN TO_DATE ('" + inicial + "' , 'yyyy/mm/dd/') "
						+ " AND TO_DATE ('" + finalFecha + "' , 'yyyy/mm/dd/') " );
			}

			else if(monto != 0)
			{
				rs = s.executeQuery("SELECT * FROM TRANSACCION WHERE MONTO  < " + monto 
						+ " AND FECHA_TRANSACCION BETWEEN TO_DATE ('" + inicial + "' , 'yyyy/mm/dd/') "
						+ " AND TO_DATE ('" + finalFecha + "' , 'yyyy/mm/dd/') " );
			}


			while(rs.next())
			{		
				int id = rs.getInt("ID_TRANSACCION");
				String correoUsuario = rs.getString("CORREO_USUARIO");
				String tipo = rs.getString("TIPO");
				Date fechaTransaccion = rs.getDate("FECHA_TRANSACCION");
				int idPuntoAtencion = rs.getInt("ID_PUNTO_ATENCION");
				TransaccionValues operacionActual = new TransaccionValues(id, correoUsuario, tipo, fechaTransaccion, idPuntoAtencion);
				transacciones.add(operacionActual);
			}


		}

		finally
		{
			return transacciones;
		}

			}



	/**
	 * Devuelve un arrayList de arrayList.
	 * El arrayList en la posición 0 es un arrayList que contiene la información
	 * de los clientes.
	 * El arrayList en la posición 1 contiene la información de las operaciones.
	 * @param id1
	 * @param id2
	 * @return
	 * @throws Exception
	 */
	public ArrayList consultarPuntoAtencionIter4(int id1, int id2) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList respuesta  = new ArrayList();
		
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM (SELECT * FROM (SELECT * FROM "
					+ " (SELECT ID AS ID_PUNTO_ATENCION FROM PUNTOS_ATENCION WHERE ID = " + id1 
					+" OR ID =  " + id2 + " ) NATURAL JOIN TRANSACCIONES) NATURAL JOIN (SELECT"
					+ " CORREO AS CORREO_USUARIO, TIPO_PERSONA FROM CLIENTES) ) NATURAL JOIN "
					+ " (SELECT CORREO AS CORREO_USUARIO, NOMBRE, NACIONALIDAD, TELEFONO, CIUDAD"
					+ " FROM USUARIOS)" );

			while(rs.next())
			{		
				String correo = rs.getString("CORREO_USUARIO");
				String nombre = rs.getString("NOMBRE");
				String nacionalidad = rs.getString("NACIONALIDAD");
				String telefono = rs.getString("TELEFONO");
				String ciudad = rs.getString("CIUDAD");
				UsuarioValues usuarioActual = new UsuarioValues(correo, null, null, null, null, nombre, nacionalidad, null, telefono, ciudad, null, null, null, null);
				respuesta.add(usuarioActual);
				
				int id = rs.getInt("ID");
				String tipo = rs.getString("TIPO");
				Date fecha = rs.getDate("FECHA_TRANSACCION");
				int idPunto = rs.getInt("ID_PUNTO_ATENCION");
				TransaccionValues transaccionActual = new TransaccionValues(id, correo, tipo, fecha, idPunto);
				respuesta.add(transaccionActual);
		         		
			}


		}

		finally
		{
			return respuesta;
		}

	}


}
