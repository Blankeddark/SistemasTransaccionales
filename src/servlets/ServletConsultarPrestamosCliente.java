package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.PrestamoValues;
import Fachada.BancAndes;

/**
 * url-pattern: /consultarPrestamosCliente
 */
public class ServletConsultarPrestamosCliente extends ASParsingServlet {
	
ArrayList<PrestamoValues> prestamos;
	
	public ServletConsultarPrestamosCliente()
	{
		prestamos = new ArrayList<PrestamoValues>(); 
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doGet de ServletConsultarPrestamoCliente");

		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		imprimirConsultarPrestamoGeneralInicial(pw);
		imprimirWrapper(pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doPost de ServletConsultarPrestamoCliente");
		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		
		String tipoPrestamo = request.getParameter("tipoPrestamo");
		String ordenarPor = request.getParameter("ordenarPor");
		String descoasc = request.getParameter("descoasc");
		
		prestamos = BancAndes.darInstancia().darPrestamosCliente(
							ServletLogin.darUsuarioActual().getCorreo(), ordenarPor, tipoPrestamo, descoasc);
				
		System.out.println("Tamaño prestamos: " + prestamos.size() );
		
		imprimirConsultarPrestamoGeneralResultado(pw);
		imprimirWrapper(pw);
	}

	private void imprimirConsultarPrestamoGeneralInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Prestamos</h1>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-3\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Filtros");
		pw.println("</div>");
		pw.println("<div class=\"panel-body\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<form role=\"form\" action=\"consultarPrestamosCliente\" method=\"post\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoPrestamo\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Vivienda</option>");
		pw.println("<option>Estudio</option>");
		pw.println("<option>Automovil</option>");
		pw.println("<option>Libre inversion</option>");
		pw.println("<option>Calamidad doméstica</option>");
		pw.println("</select>");
		pw.println("</div>");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Correo cliente</option>");
		pw.println("<option>Nombre Cliente</option>");
		pw.println("<option>Saldo pendiente</option>");
		pw.println("<option>Cuotas pagadas</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Orden:</label>");
		pw.println("<select name=\"descoasc\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>DESC</option>");
		pw.println("<option>ASC</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Buscar\"></input>");


		pw.println("</form>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-6 (nested) -->");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.row (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.panel-body -->           ");

		pw.println("</div>");


		pw.println("<div class=\"col-lg-9\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Prestamos resultantes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Prestamo</th>");
		pw.println("<th>Correo Cliente</th>");
		pw.println("<th>Tipo</th>");
		pw.println("<th>Saldo pendiente</th>");
		pw.println("<th>Cuotas pagadas</th>");
		pw.println("<th>Fecha de prestamo</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>ac.zuleta10@bancandes.com</td>");
		pw.println("<td>Ahorros</td>");
		pw.println("<td class=	\"center\">1</td>");
		pw.println("<td>2014/09/23</td>");
		pw.println("<td class=\"center\">50000</td>");
		pw.println("<td>Activa</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"even gradeC\">");
		pw.println("<td>2</td>");
		pw.println("<td>sm.madera10@bancandes.com</td>");
		pw.println("<td>Corriente</td>");
		pw.println("<td class=\"center\">1</td>");
		pw.println("<td>2015/08/13</td>");
		pw.println("<td class=\"center\">180000</td>");
		pw.println("<td>Activa</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"odd gradeA\">");
		pw.println("<td>3</td>");
		pw.println("<td>asteam@bancandes.com</td>");
		pw.println("<td>CDT</td>");
		pw.println("<td class=\"center\">2</td>");
		pw.println("<td>2014/11/13</td>");
		pw.println("<td class=\"center\">400000</td>");
		pw.println("<td>Activa</td> ");

		pw.println("</tr>");
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
	}
	
	private void imprimirConsultarPrestamoGeneralError(PrintWriter pw, String error)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Prestamos</h1>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-3\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Filtros");
		pw.println("</div>");
		pw.println("<div class=\"panel-body\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<form role=\"form\" action=\"consultarPrestamosCliente\" method=\"post\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoPrestamo\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Vivienda</option>");
		pw.println("<option>Estudio</option>");
		pw.println("<option>Automovil</option>");
		pw.println("<option>Libre inversion</option>");
		pw.println("<option>Calamidad doméstica</option>");
		pw.println("</select>");
		pw.println("</div>");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Correo cliente</option>");
		pw.println("<option>Nombre Cliente</option>");
		pw.println("<option>Saldo pendiente</option>");
		pw.println("<option>Cuotas pagadas</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Orden:</label>");
		pw.println("<select name=\"descoasc\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>DESC</option>");
		pw.println("<option>ASC</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Buscar\"></input>");


		pw.println("</form>");
		pw.println("</div>");
		pw.println("<font color=\"red\">No se pudo realizar la b&uacute;squeda. ERROR:<br>" + error);
		pw.println("<!-- /.col-lg-6 (nested) -->");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.row (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.panel-body -->           ");

		pw.println("</div>");


		pw.println("<div class=\"col-lg-9\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Prestamos resultantes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Prestamo</th>");
		pw.println("<th>Correo Cliente</th>");
		pw.println("<th>Tipo</th>");
		pw.println("<th>Saldo pendiente</th>");
		pw.println("<th>Cuotas pagadas</th>");
		pw.println("<th>Fecha de prestamo</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
	}
	
	private void imprimirConsultarPrestamoGeneralResultado(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Prestamos</h1>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-3\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Filtros");
		pw.println("</div>");
		pw.println("<div class=\"panel-body\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<form role=\"form\" action=\"consultarPrestamosCliente\" method=\"post\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoPrestamo\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Vivienda</option>");
		pw.println("<option>Estudio</option>");
		pw.println("<option>Automovil</option>");
		pw.println("<option>Libre inversion</option>");
		pw.println("<option>Calamidad doméstica</option>");
		pw.println("</select>");
		pw.println("</div>");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Correo cliente</option>");
		pw.println("<option>Nombre Cliente</option>");
		pw.println("<option>Saldo pendiente</option>");
		pw.println("<option>Cuotas pagadas</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Orden:</label>");
		pw.println("<select name=\"descoasc\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>DESC</option>");
		pw.println("<option>ASC</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Buscar\"></input>");


		pw.println("</form>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-6 (nested) -->");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.row (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.panel-body -->           ");

		pw.println("</div>");


		pw.println("<div class=\"col-lg-9\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Prestamos resultantes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Prestamo</th>");
		pw.println("<th>Correo Cliente</th>");
		pw.println("<th>Tipo</th>");
		pw.println("<th>Saldo pendiente</th>");
		pw.println("<th>Cuotas pagadas</th>");
		pw.println("<th>Fecha de prestamo</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		
		parsearTablaPrestamosTipo2(prestamos, pw);
		System.out.println("Tamaño arreglo prestamos: " + prestamos.size());
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
	}
	
	public String darTituloPagina() 
	{
		return "Consultar prestamo";
	}

}
