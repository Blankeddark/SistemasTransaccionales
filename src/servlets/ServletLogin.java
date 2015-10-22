package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.ClienteValues;
import vos.CuentaValues;
import vos.EmpleadoValues;
import vos.OficinaValues;
import vos.PrestamoValues;
import vos.TransaccionValues;
import vos.UsuarioValues;
import Fachada.BancAndes;

public class ServletLogin extends ASServlet {

	/**
	 * Modela si el usuario es un cliente
	 */
	public final static String TIPO_USUARIO_CLIENTE = "Cliente";

	/**
	 * Modela si el usuario es un gerente de oficina
	 */
	public final static String TIPO_USUARIO_GERENTE_OFICINA = "GO";

	/**
	 * Modela si el empleado es un cajero
	 */
	public final static String TIPO_EMPLEADO_CAJERO = "C";

	/**
	 * Modela si el usuario es un gerente general
	 */
	public final static String TIPO_EMPLEADO_GERENTE_GENERAL = "GG";

	private static UsuarioValues usuarioActual;

	/**
	 * Null si el usuario actual no es un GO o Cajero.
	 */
	private static EmpleadoValues empleadoActual;

	/**
	 * Null si el usuario actual no es un cliente
	 */
	private static ClienteValues clienteActual;

	/**
	 * Lista de cuentas del usuario actual. 
	 */
	private static ArrayList<CuentaValues> cuentasUsuarioActual;

	/**
	 * Null si el sujeto no es cliente
	 */
	private static ArrayList<OficinaValues> oficinaCuentasUsuarioActual;

	/**
	 * Null si el sujeto no es cliente
	 */
	private static ArrayList<PrestamoValues> prestamosUsuarioActual;

	/**
	 * Null si el sujeto no es cliente
	 */
	private static ArrayList<TransaccionValues> transaccionesUsuarioActual;

	/**
	 * Cambia a la url del servlet que maneja al tipo de usuario que está conectado.
	 */
	String urlUsuarioActual = "";

	public ServletLogin()
	{
		usuarioActual = null;
		empleadoActual = null;
		clienteActual = null;
		cuentasUsuarioActual = new ArrayList<CuentaValues>();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("En doPost de ServletLogin");

		PrintWriter pw = response.getWriter();

		imprimirEncabezado(pw);

		if (usuarioActual != null)
		{
			imprimirYaHayUsuario(pw);
			return;
		}

		String cuentaUsuario = request.getParameter("cuentaUsuario");
		String passUsuario = request.getParameter(ServletPrincipal.ID_PASS_USUARIO);

		//Sólo para probar:

		if ( iniciarSesion(pw, cuentaUsuario, passUsuario) ) //TODO quitar debug y agregar inicio de sesión real
		{

			ServletContext context= getServletContext();
			RequestDispatcher rd= context.getRequestDispatcher(urlUsuarioActual);
			rd.forward(request, response);
		}

	}

	public String darTituloPagina() {
		return "BancAndes - Iniciar sesión";
	}

	public void paraProbarGerenteOficina()
	{
		usuarioActual = new UsuarioValues("algun@gerenteof.com", "algun", "gerenteof", "123", "CC", "GO");
		empleadoActual = new EmpleadoValues(usuarioActual, 1);
	}

	public boolean iniciarSesion(PrintWriter pw, String cuentaUsuario, String contraseñaUsuario)
	{
   		BancAndes bancAndes = BancAndes.darInstancia();

		/**
		 * Este mÃ©todo retorna un arrayList que contiene 5 ArrayList.
		 * El primer arrayList contiene la informaciÃ³n del cliente en forma de un objeto clienteValues.
		 * El segundo arrayList contiene la informaciÃ³n de las cuentas del cliente en forma
		 * de cuentaValues.
		 * El tercero contiene la informaciÃ³n de las oficinas donde tiene una cuenta el usuario
		 * en forma de oficinaValue.
		 * El cuarto contiene informaciÃ³n de los prestamos del cliente en forma de
		 * prestamoValue.
		 * Y el quinto contiene la informaciÃ³n de las operaciones del cliente en forma
		 * de transaccionValue.
		 * El cliente se busca a partir de su correo el cual entra por parÃ¡metro.
		 * La informaciÃ³n que es retornada no estÃ¡ sujetada a ningÃºn tipo de filtro
		 * @return
		 * @throws Exception
		 */
		ArrayList infoClienteActual = bancAndes.consultarClienteEspecifico(cuentaUsuario, "", "", "");

		System.out.println("¿Está vacía la lista de retorno para el cliente con la cuenta " + cuentaUsuario + " ? --");
		System.err.println( infoClienteActual.isEmpty() );

		usuarioActual = bancAndes.darUsuarioInicioSesion(cuentaUsuario);

		if ( usuarioActual == null )
		{
			imprimirMainConError(pw, "No hay un usuario registrado con el correo ingresado");
			return false;
		}

		System.out.println("Usuario actual: " + usuarioActual);

		System.out.println("Contraseña ingresada: " + contraseñaUsuario);
		System.out.println("Contraseña esperada: " + usuarioActual.getContraseña() );
		
		if(contraseñaUsuario.equals( usuarioActual.getContraseña()) )
		{

			if (usuarioActual.getTipo_usuario().equals("Cliente"))
			{
				ArrayList<ClienteValues> temp = ( (ArrayList<ClienteValues>) infoClienteActual.get(0) );
				clienteActual = temp.get(0);
				cuentasUsuarioActual = (ArrayList<CuentaValues>) infoClienteActual.get(1);
				oficinaCuentasUsuarioActual = (ArrayList<OficinaValues>) infoClienteActual.get(2);
				prestamosUsuarioActual = (ArrayList<PrestamoValues>) infoClienteActual.get(3);
				transaccionesUsuarioActual = (ArrayList<TransaccionValues>) infoClienteActual.get(4);
				urlUsuarioActual = "/cliente";
			}

			else if (usuarioActual.getTipo_usuario().equals("C"))
			{
				try 
				{
					empleadoActual = new EmpleadoValues(usuarioActual, bancAndes.darOficinaEmpleado(cuentaUsuario) );
				} 

				catch (Exception e) 
				{
					imprimirMainConError(pw, "Error al asignar el empleadoActual para Cajero. <br>ERROR:<br>" + e.getMessage() );
					usuarioActual = null;
					e.printStackTrace();
					return false;
				}
				urlUsuarioActual = "/cajero";
			}

			else if (usuarioActual.getTipo_usuario().equals("GG"))
			{
				urlUsuarioActual = "/gerenteGeneral";
			}

			else if (usuarioActual.getTipo_usuario().equals("GO"))
			{
				try 
				{
					empleadoActual = new EmpleadoValues(usuarioActual, bancAndes.darOficinaEmpleado(cuentaUsuario) );
				} 

				catch (Exception e) 
				{
					imprimirMainConError(pw, "Error al asignar el empleadoActual para GO. <br>ERROR:<br>" + e.getMessage() );
					usuarioActual = null;
					e.printStackTrace();
					return false;
				}
				urlUsuarioActual = "/gerenteOficina";
			}

		}

		else
		{
			imprimirMainConError(pw, "La contrase&ntilde;a ingresada es incorrecta");
			usuarioActual = null;
			return false;
		}

		imprimirMainExitoso(pw);
		return true;
	}

