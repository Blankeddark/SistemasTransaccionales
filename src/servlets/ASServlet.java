package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class ASServlet extends HttpServlet {
	
	public abstract String darTituloPagina();

	/**
	 * Imprime el encabezado regular, utilizado en la mayoría de los HTML (sin el script que está
	 * comentado en la mayoría), con el título que se retorna en darTituloPagina(). 
	 * @param response
	 * @throws IOException si hay un problema obteniendo el writer
	 */
	public void imprimirEncabezado(PrintWriter respuesta) throws IOException
	{
		respuesta.println("<!DOCTYPE html>");
		respuesta.println("<html lang=\"en\">");

		respuesta.println("<head>");

		    respuesta.println("<meta charset=\"utf-8\">");
		    respuesta.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		    respuesta.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		    respuesta.println("<meta name=\"description\" content=\"\">");
		    respuesta.println("<meta name=\"author\" content=\"\">");

		    respuesta.println("<title>"+ darTituloPagina() +"</title>");

		    respuesta.println("<!-- Latest compiled and minified CSS -->");
		    respuesta.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">");

		    respuesta.println("<!-- Optional theme -->");
		    respuesta.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css\">");

		    respuesta.println("<!-- Latest compiled and minified JavaScript -->");
		    respuesta.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>");
		    
		respuesta.println("</head>");
	}
	
	/**
	 * Imprime el encabezado regular, utilizado en la mayoría de los HTML (sin el script que está
	 * comentado en la mayoría), con el título que se ingresa por parámetro
	 * @param response
	 * @throws IOException si hay un problema obteniendo el writer
	 */
	public void imprimirEncabezado(PrintWriter respuesta, String titulo) throws IOException
	{
		respuesta.println("<!DOCTYPE html>");
		respuesta.println("<html lang=\"en\">");

		respuesta.println("<head>");

		    respuesta.println("<meta charset=\"utf-8\">");
		    respuesta.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		    respuesta.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		    respuesta.println("<meta name=\"description\" content=\"\">");
		    respuesta.println("<meta name=\"author\" content=\"\">");

		    respuesta.println("<title>"+ titulo +"</title>");

		    respuesta.println("<!-- Bootstrap Core CSS -->");
		    respuesta.println("<link href=\"../bower_components/bootstrap/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");

		    respuesta.println("<!-- MetisMenu CSS -->");
		    respuesta.println("<link href=\"../bower_components/metisMenu/dist/metisMenu.min.css\" rel=\"stylesheet\">");

		    respuesta.println("<!-- Custom CSS -->");
		    respuesta.println("<link href=\"../dist/css/sb-admin-2.css\" rel=\"stylesheet\">");

		    respuesta.println("<!-- Custom Fonts -->");
		    respuesta.println("<link href=\"../bower_components/font-awesome/css/font-awesome.min.css\" rel=\"stylesheet\" type=\"text/css\">");

		respuesta.println("</head>");
	}
}
