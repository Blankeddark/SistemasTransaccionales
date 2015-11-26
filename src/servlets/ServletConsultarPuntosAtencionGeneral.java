package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.ConsignacionValues;
import vos.TransaccionValues;
import vos.UsuarioValues;
import Fachada.BancAndes;

/**
 * url-pattern: /consultarPuntosAtencionGeneral
 */
public class ServletConsultarPuntosAtencionGeneral extends ASParsingServlet {


	ArrayList puntosAtencion;

	public ServletConsultarPuntosAtencionGeneral()
	{
		puntosAtencion = new ArrayList<ConsignacionValues>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doGet de ServletConsultarPuntosAtencionGeneral");

		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		imprimirConsultarPuntosAtencionGeneralInicial(pw);
		imprimirWrapper(pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doPost de ServletConsultarPuntosAtencionGeneral");
		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);

		String idPuntoAtencion1 = request.getParameter("idPuntoAtencion1");
		String idPuntoAtencion2 = request.getParameter("idPuntoAtencion2");
		
		int id1 = 0;
		int id2 = 0;
		
		try
		{
			id1 = Integer.parseInt(idPuntoAtencion1);
		}
		catch (Exception e)
		{
			imprimirConsultarPuntoAtencionGeneralError(pw, "Lo ingresado para el id del punto de atencion 1 no es un n&uacute;mero v&aacute;lido.");
			imprimirWrapper(pw);
			return;
		}
		
		try
		{
			id2 = Integer.parseInt(idPuntoAtencion2);
		}
		catch (Exception e)
		{
			imprimirConsultarPuntoAtencionGeneralError(pw, "Lo ingresado para el id del punto de atencion 2 no es un n&uacute;mero v&aacute;lido.");
			imprimirWrapper(pw);
			return;
		}
		
		try 
		{
			puntosAtencion = BancAndes.darInstancia().consultarPuntoAtencionIter4(id1, id2);
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		imprimirConsultarPuntosAtencionGeneralResultados(pw);
		imprimirWrapper(pw);
	}
	
	private void imprimirConsultarPuntosAtencionGeneralResultados(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Puntos Atenci&oacute;n</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action\"consultarPuntosAtencionGeneral\">");
		pw.println("<div class=\"form-group\">");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Punto de atencion 1:</label>");
		pw.println("<input name=\"idPuntoAtencion1\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Punto de atenci&oacute;n 2:</label>");
		pw.println("<input name=\"idPuntoAtencion2\" class=\"form-control\">");
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
		pw.println("Informaci&oacute;n clientes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>Nombres</th>");
		pw.println("<th>Nacionalidad</th>");
		pw.println("<th>Telefono</th>");
		pw.println("<th>Correo</th>");
		pw.println("<th>Ciudad</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		
		parsearTablaUsuariosTipoIter4( (ArrayList<UsuarioValues>) puntosAtencion.get(0) , pw);

		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<div class=\"col-lg-9\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Consignaciones");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Transaccion</th>");
		pw.println("<th>ID Cuenta Origen</th>");
		pw.println("<th>Tipo</th>");			
		pw.println("<th>Fecha</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		
		parsearTablaOperaciones( (ArrayList<TransaccionValues>) puntosAtencion.get(1) , pw);

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

	private void imprimirConsultarPuntosAtencionGeneralInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Puntos Atenci&oacute;n</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action\"consultarPuntosAtencionGeneral\">");
		pw.println("<div class=\"form-group\">");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Punto de atencion 1:</label>");
		pw.println("<input name=\"idPuntoAtencion1\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Punto de atenci&oacute;n 2:</label>");
		pw.println("<input name=\"idPuntoAtencion2\" class=\"form-control\">");
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
		pw.println("Informaci&oacute;n clientes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>Nombres</th>");
		pw.println("<th>Nacionalidad</th>");
		pw.println("<th>Telefono</th>");
		pw.println("<th>Correo</th>");
		pw.println("<th>Ciudad</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>Camilo</td>");
		pw.println("<td>Venezolano</td>");
		pw.println("<td>4859574</td>");
		pw.println("<td>maracucho@gmail.com</td>");
		pw.println("<td>Bolivar</td>");
		pw.println("</tr>");

		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<div class=\"col-lg-9\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Consignaciones");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Transaccion</th>");
		pw.println("<th>ID Cuenta Origen</th>");
		pw.println("<th>Tipo</th>");			
		pw.println("<th>Fecha</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>16</td>");
		pw.println("<td>5</td>");
		pw.println("<td>345000</td>");
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
	
	private void imprimirConsultarPuntoAtencionGeneralError(PrintWriter pw, String error)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Puntos Atenci&oacute;n</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action\"consultarPuntosAtencionGeneral\">");
		pw.println("<div class=\"form-group\">");


		pw.println("<div class=\"form-group\">");
		pw.println("<label>Punto de atencion 1:</label>");
		pw.println("<input name=\"idPuntoAtencion1\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Punto de atenci&oacute;n 2:</label>");
		pw.println("<input name=\"idPuntoAtencion2\" class=\"form-control\">");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Buscar\"></input>");


		pw.println("</form>");
		pw.println("<font color=\"red\">No se pudo realizar la consulta. ERROR: <br>" + error + "</font>");
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
		pw.println("Informaci&oacute;n clientes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>Nombres</th>");
		pw.println("<th>Nacionalidad</th>");
		pw.println("<th>Telefono</th>");
		pw.println("<th>Correo</th>");
		pw.println("<th>Ciudad</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>Camilo</td>");
		pw.println("<td>Venezolano</td>");
		pw.println("<td>4859574</td>");
		pw.println("<td>maracucho@gmail.com</td>");
		pw.println("<td>Bolivar</td>");
		pw.println("</tr>");

		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<div class=\"col-lg-9\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Consignaciones");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Transaccion</th>");
		pw.println("<th>ID Cuenta Origen</th>");
		pw.println("<th>Tipo</th>");			
		pw.println("<th>Fecha</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>16</td>");
		pw.println("<td>5</td>");
		pw.println("<td>345000</td>");
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
		return "Consultar Puntos Atencion";
	}

}
