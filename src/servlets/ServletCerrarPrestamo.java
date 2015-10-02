package servlets;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletCerrarPrestamo extends ASServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doGet de ServletCerrarPrestamo");
		
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarGO(pw);
		imprimirCerrarPrestamoInicial(pw);
		imprimirWrapper(pw);
	}
	
	private void imprimirCerrarPrestamoInicial(PrintWriter pw)
	{
		 pw.println("<div id=\"page-wrapper\">");
         pw.println("<div class=\"row\">");
             pw.println("<div class=\"col-lg-12\">");
                 pw.println("<h1 class=\"page-header\">Cerrar Pr&eacute;stamo</h1>");
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
                                 pw.println("<form role=\"form\">");
                                     pw.println("<div class=\"form-group\">");
                                         pw.println("<label>Numero de pr&eacute;stamo:</label>");
                                         pw.println("<input class=\"form-control\">");
                                     pw.println("</div>");
                                     pw.println("<br>");
                                     
                                     pw.println("<button type=\"button\" class=\"btn btn-danger\">Cerrar</button>");
                                     
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
	}
	public String darTituloPagina() {
		return "BancAndes - Cerrar pr�stamo";
	}

}