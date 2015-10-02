package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * url-pattern: /registrarCliente
 */
public class ServletRegistrarCliente extends ASServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("En doGet registrarCliente");

		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarGO(pw);
		imprimirRegistrarClienteInicial(pw);
		imprimirWrapper(pw);
	}

	private void imprimirRegistrarClienteInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Registrar Cliente</h1>");
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
		pw.println("<label>Correo Electronico:</label>");
		pw.println("<input class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Nombre de usuario:</label>");
		pw.println("<input class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Contrase&ntilde;a:</label>");
		pw.println("<input class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Nombre:</label>");
		pw.println("<input class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Nacionalidad:</label>");
		pw.println("<input class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Direcci&oacute;n:</label>");
		pw.println("<input class=\"form-control\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tel&eacute;fono:</label>");
		pw.println("<input class=\"form-control\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ciudad:</label>");
		pw.println("<input class=\"form-control\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Departamento:</label>");
		pw.println("<input class=\"form-control\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>C&oacute;digo postal:</label>");
		pw.println("<input class=\"form-control\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>N&uacute;mero de documento:</label>");
		pw.println("<input class=\"form-control\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo de documento:</label>");
		pw.println("<select class=\"form-control\">");
		pw.println("<option>C&eacute;dula de Ciudadan&iacute;a</option>");
		pw.println("<option>C&eacute;dula de Extranher&iacute;a</option>");
		pw.println("<option>Tarjeta de Identidad</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo de persona:</label>");
		pw.println("<select class=\"form-control\">");
		pw.println("<option>Juridica</option>");
		pw.println("<option>Natural</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<br>");
		pw.println("<div>");
		pw.println("<button type=\"button\" class=\"btn btn-success\">Success</button>");
		pw.println("</div>");
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
		return "BancAndes - Registrar cliente";
	}

}
