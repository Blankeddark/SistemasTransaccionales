package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.UsuarioActivoValues;
import Fachada.BancAndes;

/**
 * url-pattern /usuarioMasActivoOficina
 */
public class ServletUsuarioMasActivoOficina extends ASServlet {

	 ArrayList<UsuarioActivoValues> usuariosMasActivos;
	 
	 public ServletUsuarioMasActivoOficina()
	 {
		 usuariosMasActivos = new ArrayList<UsuarioActivoValues>();
	 }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("En doGet de ServletUsuarioMasActivo");
		PrintWriter pw = response.getWriter();
		
		imprimirEncabezado(pw);
		imprimirSidebarGO(pw);
		imprimirUsuarioMasActivoInicial(pw);
		imprimirWrapper(pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en DoPost de ServletUsuarioMasActivo");
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarGO(pw);
		
		String tipoTransaccion = request.getParameter("tipoTransaccion");
		
		if (tipoTransaccion == null || tipoTransaccion.trim().equals("") )
		{
			imprimirUsuarioMasActivoError(pw, "Seleccione un tipo de transaccion");
			imprimirWrapper(pw);
			return;
		}
		
		else if(tipoTransaccion.equals("Abrir cuenta"))
		{
			tipoTransaccion = "AC";
		}
		
		else if(tipoTransaccion.equals("Cerrar cuenta"))
		{
			tipoTransaccion = "CC";
		}
		
		else if(tipoTransaccion.equals("Consignar"))
		{
			tipoTransaccion = "C";
		}
		
		else if(tipoTransaccion.equals("Retirar"))
		{
			tipoTransaccion = "R";
		}
		
		else if(tipoTransaccion.equals("Solicitar prestamo"))
		{
			tipoTransaccion = "SP";
		}
		
		else if(tipoTransaccion.equals("Aprobar prestamo"))
		{
			tipoTransaccion = "AP";
		}
		
		else if(tipoTransaccion.equals("Rechazar prestamo"))
		{
			tipoTransaccion="RP";
		}
		
		else if(tipoTransaccion.equals("Cerrar prestamo"))
		{
			tipoTransaccion = "CP";
		}
	
		else if(tipoTransaccion.equals("Pagar prestamo"))
		{
			tipoTransaccion = "PP";
		}
		
		else if(tipoTransaccion.equals("Pagar prestamo extraordinario"))
		{
			tipoTransaccion = "PPE";
		}
		
		usuariosMasActivos = BancAndes.darInstancia().darUsuarioActivoOficina(
				tipoTransaccion, ServletLogin.darEmpleadoActual().getOficina());

		System.out.println("Size usuariosMasActivos: " + usuariosMasActivos.size());
		for(int i = 0; i < usuariosMasActivos.size(); i++)
		{
			System.out.println( usuariosMasActivos.get(i) );
		}
		
		imprimirUsuarioMasActivoRespuesta(pw);
		imprimirWrapper(pw);
	}
	
	private void imprimirUsuarioMasActivoError(PrintWriter pw, String error)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Usuario M&aacute;s Activo</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"usuarioMasActivoOficina\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<font color=\"red\">" + error + "</font>");
		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoTransaccion\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Abrir cuenta</option>");
		pw.println("<option>Cerrar cuenta</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Retirar</option>");
		pw.println("<option>Solicitar prestamo</option>");
		pw.println("<option>Aprobar prestamo</option>");
		pw.println("<option>Rechazar prestamo</option>");
		pw.println("<option>Cerrar prestamo</option>");
		pw.println("<option>Pagar prestamo</option>");
		pw.println("<option>Pagar prestamo extraordinario</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("</div>");
		pw.println("<br>");
		pw.println("<br>");
		
		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Consultar\"></input>");

		pw.println("</form>");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-6 (nested) -->");

		pw.println("</div>");
		pw.println("<!-- /.row (nested) -->");
		pw.println("</div>");
		pw.println("<!-- /.panel-body -->  ");

		pw.println("</div>");


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
	}

	private void imprimirUsuarioMasActivoInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Usuario M&aacute;s Activo</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"usuarioMasActivoOficina\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoTransaccion\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Abrir cuenta</option>");
		pw.println("<option>Cerrar cuenta</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Retirar</option>");
		pw.println("<option>Solicitar prestamo</option>");
		pw.println("<option>Aprobar prestamo</option>");
		pw.println("<option>Rechazar prestamo</option>");
		pw.println("<option>Cerrar prestamo</option>");
		pw.println("<option>Pagar prestamo</option>");
		pw.println("<option>Pagar prestamo extraordinario</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("</div>");
		pw.println("<br>");
		pw.println("<br>");
		
		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Consultar\"></input>");

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
		pw.println("Info usuario");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>Nombre</th>");
		pw.println("<th>Correo</th>");
		pw.println("<th>Numero ID</th>");
		pw.println("<th>Tipo ID</th>");
		pw.println("<th>Tipo De Usuario</th>");
pw.println("<th>Veces realizadas</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>Sergio Madera</td>");
		pw.println("<td>sm.madera10@bancandes.com</td>");
		pw.println("<td>999897</td>");
		pw.println("<td>TI</td>");
		pw.println("<td>Cliente</td>");
		pw.println("<td>5</td>");
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

	}
	
	private void imprimirUsuarioMasActivoRespuesta(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Usuario M&aacute;s Activo</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"usuarioMasActivoOficina\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoTransaccion\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Abrir cuenta</option>");
		pw.println("<option>Cerrar cuenta</option>");
		pw.println("<option>Consignar</option>");
		pw.println("<option>Retirar</option>");
		pw.println("<option>Solicitar prestamo</option>");
		pw.println("<option>Aprobar prestamo</option>");
		pw.println("<option>Rechazar prestamo</option>");
		pw.println("<option>Cerrar prestamo</option>");
		pw.println("<option>Pagar prestamo</option>");
		pw.println("<option>Pagar prestamo extraordinario</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("</div>");
		pw.println("<br>");
		pw.println("<br>");
		
		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Consultar\"></input>");

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
		pw.println("Info usuario");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>Nombre</th>");
		pw.println("<th>Correo</th>");
		pw.println("<th>Numero ID</th>");
		pw.println("<th>Tipo ID</th>");
		pw.println("<th>Tipo De Usuario</th>");
		pw.println("<th>Veces realizadas</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		
		parsearTablaUsuarioMasActivo(pw);
		
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
	}
	
	private void parsearTablaUsuarioMasActivo(PrintWriter pw)
	{
		for(UsuarioActivoValues usuarioActual : usuariosMasActivos)
		{
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>" + usuarioActual.getNombre() + "</td>");
			pw.println("<td>" + usuarioActual.getCorreo() + "</td>");
			pw.println("<td>" + usuarioActual.getNumeroID() + "</td>");
			pw.println("<td>" + usuarioActual.getTipoID() + "</td>");
			pw.println("<td>" + usuarioActual.getTipoUsuario() + "</td>");
			pw.println("<td>" + usuarioActual.getNumTransacciones() + "</td>");
		}

	}

	public String darTituloPagina() {
		return "BancAndes - Usuario más activo";
	}

}
