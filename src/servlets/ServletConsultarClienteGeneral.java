package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * url-pattern: \consultarClienteGeneral 
 */
public class ServletConsultarClienteGeneral extends ASServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doGet de ServletConsultarClienteGeneral");

		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		imprimirConsultarClienteGeneralInicial(pw);
		imprimirWrapper(pw);
	}

	private void imprimirConsultarClienteGeneralInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Un Cliente</h1>");
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

		pw.println("<label>Correo Cliente</label>");
		pw.println("<input class=\"form-control\"> ");


		pw.println("<label>Tipo De Cuenta</label>");
		pw.println("<select class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Ahorros</option>");
		pw.println("<option>Corriente</option>");
		pw.println("<option>CDT</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<label>ID Cuenta</label>");
		pw.println("<input class=\"form-control\"> ");
		pw.println("<label>Saldo Cuenta:</label>");
		pw.println("<input class=\"form-control\"></div>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha inicial transaccion:</label>");
		pw.println("<input class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha final transaccion:</label>");
		pw.println("<input class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Valor Transaccion:</label>");
		pw.println("<div><input class=\"form-control\"> ");
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
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de Cuenta</option>");
		pw.println("<option>ID Cuenta</option>");
		pw.println("<option>ID Prestamo</option>");
		pw.println("<option>ID Oficina</option>");
		pw.println("<option>ID Transaccion</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Agrupar por:</label>");
		pw.println("<select class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de Cuenta</option>");
		pw.println("<option>ID Cuenta</option>");
		pw.println("<option>ID Prestamo</option>");
		pw.println("<option>ID Oficina</option>");
		pw.println("<option>ID Transaccion</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>Nombre Cliente</th>");
		pw.println("<th>Tipo De Persona</th>");
		pw.println("<th>Correo</th>");
		pw.println("<th>Ciudad</th>");
		pw.println("<th>Nacionalidad</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>Juan</td>");
		pw.println("<td>Natural</td>");
		pw.println("<td>jc@bancandes.com</td>");
		pw.println("<td>Barranquilla</td>");
		pw.println("<td>Colombiano</td>");

		pw.println("</tr>");
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Cuentas Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Cuenta</th>");
		pw.println("<th>Tipo De Cuenta</th>");
		pw.println("<th>Oficina Asociada</th>");
		pw.println("<th>Fecha Ultimo Movimiento</th>");
		pw.println("<th>Saldo</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>Ahorros</td>");
		pw.println("<td>3</td>");
		pw.println("<td>2014/11/23</td>");
		pw.println("<td>350000</td>");
		pw.println("<td class=\"center\">Activa</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"even gradeC\">");
		pw.println("<td>3</td>");
		pw.println("<td>Corriente</td>");
		pw.println("<td>3</td>");
		pw.println("<td class=\"center\">2015/08/29</td>");
		pw.println("<td>3500000</td>");
		pw.println("<td class=\"center\">Activa</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"odd gradeA\">");
		pw.println("<td>6</td>");
		pw.println("<td>CDT</td>");
		pw.println("<td>1</td>");
		pw.println("<td class=\"center\">2013/06/07</td>");
		pw.println("<td>0</td>");
		pw.println("<td class=\"center\">Inactiva</td> ");
		pw.println("</tr>");
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");

		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Prestamos Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Prestamo</th>");
		pw.println("<th>Monto Prestado</th>");
		pw.println("<th>Tipo</th>");
		pw.println("<th>Saldo Pendiente</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>8</td>");
		pw.println("<td>200000</td>");
		pw.println("<td>Estudio</td>");
		pw.println("<td class=\"center\">100000</td>");
		pw.println("<td>Abierto</td>");
		pw.println("</tr>");
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Operaciones Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Transaccion</th>");
		pw.println("<th>Tipo</th>");
		pw.println("<th>Fecha de realizacion</th>");
		pw.println("<th>Punto de atencion</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>6</td>");
		pw.println("<td>Pago Prestamo</td>");
		pw.println("<td>2015/07/08</td>");
		pw.println("<td>8</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>8</td>");
		pw.println("<td>Pago Extraordinario</td>");
		pw.println("<td>2015/06/07</td>");
		pw.println("<td>8</td>");
		pw.println("</tr>");
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("</div>");

		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");

		pw.println("</div>");
		pw.println("<!-- /#page-wrapper -->");

		pw.println("</div>");
	}

	public String darTituloPagina() {
		return "BancAndes - Consultar clientes";
	}

}
