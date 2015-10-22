package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOS.CerrarCuentaDAO;
import conexion.ConexionBd;

/**
 * url-pattern: /cerrarCuenta
 */
public class ServletCerrarCuenta extends ASServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("En doGet de ServletCerrarCuenta");

		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarGO(pw);
		imprimirCerrarCuentaInicial(pw);
		imprimirWrapper(pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarGO(pw);

		int numCuenta = 0;
		String numeroNuevaCuentaAsociada = request.getParameter("numeroNuevaCuentaAsociada");
		int idNueva = 0;

		try
		{
			numCuenta = Integer.parseInt( request.getParameter("numCuenta") );
		}
		catch (Exception e2)
		{
			imprimirCerrarCuentaError(pw, "El n&uacute;mero ingresado como id de la cuenta a eliminar no es v&aacute;lido.");
			return;
		}

		if(numeroNuevaCuentaAsociada != null)
		{
			if( !numeroNuevaCuentaAsociada.trim().equals(""))
			{
				try
				{
					idNueva = Integer.parseInt( numeroNuevaCuentaAsociada );
				}
				catch (Exception e2)
				{
					imprimirCerrarCuentaError(pw, "El n&uacute;mero ingresado como id de la nueva cuenta asociada no es valido no es v&aacute;lido.");
					return;
				}
			}
		}

		CerrarCuentaDAO ccd = new CerrarCuentaDAO();

		try {

			ccd.registrarCerrarCuentaExistente(numCuenta, idNueva);
			imprimirCerrarCuentaExitoso(pw);

		} 

		catch (Exception e) {
			imprimirCerrarCuentaError(pw, e.getMessage());
			e.printStackTrace();
		}
		imprimirWrapper(pw);

	}

	private void imprimirCerrarCuentaExitoso(PrintWriter pw)
	{


		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Cerrar Cuenta</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"cerrarCuenta\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>N&uacute;mero de cuenta:</label>");
		pw.println("<input class=\"form-control\" name=\"numCuenta\">");
		pw.println("</div>");
		
		pw.println("	 <div class=\"form-group\">");
		pw.println("   <label>Numero de nueva cuenta asociada:</label>");
		pw.println("<input name=\"numeroNuevaCuentaAsociada\" class=\"form-control\">");
		pw.println("<br> <font color=\"green\"> ¡Cuenta cerrada exitosamente!</font>");
		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-danger\" value=\"Cerrar\"></button>");

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

		imprimirWrapper(pw);
	}

	private void imprimirCerrarCuentaError(PrintWriter pw, String error)
	{

		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Cerrar Cuenta</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"cerrarCuenta\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>N&uacute;mero de cuenta:</label>");
		pw.println("<input class=\"form-control\" name=\"numCuenta\">");
		pw.println("</div>");
		
		pw.println("	 <div class=\"form-group\">");
		pw.println("   <label>Numero de nueva cuenta asociada:</label>");
		pw.println("<input name=\"numeroNuevaCuentaAsociada\" class=\"form-control\">");
		pw.println("<br> <font color=\"red\">La cuenta no pudo ser cerrada. Error:<br>"
				+ error + "</font>");
		pw.println("<br>");

		pw.println("<button type=\"button\" class=\"btn btn-danger\" value=\"Cerrar\"></input>");

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

		imprimirWrapper(pw);
	}

	private void imprimirCerrarCuentaInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Cerrar Cuenta</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"cerrarCuenta\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>N&uacute;mero de cuenta:</label>");
		pw.println("<input class=\"form-control\" name=\"numCuenta\">");
		pw.println("</div>");

		pw.println("	 <div class=\"form-group\">");
		pw.println("   <label>Numero de nueva cuenta asociada:</label>");
		pw.println("<input name=\"numeroNuevaCuentaAsociada\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-danger\" value=\"Cerrar\"></input>");

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
	}

	public String darTituloPagina() 
	{
		return "BancAndes - Cerrar cuenta";
	}

}