	private void imprimirMainExitoso(PrintWriter pw)
	{


		pw.println("<body>");

		pw.println("<div class=\"container\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-md-4 col-md-offset-4\">");
		pw.println("<div class=\"login-panel panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("<h3 class=\"panel-title\">Por favor inicie sesi&oacute;n</h3>");
		pw.println("</div>");
		pw.println("<div class=\"panel-body\">");
		pw.println("<form role=\"form\" method=\"post\" action=\"login\">");
		pw.println("<fieldset>");
		pw.println("<div class=\"form-group\">");
		pw.println("<input id=\"cuentaUsuario\" class=\"form-control\" placeholder=\"Nombre de usuario\" name=\"cuentaUsuario\" type=\"email\" autofocus>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<input id=\"passUsuario\" class=\"form-control\" placeholder=\"Contrase&ntilde;a\" name=\"passUsuario\" type=\"password\" value=\"\">");
		pw.println("</div>");
		pw.println("<!-- Change this to a button or input when using this as a form -->");
		pw.println("<input type=\"submit\" value=\"Iniciar Sesi&oacute;n\" id=\"botonIniciarSesion\" name=\"botonIniciarSesion\" class=\"btn btn-lg btn-success btn-block\"></input>");
		pw.println("</fieldset>");
		pw.println("<font color=\"green\">" + "Operaci&oacute;n realizada &eacute;xitosamente" + "</font>");
		pw.println("</form>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</div>");


		pw.println("</body>");

		pw.println("</html>");


	}

	private void imprimirMainConError(PrintWriter pw, String error)
	{

		pw.println("<body>");

		pw.println("<div class=\"container\">");
		pw.println("<div class=\"row\">");
		pw.println("<div class=\"col-md-4 col-md-offset-4\">");
		pw.println("<div class=\"login-panel panel panel-default\">");
		pw.println("<div class=\"panel-heading\">");
		pw.println("<h3 class=\"panel-title\">Por favor inicie sesi&oacute;n</h3>");
		pw.println("</div>");
		pw.println("<div class=\"panel-body\">");
		pw.println("<form role=\"form\" method=\"post\" action=\"login\">");
		pw.println("<fieldset>");
		pw.println("<div class=\"form-group\">");
		pw.println("<input id=\"cuentaUsuario\" class=\"form-control\" placeholder=\"Nombre de usuario\" name=\"cuentaUsuario\" type=\"email\" autofocus>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<input id=\"passUsuario\" class=\"form-control\" placeholder=\"Contrase&ntilde;a\" name=\"passUsuario\" type=\"password\" value=\"\">");
		pw.println("</div>");
		pw.println("<!-- Change this to a button or input when using this as a form -->");
		pw.println("<input type=\"submit\" value=\"Iniciar Sesi&oacute;n\" id=\"botonIniciarSesion\" name=\"botonIniciarSesion\" class=\"btn btn-lg btn-success btn-block\"></input>");
		pw.println("</fieldset>");
		pw.println("<font color=\"red\">" + error + "</font>");
		pw.println("</form>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</div>");


		pw.println("</body>");

		pw.println("</html>");

	}

	public void imprimirYaHayUsuario(PrintWriter pw)
	{
		pw.println("Ya hay un usuario conectado. En este momento la aplicación sólo permite que"
				+ " un usuario ingrese al tiempo.");
	}

	public static UsuarioValues darUsuarioActual()
	{
		return usuarioActual;
	}

	public static EmpleadoValues darEmpleadoActual()
	{
		return empleadoActual;
	}
	
	public static ArrayList<CuentaValues> darCuentasUsuarioActual()
	{
		return cuentasUsuarioActual;
	}


}
