package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOS.RegistrarOperacionCuentaDAO;

/**
 * url-pattern: /operacionesCuentas
 */
public class ServletRegistrarOperacionesCuentas extends ASServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("doGet en ServletRegistrarOperacionesCuentas");
		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarCajero(pw);
		imprimirRegistrarOperacionesInicial(pw);
		imprimirWrapper(pw);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarCajero(pw);
		
		String tipo = request.getParameter("tipo");
		String correo = request.getParameter("correo");
		String correoCajero = request.getParameter("correoCajero");
		int idCuenta = 0; 
		int monto = 0;
		int idPuntoAtencion = 0;
		pw.println("<option>Abrir</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Retirar</option>");
		
		if(tipo.equals("Consignar"))
		{
			tipo = "C";
		}
		
		else if(tipo.equals("Abrir"))
		{
			tipo = "AC";
		}
		
		else if(tipo.equals("Retirar"))
		{
			tipo = "R";
		}
		
		if (!tipo.equals("AC"))
		{
			try
			{
				idCuenta = Integer.parseInt(request.getParameter("idCuenta") );
			}
			catch (Exception e)
			{
				imprimirRegistrarOperacionesError(pw, "Lo ingresado en el campo de la id cuenta no es un n&uacute;mero v&aacute;lido.");
				imprimirWrapper(pw);
				return;
			}
			
			try
			{
				monto = Integer.parseInt(request.getParameter("monto") );
			}
			catch (Exception e)
			{
				imprimirRegistrarOperacionesError(pw, "Lo ingresado en el campo de no es un n&uacute;mero v&aacute;lido.");
				imprimirWrapper(pw);
				return;
			}
		}
		
		
		try
		{
			idPuntoAtencion = Integer.parseInt( request.getParameter("idPuntoAtencion") );
		}
		catch (Exception e)
		{
			imprimirRegistrarOperacionesError(pw, "Lo ingresado en el campo del idPuntoAtencion no es un n&uacute;mero v&aacute;lido.");
			imprimirWrapper(pw);
			return;
		}
		
		RegistrarOperacionCuentaDAO roc = new RegistrarOperacionCuentaDAO();
		
		try
		{
			//roc.registrarOperacionSobreCuentaExistente(tipo, correo, idCuenta, monto, idPuntoAtencion , correoCajero);
		}
		
		catch (Exception e)
		{
			imprimirRegistrarOperacionesError(pw, e.getMessage());
			imprimirWrapper(pw);
			e.printStackTrace();
			return;
		}
		
		imprimirRegistrarOperacionesExito(pw);
		imprimirWrapper(pw);
	}
	
	private void imprimirRegistrarOperacionesError(PrintWriter pw, String error)
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
		pw.println("<form role=\"form\" method=\"post\" action=\"operacionesCuentas\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipo\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Abrir</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Retirar</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo Cliente:</label>");
		pw.println("<input name=\"correo\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID cuenta:</label>");
		pw.println("<input name=\"idCuenta\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group input-group\">");
		pw.println("<span class=\"input-group-addon\">$</span>");
		pw.println("<input name=\"monto\" type=\"text\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Puesto de atenci&oacute;n:</label>");
		pw.println("<input name=\"idPuntoAtencion\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cajero:</label>");
		pw.println("<input name=\"correoCajero\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");
		pw.println("<font color=\"red\">No se pudo registrar la operaci&oacute;n <br> Error: " + error + "</font><br>");
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
	
	private void imprimirRegistrarOperacionesExito(PrintWriter pw)
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
		pw.println("<form role=\"form\" method=\"post\" action=\"operacionesCuentas\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipo\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Abrir</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Retirar</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo Cliente:</label>");
		pw.println("<input name=\"correo\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID cuenta:</label>");
		pw.println("<input name=\"idCuenta\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group input-group\">");
		pw.println("<span class=\"input-group-addon\">$</span>");
		pw.println("<input name=\"monto\" type=\"text\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Puesto de atenci&oacute;n:</label>");
		pw.println("<input name=\"idPuntoAtencion\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cajero:</label>");
		pw.println("<input name=\"correoCajero\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");
		pw.println("<font color=\"green\">&iexcl;La operaci&oacute;n fue realizada exitosamente!</font><br>");
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

	private void imprimirRegistrarOperacionesInicial(PrintWriter pw)
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
		pw.println("<form role=\"form\" method=\"post\" action=\"operacionesCuentas\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipo\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Abrir</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Retirar</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo Cliente:</label>");
		pw.println("<input name=\"correo\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>ID cuenta:</label>");
		pw.println("<input name=\"idCuenta\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group input-group\">");
		pw.println("<span class=\"input-group-addon\">$</span>");
		pw.println("<input name=\"monto\" type=\"text\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Puesto de atenci&oacute;n:</label>");
		pw.println("<input name=\"idPuntoAtencion\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cajero:</label>");
		pw.println("<input name=\"correoCajero\" class=\"form-control\">");
		pw.println("</div>");

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
		return "Agregar operaciones cuentas";
	}



}
