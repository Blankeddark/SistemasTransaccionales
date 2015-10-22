package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Fachada.BancAndes;
import vos.TransaccionValues;

/**
 * url-pattern: /consultarOperacionesGeneral
 */
public class ServletConsultarOperacionesGeneral extends ASParsingServlet {

	ArrayList<TransaccionValues> operaciones;

	public ServletConsultarOperacionesGeneral()
	{
		operaciones = new ArrayList<TransaccionValues>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doGet de ServletConsultarOperacionesGeneral");

		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		imprimirConsultarOperacionesGeneralInicial(pw);
		imprimirWrapper(pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doPost de ServletConsultarOperacionesGeneral");
		PrintWriter pw = response.getWriter();
		
		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		
		try
		{
			String tipoOperacion = request.getParameter("tipoOperacion");
			String ordenarPor = request.getParameter("ordenarPor");
			String descoasc = request.getParameter("descoasc");
			
			operaciones = BancAndes.darInstancia().darO
		}
		catch(Exception e)
		{
			imprimirConsultarOperacionesGeneralError(pw, e.getMessage());
			imprimirWrapper(pw);
			e.printStackTrace();
			return;
		}

	}

	private void imprimirConsultarOperacionesGeneralInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Operaciones</h1>");
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
		pw.println("<form role=\"form\" action=\"consultarOperacionesGeneral\" method=\"post\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoOperacion\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Abrir Cuenta</option>");
		pw.println("<option>Cerrar Cuenta</option>");
		pw.println("<option>Retirar</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Solicitar Prestamo</option>");
		pw.println("<option>Aprobar Prestamo</option>");
		pw.println("<option>Rechazar Prestamo</option>");
		pw.println("<option>Cerrar Prestamo</option>");
		pw.println("<option>Pago Prestamo</option>");
		pw.println("<option>Pago Prestamo Extraordinario</option>");
		pw.println("</select>");
		pw.println("</div>");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Correo cliente</option>");
		pw.println("<option>Fecha</option>");
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
		pw.println("Operaciones resultantes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Operacion</th>");
		pw.println("<th>Correo Cliente</th>");
		pw.println("<th>Tipo Operacion</th>");
		pw.println("<th>Fecha</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>ac.zuleta10@bancandes.com</td>");
		pw.println("<td>Ahorros</td>");
		pw.println("<td>2014/09/23</td>");
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

		pw.println("</div>");
		pw.println("<!-- /#page-wrapper -->");

		pw.println("</div>");

	}
	
	private void imprimirConsultarOperacionesGeneralResultados(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Operaciones</h1>");
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
		pw.println("<form role=\"form\" action=\"consultarOperacionesGeneral\" method=\"post\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoOperacion\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Abrir Cuenta</option>");
		pw.println("<option>Cerrar Cuenta</option>");
		pw.println("<option>Retirar</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Solicitar Prestamo</option>");
		pw.println("<option>Aprobar Prestamo</option>");
		pw.println("<option>Rechazar Prestamo</option>");
		pw.println("<option>Cerrar Prestamo</option>");
		pw.println("<option>Pago Prestamo</option>");
		pw.println("<option>Pago Prestamo Extraordinario</option>");
		pw.println("</select>");
		pw.println("</div>");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Correo cliente</option>");
		pw.println("<option>Fecha</option>");
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
		pw.println("Operaciones resultantes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Operacion</th>");
		pw.println("<th>Correo Cliente</th>");
		pw.println("<th>Tipo Operacion</th>");
		pw.println("<th>Fecha</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");

		parsearTablaOperaciones(operaciones);

		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
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
	
	private void imprimirConsultarOperacionesGeneralError(PrintWriter pw, String error)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Operaciones</h1>");
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
		pw.println("<form role=\"form\" action=\"consultarOperacionesGeneral\" method=\"post\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoOperacion\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Abrir Cuenta</option>");
		pw.println("<option>Cerrar Cuenta</option>");
		pw.println("<option>Retirar</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Solicitar Prestamo</option>");
		pw.println("<option>Aprobar Prestamo</option>");
		pw.println("<option>Rechazar Prestamo</option>");
		pw.println("<option>Cerrar Prestamo</option>");
		pw.println("<option>Pago Prestamo</option>");
		pw.println("<option>Pago Prestamo Extraordinario</option>");
		pw.println("</select>");
		pw.println("</div>");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Correo cliente</option>");
		pw.println("<option>Fecha</option>");
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
		pw.println("Operaciones resultantes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Operacion</th>");
		pw.println("<th>Correo Cliente</th>");
		pw.println("<th>Tipo Operacion</th>");
		pw.println("<th>Fecha</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>ac.zuleta10@bancandes.com</td>");
		pw.println("<td>Ahorros</td>");
		pw.println("<td>2014/09/23</td>");
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

		pw.println("</div>");
		pw.println("<!-- /#page-wrapper -->");

		pw.println("</div>");

	}

	public String darTituloPagina() {
		return "Consultar operaciones";
	}

}
