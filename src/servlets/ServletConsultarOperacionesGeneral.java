package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.TransaccionValues;
import Fachada.BancAndes;

/**
 * url-pattern: /consultarOperacionesGeneral
 */
public class ServletConsultarOperacionesGeneral extends ASParsingServlet {

	ArrayList<TransaccionValues> operaciones;
	String tipoOperacion = "";

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
		
		int montoInt = 0;

		String botonBuscar = request.getParameter("Buscar");
		String botonBuscarV2 = request.getParameter("BuscarV2");
		String botonBuscarV3 = request.getParameter("BuscarV3");

	 tipoOperacion = request.getParameter("tipoOperacion");
		
		
		String ordenarPor = request.getParameter("ordenarPor");
		String descoasc = request.getParameter("descoasc");
		String fechaInicial = request.getParameter("fechaInicial");
		String fechaFinal = request.getParameter("fechaFinal");
		String monto  = request.getParameter("monto");
		
		if(tipoOperacion.equals("Consignar"))
		{
			tipoOperacion = "C";
		}
		
		else if(tipoOperacion.equals("Retirar"))
		{
			tipoOperacion = "R";
		}		
		
		else if(tipoOperacion.equalsIgnoreCase("Pago Prestamo"))
		{
			tipoOperacion = "PP";
		}
		
		else if(tipoOperacion.equalsIgnoreCase("Pago Prestamo Extraordinario"))
		{
			tipoOperacion = "PPE";
		}


