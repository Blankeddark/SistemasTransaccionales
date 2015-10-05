package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.EmpleadoValues;
import vos.UsuarioValues;

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
	 * Null si el usuario actual no es un empleado.
	 */
	private static EmpleadoValues empleadoActual;
	
	public ServletLogin()
	{
		usuarioActual = null;
		empleadoActual = null;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("En doPost de ServletLogin");
		if (usuarioActual != null)
		{
			PrintWriter pw = response.getWriter();
			imprimirYaHayUsuario(pw);
			return;
		}

		String cuentaUsuario = request.getParameter(ServletPrincipal.ID_BOTON_INICIAR);
		String passUsuario = request.getParameter(ServletPrincipal.ID_PASS_USUARIO);

		//Sólo para probar:

		if ( iniciarSesion() ) //TODO quitar debug y agregar inicio de sesión real
		{
			ServletContext context= getServletContext();
			RequestDispatcher rd= context.getRequestDispatcher("/gerenteGeneral");
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

	public boolean iniciarSesion()
	{
		paraProbarGerenteOficina();
		return true;
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
	
}
