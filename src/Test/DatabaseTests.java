package Test;

import java.util.ArrayList;

import DAOS.PoblarTablasRB1DAO;
import Fachada.BancAndes;
import JSonParser.Principal;
import junit.framework.TestCase;


public class DatabaseTests extends TestCase
{
	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------
	TestDAO daoPrueba;

	// -----------------------------------------------------------------
	// Metodos
	// -----------------------------------------------------------------
	/**
	 * Escenario que se encarga de poblar las tablas con tuplas definidas
	 * dentro del escenario con el fin de desarrollar los casos de prueba.
	 */
	public void setupScenario1()
	{
		//Creamos una nueva instancia del TestDAO
		daoPrueba = new TestDAO();

		//Luego hacemos todas las inserciones necesarias para poder hacer todas las pruebas
		try 
		{
			daoPrueba.insertarUsuario("Cliente", "ac.zuleta100@uniandes.edu.co", 
					"blankeddark", "12345678", "1234", "CC", "Andres Zuleta", 
					"Colombiano", "Calle 178", "4313326", "Bogota", "Cundinamarca", "2423");
			System.out.println("Inserto el usuario");
			daoPrueba.insertarCliente(0, "ac.zuleta100@uniandes.edu.co", "N");
			System.out.println("inseto el cliente");
			daoPrueba.insertarEmpleado(0, "ac.zuleta100@uniandes.edu.co", "GO", 0);
			System.out.println("Inserto el empleado");
			daoPrueba.insertarOficina(708, "OficinaOP", "Calle 134 #67", 
					"4313645", "ac.zuleta100@uniandes.edu.co", "Bogota", "Cundinamarca");
			System.out.println("Inserto la oficina");
			daoPrueba.insertarEmpleado(1000, "ac.zuleta100@uniandes.edu.co", "GO", 708);
			System.out.println("actualizo el empleado");
			daoPrueba.insertarCuenta(101, "ac.zuleta100@uniandes.edu.co", 
					"Ahorros", 708, 1400000, "Activa");
			System.out.println("Inserto la cuenta");
			daoPrueba.insertarTipoPrestamo(0, "Prestamo estudiantil");

			daoPrueba.insertarPrestamo(404, "ac.zuleta100@uniandes.edu.co", 1000000, 
					"Prestamo estudiantil", 14, 100000, 1000000, "Abierto", 10, 
					(float) 0.0, 0);

			daoPrueba.insertarPuntosAtencion(550, "Fisico", 708, 
					"ac.zuleta100@uniandes.edu.co", "Calle 45 #67", "Bogota", "Cundinamarca");

			daoPrueba.insertarTransacciones(303, "ac.zuleta100@uniandes.edu.co", 
					"AP", 550);

			daoPrueba.insertarAperturaCuenta(0, 303, "ac.zuleta100@uniandes.edu.co", 
					"Ahorros");

			daoPrueba.insertarCierreCuenta(0, 303, 101);

			daoPrueba.insertarConsignacion(0, 303, 101, 101, 500000);

			daoPrueba.insertarRetiro(0, 303, 101, 100000);

			daoPrueba.insertarSolicitudPrestamo(0, 303, 500000, "Prestamo estudiantil", "Aprobada");

			daoPrueba.insertarAprobacionesPrestamo(0, 303, 303);

			daoPrueba.insertarRechazoPrestamo(0, 303, 303);

			daoPrueba.insertarCierePrestamo(0, 303, 404);

			daoPrueba.insertarPagosPrestamo(0, 303, 404, 40000);

		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * Escenario que se encarga de borrar todas las tuplas insertadas en el
	 * escenario 1.
	 */
	public void setupScenario2()
	{   
		daoPrueba = new TestDAO();

		ArrayList borrar = new ArrayList();
		borrar.add("PAGOS_PRESTAMO : ID_PAGO = 303");
		borrar.add("CIERRES_PRESTAMOS : ID_CIERRE = 303");
		borrar.add("RECHAZOS_PRESTAMO : ID = 303");
		borrar.add("APROBACIONES_PRESTAMO : ID = 303");
		borrar.add("SOLICITUDES_PRESTAMO : ID  = 303");
		borrar.add("RETIROS : ID  = 303");
		borrar.add("CONSIGNACIONES : ID = 303");
		borrar.add("CIERRE_CUENTAS : ID = 303");
		borrar.add("APERTURA_CUENTAS : ID = 303");
		borrar.add("TRANSACCIONES : ID_TRANSACCION = 303");
		borrar.add("PUNTOS_ATENCION : CORREO_CAJERO = 'ac.zuleta100@uniandes.edu.co' ");
		borrar.add("PRESTAMOS : CORREO_CLIENTE = 'ac.zuleta100@uniandes.edu.co'");
		borrar.add("TIPOS_PRESTAMOS : TIPO_PRESTAMO = 'Prestamo estudiantil'");
		borrar.add("CUENTAS : CORREO = 'ac.zuleta100@uniandes.edu.co' ");
		borrar.add("OFICINAS : GERENTE = 'ac.zuleta100@uniandes.edu.co'" );
		borrar.add("EMPLEADOS : CORREO = 'ac.zuleta100@uniandes.edu.co'");
		borrar.add("CLIENTES : CORREO = 'ac.zuleta100@uniandes.edu.co' ");
		borrar.add("USUARIOS : CORREO = 'ac.zuleta100@uniandes.edu.co' ");

		for (int i = 0; i < borrar.size(); i++) 
		{
			String cadenaCompleta = (String) borrar.get(i);
			String info[] = cadenaCompleta.split(":");
			String tabla = info[0];
			String condicion = info[1];

			if(tabla.equals("OFICINAS "))
			{
				try {
					daoPrueba.ponerNullEmpleados();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}

			try 
			{

				daoPrueba.borrarDeTablas(tabla, condicion);
			}

			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public int setupScenario3()
	{   
		daoPrueba = new TestDAO();
		int errores = 0;

		ArrayList borrar = new ArrayList();
		borrar.add("PAGOS_PRESTAMO : ID_PAGO = 303");
		borrar.add("CIERRES_PRESTAMOS : ID_CIERRE = 303");
		borrar.add("RECHAZOS_PRESTAMO : ID = 303");
		borrar.add("APROBACIONES_PRESTAMO : ID = 303");
		borrar.add("SOLICITUDES_PRESTAMO : ID  = 303");
		borrar.add("RETIROS : ID  = 303");
		borrar.add("CONSIGNACIONES : ID = 303");
		borrar.add("CIERRE_CUENTAS : ID = 303");
		borrar.add("APERTURA_CUENTAS : ID = 303");
		borrar.add("TRANSACCIONES : ID_TRANSACCION = 303");
		borrar.add("PUNTOS_ATENCION : CORREO_CAJERO = 'ac.zuleta100@uniandes.edu.co' ");
		borrar.add("PRESTAMOS : CORREO_CLIENTE = 'ac.zuleta100@uniandes.edu.co'");
		borrar.add("TIPOS_PRESTAMOS : TIPO_PRESTAMO = 'Prestamo estudiantil'");
		borrar.add("CUENTAS : CORREO = 'ac.zuleta100@uniandes.edu.co' ");
		borrar.add("OFICINAS : GERENTE = 'ac.zuleta100@uniandes.edu.co'" );
		borrar.add("EMPLEADOS : CORREO = 'ac.zuleta100@uniandes.edu.co'");
		borrar.add("CLIENTES : CORREO = 'ac.zuleta100@uniandes.edu.co' ");
		borrar.add("USUARIOS : CORREO = 'ac.zuleta100@uniandes.edu.co' ");

		for (int i = 0; i < borrar.size(); i++) 
		{
			String cadenaCompleta = (String) borrar.get(i);
			String info[] = cadenaCompleta.split(":");
			String tabla = info[0];
			String condicion = info[1];

			try 
			{

				daoPrueba.borrarDeTablas(tabla, condicion);
			}

			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				String mensaje = e.getMessage();
				if(!mensaje.equals("")) errores++;
				e.printStackTrace();
			}

		}

		return errores;
	}


	/**
	 * Método que se encarga de probar que la restricción de integridad
	 * de la llave primaria se cumple para todas las tablas que posean una llave
	 * primaria. En este caso para la realización de dicha prueba primero se pueblan
	 * las tablas con los datos definidos en el setupScenario1(), luego se intentan
	 * agregar tuplas con el mismo valor de PK en todas las tablas que posean PK,
	 * si todas las inserciones lanzan excepción entonces la prueba está bien.
	 * Finalmente se borran las tablas insertadas.
	 */
	public void testProbarPKs()
	{
		setupScenario1();
		int errores = 0;
		ArrayList<String> excepciones = new ArrayList<String>();

		try 
		{
			daoPrueba.insertarUsuario("GG", "ac.zuleta100@uniandes.edu.co", 
					"masterBlaster", "contraseña", "1928475", "TI", "Juan Perez", 
					"Boliviano", "Calle 78", "8975752", "Barranquilla", "Atlantico", "8575");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarCuenta(101, "ac.zuleta100@uniandes.edu.co", "Corriente", 
					708, 1456000, "Activa");
		}

		catch(Exception a)
		{
			String mensaje = a.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			a.printStackTrace();
		}

		try
		{
			daoPrueba.insertarOficina(708, "Mi Oficina", "Calle del muerto", "6666", 
					"ac.zuleta100@uniandes.edu.co", "Cali", "Ni Idea");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarTipoPrestamo(0, "Prestamo estudiantil");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarPrestamo(404, "ac.zuleta100@uniandes.edu.co", 1000000, 
					"Prestamo estudiantil", 14, 100000, 1000000, "Abierto", 10, 
					(float) 0.0, 0);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarPuntosAtencion(550, "Fisico", 708, 
					"ac.zuleta100@uniandes.edu.co", "Calle 47 #87", "Bogota", "Cundinamarca");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarTransacciones(303, "ac.zuleta100@uniandes.edu.co", 
					"AP", 550);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		setupScenario2();

		for (int i = 0; i < excepciones.size(); i++) 
		{
			System.out.println(excepciones.get(i));	
		}

		assertTrue(errores == 7);
	}

	/**
	 * Método que se encarga de probar la restricción de integridad correspondiente
	 * a la llave fóranea (si la posee) para todas las tablas de la base de datos.
	 * Para realizar esto primero se poblan las tablas utilizando el setupScenario1()
	 * Luego agregamos valores a las tablas que posean FK que hagan referencia a valores inexistentes
	 * dentro de la base de datos de forma de que tiren excepcion. Si al final la cantidad
	 * de excepciones lanzadas es igual al número de tablas evaluadas entonces la prueba es un
	 * éxito. 
	 */
	public void testProbarFKs()
	{
		setupScenario1();
		int errores = 0;
		ArrayList<String> excepciones = new ArrayList<String>();

		try
		{
			daoPrueba.insertarCliente(0, "correoInexistente@hotmail", "N");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarCuenta(102, "correoInexistenteq@hotmail.com", "Corriente", 
					708, 1456000, "Activa");
		}

		catch(Exception a)
		{
			String mensaje = a.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			a.printStackTrace();
		}

		try
		{
			daoPrueba.insertarEmpleado(0, "correoInexistente@hotmail.com", "GO", 708);
		}

		catch(Exception a)
		{
			String mensaje = a.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			a.printStackTrace();
		}

		try
		{
			daoPrueba.insertarOficina(709, "Mi Oficina", "Calle del muerto", "6666", 
					"gerenteInexistente@hotmail.com", "Cali", "Ni Idea");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarPrestamo(403, "clienteInexistente@hotmail", 1000000, 
					"Prestamo estudiantil", 14, 100000, 1000000, "Abierto", 10, 
					(float) 0.0, 0);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarPuntosAtencion(551, "Fisico", 708, 
					"cajeroInexistente@hotmail.com", "Calle 47 #87", "Bogota", "Cundinamarca");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarTransacciones(304, "correoInexistente@hotmail.com", 
					"AP", 550);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarAperturaCuenta(0, 3033, "correoInexistente@hotmail.com", 
					"Ahorros");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarCierreCuenta(0, 3033, 1021);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarConsignacion(0, 3032, 1011, 1011, 500000);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarRetiro(0, 3032, 1021, 100000);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarSolicitudPrestamo(0, 303, 500000, "Prestamo inexistente", "Aprobada");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarAprobacionesPrestamo(0, 3023, 3023);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarRechazoPrestamo(0, 3203, 3203);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarCierePrestamo(0, 3023, 4304);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarPagosPrestamo(0, 3403, 4504, 40000);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		setupScenario2();
		assertTrue(errores == 16);
	}

	/**
	 * Método que se encarga de probar las restricciones de integridad de datos
	 * referentes a las llaves foraneas. En este caso se intenta demostrar que
	 * un atributo dependiente que referencia a un atributo maestro puede ser eliminado
	 * sin problemas de la tabla, pero que para una tabla maestra esta no puede ser eliminada
	 * hasta que todos sus tablas dependientes hayan sido eliminadas.
	 */
	public void testEliminarFKs()
	{
		setupScenario1();
		int errores = setupScenario3();
		assertTrue(errores == 3);
	}

	/**
	 * Método que se encarga de probar las restricciones de integridad de datos de las tablas
	 * correspondientes a las CK. En este caso primero se poblan las tablas
	 * con la información definida en setupScenario1(), luego se intentan agregar datos
	 * no validos a las columnas de las tablas que posean CK, a lo que estas tiraran
	 * excepcion, si al final el número de excepciones es igual al número de intentos
	 * de inserción realizados entonces la prueba es un éxito.
	 */
	public void testProbarCKs()
	{
		setupScenario1();
		int errores = 0;
		ArrayList<String> excepciones = new ArrayList<String>();
		
		try 
		{
			daoPrueba.insertarUsuario("GG", "ac.zuleta100@uniandes.edu.co", 
					"masterBlaster", "contraseña", "1928475", "TH", "Juan Perez", 
					"Boliviano", "Calle 78", "8975752", "Barranquilla", "Atlantico", "8575");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarCuenta(102, "ac.zuleta100@uniandes.edu.co", "NiTipo", 
					708, 1456000, "Activa");
		}

		catch(Exception a)
		{
			String mensaje = a.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			a.printStackTrace();
		}


		try
		{
			daoPrueba.insertarPrestamo(405, "ac.zuleta100@uniandes.edu.co", 1000000, 
					"Prestamo estudiantil", 0, 0, 1000000, "Abierto", -56, 
					(float) 0.0, -45);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarPuntosAtencion(551, "Ficticio", 708, 
					"ac.zuleta100@uniandes.edu.co", "Calle 47 #87", "Bogota", "Cundinamarca");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try
		{
			daoPrueba.insertarTransacciones(301, "ac.zuleta100@uniandes.edu.co", 
					"HR", 551);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}
		
		try
		{
			daoPrueba.insertarCliente(0, "ac.zuleta100@uniandes.edu.co", "H");
		}
		
		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}
        
		try
		{
			daoPrueba.insertarEmpleado(0, "ac.zuleta100@uniandes.edu.co", "HC", 708);
		}
		
		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}
		
		try 
		{
			daoPrueba.insertarAperturaCuenta(0, 303, "ac.zuleta100@uniandes.edu.co", 
					"Cuentota");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarConsignacion(0, 303, 101, 101, 0);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarRetiro(0, 303, 101, 0);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarSolicitudPrestamo(0, 303, 0, "Prestamo estudiantil", "Ninguna");
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		try 
		{
			daoPrueba.insertarPagosPrestamo(0, 303, 404, 0);
		}

		catch (Exception e) 
		{
			String mensaje = e.getMessage();
			excepciones.add(mensaje);
			if(!mensaje.equalsIgnoreCase("")) errores++;
			e.printStackTrace();
		}

		setupScenario2();
		assertTrue(errores == 12);

	}
	
	public void testJson()
	{
		PoblarTablasRB1DAO  poblarDAO = new PoblarTablasRB1DAO();
		Principal parser = new Principal();
		ArrayList<String> clientes = parser.darClientes();
		System.out.println("Paso por aquí");
		for (int i = 0; i < clientes.size(); i++) 
		{
			String[] temp = clientes.get(i).split(",");
			String Correo = temp[0];
			String TipoPersona = temp[1];
			String Login = temp[2];
			String Password = temp[3];
			String NumeroId = temp[4];
			String TipoId = temp[5];
			String Nombre = temp[6];
			String Nacionalidad = temp[7];
			String Direccion = temp[8];
			String Telefono = temp[9];
			String Ciudad = temp[10];
			String Departamento = temp[11];
			String CodPostal = temp[12];
			String FechaRegistro = temp[13];
			String TipoUsuario = temp[14];

			try 
			{
				poblarDAO.insertarUsuario(TipoUsuario, Correo, Login, Password, NumeroId, TipoId, Nombre, Nacionalidad, Direccion, Telefono, Ciudad, Departamento, CodPostal);
				poblarDAO.insertarCliente(Correo, TipoPersona);
			}

			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		ArrayList<String> cuentas = parser.darCuentas();
		for (int i = 0; i < cuentas.size(); i++) 
		{   
			String[] arreglo = cuentas.get(i).split(",");
			String Correo = arreglo[0];
			String TipoCuenta = arreglo[1];
			int Oficina	 = Integer.parseInt(arreglo[2]);
			String FechaUltimoMovimiento = arreglo[3];
			int Saldo= Integer.parseInt(arreglo[4]);
			String Estado = arreglo[5];
			try 
			{
				poblarDAO.insertarCuenta(Correo, TipoCuenta, Oficina, Saldo, Estado);
			}

			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try 
		{
			poblarDAO.insertarOficina("HQ", "CALLE 44 # 56 - 22", "343526", 
					"worker1@bancandes.com", "Bogotá", "Cundinamarca");
			poblarDAO.insertarPuntosAtencion("ATM", 1, "", "", "", "");
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> operaciones = parser.darTransacciones();
		for (int i = 0; i < operaciones.size(); i++) 
		{
			String[] arreglo = operaciones.get(i).split(",");
			String CorreoUsuario = arreglo[0];
			String Tipo = arreglo[1];
			//String FechaTransaccion = arreglo[2];
			int IdPuntoAtencion = Integer.parseInt(arreglo[3]);
			try 
			{
				poblarDAO.insertarTransacciones(CorreoUsuario, Tipo, IdPuntoAtencion);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
