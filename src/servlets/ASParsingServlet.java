package servlets;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import vos.ClienteValues;
import vos.ConsignacionValues;
import vos.CuentaValues;
import vos.PrestamoValues;
import vos.TransaccionValues;
import vos.UsuarioValues;

/**
 * Clase abstracta que heredan todos los servlets que deben tratar con cuentas o clientes y
 * pasarlos a la interfaz web.
 */
public abstract class ASParsingServlet extends ASServlet {

	private final static int MAXIMO_DATOS_PAGINA = 50;
	/**
	 * Parse una tabla de cuentas en orden:
	 * idCuenta
	 * tipoCuenta
	 * oficina
	 * fechaUltimoMovimiento
	 * saldo
	 * estado
	 * @param cuentas
	 * @param pw
	 */
	protected void parsearTablaCuentas(ArrayList<CuentaValues> cuentas, PrintWriter pw)
	{
		for (int i = 0; i < MAXIMO_DATOS_PAGINA; i++)
		{
			CuentaValues cuentaActual = cuentas.get(i);
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>" + cuentaActual.getIdCuenta() + "</td>");
			pw.println("<td>" + cuentaActual.getTipoCuenta() + "</td>");
			pw.println("<td>" + cuentaActual.getOficina() + "</td>");
			pw.println("<td>" + cuentaActual.getFechaUltimoMovimiento().toString() + "</td>");
			pw.println("<td>" + cuentaActual.getSaldo() +"</td>");
			pw.println("<td>" + cuentaActual.getEstado() + "</td>");
			pw.println("</tr>");
		} 
	}
	
	protected void parsearTablaOperaciones(ArrayList<TransaccionValues> operaciones, PrintWriter pw)
	{
		for(int i = 0; i < MAXIMO_DATOS_PAGINA && i < operaciones.size(); i++)
		{
			TransaccionValues transaccionActual = operaciones.get(i);
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>" + transaccionActual.getIdTransaccion() + "</td>");
			pw.println("<td>" + transaccionActual.getCorreoUsuario() + "</td>");
			pw.println("<td>" + transaccionActual.getTipo( ) + "</td>");
			pw.println("<td>" + transaccionActual.getFechaTransaccion() + "</td>");
			pw.println("</tr>");
		}
	}
	
	protected void parsearTablaOperacionesFake(ArrayList<TransaccionValues> operaciones, String tipoOperacion, PrintWriter pw)
	{
		Random random = new Random();
		int num = 0;
		int num2 = 0;
		TransaccionValues temp = null;
		
		for(int j = 0; j < MAXIMO_DATOS_PAGINA && j < operaciones.size(); j++)
		{
			num = random.nextInt(operaciones.size());
			num2 = random.nextInt(operaciones.size());
			
			temp = operaciones.get(num);
			operaciones.set(num, operaciones.get(num2));
			operaciones.set(num2, temp);
			
		}
		for(int i = 0; i < MAXIMO_DATOS_PAGINA && i < operaciones.size(); i++)
		{
			TransaccionValues transaccionActual = operaciones.get(i);
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>" + transaccionActual.getIdTransaccion() + "</td>");
			pw.println("<td>" + transaccionActual.getCorreoUsuario() + "</td>");
			pw.println("<td>" + tipoOperacion + "</td>");
			pw.println("<td>" + transaccionActual.getFechaTransaccion() + "</td>");
			pw.println("</tr>");
		}
	}
	
	protected void parsearTablaUsuariosTipoIter4(ArrayList<UsuarioValues> usuarios, PrintWriter pw)
	{
		for (int i = 0; i < MAXIMO_DATOS_PAGINA && i < usuarios.size(); i++)
		{
			UsuarioValues usuarioActual = usuarios.get(i);
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>" + usuarioActual.getNombre() + "</td>");
			pw.println("<td>" + usuarioActual.getNacionalidad() + "</td>");
			pw.println("<td>" + usuarioActual.getTelefono() + "</td>");
			pw.println("<td>" + usuarioActual.getCorreo() + "</td>");
			pw.println("<td>" + usuarioActual.getCiudad() + "</td>");
			pw.println("</tr>");
		}
	}
	
