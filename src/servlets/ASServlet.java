package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class ASServlet extends HttpServlet {

	public abstract String darTituloPagina();

	/**
	 * Imprime el encabezado regular, utilizado en la mayoría de los HTML (sin el script que está
	 * comentado en la mayoría), con el título que se retorna en darTituloPagina(). 
	 * @param response
	 * @throws IOException si hay un problema obteniendo el writer
	 */
	public void imprimirEncabezado(PrintWriter respuesta) throws IOException
	{
		respuesta.println("<!DOCTYPE html>");
		respuesta.println("<html lang=\"en\">");

		respuesta.println("<head>");

		respuesta.println("<meta charset=\"utf-8\">");
		respuesta.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		respuesta.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		respuesta.println("<meta name=\"description\" content=\"\">");
		respuesta.println("<meta name=\"author\" content=\"\">");

		respuesta.println("<title>"+ darTituloPagina() +"</title>");

		respuesta.println("<!-- Latest compiled and minified CSS -->");
		respuesta.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">");

		respuesta.println("<!-- Optional theme -->");
		respuesta.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css\">");

		respuesta.println("<!-- Latest compiled and minified JavaScript -->");
		respuesta.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>");

		respuesta.println("</head>");
	}

	/**
	 * Imprime el encabezado regular, utilizado en la mayoría de los HTML (sin el script que está
	 * comentado en la mayoría), con el título que se ingresa por parámetro
	 * @param response
	 * @throws IOException si hay un problema obteniendo el writer
	 */
	public void imprimirEncabezado(PrintWriter respuesta, String titulo) throws IOException
	{
		respuesta.println("<!DOCTYPE html>");
		respuesta.println("<html lang=\"en\">");

		respuesta.println("<head>");

		respuesta.println("<meta charset=\"utf-8\">");
		respuesta.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		respuesta.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		respuesta.println("<meta name=\"description\" content=\"\">");
		respuesta.println("<meta name=\"author\" content=\"\">");

		respuesta.println("<title>"+ titulo +"</title>");

		respuesta.println("<!-- Bootstrap Core CSS -->");
		respuesta.println("<link href=\"../bower_components/bootstrap/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");

		respuesta.println("<!-- MetisMenu CSS -->");
		respuesta.println("<link href=\"../bower_components/metisMenu/dist/metisMenu.min.css\" rel=\"stylesheet\">");

		respuesta.println("<!-- Custom CSS -->");
		respuesta.println("<link href=\"../dist/css/sb-admin-2.css\" rel=\"stylesheet\">");

		respuesta.println("<!-- Custom Fonts -->");
		respuesta.println("<link href=\"../bower_components/font-awesome/css/font-awesome.min.css\" rel=\"stylesheet\" type=\"text/css\">");

		respuesta.println("</head>");
	}

	/**
	 * Imprime todo el HTML hasta el sidebar com&uacute;n para todas las páginas en el sidebar del 
	 * Gerente Oficina
	 * @param pw PrintWriter del response en el que se escribirá el sidebar com&uacute;n.
	 */
	public void imprimirSidebarGO(PrintWriter pw)
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
		//pw.println("<form role=\"form\" method=\"get\" action=\"registrarCliente\">");
		pw.println("<li>");
		pw.println("<a href=\"registrarCliente\">Registrar cliente</a>");
		pw.println("</li>");
		//pw.println("</form>");
		pw.println("<li>");
		pw.println("<a href=\"registrarCuenta\">Registrar cuenta</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"cerrarCuenta\">Cerrar cuenta</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"registrarPrestamo\">Registrar pr&eacute;stamo</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"cerrarPrestamo\">Cerrar pr&eacute;stamo</a>");
		pw.println("</li>");
		pw.println("</ul>                 ");
		pw.println("<!-- /.nav-second-level -->");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"#\"><i class=\"fa fa-table fa-fw\"></i> Consultas <span class=\"fa arrow\"></span></a>");
		pw.println("<ul class=\"nav nav-second-level\">");
		pw.println("<li>");
		pw.println("<a href=\"consultarCuentaOficina\">Consultar cuentas</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"consultarClienteOficina\">Consultar cliente</a>");
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
	}

	public void imprimirSidebarGG(PrintWriter pw)
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
		pw.println("<li><a href=\"login.html\"><i class=\"fa fa-sign-out fa-fw\"></i> Cerrar Sesión</a>");
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
		pw.println("<a href=\"#\"><i class=\"fa fa-table fa-fw\"></i> Consultas <span class=\"fa arrow\"></span></a>");
		pw.println("<ul class=\"nav nav-second-level\">");
		pw.println("<li>");
		pw.println("<a href=\"consultarCuentaGeneral\">Consultar cuentas</a>");
		pw.println("</li>");
		pw.println("<li>");
		pw.println("<a href=\"consultarClienteGeneral\">Consultar cliente</a>");
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
	}

	/**
	 * Imprime el wrapper de las páginas, es decir, la colección de links jquery, BT y demás
	 * @param pw PrintWriter del response en el que se escribirá el wrapper.
	 */
	public void imprimirWrapper(PrintWriter pw)
	{
		pw.println("<!-- /#wrapper -->");

		pw.println("<!-- jQuery -->");
		pw.println("<script src=\"../bower_components/jquery/dist/jquery.min.js\"></script>");

		pw.println("<!-- Bootstrap Core JavaScript -->");
		pw.println("<script src=\"../bower_components/bootstrap/dist/js/bootstrap.min.js\"></script>");

		pw.println("<!-- Metis Menu Plugin JavaScript -->");
		pw.println("<script src=\"../bower_components/metisMenu/dist/metisMenu.min.js\"></script>");

		pw.println("<!-- Custom Theme JavaScript -->");
		pw.println("<script src=\"../dist/js/sb-admin-2.js\"></script>");
	}


}
