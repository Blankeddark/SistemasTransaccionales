package DAOS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vos.Top10Values;

public class Top10ActividadesDAO 
{
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";

	public Connection conexion;

	private String usuario;

	private String clave;

	private String cadenaConexion;
	
	
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
	
	public ArrayList<Top10Values> darTop10TransaccionesGerenteGeneral() throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<Top10Values>  cuentas = new ArrayList<Top10Values> ();
		int posicion = 1;
		int promedio = 0;

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);

			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("SELECT TIPO, COUNT(TIPO) AS VECES_USADA  "
					+ "FROM TRANSACCIONES GROUP BY TIPO "
					+ "ORDER BY COUNT(TIPO) DESC");

			while(rs.next())
			{
				String tipo = rs.getString("TIPO");
				int usada = rs.getInt("VECES_USADA");
				if(tipo.equalsIgnoreCase("R"))
				{
					promedio = (int) (Math.random() * 150000);
				}

				else if(tipo.equalsIgnoreCase("C"))
				{
					promedio = (int) (Math.random() * 500000);
				}

				Top10Values topActual = new Top10Values(posicion, tipo, usada, promedio);
				cuentas.add(topActual);

				posicion++;
				System.out.println("---------Usuarios-------");		
			}

		}

		finally
		{
			return cuentas;
		}

	}

	public ArrayList<Top10Values> darTop10TransaccionesGerenteOficina(int oficina) throws Exception
	{
		PreparedStatement prepStat = null;
		ArrayList<Top10Values>  cuentas = new ArrayList<Top10Values> ();
		int posicion = 1;
		int promedio = 0;

		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			Statement s = conexion.createStatement();
			System.out.println("A punto de eejecutar la transacción en Top10ActividadesDHAO");
			
			ResultSet rs = s.executeQuery("SELECT TIPO, COUNT(TIPO) AS VECES_USADA "
					+ "FROM (SELECT TIPO, "
					+ "ID_PUNTO_ATENCION AS ID FROM TRANSACCIONES) t JOIN "
					+ "(SELECT ID, OFICINA FROM PUNTOS_ATENCION) p"
					+ " ON (t.id = p.id)  WHERE OFICINA = 1"
					+ " GROUP BY TIPO ORDER BY COUNT(TIPO) DESC");
			System.out.println(rs);
			System.out.println("llegó hasta antes del while en Top10Actividades");
			while(rs.next())
			{
				String tipo = rs.getString("TIPO");
				int usada = rs.getInt("VECES_USADA");
				System.out.println(usada);
				if(tipo.equalsIgnoreCase("R"))
				{
					promedio = (int) (Math.random() * 150000);
				}

				else if(tipo.equalsIgnoreCase("C"))
				{
					promedio = (int) (Math.random() * 500000);
				}

				Top10Values topActual = new Top10Values(posicion, tipo, usada, promedio);
				cuentas.add(topActual);

				posicion++;
				System.out.println("---------" + tipo + "-------");		
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		finally
		{
			return cuentas;
		}

	}
}
