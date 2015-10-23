package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Fachada.BancAndes;

/**
 * url-pattern: /asociarCuentaCliente
 */
public class ServletAsociarCuentaEmpleadoCliente extends ASServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("En doGet de ServletAsociarCuentaEmpleadoCliente");

		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarCliente(pw);
		imprimirAsociarCuentaClienteInicial(pw);
		imprimirWrapper(pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("En doPost de ServletAsociaCuentaEmpleadoCliente");
		PrintWriter pw = response.getWriter();
		
		imprimirEncabezado(pw);
		imprimirSidebarCliente(pw);
		
		String correoEmpleador = ServletLogin.darUsuarioActual().getCorreo();
		String cuentaAAsociar = request.getParameter("cuentaAAsociar");
		int idCuentaAsociar = 0;
		String correoEmpleado = request.getParameter("correoEmpleado");
		
		String cuentaEmpleado = request.getParameter("cuentaEmpleado");
		int idCuentaEmpleado = 0;
		String valorAPagar = request.getParameter("valorAPagar");
		int valor = 0;
		String frecuenciaPago = request.getParameter("frecuenciaPago");
		
		try
		{
			idCuentaAsociar = Integer.parseInt(cuentaAAsociar);
		}
		catch(Exception e)
		{
			imprimirAsociarCuentaClienteError(pw, "Lo ingresado para el valor de la cuenta a asociar no es n&uacute;mero v&aacute;lido");
			imprimirWrapper(pw);
			return;
		}
		
		if(correoEmpleado.trim().equals("") )
		{
			imprimirAsociarCuentaClienteError(pw, "Por favor ingresa un correo para el empleado");
			imprimirWrapper(pw);
			return;
		}
		
		try
		{
			idCuentaEmpleado = Integer.parseInt(cuentaEmpleado);
		}
		catch(Exception e)
		{
			imprimirAsociarCuentaClienteError(pw, "Lo ingresado para el id de la cuenta empleado no es n&uacute;mero v&aacute;lido"); 
		}
		
		try
		{
			valor = Integer.parseInt(valorAPagar);
		}
		catch(Exception e)
		{
			imprimirAsociarCuentaClienteError(pw, "Lo ingresado para el valor a pagar no es n&uacute;mero v&aacute;lido"); 
		}
		
		if(frecuenciaPago.trim().equals(""))
		{
			imprimirAsociarCuentaClienteError(pw, "Seleccione una frecuencia de pago");
			imprimirWrapper(pw);
			return;
		}
		
		BancAndes.darInstancia().asociarCuentaAEmpleado(correoEmpleador, idCuentaAsociar, correoEmpleado, idCuentaEmpleado, valor, frecuenciaPago);
		imprimirAsociarCuentaClienteExito(pw);
		imprimirWrapper(pw);
	}

	private void imprimirAsociarCuentaClienteError(PrintWriter pw, String error)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Asociar cuenta a un empleado</h1>");
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
		pw.println("<form role=\"form\" action=\"asociarCuentaCliente\" method=\"post\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cuenta a asociar:</label>");
		pw.println("<input name=\"cuentaAAsociar\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo empleado:</label>");
		pw.println("<input name=\"correoEmpleado\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cuenta Empleado:</label>");
		pw.println("<input name=\"cuentaEmpleado\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Valor a pagar:</label>");
		pw.println("<input name=\"valorAPagar\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Frecuencia de pago:</label>");
		pw.println("<select name=\"frecuenciaPago\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Quincenal</option>");
		pw.println("<option>Mensual</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Asociar\"></input>");

		pw.println("</form>");
		pw.println("<font color=\"red\">No se pudo realizar la operaci&oacute;n. ERROR:<br>" + error);
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
		pw.println("<!-- /#wrapper -->");
	}
	
	private void imprimirAsociarCuentaClienteExito(PrintWriter pw)
	{

			pw.println("<div id=\"page-wrapper\">");
			pw.println("<div class=\"row\">");
			pw.println("<div class=\"col-lg-12\">");
			pw.println("<h1 class=\"page-header\">Asociar cuenta a un empleado</h1>");
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
			pw.println("<form role=\"form\" action=\"asociarCuentaCliente\" method=\"post\">");
			pw.println("<div class=\"form-group\">");
			pw.println("<label>Cuenta a asociar:</label>");
			pw.println("<input name=\"cuentaAAsociar\" class=\"form-control\">");
			pw.println("</div>");
			pw.println("<div class=\"form-group\">");
			pw.println("<label>Correo empleado:</label>");
			pw.println("<input name=\"correoEmpleado\" class=\"form-control\">");
			pw.println("</div>");
			pw.println("<div class=\"form-group\">");
			pw.println("<label>Cuenta Empleado:</label>");
			pw.println("<input name=\"cuentaEmpleado\" class=\"form-control\">");
			pw.println("</div>");
			pw.println("<div class=\"form-group\">");
			pw.println("<label>Valor a pagar:</label>");
			pw.println("<input name=\"valorAPagar\" class=\"form-control\">");
			pw.println("</div>");
			pw.println("<div class=\"form-group\">");
			pw.println("<label>Frecuencia de pago:</label>");
			pw.println("<select name=\"frecuenciaPago\" class=\"form-control\">");
			pw.println("<option> </option>");
			pw.println("<option>Quincenal</option>");
			pw.println("<option>Mensual</option>");
			pw.println("</select>");
			pw.println("</div>");

			pw.println("<br>");

			pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Asociar\"></input>");

			pw.println("</form>");
			pw.println("<font color=\"green\">La operaci&oacute;n fue realizada &eacute;xitosamente</font>");
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
			pw.println("<!-- /#wrapper -->");
		
	}
	
	private void imprimirAsociarCuentaClienteInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Asociar cuenta a un empleado</h1>");
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
		pw.println("<form role=\"form\" action=\"asociarCuentaCliente\" method=\"post\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cuenta a asociar:</label>");
		pw.println("<input name=\"cuentaAAsociar\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo empleado:</label>");
		pw.println("<input name=\"correoEmpleado\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Cuenta Empleado:</label>");
		pw.println("<input name=\"cuentaEmpleado\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Valor a pagar:</label>");
		pw.println("<input name=\"valorAPagar\" class=\"form-control\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Frecuencia de pago:</label>");
		pw.println("<select name=\"frecuenciaPago\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Quincenal</option>");
		pw.println("<option>Mensual</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<br>");

		pw.println("<input type=\"submit\" class=\"btn btn-primary\" value=\"Asociar\"></input>");

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
		pw.println("<!-- /#wrapper -->");
	}

	public String darTituloPagina()
	{
		return "Asociar cuenta a empleado";
	}

}
