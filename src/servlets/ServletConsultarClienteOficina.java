package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.ClienteValues;
import vos.CuentaValues;
import vos.PrestamoValues;
import Fachada.BancAndes;

/**
 * url-pattern: \consultarClienteOficina 
 */
public class ServletConsultarClienteOficina extends ASParsingServlet {

	ArrayList rtaClientes;
	
	public ServletConsultarClienteOficina()
	{
		rtaClientes = new ArrayList();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doGet de ServletConsultarClienteOficina");

		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirSidebarGG(pw);
		imprimirConsultarClienteOficinaInicial(pw);
		imprimirWrapper(pw);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("en doPost de ServletConsultaClienteOficina");
		PrintWriter pw = response.getWriter();
		
		//Información ordenar
		String ordenarPor = request.getParameter("ordenarPor");
		String agruparPor = request.getParameter("agruparPor");
		
		BancAndes bancAndes = BancAndes.darInstancia();
		
		if (ordenarPor == null)
		{
			ordenarPor = "";
		}
		
		else
		{
			if (ordenarPor.equals("ID Cuenta"))
			{
				ordenarPor = "cuenta";
			}
			
			else if(ordenarPor.equals("ID Prestamo"))
			{
				ordenarPor = "Prestamo";
			}
			
			else if(ordenarPor.equals("ID Oficina"))
			{
				ordenarPor = "Oficina";
			}
			
			else if(ordenarPor.equals("ID Transaccion"))
			{
				ordenarPor = "Transaccion";
			}

		}
		
		if (agruparPor == null)
		{
			agruparPor = "";
		}
		else
		{
			agruparPor = ""; //TODO
		}
		
		//Información del cliente
		String correoCliente = request.getParameter("correoCliente");
		
		if (correoCliente != null)
		{
			if (!correoCliente.trim().equals(""))
			{
				System.out.println("pasa por correoCliente");
				rtaClientes = bancAndes.consultarClienteEspecifico(correoCliente, ordenarPor, "DESC", agruparPor);
				System.out.println("¿Es null? ---- " + rtaClientes);
				System.out.println("Tamaño: " + rtaClientes.size());
				
				for(int i = 0; i < rtaClientes.size(); i++)
				{
					System.out.println( rtaClientes.get(i) );
				}
				
				if ( rtaClientes.isEmpty() )
				{
					imprimirInformacionError(pw, "No existe un cliente con el correo ingresado.");
				}
			}
		}
		
		else
		{
			imprimirInformacionError(pw, "Ingrese un correo para el cliente a buscar");
			imprimirWrapper(pw);
			return;
		}
		
		ArrayList<CuentaValues> cuentas = (ArrayList<CuentaValues>) rtaClientes.get(1);
		
		filtrarCuentasPorOficina( ServletLogin.darEmpleadoActual().getOficina(), cuentas );
		
		//Información de la cuenta
		String tipoCuenta = request.getParameter("tipoCuenta");
		String idCuentaCliente = request.getParameter("idCuentaCliente");
		String saldoCuenta = request.getParameter("saldoCuenta");
		
		if (tipoCuenta != null)
		{
			if ( !tipoCuenta.trim().equals(""))
			{
				filtrarCuentasPorTipoCuenta(tipoCuenta, cuentas);
			}
		}
		
		if (idCuentaCliente != null)
		{
			if(!idCuentaCliente.trim().equals(""))
			{
				int idCuenta = 0;
				try
				{
					idCuenta = Integer.parseInt(idCuentaCliente);
				}
				
				catch(Exception e)
				{
					imprimirInformacionError(pw, "Lo ingresado para la id de la cuenta no es un n&uacute;mero v&aacute;lido");
					imprimirWrapper(pw);
					return;
				}
				
				filtrarCuentasPorIdCuenta(idCuenta, cuentas);
			}
		}
		
		if (saldoCuenta != null)
		{
			if(!saldoCuenta.trim().equals(""))
			{
				try
				{
					Integer.parseInt(saldoCuenta);
				}
				
				catch(Exception e)
				{
					imprimirInformacionError(pw, "Lo ingresado para el saldo de la cuenta no es un n&uacute;mero v&aacute;lido");
					imprimirWrapper(pw);
					return;
				}
				
				filtrarCuentasPorSaldo(Integer.parseInt(saldoCuenta), cuentas);
			}
		}
		
		//Información transacción. TODO, filtros aún no funcionando.
		String fechaInicialTransaccion = request.getParameter("fechaInicialTransaccion");
		String fechaFinalTransaccion = request.getParameter("fechaFinalTransaccion");
		String valorTransaccion = request.getParameter("valorTransaccion");
		
	}
	
