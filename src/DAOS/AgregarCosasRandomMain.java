package DAOS;

import java.sql.SQLException;
import java.text.ParseException;

public class AgregarCosasRandomMain {
	
	public static void main(String[] args) throws SQLException, ParseException, InterruptedException
	{
		AgregarClientesDAO agregarClientes = new AgregarClientesDAO();
		AgregarConsignacionesDAO agregarConsignaciones = new AgregarConsignacionesDAO();
		AgregarCuentasDAO agregarCuentas = new AgregarCuentasDAO();
		AgregarOficinasDAO agregarOficinas = new AgregarOficinasDAO();
		AgregarPuntosAtencionDAO agregarPuntosAtencion = new AgregarPuntosAtencionDAO();
		
		//agregarOficinas.agregarOficinasRandom(700);
		//agregarClientes.agregarClientesConThread(972000);
		//agregarCuentas.agregarCuentasRandom(500000);
		//agregarPuntosAtencion.agregarPuntosAtencion(1000);
		agregarConsignaciones.agregarConsignacionesRandom(500000);
	}
	
	

}
