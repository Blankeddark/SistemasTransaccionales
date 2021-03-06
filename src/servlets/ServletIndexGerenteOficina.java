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
