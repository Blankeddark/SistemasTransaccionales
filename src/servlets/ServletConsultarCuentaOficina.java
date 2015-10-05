package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Fachada.BancAndes;
import vos.CuentaValues;

/**
 * url-pattern: /consultarCuentaOficina
 */
public class ServletConsultarCuentaOficina extends ASParsingServlet {

	ArrayList<CuentaValues> cuentas;

	public ServletConsultarCuentaOficina()
	{
		cuentas = new ArrayList<CuentaValues>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doGet de ServletConsultarCuentaGerente");

		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarGO(pw);
		imprimirConsultarCuentasOficinaInicial(pw);
		imprimirWrapper(pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("doPost en ServletConsultarCuentaOficina");
		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);
		imprimirSidebarGO(pw);

		BancAndes bancAndes = BancAndes.darInstancia();


		String ordenarPor = request.getParameter("ordenarPor");
		String agruparPor = request.getParameter("agruparPor");

		if(ordenarPor == null)
		{
			ordenarPor = "";
		}

		if(agruparPor == null)
		{
			agruparPor = "";
		}

		int idOficina = ServletLogin.darEmpleadoActual().getOficina();

		cuentas = bancAndes.darCuentasGerenteOficina(idOficina, ordenarPor, agruparPor, "DESC");
		System.out.println(cuentas);

		for(int i = 0; i < cuentas.size(); i++)
		{
			System.out.println(cuentas.get(i));
		}

		String tipoCuenta = request.getParameter("tipoCuenta");

		if (tipoCuenta != null)
		{
			if ( !tipoCuenta.trim().equals(""))
			{
				filtrarCuentasPorTipoCuenta(tipoCuenta, cuentas);
			}
		}

		String correoCliente = request.getParameter("correoCliente");

		if (correoCliente != null)
		{
			if( !correoCliente.trim().equals(""))
			{
				filtrarCuentasPorCorreoCliente(correoCliente, cuentas);
			}
		}

		String rangoSaldoInicial = request.getParameter("rangoSaldoInicial");
		String rangoSaldoFinal = request.getParameter("rangoSalgoFinal");
		
		boolean haySaldoInicial = false;
		boolean haySaldoFinal = false;
		
		if(rangoSaldoInicial != null)
		{
			if( !rangoSaldoInicial.trim().equals(""))
			{
				try
				{
					Integer.parseInt(rangoSaldoInicial);
					haySaldoInicial = true;
				}
				catch(Exception e)
				{
					imprimirConsultarCuentasOficinaError(pw, "El valor ingresado en Desde (saldoInicial) no es un n&uacute;mero v&aacute;lido");
					imprimirWrapper(pw);
					return;
				}
			}
		}

		if(rangoSaldoFinal != null)
		{
			if( !rangoSaldoFinal.trim().equals(""))
			{
				try
				{
					Integer.parseInt(rangoSaldoFinal);
					haySaldoFinal = true;
				}
				catch(Exception e)
				{
					imprimirConsultarCuentasOficinaError(pw, "El valor ingresado en Hasta (saldoFinal) no es un n&uacute;mero v&aacute;lido");
					imprimirWrapper(pw);
					return;
				}
			}
		}

		if(haySaldoInicial && haySaldoFinal)
		{
			filtrarCuentasPorRangoSaldo(
					Integer.parseInt(rangoSaldoInicial),
					Integer.parseInt(rangoSaldoFinal), cuentas);
		}

		//Fechas, no sirven.
		String fechaApertura = request.getParameter("fechaApertura");
		String fechaUltimoMovimiento = request.getParameter("fechaUltimoMovimiento");

		imprimirConsultarCuentasOficinaResultado(pw);
		imprimirWrapper(pw);
	}

	private void imprimirConsultarCuentasOficinaInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Cuentas</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"consultarCuentaOficina\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoCuenta\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Ahorros</option>");
		pw.println("<option>Corriente</option>");
		pw.println("<option>CDT</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<label>Desde:</label>");
		pw.println("<input name=\"rangoSaldoInicial\" class=\"form-control\"> ");

		pw.println("<label>Hasta:</label>");
		pw.println("<input name=\"rangoSalgoFinal\" class=\"form-control\"></div>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha de apertura:</label>");
		pw.println("<input name=\"fechaApertura\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha de &uacute;ltimo movimiento:</label>");
		pw.println("<input name=\"fechaUltimoMovimiento\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo Cliente:</label>");
		pw.println("<div><input name=\"correoCliente\" class=\"form-control\"> ");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de cuenta</option>");
		pw.println("<option>Saldo</option>");
		pw.println("<option>Fecha de apertura</option>");
		pw.println("<option>Fecha &uacute;ltimo movimiento</option>");
		pw.println("<option>Cliente</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Agrupar por:</label>");
		pw.println("<select name=\"agruparPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de cuenta</option>");
		pw.println("<option>Fecha de apertura</option>");
		pw.println("<option>Fecha &uacute;ltimo movimiento</option>");
		pw.println("<option>Cliente</option>");
		pw.println("</select>");
		pw.println("</div>");
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
		pw.println("Cuentas resultantes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Cuenta</th>");
		pw.println("<th>Correo Dueño</th>");
		pw.println("<th>Tipo De Cuenta</th>");
		pw.println("<th>Oficina Asociada</th>");
		pw.println("<th>Fecha &uacute;ltimo movimiento</th>");
		pw.println("<th>Saldo</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>ac.zuleta10@bancandes.com</td>");
		pw.println("<td>Ahorros</td>");
		pw.println("<td class=\"center\">1</td>");
		pw.println("<td>2014/09/23</td>");
		pw.println("<td class=\"center\">50000</td>");
		pw.println("<td>Activa</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"even gradeC\">");
		pw.println("<td>2</td>");
		pw.println("<td>sm.madera10@bancandes.com</td>");
		pw.println("<td>Corriente</td>");
		pw.println("<td class=\"center\">1</td>");
		pw.println("<td>2015/08/13</td>");
		pw.println("<td class=\"center\">180000</td>");
		pw.println("<td>Activa</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"odd gradeA\">");
		pw.println("<td>3</td>");
		pw.println("<td>asteam@bancandes.com</td>");
		pw.println("<td>CDT</td>");
		pw.println("<td class=\"center\">2</td>");
		pw.println("<td>2014/11/13</td>");
		pw.println("<td class=\"center\">400000</td>");
		pw.println("<td>Activa</td>");

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

	private void imprimirConsultarCuentasOficinaResultado(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Cuentas</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"consultarCuentaOficina\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoCuenta\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Ahorros</option>");
		pw.println("<option>Corriente</option>");
		pw.println("<option>CDT</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<label>Desde:</label>");
		pw.println("<input name=\"rangoSaldoInicial\" class=\"form-control\"> ");

		pw.println("<label>Hasta:</label>");
		pw.println("<input name=\"rangoSalgoFinal\" class=\"form-control\"></div>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha de apertura:</label>");
		pw.println("<input name=\"fechaApertura\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha de &uacute;ltimo movimiento:</label>");
		pw.println("<input name=\"fechaUltimoMovimiento\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo Cliente:</label>");
		pw.println("<div><input name=\"correoCliente\" class=\"form-control\"> ");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de cuenta</option>");
		pw.println("<option>Saldo</option>");
		pw.println("<option>Fecha de apertura</option>");
		pw.println("<option>Fecha &uacute;ltimo movimiento</option>");
		pw.println("<option>Cliente</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Agrupar por:</label>");
		pw.println("<select name=\"agruparPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de cuenta</option>");
		pw.println("<option>Fecha de apertura</option>");
		pw.println("<option>Fecha &uacute;ltimo movimiento</option>");
		pw.println("<option>Cliente</option>");
		pw.println("</select>");
		pw.println("</div>");
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
		pw.println("Cuentas resultantes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Cuenta</th>");
		pw.println("<th>Correo Dueño</th>");
		pw.println("<th>Tipo De Cuenta</th>");
		pw.println("<th>Oficina Asociada</th>");
		pw.println("<th>Fecha &uacute;ltimo movimiento</th>");
		pw.println("<th>Saldo</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");

		parsearTablaCuentasTipo2(cuentas, pw);

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

	private void imprimirConsultarCuentasOficinaError(PrintWriter pw, String error)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Cuentas</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"consultarCuentaOficina\">");
		pw.println("<div class=\"form-group\">");
		pw.println("<font color=\"red\">" + error + "</font>");
		pw.println("<label>Tipo:</label>");
		pw.println("<select name=\"tipoCuenta\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Ahorros</option>");
		pw.println("<option>Corriente</option>");
		pw.println("<option>CDT</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<label>Desde:</label>");
		pw.println("<input name=\"rangoSaldoInicial\" class=\"form-control\"> ");

		pw.println("<label>Hasta:</label>");
		pw.println("<input name=\"rangoSalgoFinal\" class=\"form-control\"></div>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha de apertura:</label>");
		pw.println("<input name=\"fechaApertura\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha de &uacute;ltimo movimiento:</label>");
		pw.println("<input name=\"fechaUltimoMovimiento\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Correo Cliente:</label>");
		pw.println("<div><input name=\"correoCliente\" class=\"form-control\"> ");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de cuenta</option>");
		pw.println("<option>Saldo</option>");
		pw.println("<option>Fecha de apertura</option>");
		pw.println("<option>Fecha &uacute;ltimo movimiento</option>");
		pw.println("<option>Cliente</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Agrupar por:</label>");
		pw.println("<select name=\"agruparPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de cuenta</option>");
		pw.println("<option>Fecha de apertura</option>");
		pw.println("<option>Fecha &uacute;ltimo movimiento</option>");
		pw.println("<option>Cliente</option>");
		pw.println("</select>");
		pw.println("</div>");
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
		pw.println("Cuentas resultantes");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Cuenta</th>");
		pw.println("<th>Correo Dueño</th>");
		pw.println("<th>Tipo De Cuenta</th>");
		pw.println("<th>Oficina Asociada</th>");
		pw.println("<th>Fecha &uacute;ltimo movimiento</th>");
		pw.println("<th>Saldo</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>ac.zuleta10@bancandes.com</td>");
		pw.println("<td>Ahorros</td>");
		pw.println("<td class=\"center\">1</td>");
		pw.println("<td>2014/09/23</td>");
		pw.println("<td class=\"center\">50000</td>");
		pw.println("<td>Activa</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"even gradeC\">");
		pw.println("<td>2</td>");
		pw.println("<td>sm.madera10@bancandes.com</td>");
		pw.println("<td>Corriente</td>");
		pw.println("<td class=\"center\">1</td>");
		pw.println("<td>2015/08/13</td>");
		pw.println("<td class=\"center\">180000</td>");
		pw.println("<td>Activa</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"odd gradeA\">");
		pw.println("<td>3</td>");
		pw.println("<td>asteam@bancandes.com</td>");
		pw.println("<td>CDT</td>");
		pw.println("<td class=\"center\">2</td>");
		pw.println("<td>2014/11/13</td>");
		pw.println("<td class=\"center\">400000</td>");
		pw.println("<td>Activa</td>");

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
		return "BancAndes - Consultar cuentas";
	}

}
