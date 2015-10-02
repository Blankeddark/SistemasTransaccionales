package Fachada;

import DAOS.CerrarCuentaDAO;
import DAOS.CerrarPrestamoDAO;
import DAOS.ConsultarClienteDAO;
import DAOS.ConsultarCuentasDAO;
import DAOS.PoblarTablasRB1DAO;
import DAOS.RegistrarOperacionCuentaDAO;
import DAOS.RegistrarOperacionSobrePrestamoDAO;


/**
 * Clase BancAndes, que representa la fachada de comunicaci�n entre
 * la interfaz y la conexi�n con la base de datos. Atiende todas
 * las solicitudes.
 */
public class BancAndes 
{
	private CerrarCuentaDAO cerrarCuentaDao;
	private CerrarPrestamoDAO cerrarPrestamoDao;
	private ConsultarClienteDAO consultarClienteDAO;
	private ConsultarCuentasDAO consultarCuentasDAO;
	private PoblarTablasRB1DAO poblarDAO;
	private RegistrarOperacionCuentaDAO registrarOperacionCuentaDAO;
	private RegistrarOperacionSobrePrestamoDAO registrarOperacionPrestamoDAO;

	// -----------------------------------------------------------------
	// Singleton
	// -----------------------------------------------------------------


	/**
	 * Instancia �nica de la clase
	 */
	private static BancAndes instancia;

	/**
	 * Devuelve la instancia �nica de la clase
	 * @return Instancia �nica de la clase
	 */
	public static BancAndes darInstancia( )
	{
		if( instancia == null )
		{
			instancia = new BancAndes( );
		}

		return instancia;
	}

	/**
	 * contructor de la clase. Inicializa el atributo dao.
	 */
	private BancAndes()
	{
		cerrarCuentaDao = new CerrarCuentaDAO();
		cerrarPrestamoDao = new CerrarPrestamoDAO();
		consultarClienteDAO = new ConsultarClienteDAO();
		consultarCuentasDAO = new ConsultarCuentasDAO();
		poblarDAO = new PoblarTablasRB1DAO();
		registrarOperacionCuentaDAO = new RegistrarOperacionCuentaDAO();
		registrarOperacionPrestamoDAO = new RegistrarOperacionSobrePrestamoDAO();
	}

	/**
	 * inicializa el dao, d�ndole la ruta en donde debe encontrar
	 * el archivo properties.
	 * @param ruta ruta donde se encuentra el archivo properties
	 */
	public void inicializarRuta(String ruta)
	{
		cerrarCuentaDao.inicializar(ruta);
		cerrarPrestamoDao.inicializar(ruta);
		consultarClienteDAO.inicializar(ruta);
		consultarCuentasDAO.inicializar(ruta);
		poblarDAO.inicializar(ruta);
		registrarOperacionCuentaDAO.inicializar(ruta);
		registrarOperacionPrestamoDAO.inicializar(ruta);
	}


	// ---------------------------------------------------
	// M�todos asociados a los casos de uso: Consulta
	// ---------------------------------------------------

	// ---------------------------------------------------
	// M�todos asociados a los casos de uso: Registrar
	// ---------------------------------------------------
	/**
	 * M�todo que se encarga de eliminar una cuenta cualquiera dentro de la base
	 * de datos cuyo ID entra por par�metro.
	 * @param idCuenta
	 */
	public void cerrarCuenta(int idCuenta)
	{
		try 
		{
			cerrarCuentaDao.registrarCerrarCuentaExistente(idCuenta);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * M�todo con el cual se resuelve el RF5. Lo que se hace es que este m�todo
	 * cierra una cuenta que se encuentra en una oficina en espec�fico. Por tanto
	 * se debe sacar el id de la oficina en la cual se encuentra el gerente de oficina actual
	 * y si la cuenta que se va a eliminar fue creada en su oficina entonces la cuenta
	 * ser� cerrada con �xito.
	 * @param idCuenta
	 * @param oficina
	 */
	public void cerrarCuentaEnOficinaEspecifica(int idCuenta, int oficina)
	{
		try 
		{
			cerrarCuentaDao.registrarCerrarCuentaExistenteEnOficinaEspecifica(idCuenta, oficina);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * M�todo que cierra un prestamo una vez el saldo pendiente de este llega a cero, es decir
	 * cuando el prestamo ha sido pagado. (Si necesitas un m�todo auxiliar para ayudarte en este
	 * requerimiento puedes usar el m�todo debajo de este)
	 * @param idEliminar
	 */
	public void cerrarPrestamoRF9(int idEliminar, int oficinaGerenteActual)
	{
		try 
		{
			cerrarPrestamoDao.cerrarPrestamoExistentePagado(idEliminar, oficinaGerenteActual);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * M�todo que retorna un arrayList con objetos tipo prestamoValues
	 * que representan todos los prestamos que han sido pagados y que pueden
	 * ser cerrados dentro de la base de datos.
	 */
	public void darTodosLosPrestamosPagados()
	{
		try 
		{
			cerrarPrestamoDao.darTodosLosPrestamosPagados();
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Este m�todo se encarga de resolver el RF6
	 * @param tipo
	 * @param correo_cliente
	 * @param id_cuenta
	 * @param valor
	 * @param puesto_atencion
	 * @param cajero
	 */
	public void registrarOperacionSobreCuenta(String tipo, String correo_cliente, int id_cuenta,
			int valor, int puesto_atencion, String cajero)
	{
		try 
		{
			registrarOperacionCuentaDAO.registrarOperacionSobreCuentaExistente(tipo, correo_cliente, 
					id_cuenta, valor, puesto_atencion, cajero);
		} 

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Dependiendo de la operaci�n que se vaya a realizar son los campos a utilizar.
	 * @param tipo 
	 * @param correo_cliente 
	 * @param id_cuenta
	 * @param valor
	 * @param puesto_atencion
	 * @param cajero
	 * @param tipo_prestamo
	 * @param id_solicitud
	 * @param numCuotas
	 * @param idPrestamo
	 */
	public void registrarCuentaSobrePrestamoRF8(String tipo, String correo_cliente, int id_cuenta,
			int valor, int puesto_atencion, String cajero, String tipo_prestamo, int id_solicitud,
			int numCuotas, int idPrestamo)
	{
		try {
			registrarOperacionPrestamoDAO.registrarOperacionSobrePrestamoExistente(tipo, 
					correo_cliente, id_cuenta, valor, puesto_atencion, cajero, tipo_prestamo, 
					id_solicitud, numCuotas, idPrestamo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}