	/**
	 * Parsea las cuentas en orden:
	 * idCuenta
	 * correoDueñoCuenta
	 * tipoCuenta
	 * oficinaAsociada
	 * fechaUltimoMovimiento
	 * saldo
	 * estado
	 * @param cuentas
	 * @param pw
	 */
	protected void parsearTablaCuentasTipo2(ArrayList<CuentaValues> cuentas, PrintWriter pw)
	{
		for (int i = 0; i < MAXIMO_DATOS_PAGINA && i < cuentas.size(); i++)
		{
			CuentaValues cuentaActual = cuentas.get(i);
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>" + cuentaActual.getIdCuenta() + "</td>");
			pw.println("<td>" + cuentaActual.getCorreo() + "</td>");
			pw.println("<td>" + cuentaActual.getTipoCuenta() + "</td>");
			pw.println("<td>" + cuentaActual.getOficina() + "</td>");
			pw.println("<td>" + cuentaActual.getFechaUltimoMovimiento().toString() + "</td>");
			pw.println("<td>" + cuentaActual.getSaldo() +"</td>");
			pw.println("<td>" + cuentaActual.getEstado() + "</td>");
			pw.println("</tr>");
			
		}

	}
	 
	protected void parsearTablaClientes(ArrayList<ClienteValues> clientes, PrintWriter pw)
	{
		for (int i = 0; i < MAXIMO_DATOS_PAGINA && i < clientes.size(); i++)
		{ 
			ClienteValues clienteActual = clientes.get(i);	
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>" + clienteActual.getNombre() + "</td>");
			pw.println("<td>" + clienteActual.getTipoPersona() + "</td>");
			pw.println("<td>" + clienteActual.getCorreo() + "</td>");
			pw.println("<td>" + clienteActual.getCiudad() + "</td>");
			pw.println("<td>" + clienteActual.getNacionalidad() + "</td>");
			pw.println("</tr>");
		}
	}
	
	protected void parsearTablaPrestamos(ArrayList<PrestamoValues> prestamos, PrintWriter pw)
	{
		for (int i = 0; i < MAXIMO_DATOS_PAGINA && i < prestamos.size(); i++)
		{
			PrestamoValues prestamoActual = prestamos.get(i);
			pw.println("<tr class=\"odd gradeX\">");

			pw.println("<td>" + prestamoActual.getId() + "</td>");
			pw.println("<td>" + prestamoActual.getMontoPrestado() +"</td>");
			pw.println("<td>" + prestamoActual.getTipo() + "</td>");
			pw.println("<td class=\"center\">" + prestamoActual.getSaldoPendiente() + "</td>");
			pw.println("<td>" + prestamoActual.getEstado() + "</td>");
			
			pw.println("</tr>");
		}
	}
	
	protected void parsearTablaPrestamosTipo2(ArrayList<PrestamoValues> prestamos, PrintWriter pw)
	{
		for (int i = 0; i < MAXIMO_DATOS_PAGINA && i < prestamos.size(); i++)
		{
			PrestamoValues prestamoActual = prestamos.get(i);
			pw.println("<tr class=\"odd gradeX\">");

			pw.println("<td>" + prestamoActual.getId() + "</td>");
			pw.println("<td>" + prestamoActual.getCorreoCliente() +"</td>");
			pw.println("<td>" + prestamoActual.getTipo() + "</td>");
			pw.println("<td class=\"center\">" + prestamoActual.getSaldoPendiente() + "</td>");
			pw.println("<td>" + prestamoActual.getCuotasEfectivas() + "</td>");
			pw.println("<td>" + prestamoActual.getFechaPrestamo().toString() + "</td>");
			pw.println("<td>" + prestamoActual.getEstado() + "</td>");
			pw.println("</tr>");
		}
	}
	
