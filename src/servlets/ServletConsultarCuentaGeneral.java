package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * url-pattern: /consultarCuentaGeneral
 */
public class ServletConsultarCuentaGeneral extends ASServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doGet de ServletConsultarCuentaGerente");

		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		imprimirConsultarCuentasGeneralInicial(pw);
		imprimirWrapper(pw);
	}

	private void imprimirConsultarCuentasGeneralInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Cuentas</h1>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-3\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Por favor llenar los siguientes campos");
		pw.println("</div>");
		pw.println("<div class=\"panel-body\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<form role=\"form\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Ahorros</option>");
		pw.println("<option>Corriente</option>");
		pw.println("<option>CDT</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<label>Desde:</label>");
		pw.println("<input class=\"form-control\"> ");
		pw.println("<label>Hasta:</label>");
		pw.println("<input class=\"form-control\"></div>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha de apertura:</label>");
		pw.println("<input class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha de &uacute;ltimo movimiento:</label>");
		pw.println("<input class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo Cliente:</label>");
		pw.println("<div><input class=\"form-control\"> ");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de cuenta</option>");
		pw.println("<option>Saldo</option>");
		pw.println("<option>Fecha de apertura</option>");
		pw.println("<option>Fecha &uacute;ltimo movimiento</option>");
		pw.println("<option>Cliente</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Agrupar por:</label>");
		pw.println("<select class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de cuenta</option>");
		pw.println("<option>Fecha de apertura</option>");
		pw.println("<option>Fecha &uacute;ltimo movimiento</option>");
		pw.println("<option>Cliente</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<br>");

		pw.println("<button type=\"button\" class=\"btn btn-primary\">Consultar</button>");



		pw.println("</form>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-6 (nested) -->");

		pw.println("</div>");
		pw.println("<!-- /.row (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.panel-body -->           ");

		pw.println("</div>");


		pw.println("<div class=\"col-lg-9\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Cuentas resultantes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Cuenta</th>");
		pw.println("<th>Correo Dueño</th>");
		pw.println("<th>Tipo De Cuenta</th>");
		pw.println("<th>Oficina Asociada</th>");
		pw.println("<th>Fecha &uacute;ltimo movimiento</th>");
		pw.println("<th>Saldo</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>ac.zuleta10@bancandes.com</td>");
		pw.println("<td>Ahorros</td>");
		pw.println("<td class=\"center\">1</td>");
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

		pw.println("</div>");
		pw.println("<!-- /#page-wrapper -->");

		pw.println("</div>");
	}

	public String darTituloPagina() {
		return "BancAndes - Consultar cuentas";
	}

}
