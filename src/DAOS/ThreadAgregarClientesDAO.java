package DAOS;

import java.sql.Connection;
import java.util.Random;

import _ASTools.UsaRandom;

public class ThreadAgregarClientesDAO extends Thread {
	
	int cantidadClientes;
	AgregarClientesDAO agregarClientes;
	Random random;
	Connection conexion;
	
	public ThreadAgregarClientesDAO(int param, Connection con)
	{
		super();
		cantidadClientes = param;
		agregarClientes = new AgregarClientesDAO();
		random = new Random();
		conexion = con;

	}
	
	public void run()
	{

		for (int i = 0; i < cantidadClientes; i++)
		{
//			if (i % 30 == 0)
//			{
//				TimeUnit.MILLISECONDS.sleep(500);
//			}
			try
			{
			  agregarClientes.agregarUsuarioRandomCompartirConnection(random, conexion);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				cantidadClientes++;
				continue;
			}
		}
	}

}
