package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.UsuarioValues;

public class ServletLogin extends ASServlet {

	private UsuarioValues usuarioActual;

	public ServletLogin()
	{
		usuarioActual = null;
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
			RequestDispatcher rd= context.getRequestDispatcher("/gerenteOficina");
			rd.forward(request, response);
		}

	}

	public String darTituloPagina() {
		return "BancAndes - Iniciar sesión";
	}

	public void paraProbarGerenteOficina()
	{
		usuarioActual = new UsuarioValues("algun@gerenteof.com", "algun", "gerenteof", "123", "CC", "GO");
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

}
