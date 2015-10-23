package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.CuentaValues;
import Fachada.BancAndes;

/**
 * url-pattern: /pagarNominaCliente
 */
public class ServletPagarNominaCliente extends ASServlet {

	ArrayList cuentasSinPagar;
	
	public ServletPagarNominaCliente()
	{
		cuentasSinPagar = new ArrayList();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doGet de ServletPagarNominaCliente");

		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarCliente(pw);
		imprimirPagarNominaClienteInicial(pw);
		imprimirWrapper(pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doPost de ServletPagarNominaCliente");
		PrintWriter pw = response.getWriter();
		
		String cuentaAUsar = request.getParameter("cuentaAUsar");
		int idCuentaAUsar = 0;
		
		imprimirEncabezado(pw);
		imprimirSidebarCliente(pw);
		
		try
		{
			idCuentaAUsar = Integer.parseInt(cuentaAUsar);
		}
		catch (Exception e)
		{
			imprimirPagarNominaClienteError(pw, "Lo ingresado en el campo del id de la cuenta a usar no es un n&uacute;mero v&aacute;lido");
			imprimirWrapper(pw);
			return;
		}
		
		ArrayList<CuentaValues> cuentas = ServletLogin.darCuentasUsuarioActual();
		boolean esSuCuenta = false;
		
		for(int i = 0; i <cuentas.size() && !esSuCuenta; i++)
		{
			if(cuentas.get(i).getIdCuenta() == idCuentaAUsar)
			{
				esSuCuenta = true;
			}
		}
		
		if(!esSuCuenta)
		{
			imprimirPagarNominaClienteError(pw, "La cuenta con el id ingresado no existe o no pertenece al usuario actual.");
			imprimirWrapper(pw);
			return;
		}
		
		cuentasSinPagar = BancAndes.darInstancia().registrarPagarNomina(idCuentaAUsar, ServletLogin.darUsuarioActual().getCorreo());
		
		if( !cuentasSinPagar.isEmpty() )
		{
			String error = "No hubo suficiente dinero para pagar todas las cuentas. "
					+ "<br>Las cuentas con uno de los siguientes ID no recibieron el pago:<br>";
			
			for (int i = 0; i < cuentasSinPagar.size(); i++)
			{
				error += cuentasSinPagar.get(i) + "<br>";
			}
			
			imprimirPagarNominaClienteError(pw, error);
		}
		
		imprimirPagarNominaClienteExito(pw);
		imprimirWrapper(pw);
	}
	
	private void imprimirPagarNominaClienteExito(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Pagar La Nomina</h1>");
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
		pw.println("<form role=\"form\" action=\"pagarNominaCliente\" method=\"post\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cuenta a usar:</label>");
		pw.println("<input name=\"cuentaAUsar\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Pagar nomina\"></input>");

		pw.println("</form>");
		pw.println("</div>");
		pw.println("<font color=\"green\">Operaci&oacute;n realizada &eacute;xitosamente");
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
		pw.println("<!-- /#wrapper -->");

	}
	
	private void imprimirPagarNominaClienteError(PrintWriter pw, String error)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Pagar La Nomina</h1>");
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
		pw.println("<form role=\"form\" action=\"pagarNominaCliente\" method=\"post\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cuenta a usar:</label>");
		pw.println("<input name=\"cuentaAUsar\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Pagar nomina\"></input>");

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
		pw.println("<!-- /#wrapper -->");

	}

	private void imprimirPagarNominaClienteInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Pagar La Nomina</h1>");
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
		pw.println("<form role=\"form\" action=\"pagarNominaCliente\" method=\"post\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cuenta a usar:</label>");
		pw.println("<input name=\"cuentaAUsar\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Pagar nomina\"></input>");

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
		pw.println("<!-- /#wrapper -->");

	}

	public String darTituloPagina()
	{
		return "Pagar nomina cliente";
	}



}
