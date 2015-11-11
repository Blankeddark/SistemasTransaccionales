package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.Top10Values;
import Fachada.BancAndes;

/**
 * url-pattern: /top10General
 */
public class ServletTop10General extends ASParsingServlet {

	ArrayList<Top10Values> valuesTop10;

	public ServletTop10General()
	{
		valuesTop10 = new ArrayList<Top10Values>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doGet de ServletTop10General");
		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		imprimirTop10InicialGeneral(pw);
		imprimirWrapper(pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter pw = response.getWriter();
		valuesTop10 = BancAndes.darInstancia().darTop10TransaccionesGeneral();
		
		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		imprimirTop10ResultadoGeneral(pw);
		imprimirWrapper(pw);
	}

	private void imprimirTop10InicialGeneral(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Top 10 transacciones</h1>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("<div class=\"row\">");
		pw.println("<form method=\"post\" action=\"top10Gerente\">");
		pw.println("<div><label>Desde:</label>");
		pw.println("<input class=\"form-control\"> </div>");
		pw.println("<div><label>Hasta:</label>");
		pw.println("<input class=\"form-control\"></div> ");
		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Consultar Top 10\"></input>");
		pw.println("</form>");
		pw.println("<div class=\"col-lg-9\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Info usuario");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>Top</th>");
		pw.println("<th>Tipo de operacion</th>");
		pw.println("<th>Valor promedio</th>");
		pw.println("<th>Veces realizada</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>Consignacion</td>");
		pw.println("<td>600000</td>");
		pw.println("<td>89</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>2</td>");
		pw.println("<td>Retiro</td>");
		pw.println("<td>120000</td>");
		pw.println("<td>56</td>");
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


	private void imprimirTop10ResultadoGeneral(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Top 10 transacciones</h1>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");
		pw.println("<div class=\"row\">");
		pw.println("<div><label>Desde:</label>");
		pw.println("<input class=\"form-control\"> </div>");
		pw.println("<div><label>Hasta:</label>");
		pw.println("<input class=\"form-control\"></div> ");
		pw.println("<button type=\"button\" class=\"btn btn-primary\">Consultar Top 10</button>");

		pw.println("<div class=\"col-lg-9\">");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Info usuario");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>Top</th>");
		pw.println("<th>Tipo de operacion</th>");
		pw.println("<th>Valor promedio</th>");
		pw.println("<th>Veces realizada</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");

		parsearTablaResultadoGeneral(pw);
		
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

	private void parsearTablaResultadoGeneral(PrintWriter pw)
	{
		for(Top10Values actual : valuesTop10)
		{
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>" + actual.getPuesto() + "</td>");
			pw.println("<td>" + actual.getTipo() + "</td>");
			pw.println("<td>" + actual.getPromedio() + "</td>");
			pw.println("<td>" + actual.getVecesUsada() + "</td>");
			pw.println("</tr>");
		}
	}

	public String darTituloPagina() {
		return "BancAndes - Consultar top 10 requerimientos";
	}

}
