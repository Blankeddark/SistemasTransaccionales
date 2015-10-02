package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Fachada.BancAndes;

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
	
	/**
	 * Registra el cliente cuyos datos fueron ingresados por par�metro.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String correo = request.getParameter("correo");
		String login = request.getParameter("login");
		String contrase�a = request.getParameter("contrase�a");
		String nombreUsuario = request.getParameter("nombreUsuario");
		String nacionalidad = request.getParameter("nacionalidad");
		String direccion = request.getParameter("direccion");
		String telefono = request.getParameter("telefono");
		String ciudad = request.getParameter("ciudad");
		String departamento = request.getParameter("departamento");
		String codPostal = request.getParameter("codPostal");
		String numId = request.getParameter("numId");
		String tipo_id = request.getParameter("tipo_id");
		String tipo_persona = request.getParameter("tipoPersona");
		
		if(tipo_id.contains("ciudada"))
		{
			tipo_id = "CC";
		}
		
		else if(tipo_id.contains("extranjer"))
		{
			tipo_id="CE";
		}
		
		else if(tipo_id.equals("Tarjeta de Identidad"))
		{
			tipo_id="TI";
		}
		
		if(tipo_persona.startsWith("J"))
		{
			tipo_persona = "J";
		}
		
		else
		{
			tipo_persona = "N";
		}
		
		System.out.println(correo); //TODO terminar m�todo de inserci�n de cliente.
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
		pw.println("<form role=\"form\" method=\"post\" action=\"registrarCliente\">"); //TODO
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo Electronico:</label>");
		pw.println("<input class=\"form-control\" name=\"correo\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Nombre de usuario:</label>");
		pw.println("<input class=\"form-control\" name=\"login\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Contrase&ntilde;a:</label>");
		pw.println("<input class=\"form-control\" name=\"contrase�a\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Nombre:</label>");
		pw.println("<input class=\"form-control\" name=\"nombreUsuario\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Nacionalidad:</label>");
		pw.println("<input class=\"form-control\" name=\"nacionalidad\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Direcci&oacute;n:</label>");
		pw.println("<input class=\"form-control\" name=\"direccion\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tel&eacute;fono:</label>");
		pw.println("<input class=\"form-control\" name=\"telefono\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ciudad:</label>");
		pw.println("<input class=\"form-control\" name=\"ciudad\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Departamento:</label>");
		pw.println("<input class=\"form-control\" name=\"departamento\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>C&oacute;digo postal:</label>");
		pw.println("<input class=\"form-control\" name=\"codPostal\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>N&uacute;mero de documento:</label>");
		pw.println("<input class=\"form-control\" name=\"numId\">");

		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo de documento:</label>");
		pw.println("<select class=\"form-control\" name=\"tipo_id\">");
		pw.println("<option>C&eacute;dula de Ciudadan&iacute;a</option>");
		pw.println("<option>C&eacute;dula de Extranjer&iacute;a</option>");
		pw.println("<option>Tarjeta de Identidad</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Tipo de persona:</label>");
		pw.println("<select class=\"form-control\" name=\"tipoPersona\">");
		pw.println("<option>Juridica</option>");
		pw.println("<option>Natural</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<br>");
		pw.println("<div>");
		pw.println("<input type=\"submit\" class=\"btn btn-success\" name=\"botonRegistrar\" value=\"Registrar\"></button>");
		pw.println("</div>");
		pw.println("</form>"); //TODO
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