		if (botonBuscar != null)
		{
			try
			{

				operaciones = BancAndes.darInstancia().darOperacionesGeneral(ordenarPor, tipoOperacion, descoasc);
			}

			catch(Exception e)
			{
				imprimirConsultarOperacionesGeneralError(pw, e.getMessage());
				imprimirWrapper(pw);
				e.printStackTrace();
				return;
			}
		}
		else if (botonBuscarV2 != null)
		{
			if(tipoOperacion.trim().equals("") || tipoOperacion == null)
			{
				imprimirConsultarOperacionesGeneralError(pw, "Seleccione un tipo de operacion");
				imprimirWrapper(pw);
				return;
			}
			
			if (fechaInicial.equals("") || fechaFinal.equals(""))
			{
				imprimirConsultarOperacionesGeneralError(pw, "Tanto la fecha inicial como la final deben ser v&aacute;lidas y estr en formato dd/MM/yyyy");
				imprimirWrapper(pw);
				return;
			}
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			Date fechaInicialDate = null;
			try {
				fechaInicialDate = format.parse(fechaInicial);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Date fechaFinalDate = null;
			try {
				fechaFinalDate = format.parse(fechaFinal);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (! (fechaInicialDate.before(fechaFinalDate) ) )
			{
				imprimirConsultarOperacionesGeneralError(pw, "La fecha final debe ser posterior a la inicial");
				imprimirWrapper(pw);
				return;
			}
			
			try
			{
				montoInt = Integer.parseInt(monto);
			}
			
			catch(Exception e)
			{
				imprimirConsultarOperacionesGeneralError(pw, "Lo ingresado en monto no es un n&uacute;mero v&aacute;lido");
				imprimirWrapper(pw);
				e.printStackTrace();
				return;
			}
			
			try
			{

				operaciones = BancAndes.darInstancia().consultarOperacionesV2(fechaInicial, fechaFinal, montoInt, tipoOperacion);
			}

			catch(Exception e)
			{
				imprimirConsultarOperacionesGeneralError(pw, e.getMessage());
				imprimirWrapper(pw);
				e.printStackTrace();
				return;
			}
		}

		else if (botonBuscarV3 != null)
		{
			if(tipoOperacion.trim().equals("") || tipoOperacion == null)
			{
				imprimirConsultarOperacionesGeneralError(pw, "Seleccione un tipo de operacion");
				imprimirWrapper(pw);
				return;
			}
			
			if (fechaInicial.equals("") || fechaFinal.equals(""))
			{
				imprimirConsultarOperacionesGeneralError(pw, "Tanto la fecha inicial como la final deben ser v&aacute;lidas y estr en formato dd/MM/yyyy");
				imprimirWrapper(pw);
				return;
			}
			
			try
			{
				montoInt = Integer.parseInt(monto);
			}
			
			catch(Exception e)
			{
				imprimirConsultarOperacionesGeneralError(pw, "Lo ingresado en monto no es un n&uacute;mero v&aacute;lido");
				imprimirWrapper(pw);
				e.printStackTrace();
				return;
			}
			
			try
			{

				operaciones = BancAndes.darInstancia().consultarOperacionesV3(fechaInicial, fechaFinal, montoInt, tipoOperacion);
			}

			catch(Exception e)
			{
				imprimirConsultarOperacionesGeneralError(pw, e.getMessage());
				imprimirWrapper(pw);
				e.printStackTrace();
				return;
			}
		}

		imprimirConsultarOperacionesGeneralResultados(pw);
		imprimirWrapper(pw);
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
		pw.println("<option>Retirar</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Pago Prestamo</option>");
		pw.println("<option>Pago Prestamo Extraordinario</option>");
		pw.println("</select>");
		pw.println("</div>");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha inicial transaccion:</label>");
		pw.println("<input class=\"form-control\" name=\"fechaInicial\" placeholder=\"DD/MM/AAAA\" value=\"\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha final transaccion:</label>");
		pw.println("<input name=\"fechaFinal\" class=\"form-control\" placeholder=\"DD/MM/AAAA\" value=\"\">");
		pw.println("</div>");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Monto:</label>");
		pw.println("<input name=\"monto\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" name=\"Buscar\" value=\"Buscar\"></input>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" name=\"BuscarV2\" value=\"Buscar V2\"></input>");

		pw.println("<input type=\"submit\" class=\"btn btn-danger\" name=\"BuscarV3\" value=\"Buscar V3\"></input>");



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
		pw.println("<option>Retirar</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Pago Prestamo</option>");
		pw.println("<option>Pago Prestamo Extraordinario</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha inicial transaccion:</label>");
		pw.println("<input class=\"form-control\" name=\"fechaInicial\" placeholder=\"DD/MM/AAAA\" value=\"\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha final transaccion:</label>");
		pw.println("<input name=\"fechaFinal\" class=\"form-control\" placeholder=\"DD/MM/AAAA\" value=\"\">");
		pw.println("</div>");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Monto:</label>");
		pw.println("<input name=\"monto\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" name=\"Buscar\" value=\"Buscar\"></input>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" name=\"BuscarV2\" value=\"Buscar V2\"></input>");

		pw.println("<input type=\"submit\" class=\"btn btn-danger\" name=\"BuscarV3\" value=\"Buscar V3\"></input>");


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

		parsearTablaOperacionesFake(operaciones, tipoOperacion, pw);

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
		pw.println("<option>Retirar</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Pago Prestamo</option>");
		pw.println("<option>Pago Prestamo Extraordinario</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha inicial transaccion:</label>");
		pw.println("<input class=\"form-control\" name=\"fechaInicial\" placeholder=\"DD/MM/AAAA\" value=\"\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha final transaccion:</label>");
		pw.println("<input name=\"fechaFinal\" class=\"form-control\" placeholder=\"DD/MM/AAAA\" value=\"\">");
		pw.println("</div>");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Monto:</label>");
		pw.println("<input name=\"monto\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" name=\"Buscar\" value=\"Buscar\"></input>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" name=\"BuscarV2\" value=\"Buscar V2\"></input>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" name=\"BuscarV3\" value=\"Buscar V3\"></input>");


		pw.println("</form>");
		pw.println("</div>");
		pw.println("<font color=\"red\">No se pudo realizar la b&uacute;squeda. ERROR:<br>" + error + "</font>");
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
