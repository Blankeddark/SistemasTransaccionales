package DAOS;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Random;

public class ThreadAgregarCuentasDAO extends Thread {

	int numeroCuentas;
	Connection con;
	AgregarCuentasDAO agregarCuentas;

	public ThreadAgregarCuentasDAO(int cuentas, Connection con)
	{
		numeroCuentas = cuentas;
		agregarCuentas = new AgregarCuentasDAO();
		this.con = con;
	}

	public void run()
	{

		Random random = new Random();

		for (int i = 0; i < numeroCuentas; i++)
		{
			try
			{
				agregarCuentas.agregarCuentaRandomCompartirConnection(random, con);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				numeroCuentas++;
				continue;
			}
		}
	}

}
