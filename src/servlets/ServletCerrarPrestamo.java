package servlets;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOS.CerrarPrestamoDAO;
import DAOS.ConsultarCuentasDAO;
import Fachada.BancAndes;


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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doPost de ServletCerrarPrestamo");
		ServletContext context = request.getServletContext();
		String path = context.getRealPath("/");
		System.out.println(path);
		PrintWriter pw = response.getWriter();
		String numPrestamo = request.getParameter("numPrestamo");
		System.out.println(numPrestamo);
		
		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		
		int idPrestamo = 0;
		CerrarPrestamoDAO cpd = new CerrarPrestamoDAO();
		ConsultarCuentasDAO ccd = new ConsultarCuentasDAO();
		int oficina = 1;
		
		try
		{
			idPrestamo = Integer.parseInt(numPrestamo);
		}
		catch (Exception e2)
		{
			imprimirCerrarPrestamoError(pw, "El numero ingresado no es valido");
			e2.printStackTrace();
			return;
		}
		
		try
		{
			cpd.cerrarPrestamoExistentePagado(idPrestamo, oficina);
			
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			imprimirCerrarPrestamoError(pw, "No se pudo cerrar el prestamo.");
			imprimirWrapper(pw);
			e.printStackTrace();
			return;
		}
		
//		try
//		{
//			BancAndes.darInstancia().cerrarPrestamoRF9(idPrestamo, oficina);
//		}
//		catch(Exception e3)
//		{
//			imprimirCerrarPrestamoError(pw, e3.getMessage());
//			imprimirWrapper(pw);
//			return;
//		}
		
		imprimirCerrarPrestamoExitoso(pw);
		imprimirWrapper(pw);
	}
	

	private void imprimirCerrarPrestamoError(PrintWriter pw, String error)
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
		pw.println("<form role=\"form\" method=\"post\" action=\"cerrarPrestamo\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Numero de pr&eacute;stamo:</label>");
		pw.println("<input class=\"form-control\" name=\"numPrestamo\">");
		pw.println("</div>");
		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-danger\" value=\"Cerrar\"></input> <br>");
		pw.println("<font color=\"red\">Hubo un error cerrando el prestamo. <br> Error: " + error +"</font>");

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
	
	private void imprimirCerrarPrestamoExitoso(PrintWriter pw)
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
		pw.println("<form role=\"form\" method=\"post\" action=\"cerrarPrestamo\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Numero de pr&eacute;stamo:</label>");
		pw.println("<input class=\"form-control\" name=\"numPrestamo\">");
		pw.println("</div>");
		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-danger\" value=\"Cerrar\"></button> <br>");
		pw.println("<font color=\"green\">¡Prestamo cerrado exitosamente!</font>");

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
		pw.println("<form role=\"form\" method=\"post\" action=\"cerrarPrestamo\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Numero de pr&eacute;stamo:</label>");
		pw.println("<input name=\"numPrestamo\" id=\"numPrestamo\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-danger\" value=\"Cerrar\"></input>");

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
		return "BancAndes - Cerrar préstamo";
	}

}