	protected void parsearTablaConsignaciones(ArrayList<ConsignacionValues> consignaciones, PrintWriter pw)
	{
		for (int i = 0; i < MAXIMO_DATOS_PAGINA && i < consignaciones.size(); i++)
		{
			ConsignacionValues consignacionActual = consignaciones.get(i);
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>"+ consignacionActual.getId()  +"</td>");
			pw.println("<td>" + consignacionActual.getIdCuentaOrigen() +"</td>");
			pw.println("<td>"+  consignacionActual.getIdCuentaDestino()+ "</td>");
			pw.println("<td>"+ consignacionActual.getMonto() + "</td>");
			pw.println("</tr>");
		}

	}
	/**
	 * 
	 * @param tipoCuenta tipoCuenta.equals("Ahorros") || tipoCuenta.equals("Corriente")
	 * 					|| tipoCuenta.equals("CDT")
	 */
	protected void filtrarCuentasPorTipoCuenta(String tipoCuenta, ArrayList<CuentaValues> cuentas)
	{
		CuentaValues cuentaActual = null;
		for(int i = 0; i < cuentas.size(); i++)
		{
			cuentaActual = cuentas.get(i);
			if( !cuentaActual.getTipoCuenta().equals(tipoCuenta) )
			{
				cuentas.remove(i);
			}
		}
	}

	protected void filtrarCuentasPorIdCuenta(int idCuenta, ArrayList<CuentaValues> cuentas)
	{
		CuentaValues cuentaActual = null;
		for(int i = 0; i < cuentas.size(); i++)
		{
			cuentaActual = cuentas.get(i);
			if( cuentaActual.getIdCuenta() != idCuenta )
			{
				cuentas.remove(i);
			}
		}
	}
	
	protected void filtrarCuentasPorSaldo(int saldo, ArrayList<CuentaValues> cuentas)
	{
		CuentaValues cuentaActual = null;
		for(int i = 0; i < cuentas.size(); i++)
		{
			cuentaActual = cuentas.get(i);
			if( cuentaActual.getSaldo() != saldo )
			{
				cuentas.remove(i);
			}
		}
	}
	
	protected void filtrarCuentasPorCorreoCliente(String correoCliente, ArrayList<CuentaValues> cuentas)
	{
		CuentaValues cuentaActual = null;
		for(int i = 0; i< cuentas.size() + 1; i++)
		{
			cuentaActual = cuentas.get(i);
			if ( !cuentaActual.getCorreo().equals(correoCliente) )
			{
				cuentas.remove(i);
			}
		}
	}
	
	protected void filtrarCuentasPorRangoSaldo(int rangoSaldoInicial, int rangoSaldoFinal, ArrayList<CuentaValues> cuentas)
	{
		CuentaValues cuentaActual = null;
		for(int i = 0; i< cuentas.size(); i++)
		{
			cuentaActual = cuentas.get(i);
			if ( cuentaActual.getSaldo() < rangoSaldoInicial || cuentaActual.getSaldo() > rangoSaldoFinal)
			{
				cuentas.remove(i);
			}
		}
	}
	
	protected void filtrarCuentasPorOficina(int oficina, ArrayList<CuentaValues> cuentas)
	{
		CuentaValues cuentaActual = null;
		for(int i = 0; i< cuentas.size(); i++)
		{
			cuentaActual = cuentas.get(i);
			if ( cuentaActual.getOficina() != oficina)
			{
				cuentas.remove(i);
			}
		}
	}
	
	protected void filtrarPrestamosPorTipoPrestamo(ArrayList<PrestamoValues> prestamos, String tipoPrestamo)
	{
		for (int i = 0; i < prestamos.size(); i++)
		{
			if( !prestamos.get(i).getTipo().equalsIgnoreCase(tipoPrestamo) )
			{
				prestamos.remove(i);
			}
		}
	}
	
	
}
