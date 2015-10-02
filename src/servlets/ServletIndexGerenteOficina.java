package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet que se encarga del caso de uso de desplegar el index del Gerente Oficina.
 * 
 * url-pattern: /gerenteOficina 
 *
 */
public class ServletIndexGerenteOficina extends ASServlet {

	/**
	 * Imprime el cuerpo del HTML que contiene el index inicial para el 
	 * Gerente de Oficina.
	 * @param pw
	 */
	private void imprimirCuerpoIndexGerenteOficina(PrintWriter pw)
	{
		imprimirSidebarGO(pw);

		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Men&uacute; de gerente</h1>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<div class=\"panel panel-default\">");

		pw.println("<div class=\"panel-body\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-6\">");
		pw.println("<form role=\"form\">");

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
		
		imprimirWrapper(pw);

		pw.println("</body>");

		pw.println("</html>");

	}

	private static void imprimirCuerpoCerrarPrestamo(PrintWriter pw)
	{
		pw.println("<body>");

		pw.println("<div id=\"wrapper\">");

		pw.println("<!-- Navigation -->");
		pw.println("<nav class=\"navbar navbar-default navbar-static-top\" role=\"navigation\" style=\"margin-bottom: 0\">");
		pw.println("<div class=\"navbar-header\">");
		pw.println("<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">");
		pw.println("<span class=\"sr-only\">Toggle navigation</span>");
		pw.println("<span class=\"icon-bar\"></span>");
		pw.println("<span class=\"icon-bar\"></span>");
		pw.println("<span class=\"icon-bar\"></span>");
		pw.println("</button>");
		pw.println("<a class=\"navbar-brand\" href=\"index.html\">Sistema BancAndes V2.0</a>");
		pw.println("</div>");
		pw.println("<!-- /.navbar-header -->");

		pw.println("<ul class=\"nav navbar-top-links navbar-right\">");

		pw.println("<!-- /.dropdown -->");
		pw.println("<li class=\"dropdown\">");
		pw.println("<a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">");
		pw.println("<i class=\"fa fa-user fa-fw\"></i>  <i class=\"fa fa-caret-down\"></i>");
		pw.println("</a>");
		pw.println("<ul class=\"dropdown-menu dropdown-user\">");
		pw.println("<li><a href=\"#\"><i class=\"fa fa-user fa-fw\"></i> Perfil de usuario</a>");
		pw.println("</li>");
		pw.println("<li><a href=\"#\"><i class=\"fa fa-gear fa-fw\"></i> Configuraciones</a>");
		pw.println("</li>");
		pw.println("<li class=\"divider\"></li>");
		pw.println("<li><a href=\"login.html\"><i class=\"fa fa-sign-out fa-fw\"></i> Cerrar Sesi&oacute;n</a>");
		pw.println("</li>");
		pw.println("</ul>");
		pw.println("<!-- /.dropdown-user -->");
		pw.println("</li>");
		pw.println("<!-- /.dropdown -->");
		pw.println("</ul>");
		pw.println("<!-- /.navbar-top-links -->");

		pw.println("<div class=\"navbar-default sidebar\" role=\"navigation\">");
		pw.println("<div class=\"sidebar-nav navbar-collapse\">");
		pw.println("<ul class=\"nav\" id=\"side-menu\">");
		pw.println("<li class=\"sidebar-search\">");
		pw.println("<div class=\"input-group custom-search-form\">");
		pw.println("<input type=\"text\" class=\"form-control\" placeholder=\"Search...\">");
		pw.println("<span class=\"input-group-btn\">");
		pw.println("<button class=\"btn btn-default\" type=\"button\">");
		pw.println("<i class=\"fa fa-search\"></i>");
		pw.println("</button>");
		pw.println("</span>");
		pw.println("</div>");
		pw.println("<!-- /input-group -->");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"#\"><i class=\"fa fa-edit fa-fw\"></i> Registros<span class=\"fa arrow\"></span></a>");
		pw.println("<ul class=\"nav nav-second-level\">");
		pw.println("<li>");
		pw.println("<a href=\"registrarCliente.html\">Registrar cliente</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"registrarCuenta.html\">Registrar cuenta</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"cerrarCuenta.html\">Cerrar cuenta</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"registrarOperacionCuenta.html\">Registrar operaci&oacute;n en cuenta</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"registrarPrestamo.html\">Registrar pr&eacute;stamo</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"cerrarPrestamo.html\">Cerrar pr&eacute;stamo</a>");
		pw.println("</li>");
		pw.println("</ul>");
		pw.println("<!-- /.nav-second-level -->");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"#\"><i class=\"fa fa-table fa-fw\"></i> Consultas <span class=\"fa arrow\"></span></a>");
		pw.println("<ul class=\"nav nav-second-level\">");
		pw.println("<li>");
		pw.println("<a href=\"consultarCuenta.html\">Consultar cuentas</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"consultarCliente.html\">Consultar cliente</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"login.html\">Top 10</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"login.html\">Usuario m&aacute;s activo</a>");
		pw.println("</li>");
		pw.println("</ul>");
		pw.println("<!-- /.nav-second-level -->");
		pw.println("</li>");
		pw.println("</ul>");
		pw.println("</div>");
		pw.println("<!-- /.sidebar-collapse -->");
		pw.println("</div>");
		pw.println("<!-- /.navbar-static-side -->");
		pw.println("</nav>");

		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Cerrar Pr&eacute;stamo</h1>");
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
		pw.println("<label>Numero de pr&eacute;stamo:</label>");
		pw.println("<input class=\"form-control\">");
		pw.println("</div>");
		pw.println("<br>");

		pw.println("<button type=\"button\" class=\"btn btn-danger\">Cerrar</button>");

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

		pw.println("</body>");
		pw.println("</html>");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("En doPost de ServletIndexGerenteOficina");
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirCuerpoIndexGerenteOficina(pw);
	}

	public String darTituloPagina() 
	{
		return "BancAndes - Gerente Oficina";
	}
}
