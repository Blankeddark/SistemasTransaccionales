package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vos.UsuarioValues;

/**
 * Retorna la p�gina inicial del programa
 * 
 * URL: /main.html
 * @author Sergio
 *
 */
public class ServletPrincipal extends ASServlet {

	/**
	 * La id del bot�n iniciar en el HTML del login
	 */
	private final static String ID_BOTON_INICIAR = "botonIniciarSesion";
	
	/**
	 * id del input de usuario en el HTML del login
	 */
	private final static String ID_INPUT_USUARIO = "cuentaUsuario";
	
	/**
	 * id del input del usuario en el HTML del login
	 */
	private final static String ID_PASS_USUARIO = "passUsuario";
	
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
	
	/**
	 * Modela el usuario actualmente logueado en la p�gina.
	 */
	private UsuarioValues usuarioActual;
	
	//M�todos

	public ServletPrincipal()
	{
		usuarioActual = null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(pw);
		imprimirMain(pw);
	}

	private void imprimirMain(PrintWriter pw)
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
		pw.println("<form role=\"form\" method=\"post\" action=\"main.html\">");
		pw.println("<fieldset>");
		pw.println("<div class=\"form-group\">");
		pw.println("<input id=\""+ID_INPUT_USUARIO + "\" class=\"form-control\" placeholder=\"Nombre de usuario\" name=\"email\" type=\"email\" autofocus>");
		pw.println("</div>");
		pw.println("<div class=\"form-group\">");
		pw.println("<input id=\""+ID_PASS_USUARIO+"\" class=\"form-control\" placeholder=\"Contrase&ntilde;a\" name=\"password\" type=\"password\" value=\"\">");
		pw.println("</div>");
		pw.println("<!-- Change this to a button or input when using this as a form -->");
		pw.println("<input type=\"submit\" value=\"Iniciar Sesi&oacute;n\" id=\"" + ID_BOTON_INICIAR+ "\" name=\"" + ID_BOTON_INICIAR+ "\" class=\"btn btn-lg btn-success btn-block\"></input>");
		pw.println("</fieldset>");
		pw.println("</form>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</div>");

		//		pw.println("<!-- jQuery -->");
		//		pw.println("<script src=\"../bower_components/jquery/dist/jquery.min.js\"></script>");
		//
		//		pw.println("<!-- Bootstrap Core JavaScript -->");
		//		pw.println("<script src=\"../bower_components/bootstrap/dist/js/bootstrap.min.js\"></script>");
		//
		//		pw.println("<!-- Metis Menu Plugin JavaScript -->");
		//		pw.println("<script src=\"../bower_components/metisMenu/dist/metisMenu.min.js\"></script>");
		//
		//		pw.println("<!-- Custom Theme JavaScript -->");
		//		pw.println("<script src=\"../dist/js/sb-admin-2.js\"></script>");

		pw.println("</body>");

		pw.println("</html>");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("doPost en ServletPrincipal");
		String botonIniciar = request.getParameter(ID_BOTON_INICIAR);

		PrintWriter pw = response.getWriter();
		
		System.out.println(botonIniciar);
		if (botonIniciar != null)
		{
			if (usuarioActual != null)
			{
				imprimirYaHayUsuario(pw);
				return;
			}
			
			String cuentaUsuario = request.getParameter(ID_BOTON_INICIAR);
			String passUsuario = request.getParameter(ID_PASS_USUARIO);
			
			//S�lo para probar:
			
			paraProbarGerente();
			
			//TODO UsuarioValues usuarioConCuenta =  agregar m�todo que verifica que la cuenta exista

			
			//TODO agregar m�todo que verifica que la pass es correcta para la cuenta
			//TODO
			
			imprimirEncabezado(pw);
			if( usuarioActual.getTipo_usuario().equals(TIPO_USUARIO_GERENTE_OFICINA) )
			ServletIndexGerenteOficina.imprimirCuerpoInicialGerenteOficina(pw);
			
		}
	}
	
	private void paraProbarGerente()
	{
		usuarioActual = new UsuarioValues("algo@algo.com", "log", "in", "123", "CC", "GO");
	}
	
	private void imprimirYaHayUsuario(PrintWriter pw)
	{
		pw.println("Ya hay un usuario conectado.");
	}

	public String darTituloPagina() 
	{
		return "BancAndes";
	}
}
