package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * url-pattern: /registrarCuenta 
 */
public class ServletRegistrarCuenta extends ASServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("En doGet ServletRegistrarCuenta");

		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarGO(pw);
		imprimirRegistrarCuentaInicial(pw);
		imprimirWrapper(pw);
	}

	private void imprimirRegistrarCuentaInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Registrar Cuenta</h1>");
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
		pw.println("<form role=\"form\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo:</label>");
		pw.println("<select class=\"form-control\">");
		pw.println("<option>Ahorros</option>");
		pw.println("<option>Corriente</option>");
		pw.println("<option>CDT</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo Cliente:</label>");
		pw.println("<input class=\"form-control\">");
		pw.println("</div>");
		pw.println("<br>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Saldo Inicial:</label>");
		pw.println("<input class=\"form-control\">");
		pw.println("</div>");
		pw.println("<button type=\"button\" class=\"btn btn-primary\">Registrar</button>");

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
		return "BancAndes - Registrar cuenta";
	}

}
