package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.CuentaValues;
import DAOS.RegistrarOperacionCuentaDAO;

/**
 * url-pattern: /registrarOperacionesCuentaCliente
 */
public class ServletRegistrarOperacionCuentaCliente extends ASServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("doGet en ServletRegistrarOperacionesCuentaCliente");
		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarCliente(pw);
		imprimirRegistrarOperacionesClienteInicial(pw);
		imprimirWrapper(pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarCliente(pw);

		String tipo = request.getParameter("tipo");
		String correo = ServletLogin.darUsuarioActual().getCorreo();

		/**
		 * En este caso, quien realiza la transacción es el mismo cliente.
		 */

		String correoCajero = correo;
		int idCuentaOrigen = 0; 
		int monto = 0;
		int idPuntoAtencion = 10;
		int idCuentaDestino = 0;

		if(tipo.equals("Consignar"))
		{
			tipo = "C";
		}

		else if(tipo.equals("Retirar"))
		{
			tipo = "R";
		}

		try
		{
			idCuentaOrigen = Integer.parseInt(request.getParameter("idCuenta") );
		}
		catch (Exception e)
		{
			imprimirRegistrarOperacionesClienteError(pw, "Lo ingresado en el campo de la id cuenta origen no es un n&uacute;mero v&aacute;lido.");
			imprimirWrapper(pw);
			return;
		}
		
		boolean lePertenece = false;
		ArrayList<CuentaValues> cuentas = ServletLogin.darCuentasUsuarioActual();
		
		for(int i = 0; i < cuentas.size() && !lePertenece; i++)
		{
			if(cuentas.get(i).getIdCuenta() == idCuentaOrigen)
			{
				lePertenece = true;
			}
		}
		
		if(!lePertenece)
		{
			imprimirRegistrarOperacionesClienteError(pw, "La cuenta con el id ingresado en id cuenta origen no existe o no pertenece al usuario actual.");
			imprimirWrapper(pw);
			return;
		}
		
		try
		{
			idCuentaDestino = Integer.parseInt(request.getParameter("idCuentaDestino") );
		}
		catch (Exception e)
		{
			imprimirRegistrarOperacionesClienteError(pw, "Lo ingresado en el campo de la id cuenta destino no es un n&uacute;mero v&aacute;lido.");
			imprimirWrapper(pw);
			return;
		}

		try
		{
			monto = Integer.parseInt(request.getParameter("monto") );
		}

		catch (Exception e)
		{
			imprimirRegistrarOperacionesClienteError(pw, "Lo ingresado en el campo de no es un n&uacute;mero v&aacute;lido.");
			imprimirWrapper(pw);
			return;

		}

		RegistrarOperacionCuentaDAO roc = new RegistrarOperacionCuentaDAO();
		
		try
		{
			roc.registrarOperacionSobreCuentaExistente(tipo,
					ServletLogin.darUsuarioActual().getCorreo(), 
					idCuentaDestino,
					monto, 
					idPuntoAtencion, 
					correoCajero, 
					idCuentaOrigen);
		}

		catch (Exception e)
		{
			imprimirRegistrarOperacionesClienteError(pw, e.getMessage());
			imprimirWrapper(pw);
			e.printStackTrace();
			return;
		}

		imprimirRegistrarOperacionesClienteExito(pw);
		imprimirWrapper(pw);
	}

	private void imprimirRegistrarOperacionesClienteError(PrintWriter pw, String error)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Realizar transacci&oacute;n</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"registrarOperacionesCuentaCliente\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipo\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Retirar</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID cuenta origen:</label>");
		pw.println("<input name=\"idCuenta\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID cuenta destino:</label>");
		pw.println("<input name=\"idCuentaDestino\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group input-group\">");
		pw.println("<span class=\"input-group-addon\">$</span>");
		pw.println("<input name=\"monto\" type=\"text\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");

		pw.println("<div class=\"form-group\">");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Registrar\"></button>");

		pw.println("</form>");
		pw.println("</div>");
		pw.println("<font color=\"red\">La operaci&oacute;n no pudo ser realizada. ERROR:<br>" + error);
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

	private void imprimirRegistrarOperacionesClienteExito(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Realizar transacci&oacute;n</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"registrarOperacionesCuentaCliente\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipo\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Retirar</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID cuenta origen:</label>");
		pw.println("<input name=\"idCuenta\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID cuenta destino:</label>");
		pw.println("<input name=\"idCuentaDestino\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group input-group\">");
		pw.println("<span class=\"input-group-addon\">$</span>");
		pw.println("<input name=\"monto\" type=\"text\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");

		pw.println("<div class=\"form-group\">");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Registrar\"></button>");

		pw.println("</form>");
		pw.println("<font color=\"green\">Operaci&oacute;n realizada &eacute;xitosamente");
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

	private void imprimirRegistrarOperacionesClienteInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Realizar transacci&oacute;n</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"registrarOperacionesCuentaCliente\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipo\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Retirar</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID cuenta origen:</label>");
		pw.println("<input name=\"idCuenta\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID cuenta destino:</label>");
		pw.println("<input name=\"idCuentaDestino\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group input-group\">");
		pw.println("<span class=\"input-group-addon\">$</span>");
		pw.println("<input name=\"monto\" type=\"text\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");

		pw.println("<div class=\"form-group\">");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Registrar\"></button>");

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

	public String darTituloPagina() {
		return "BancAndes - Registrar operaci&oacute;n";
	}

}