	private void imprimirConsultarClienteOficinaInicial(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Un Cliente</h1>");
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
		pw.println("<form role=\"form\" method=\"post\" action=\"consultarClienteOficina\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Correo Cliente</label>");
		pw.println("<input name=\"correoCliente\" class=\"form-control\"> ");


		pw.println("<label>Tipo De Cuenta</label>");
		pw.println("<select name=\"tipoCuenta\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Ahorros</option>");
		pw.println("<option>Corriente</option>");
		pw.println("<option>CDT</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<label>ID Cuenta</label>");
		pw.println("<input name=\"idCuentaCliente\" class=\"form-control\"> ");
		pw.println("<label>Saldo Cuenta:</label>");
		pw.println("<input name=\"saldoCuenta\" class=\"form-control\"></div>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha inicial transaccion:</label>");
		pw.println("<input name=\"fechaInicialTransaccion\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha final transaccion:</label>");
		pw.println("<input name=\"fechaFinalTransaccion\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Valor Transaccion:</label>");
		pw.println("<div><input name=\"valorTransaccion\" class=\"form-control\"> ");
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
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de Cuenta</option>");
		pw.println("<option>ID Cuenta</option>");
		pw.println("<option>ID Prestamo</option>");
		pw.println("<option>ID Oficina</option>");
		pw.println("<option>ID Transaccion</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Agrupar por:</label>");
		pw.println("<select name=\"agruparPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de Cuenta</option>");
		pw.println("<option>ID Cuenta</option>");
		pw.println("<option>ID Prestamo</option>");
		pw.println("<option>ID Oficina</option>");
		pw.println("<option>ID Transaccion</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"panel-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>Nombre Cliente</th>");
		pw.println("<th>Tipo De Persona</th>");
		pw.println("<th>Correo</th>");
		pw.println("<th>Ciudad</th>");
		pw.println("<th>Nacionalidad</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>Juan</td>");
		pw.println("<td>Natural</td>");
		pw.println("<td>jc@bancandes.com</td>");
		pw.println("<td>Barranquilla</td>");
		pw.println("<td>Colombiano</td>");

		pw.println("</tr>");
		
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Cuentas Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Cuenta</th>");
		pw.println("<th>Tipo De Cuenta</th>");
		pw.println("<th>Oficina Asociada</th>");
		pw.println("<th>Fecha Ultimo Movimiento</th>");
		pw.println("<th>Saldo</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>1</td>");
		pw.println("<td>Ahorros</td>");
		pw.println("<td>3</td>");
		pw.println("<td>2014/11/23</td>");
		pw.println("<td>350000</td>");
		pw.println("<td class=\"center\">Activa</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"even gradeC\">");
		pw.println("<td>3</td>");
		pw.println("<td>Corriente</td>");
		pw.println("<td>3</td>");
		pw.println("<td class=\"center\">2015/08/29</td>");
		pw.println("<td>3500000</td>");
		pw.println("<td class=\"center\">Activa</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"odd gradeA\">");
		pw.println("<td>6</td>");
		pw.println("<td>CDT</td>");
		pw.println("<td>1</td>");
		pw.println("<td class=\"center\">2013/06/07</td>");
		pw.println("<td>0</td>");
		pw.println("<td class=\"center\">Inactiva</td> ");
		pw.println("</tr>");
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");

		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Prestamos Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Prestamo</th>");
		pw.println("<th>Monto Prestado</th>");
		pw.println("<th>Tipo</th>");
		pw.println("<th>Saldo Pendiente</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>8</td>");
		pw.println("<td>200000</td>");
		pw.println("<td>Estudio</td>");
		pw.println("<td class=\"center\">100000</td>");
		pw.println("<td>Abierto</td>");
		pw.println("</tr>");
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Operaciones Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Transaccion</th>");
		pw.println("<th>Tipo</th>");
		pw.println("<th>Fecha de realizacion</th>");
		pw.println("<th>Punto de atencion</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>6</td>");
		pw.println("<td>Pago Prestamo</td>");
		pw.println("<td>2015/07/08</td>");
		pw.println("<td>8</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>8</td>");
		pw.println("<td>Pago Extraordinario</td>");
		pw.println("<td>2015/06/07</td>");
		pw.println("<td>8</td>");
		pw.println("</tr>");
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("</div>");

		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");

		pw.println("</div>");
		pw.println("<!-- /#page-wrapper -->");

		pw.println("</div>");
	}
	
	private void imprimirInformacionRespuesta(PrintWriter pw)
	{
		pw.println("<div id=\"page-wrapper\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-lg-12\">");
		pw.println("<h1 class=\"page-header\">Consultar Un Cliente</h1>");
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
		pw.println("<form role=\"form\">");
		pw.println("<div class=\"form-group\">");

		pw.println("<label>Correo Cliente</label>");
		pw.println("<input name=\"correoCliente\" class=\"form-control\"> ");


		pw.println("<label>Tipo De Cuenta</label>");
		pw.println("<select name=\"tipoCuenta\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Ahorros</option>");
		pw.println("<option>Corriente</option>");
		pw.println("<option>CDT</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<label>ID Cuenta</label>");
		pw.println("<input name=\"idCuentaCliente\" class=\"form-control\"> ");
		pw.println("<label>Saldo Cuenta:</label>");
		pw.println("<input name=\"saldoCuenta\" class=\"form-control\"></div>");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha inicial transaccion:</label>");
		pw.println("<input name=\"fechaInicialTransaccion\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");

		pw.println("<div class=\"form-group\">");
		pw.println("<label>Fecha final transaccion:</label>");
		pw.println("<input name=\"fechaFinalTransaccion\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Valor Transaccion:</label>");
		pw.println("<div><input name=\"valorTransaccion\" class=\"form-control\"> ");
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
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Ordenar por:</label>");
		pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de Cuenta</option>");
		pw.println("<option>ID Cuenta</option>");
		pw.println("<option>ID Prestamo</option>");
		pw.println("<option>ID Oficina</option>");
		pw.println("<option>ID Transaccion</option>");
		pw.println("</select>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<label>Agrupar por:</label>");
		pw.println("<select name=\"agruparPor\" class=\"form-control\">");
		pw.println("<option> </option>");
		pw.println("<option>Tipo de Cuenta</option>");
		pw.println("<option>ID Cuenta</option>");
		pw.println("<option>ID Prestamo</option>");
		pw.println("<option>ID Oficina</option>");
		pw.println("<option>ID Transaccion</option>");
		pw.println("</select>");
		pw.println("</div>");

		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"panel-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>Nombre Cliente</th>");
		pw.println("<th>Tipo De Persona</th>");
		pw.println("<th>Correo</th>");
		pw.println("<th>Ciudad</th>");
		pw.println("<th>Nacionalidad</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		
		imprimirInformacionClientes(pw); //TODO
		
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<!-- /.col-lg-12 -->");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Cuentas Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Cuenta</th>");
		pw.println("<th>Tipo De Cuenta</th>");
		pw.println("<th>Oficina Asociada</th>");
		pw.println("<th>Fecha Ultimo Movimiento</th>");
		pw.println("<th>Saldo</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");

		imprimirInformacionCuentasClientes(pw); //TODO
		
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");

		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Prestamos Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Prestamo</th>");
		pw.println("<th>Monto Prestado</th>");
		pw.println("<th>Tipo</th>");
		pw.println("<th>Saldo Pendiente</th>");
		pw.println("<th>Estado</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		
		imprimirInformacionPrestamosClientes(pw); //TODO
		
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("<div class=\"panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("Informaci&oacute;n Operaciones Cliente");
		pw.println("</div>");
		pw.println("<!-- /.panel-heading -->");
		pw.println("<div class=\"pan       el-body\">");
		pw.println("<div class=\"dataTable_wrapper\">");
		pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
		pw.println("<thead>");
		pw.println("<tr>");
		pw.println("<th>ID Transaccion</th>");
		pw.println("<th>Tipo</th>");
		pw.println("<th>Fecha de realizacion</th>");
		pw.println("<th>Punto de atencion</th>");
		pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>6</td>");
		pw.println("<td>Pago Prestamo</td>");
		pw.println("<td>2015/07/08</td>");
		pw.println("<td>8</td>");
		pw.println("</tr>");
		pw.println("<tr class=\"odd gradeX\">");
		pw.println("<td>8</td>");
		pw.println("<td>Pago Extraordinario</td>");
		pw.println("<td>2015/06/07</td>");
		pw.println("<td>8</td>");
		pw.println("</tr>");
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("<!-- /.panel -->");
		pw.println("</div>");
		pw.println("</div>");

		pw.println("<!-- /.col-lg-12 -->");
		pw.println("</div>");
		pw.println("<!-- /.row -->");

		pw.println("</div>");
		pw.println("<!-- /#page-wrapper -->");

		pw.println("</div>");
	}
	
	private void imprimirInformacionError(PrintWriter pw, String error)
	{
		
			pw.println("<div id=\"page-wrapper\">");
			pw.println("<div class=\"row\">");
			pw.println("<div class=\"col-lg-12\">");
			pw.println("<h1 class=\"page-header\">Consultar Un Cliente</h1>");
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
			pw.println("<form role=\"form\">");
			pw.println("<div class=\"form-group\">");

			pw.println("<label>Correo Cliente</label>");
			pw.println("<input name=\"correoCliente\" class=\"form-control\"> ");
			pw.println("<font color=\"red\">" + error + "</font> ");

			pw.println("<label>Tipo De Cuenta</label>");
			pw.println("<select name=\"tipoCuenta\" class=\"form-control\">");
			pw.println("<option> </option>");
			pw.println("<option>Ahorros</option>");
			pw.println("<option>Corriente</option>");
			pw.println("<option>CDT</option>");
			pw.println("</select>");
			pw.println("</div>");

			pw.println("<label>ID Cuenta</label>");
			pw.println("<input name=\"idCuentaCliente\" class=\"form-control\"> ");
			pw.println("<label>Saldo Cuenta:</label>");
			pw.println("<input name=\"saldoCuenta\" class=\"form-control\"></div>");
			pw.println("</div>");

			pw.println("<div class=\"form-group\">");
			pw.println("<label>Fecha inicial transaccion:</label>");
			pw.println("<input name=\"fechaInicialTransaccion\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
			pw.println("</div>");

			pw.println("<div class=\"form-group\">");
			pw.println("<label>Fecha final transaccion:</label>");
			pw.println("<input name=\"fechaFinalTransaccion\" class=\"form-control\" placeholder=\"AAAA/MM/DD\" name=\"password\" type=\"password\" value=\"\">");
			pw.println("</div>");
			pw.println("<div class=\"form-group\">");
			pw.println("<label>Valor Transaccion:</label>");
			pw.println("<div><input name=\"valorTransaccion\" class=\"form-control\"> ");
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
			pw.println("<div class=\"form-group\">");
			pw.println("<label>Ordenar por:</label>");
			pw.println("<select name=\"ordenarPor\" class=\"form-control\">");
			pw.println("<option> </option>");
			pw.println("<option>Tipo de Cuenta</option>");
			pw.println("<option>ID Cuenta</option>");
			pw.println("<option>ID Prestamo</option>");
			pw.println("<option>ID Oficina</option>");
			pw.println("<option>ID Transaccion</option>");
			pw.println("</select>");
			pw.println("</div>");
			pw.println("<div class=\"form-group\">");
			pw.println("<label>Agrupar por:</label>");
			pw.println("<select name=\"agruparPor\" class=\"form-control\">");
			pw.println("<option> </option>");
			pw.println("<option>Tipo de Cuenta</option>");
			pw.println("<option>ID Cuenta</option>");
			pw.println("<option>ID Prestamo</option>");
			pw.println("<option>ID Oficina</option>");
			pw.println("<option>ID Transaccion</option>");
			pw.println("</select>");
			pw.println("</div>");

			pw.println("<div class=\"panel panel-default\">");
			pw.println("<div class=\"panel-heading\">");
			pw.println("Informaci&oacute;n Cliente");
			pw.println("</div>");
			pw.println("<!-- /.panel-heading -->");
			pw.println("<div class=\"panel-body\">");
			pw.println("<div class=\"dataTable_wrapper\">");
			pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
			pw.println("<thead>");
			pw.println("<tr>");
			pw.println("<th>Nombre Cliente</th>");
			pw.println("<th>Tipo De Persona</th>");
			pw.println("<th>Correo</th>");
			pw.println("<th>Ciudad</th>");
			pw.println("<th>Nacionalidad</th>");
			pw.println("</tr>");
			pw.println("</thead>");
			pw.println("<tbody>");
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>Juan</td>");
			pw.println("<td>Natural</td>");
			pw.println("<td>jc@bancandes.com</td>");
			pw.println("<td>Barranquilla</td>");
			pw.println("<td>Colombiano</td>");

			pw.println("</tr>");
			
			pw.println("</tbody>");
			pw.println("</table>");
			pw.println("</div>");
			pw.println("</div>");
			pw.println("<!-- /.panel -->");
			pw.println("</div>");
			pw.println("<!-- /.col-lg-12 -->");
			pw.println("<div class=\"panel panel-default\">");
			pw.println("<div class=\"panel-heading\">");
			pw.println("Informaci&oacute;n Cuentas Cliente");
			pw.println("</div>");
			pw.println("<!-- /.panel-heading -->");
			pw.println("<div class=\"pan       el-body\">");
			pw.println("<div class=\"dataTable_wrapper\">");
			pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
			pw.println("<thead>");
			pw.println("<tr>");
			pw.println("<th>ID Cuenta</th>");
			pw.println("<th>Tipo De Cuenta</th>");
			pw.println("<th>Oficina Asociada</th>");
			pw.println("<th>Fecha Ultimo Movimiento</th>");
			pw.println("<th>Saldo</th>");
			pw.println("<th>Estado</th>");
			pw.println("</tr>");
			pw.println("</thead>");
			pw.println("<tbody>");
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>1</td>");
			pw.println("<td>Ahorros</td>");
			pw.println("<td>3</td>");
			pw.println("<td>2014/11/23</td>");
			pw.println("<td>350000</td>");
			pw.println("<td class=\"center\">Activa</td>");
			pw.println("</tr>");
			pw.println("<tr class=\"even gradeC\">");
			pw.println("<td>3</td>");
			pw.println("<td>Corriente</td>");
			pw.println("<td>3</td>");
			pw.println("<td class=\"center\">2015/08/29</td>");
			pw.println("<td>3500000</td>");
			pw.println("<td class=\"center\">Activa</td>");
			pw.println("</tr>");
			pw.println("<tr class=\"odd gradeA\">");
			pw.println("<td>6</td>");
			pw.println("<td>CDT</td>");
			pw.println("<td>1</td>");
			pw.println("<td class=\"center\">2013/06/07</td>");
			pw.println("<td>0</td>");
			pw.println("<td class=\"center\">Inactiva</td> ");
			pw.println("</tr>");
			pw.println("</tbody>");
			pw.println("</table>");
			pw.println("</div>");
			pw.println("</div>");
			pw.println("<!-- /.panel -->");
			pw.println("</div>");

			pw.println("<div class=\"panel panel-default\">");
			pw.println("<div class=\"panel-heading\">");
			pw.println("Informaci&oacute;n Prestamos Cliente");
			pw.println("</div>");
			pw.println("<!-- /.panel-heading -->");
			pw.println("<div class=\"pan       el-body\">");
			pw.println("<div class=\"dataTable_wrapper\">");
			pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
			pw.println("<thead>");
			pw.println("<tr>");
			pw.println("<th>ID Prestamo</th>");
			pw.println("<th>Monto Prestado</th>");
			pw.println("<th>Tipo</th>");
			pw.println("<th>Saldo Pendiente</th>");
			pw.println("<th>Estado</th>");
			pw.println("</tr>");
			pw.println("</thead>");
			pw.println("<tbody>");
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>8</td>");
			pw.println("<td>200000</td>");
			pw.println("<td>Estudio</td>");
			pw.println("<td class=\"center\">100000</td>");
			pw.println("<td>Abierto</td>");
			pw.println("</tr>");
			pw.println("</tbody>");
			pw.println("</table>");
			pw.println("</div>");
			pw.println("</div>");
			pw.println("<!-- /.panel -->");
			pw.println("</div>");
			pw.println("<div class=\"panel panel-default\">");
			pw.println("<div class=\"panel-heading\">");
			pw.println("Informaci&oacute;n Operaciones Cliente");
			pw.println("</div>");
			pw.println("<!-- /.panel-heading -->");
			pw.println("<div class=\"pan       el-body\">");
			pw.println("<div class=\"dataTable_wrapper\">");
			pw.println("<table class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">");
			pw.println("<thead>");
			pw.println("<tr>");
			pw.println("<th>ID Transaccion</th>");
			pw.println("<th>Tipo</th>");
			pw.println("<th>Fecha de realizacion</th>");
			pw.println("<th>Punto de atencion</th>");
			pw.println("</tr>");
			pw.println("</thead>");
			pw.println("<tbody>");
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>6</td>");
			pw.println("<td>Pago Prestamo</td>");
			pw.println("<td>2015/07/08</td>");
			pw.println("<td>8</td>");
			pw.println("</tr>");
			pw.println("<tr class=\"odd gradeX\">");
			pw.println("<td>8</td>");
			pw.println("<td>Pago Extraordinario</td>");
			pw.println("<td>2015/06/07</td>");
			pw.println("<td>8</td>");
			pw.println("</tr>");
			pw.println("</tbody>");
			pw.println("</table>");
			pw.println("</div>");
			pw.println("</div>");
			pw.println("<!-- /.panel -->");
			pw.println("</div>");
			pw.println("</div>");

			pw.println("<!-- /.col-lg-12 -->");
			pw.println("</div>");
			pw.println("<!-- /.row -->");

			pw.println("</div>");
			pw.println("<!-- /#page-wrapper -->");

			pw.println("</div>");
		
	}
	
	private void imprimirInformacionClientes(PrintWriter pw)
	{
		if (rtaClientes.isEmpty())
		{
			return;
		}
		
		ArrayList<ClienteValues> clientes = (ArrayList<ClienteValues>) rtaClientes.get(0);
		
		parsearTablaClientes(clientes, pw);
	}
	
	private void imprimirInformacionCuentasClientes(PrintWriter pw)
	{
		ArrayList<CuentaValues> cuentas = (ArrayList<CuentaValues>) rtaClientes.get(1);
		
		parsearTablaCuentas(cuentas, pw);
	}
	
	private void imprimirInformacionPrestamosClientes(PrintWriter pw)
	{
		ArrayList<PrestamoValues> prestamos = (ArrayList<PrestamoValues>) rtaClientes.get(3);
		
		parsearTablaPrestamos(prestamos, pw);

	}

	public String darTituloPagina() {
		return "BancAndes - Consultar clientes";
	}

}
