package Fachada;

import java.util.ArrayList;

import vos.ConsignacionValues;
import vos.PrestamoValues;
import vos.Top10Values;
import vos.TransaccionValues;
import vos.UsuarioActivoValues;
import vos.UsuarioValues;
import DAOS.AsociarCuentaDAO;
import DAOS.CerrarCuentaDAO;
import DAOS.CerrarPrestamoDAO;
import DAOS.ConsultaUsuarioMasActivoDAO;
import DAOS.ConsultarClienteDAO;
import DAOS.ConsultarCuentasDAO;
import DAOS.ConsultarOperacionesDAO;
import DAOS.ConsultarPrestamosDAO;
import DAOS.Iter4DAO;
import DAOS.PagarNominaDAO;
import DAOS.PoblarTablasRB1DAO;
import DAOS.RegistrarOperacionCuentaDAO;
import DAOS.RegistrarOperacionSobrePrestamoDAO;
import DAOS.Top10ActividadesDAO;


/**
 * Clase BancAndes, que representa la fachada de comunicación entre
 * la interfaz y la conexión con la base de datos. Atiende todas
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
	private ConsultaUsuarioMasActivoDAO consultarActivoDAO;
	private Top10ActividadesDAO top10;
	private ConsultarOperacionesDAO consultaOperacionesDAO;
	private ConsultarPrestamosDAO consultaPrestamoDAO;
	private AsociarCuentaDAO asociarDAO;
	private Iter4DAO iter4Dao;

	private PagarNominaDAO pagarNominaDAO;

	// -----------------------------------------------------------------
	// Singleton
	// -----------------------------------------------------------------


	/**
	 * Instancia única de la clase
	 */
	private static BancAndes instancia;

	/**
	 * Devuelve la instancia única de la clase
	 * @return Instancia única de la clase
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
	public BancAndes()
	{
		cerrarCuentaDao = new CerrarCuentaDAO();
		cerrarPrestamoDao = new CerrarPrestamoDAO();
		consultarClienteDAO = new ConsultarClienteDAO();
		consultarCuentasDAO = new ConsultarCuentasDAO();
		poblarDAO = new PoblarTablasRB1DAO();
		registrarOperacionCuentaDAO = new RegistrarOperacionCuentaDAO();
		registrarOperacionPrestamoDAO = new RegistrarOperacionSobrePrestamoDAO();
		consultarActivoDAO = new ConsultaUsuarioMasActivoDAO();
		top10 = new Top10ActividadesDAO();
		consultaOperacionesDAO = new ConsultarOperacionesDAO();
		consultaPrestamoDAO = new ConsultarPrestamosDAO();
		asociarDAO = new AsociarCuentaDAO();
		iter4Dao = new Iter4DAO();
		pagarNominaDAO = new PagarNominaDAO();
	}

	/**
	 * inicializa el dao, dándole la ruta en donde debe encontrar
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
		consultarActivoDAO.inicializar(ruta);
		iter4Dao.inicializar(ruta);
	}


	// ---------------------------------------------------
	// Métodos asociados a los casos de uso: Consulta
	// ---------------------------------------------------

	// ---------------------------------------------------
	// Métodos asociados a los casos de uso: Registrar
	// ---------------------------------------------------
	/**
	 * Método que se encarga de eliminar una cuenta cualquiera dentro de la base
	 * de datos cuyo ID entra por parámetro.
	 * @param idCuenta
	 */
	public void cerrarCuenta(String correoUsuario, int idCuenta, int nuevaCuenta)
	{
		try 
		{
			cerrarCuentaDao.registrarCerrarCuentaExistente(correoUsuario, idCuenta, nuevaCuenta);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Método con el cual se resuelve el RF5. Lo que se hace es que este método
	 * cierra una cuenta que se encuentra en una oficina en específico. Por tanto
	 * se debe sacar el id de la oficina en la cual se encuentra el gerente de oficina actual
	 * y si la cuenta que se va a eliminar fue creada en su oficina entonces la cuenta
	 * será cerrada con éxito.
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
	 * Método que cierra un prestamo una vez el saldo pendiente de este llega a cero, es decir
	 * cuando el prestamo ha sido pagado. (Si necesitas un método auxiliar para ayudarte en este
	 * requerimiento puedes usar el método debajo de este)
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
	 * Método que retorna un arrayList con objetos tipo prestamoValues
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
	 * Este método se encarga de resolver el RF6
	 * @param tipo
	 * @param correo_cliente
	 * @param id_cuenta
	 * @param valor
	 * @param puesto_atencion
	 * @param cajero
	 */
	public void registrarOperacionSobreCuenta(String tipo, String correo_cliente, int id_cuenta,
			int valor, int puesto_atencion, String cajero, int cuentaDestino)
	{
		try 
		{
			registrarOperacionCuentaDAO.registrarOperacionSobreCuentaExistente(tipo, correo_cliente, 
					id_cuenta, valor, puesto_atencion, cajero, cuentaDestino);
		} 

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList consultarClienteEspecifico(String pCorreo, String ordenarPor, String descoasc, String agruparPor)
	{
		try 
		{
			ArrayList rta = consultarClienteDAO.darClienteEspecifico(pCorreo, ordenarPor, descoasc, agruparPor);
			return rta;
		}


		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList darCuentasGerenteGeneral(String ordenarPor, String agruparPor, String descoasc)
	{
		try 
		{
			return  consultarCuentasDAO.darCuentas(ordenarPor, agruparPor, descoasc);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList darCuentasPorTipoGerenteGeneral(String tipo, String ordenarPor, String agruparPor, String descoasc)
	{
		try 
		{
			return consultarCuentasDAO.darCuentasPorTipo(tipo, ordenarPor, agruparPor, descoasc);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return null;
	}

	public ArrayList darCuentasPorRangoSaldoGerenteGeneral(int desde, int hasta, String ordenarPor, String agruparPor, String descoasc)
	{
		try 
		{
			return consultarCuentasDAO.darCuentasPorRangoSaldo(desde, hasta, ordenarPor, agruparPor, descoasc);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return null;
	}

	public ArrayList darCuentasPorFechaGerenteGeneral(String fecha, String ordenarPor, String agruparPor, String descoasc)
	{
		try 
		{
			return consultarCuentasDAO.darCuentasPorFechaUltimoMovimiento(fecha, ordenarPor, agruparPor, descoasc);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return null;
	}

	public ArrayList darCuentasPorTipoGerenteOficina(int idOficina, String tipo, String ordenarPor, String agruparPor, String descoasc)
	{
		try 
		{
			return consultarCuentasDAO.darCuentasOficinaParticularPorTipoDeCuenta(idOficina, tipo, descoasc, agruparPor, ordenarPor);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return null;
	}

	public ArrayList darCuentasGerenteOficina(int idOficina, String ordenarPor, String agruparPor, String descoasc)
	{
		try 
		{
			return consultarCuentasDAO.darCuentasOficinaParticular(idOficina, ordenarPor, agruparPor, descoasc);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return null;
	}

	public ArrayList darCuentasPorRangoSaldoGerenteOficina(int idOficina, int desde, int hasta, String ordenarPor, String agruparPor, String descoasc)
	{
		try 
		{
			return consultarCuentasDAO.darCuentasOficinaParticularPorRangoDeSaldo(idOficina, desde, hasta, descoasc, ordenarPor, agruparPor);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return null;
	}

	public ArrayList darCuentasPorRangoSaldoGerenteOficina(int idOficina, String fecha, String ordenarPor, String agruparPor, String descoasc)
	{
		try 
		{
			return consultarCuentasDAO.darCuentasOficinaParticularPorFechaUltimoMovimiento(idOficina, fecha, descoasc, agruparPor, ordenarPor);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return null;
	}

	/**
	 * Este método devuelve un objeto de tipo usuariosValues a partir del inicio de sesión de un usuario
	 */
	public UsuarioValues darUsuarioInicioSesion(String correo)
	{
		try 
		{
			return consultarClienteDAO.darUsuarioInicioSesion(correo);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Método que retorna la oficina del empleado que se logueó
	 * @param correo
	 * @return
	 * @throws Exception 
	 */
	public int darOficinaEmpleado(String correo) throws Exception
	{
		int oficina = consultarClienteDAO.darOficinaEmpleado(correo);
		if(oficina == 0)
		{
			throw new Exception("La oficina no existe");
		}

		else
		{
			return oficina;
		}
	}


	/**
	 * Dependiendo de la operación que se vaya a realizar son los campos a utilizar.
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
			int numCuotas, int idPrestamo, int cuentaOrigen)
	{
		try {
			registrarOperacionPrestamoDAO.registrarOperacionSobrePrestamoExistente(tipo, 
					correo_cliente, id_cuenta, valor, puesto_atencion, cajero, tipo_prestamo, 
					id_solicitud, numCuotas, idPrestamo, cuentaOrigen);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public ArrayList<UsuarioActivoValues> darUsuarioActivoGeneral(String tipoTransaccion)
	{
		try {
			return consultarActivoDAO.darUsuarioActivoGeneral(tipoTransaccion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList registrarPagarNomina(int cuenta, String correoCliente)
	{
		try
		{
			return pagarNominaDAO.registrarPagarNomina(cuenta, correoCliente);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<UsuarioActivoValues> darUsuarioActivoOficina(String tipoTransaccion, int oficina)
	{
		try {
			return consultarActivoDAO.darUsuarioActivoOficina(tipoTransaccion, oficina);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	public ArrayList<Top10Values> darTop10TransaccionesGeneral()
	{
		try {
			return top10.darTop10TransaccionesGerenteGeneral();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Top10Values> darTop10TransaccionesOficina(int oficina)
	{
		try {
			return top10.darTop10TransaccionesGerenteOficina(oficina);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<TransaccionValues> darOperacionesV2(String fechaInicial, String fechaFinal, String tipoOperacion, int monto)
	{
		try
		{
			return iter4Dao.darOperacionesV2(fechaInicial, fechaFinal, tipoOperacion, monto);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<ConsignacionValues> darConsignacionesIter4(int monto, String tipoPrestamo) throws Exception
	{
		try
		{
			return iter4Dao.darConsignacionesIter4(monto, tipoPrestamo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<TransaccionValues> darOperacionesV3(String fechaInicial, String fechaFinal, 
			String tipoOperacion, int monto) throws Exception
			{
		try
		{
			return iter4Dao.darOperacionesV3(fechaInicial, fechaFinal, tipoOperacion, monto);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
			}

	public ArrayList consultarPuntoAtencionIter4(int id1, int id2) throws Exception
	{
		try
		{
			return iter4Dao.consultarPuntoAtencionIter4(id1, id2);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<TransaccionValues> darOperacionesCliente( String ordenarPor, String filtrarPor, String desasc,String correoCliente)
	{
		try {
			return consultaOperacionesDAO.darOperacionesCliente(ordenarPor, filtrarPor, desasc, correoCliente) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<TransaccionValues> darOperacionesOficina( int idOficina, String ordenarPor, String desasc, String filtrarPor)
	{
		try {
			return consultaOperacionesDAO.darOperacionesOficina(idOficina, ordenarPor, desasc, filtrarPor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<TransaccionValues> darOperacionesGeneral( String ordenarPor, String filtrarPor, String desasc)
	{
		try {
			return consultaOperacionesDAO.darOperacionesGeneral(ordenarPor, desasc, filtrarPor) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<PrestamoValues> darPrestamosCliente( String correoCliente, String ordenarPor, String filtrarPor, String desasc)
	{
		try {
			return consultaPrestamoDAO.darPrestamosCliente( correoCliente, ordenarPor, desasc, filtrarPor) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<PrestamoValues> darPrestamosOficina( int idOficina, String ordenarPor, String filtrarPor, String desasc)
	{
		try {
			return consultaPrestamoDAO.darPrestamosOficina( idOficina, ordenarPor, desasc, filtrarPor) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<PrestamoValues> darPrestamosGeneral( String ordenarPor, String filtrarPor, String desasc)
	{
		try {
			return consultaPrestamoDAO.darPrestamosGeneral(ordenarPor, desasc, filtrarPor) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * Método que se encarga de solucionar el RF12 "Asociar cuenta de cliente empresarial a empleado"
	 */
	public void asociarCuentaAEmpleado(String correoEmpleador, int cuentaEmpleador, String correoEmpleado, int cuentaEmpleado, int valorPagar, String frecuencia)
	{
		try 
		{
			asociarDAO.registrarAsociarCuenta(correoEmpleador, cuentaEmpleador, correoEmpleado, cuentaEmpleado, valorPagar, frecuencia);
		} 

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}


	/**
	 * Método que se encarga de resolver RFC7
	 * @param fecha1
	 * @param fecha2
	 * @param monto
	 * @param tipo
	 */
	public ArrayList consultarOperacionesV2(String fecha1, String fecha2, int monto, String tipo)
	{
		try 
		{
			return iter4Dao.darOperacionesV2(fecha1, fecha2, tipo, monto);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Método que se encarga de resolver RFC8
	 * @param fecha1
	 * @param fecha2
	 * @param monto
	 * @param tipo
	 * @return
	 */
	public ArrayList consultarOperacionesV3(String fecha1, String fecha2, int monto, String tipo)
	{
		try 
		{
			return iter4Dao.darOperacionesV3(fecha1, fecha2, tipo, monto);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * Mpetodo que resuelve RFC9
	 * @param monto
	 * @param tipo
	 * @return
	 */
	public ArrayList consultarConsignaciones(int monto, String tipo)
	{
		try 
		{
			return iter4Dao.darConsignacionesIter4(monto, tipo);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Mpetodo que resuelve RFC10
	 * @param punto1
	 * @param punto2
	 * @return
	 */
	public ArrayList consultarPuntosAtencion(int punto1, int punto2)
	{
		try 
		{
			return iter4Dao.consultarPuntoAtencionIter4(punto1, punto2);
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
