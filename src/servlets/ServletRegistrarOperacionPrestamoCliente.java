package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.CuentaValues;
import DAOS.RegistrarOperacionSobrePrestamoDAO;

/**
 * url-pattern: registrarOperacionPrestamoCliente
 */
public class ServletRegistrarOperacionPrestamoCliente extends ASServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("doGet en ServletRegistrarOperacionesPrestamos");

		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarCliente(pw);
		imprimirOperacionesPrestamoClienteInicial(pw);
		imprimirWrapper(pw);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarCliente(pw);

		String tipoOperacion = request.getParameter("tipoOperacion");
		/**
		 * El cliente es quien realiza la operación
		 */
		String correoCliente = ServletLogin.darUsuarioActual().getCorreo();
		
		int idCuenta = 0;
		int idSolicitud = 0;
		int idPrestamo = 0;
		int monto = 0;
		int numCuotas = 0;
		int idPuntoAtencion = 10;
		String correoCajero = ServletLogin.darUsuarioActual().getCorreo();
		String tipoPrestamo = request.getParameter("tipoPrestamo");

		try
		{
			idCuenta = Integer.parseInt ( request.getParameter("idCuentaOrigen") );
		}
		
		catch (Exception e)
		{
			imprimirOperacionesPrestamosError(pw, "Lo ingresado en idCuenta no es un n&uacute;mero v&aacute;lido");
			imprimirWrapper(pw);
			return;
		}
		
		ArrayList<CuentaValues> cuentasActuales = ServletLogin.darCuentasUsuarioActual();
		boolean encontro = false;
		
		for(int i = 0; i < cuentasActuales.size() && !encontro; i++)
		{
			if(cuentasActuales.get(i).getIdCuenta() == idCuenta)
			{
				encontro = true;
			}
		}
		
		if(!encontro)
		{
			imprimirOperacionesPrestamosError(pw, "La cuenta con el id ingresado no existe o no pertenece al cliente actual.");
			imprimirWrapper(pw);
			return;
		}

		//		pw.println("<option>Solicitar</option>");
		//		pw.println("<option>Aprobar</option>");
		//		pw.println("<option>Rechazar</option>");
		//		pw.println("<option>Pagar cuota</option>");
		//		pw.println("<option>Pagar cuota extraordinaria</option>");

		//		if(tipoOperacion.equals("Pagar cuota") || tipoOperacion.equals("Pagar cuota extraordinaria"))
		//		{
		try
		{
			monto = Integer.parseInt ( request.getParameter("monto") );
		}
		catch (Exception e)
		{
			imprimirOperacionesPrestamosError(pw, "Lo ingresado en el monto no es un n&uacute;mero v&aacute;lido");
			imprimirWrapper(pw);
			return;
		}
		//}

		try
		{
			idPuntoAtencion = Integer.parseInt ( request.getParameter("idPuntoAtencion"));
		}
		catch (Exception e)
		{
			imprimirOperacionesPrestamosError(pw, "Lo ingresado en idPuntoAtencion no es un n&uacute;mero v&aacute;lido");
			imprimirWrapper(pw);
			return;
		}
		//		pw.println("<option>Solicitar</option>");
		//		pw.println("<option>Aprobar</option>");
		//		pw.println("<option>Rechazar</option>");
		//		pw.println("<option>Pagar cuota</option>");
		//		pw.println("<option>Pagar cuota extraordinaria</option>");

		if(! (tipoOperacion.equals("Pagar cuota") || tipoOperacion.equals("Pagar cuota extraordinaria") ))
		{
			try
			{
				idSolicitud = Integer.parseInt ( request.getParameter("idSolicitud") );
			}
			catch (Exception e)
			{
				imprimirOperacionesPrestamosError(pw, "Lo ingresado en idSolicitud no es un n&uacute;mero v&aacute;lido");
				imprimirWrapper(pw);
				return;
			}

		}
		try
		{
			numCuotas = Integer.parseInt ( request.getParameter("numCuotas") );
		}
		catch (Exception e)
		{
			imprimirOperacionesPrestamosError(pw, "Lo ingresado en numCuotas no es un n&uacute;mero v&aacute;lido");
			imprimirWrapper(pw);
			return;
		}
		
		try
		{
			idPrestamo = Integer.parseInt ( request.getParameter("idPrestamo") );
		}
		catch (Exception e)
		{
			imprimirOperacionesPrestamosError(pw, "Lo ingresado en idPrestamo no es un n&uacute;mero v&aacute;lido");
			imprimirWrapper(pw);
			return;
		}

		RegistrarOperacionSobrePrestamoDAO rospd = new RegistrarOperacionSobrePrestamoDAO();

		try {
			rospd.registrarOperacionSobrePrestamoExistente(tipoOperacion, correoCajero, idCuenta, monto, 10, correoCajero, tipoPrestamo, idSolicitud, numCuotas, idPrestamo, idCuenta);

		}
		catch (Exception e)
		{
			imprimirOperacionesPrestamosError(pw, e.getMessage());
			imprimirWrapper(pw);
			e.printStackTrace();
			return;
		}

		imprimirOperacionesPrestamosExito(pw);
		imprimirWrapper(pw);
	}

	private void imprimirOperacionesPrestamosExito(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Realizar operaci&oacute;n sobre pr&eacute;stamo</h1>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Por favor llenar los siguientes campos");
		pw.println("</div>");
		pw.println("<div class=\"panel-body\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-6\">");
		pw.println("<form role=\"form\" method=\"post\" action=\"registrarOperacionPrestamoCliente\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo Operacion:</label>");
		pw.println("<select name=\"tipoOperacion\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Solicitar</option>");
		pw.println("<option>Pagar cuota</option>");
		pw.println("<option>Pagar cuota extraordinaria</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID Cuenta:</label>");
		pw.println("<input name=\"idCuenta\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID Solicitud:</label>");
		pw.println("<input name=\"idSolicitud\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID prestamo:</label>");
		pw.println("<input name=\"idPrestamo\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group input-group\">");
		pw.println("<span class=\"input-group-addon\">$</span>");
		pw.println("<input type=\"text\" name=\"monto\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Numero de cuotas:</label>");
		pw.println("<input name=\"numCuotas\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Punto de atencion:</label>");
		pw.println("<input name=\"idPuntoAtencion\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cajero:</label>");
		pw.println("<input name=\"idCajero\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo Prestamo:</label>");
		pw.println("<input name=\"tipoPrestamo\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");
		pw.println("<font color=\"green\">La operaci&oacute;n fue registrada exitosamente</font><br>");
		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Registrar\"></input>");

		pw.println("</form>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-6 (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.row (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.panel-body -->");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("</div>");
		pw.println("<!-- /#page-wrapper -->");

		pw.println("</div>");
	}

	private void imprimirOperacionesPrestamosError(PrintWriter pw, String error)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Realizar operaci&oacute;n sobre pr&eacute;stamo</h1>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Por favor llenar los siguientes campos");
		pw.println("</div>");
		pw.println("<div class=\"panel-body\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-6\">");
		pw.println("<form role=\"form\" method=\"post\" action=\"registrarOperacionPrestamoCliente\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo Operacion:</label>");
		pw.println("<select name=\"tipoOperacion\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Solicitar</option>");
		pw.println("<option>Pagar cuota</option>");
		pw.println("<option>Pagar cuota extraordinaria</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID prestamo:</label>");
		pw.println("<input name=\"idPrestamo\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group input-group\">");
		pw.println("<span class=\"input-group-addon\">$</span>");
		pw.println("<input type=\"text\" name=\"monto\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Numero de cuotas:</label>");
		pw.println("<input name=\"numCuotas\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo Prestamo:</label>");
		pw.println("<input name=\"tipoPrestamo\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Registrar\"></input>");

		pw.println("</form>");
		pw.println("<font color=\"red\">No se pudo registrar la operaci&oacute;n. ERROR:<br>" + error);
		pw.println("</div>");
		pw.println("<!-- /.col-lg-6 (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.row (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.panel-body -->");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("</div>");
		pw.println("<!-- /#page-wrapper -->");

		pw.println("</div>");
	}

	private void imprimirOperacionesPrestamoClienteInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Realizar operaci&oacute;n sobre pr&eacute;stamo</h1>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Por favor llenar los siguientes campos");
		pw.println("</div>");
		pw.println("<div class=\"panel-body\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-6\">");
		pw.println("<form role=\"form\" method=\"post\" action=\"registrarOperacionPrestamoCliente\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo Operacion:</label>");
		pw.println("<select name=\"tipoOperacion\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Solicitar</option>");
		pw.println("<option>Pagar cuota</option>");
		pw.println("<option>Pagar cuota extraordinaria</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID prestamo:</label>");
		pw.println("<input name=\"idPrestamo\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID cuenta origen:</label>");
		pw.println("<input name=\"idCuentaOrigen\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group input-group\">");
		pw.println("<span class=\"input-group-addon\">$</span>");
		pw.println("<input type=\"text\" name=\"monto\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Numero de cuotas:</label>");
		pw.println("<input name=\"numCuotas\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo Prestamo:</label>");
		pw.println("<input name=\"tipoPrestamo\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Registrar\"></input>");

		pw.println("</form>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-6 (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.row (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.panel-body -->");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("</div>");
		pw.println("<!-- /#page-wrapper -->");

		pw.println("</div>");
	}
	
	public String darTituloPagina() 
	{
		return "Registrar operaci&oacute;n pr&eacute;stamo";
	}
	
	

}
