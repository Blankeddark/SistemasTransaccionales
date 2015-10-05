package servlets;

import java.io.PrintWriter;
import java.util.ArrayList;

import vos.ClienteValues;
import vos.CuentaValues;
import vos.PrestamoValues;

/**
 * Clase abstracta que heredan todos los servlets que deben tratar con cuentas o clientes y
 * pasarlos a la interfaz web.
 */
public abstract class ASParsingServlet extends ASServlet {

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
	public void parsearTablaCuentas(ArrayList<CuentaValues> cuentas, PrintWriter pw)
	{
		for (CuentaValues cuentaActual : cuentas)
		{
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
	public void parsearTablaCuentasTipo2(ArrayList<CuentaValues> cuentas, PrintWriter pw)
	{
		for (CuentaValues cuentaActual : cuentas)
		{
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
	 
	public void parsearTablaClientes(ArrayList<ClienteValues> clientes, PrintWriter pw)
	{
		for (ClienteValues clienteActual : clientes)
		{ 
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>" + clienteActual.getNombre() + "</td>");
			pw.println("<td>" + clienteActual.getTipoPersona() + "</td>");
			pw.println("<td>" + clienteActual.getCorreo() + "</td>");
			pw.println("<td>" + clienteActual.getCiudad() + "</td>");
			pw.println("<td>" + clienteActual.getNacionalidad() + "</td>");
			pw.println("</tr>");
		}
	}
	
	public void parsearTablaPrestamos(ArrayList<PrestamoValues> prestamos, PrintWriter pw)
	{
		for (PrestamoValues prestamoActual : prestamos)
		{
			pw.println("<tr class=\"odd gradeX\">");

			pw.println("<td>" + prestamoActual.getId() + "</td>");
			pw.println("<td>" + prestamoActual.getMontoPrestado() +"</td>");
			pw.println("<td>" + prestamoActual.getTipo() + "</td>");
			pw.println("<td class=\"center\">" + prestamoActual.getSaldoPendiente() + "</td>");
			pw.println("<td>" + prestamoActual.getEstado() + "</td>");
			
			pw.println("</tr>");
		}
	}
	/**
	 * 
	 * @param tipoCuenta tipoCuenta.equals("Ahorros") || tipoCuenta.equals("Corriente")
	 * 					|| tipoCuenta.equals("CDT")
	 */
	public void filtrarCuentasPorTipoCuenta(String tipoCuenta, ArrayList<CuentaValues> cuentas)
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

	public void filtrarCuentasPorIdCuenta(int idCuenta, ArrayList<CuentaValues> cuentas)
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
	
	public void filtrarCuentasPorSaldo(int saldo, ArrayList<CuentaValues> cuentas)
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
	
	
}
