package _ASTools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Funcionalidades aleatorias que pueden ser �tiles en muchos casos.
 */
public class UsaRandom {
	
	private char[] alphabet;
	private SimpleDateFormat format;
	private ArrayList<String> comienzoDireccionesPermitidas;
	private ArrayList<String> nacionalidadesPermitidas;
	private ArrayList<String> ciudadesPermitidas;
	
	public UsaRandom()
	{
		alphabet  = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		format = new SimpleDateFormat("dd/MM/yyyy");
		inicializarComienzoDireccionesPermitidas();
		inicializarCiudadesPermitidas();
		inicializarNacionalidadesPermitidas();
	}
	
	public void inicializarCiudadesPermitidas()
	{
		ciudadesPermitidas = new ArrayList<String>();
		ciudadesPermitidas.add("Bogota,Cundinamarca");
		ciudadesPermitidas.add("Riohacha,La Guajira");
		ciudadesPermitidas.add("Santa Marta,Magdalena");
		ciudadesPermitidas.add("Medellin,Antioquia");
		ciudadesPermitidas.add("Barranquilla,Atlantico");
	}
	
	public void inicializarNacionalidadesPermitidas()
	{
		nacionalidadesPermitidas = new ArrayList<String>();
		nacionalidadesPermitidas.add("Colombiana");
		nacionalidadesPermitidas.add("Iraqui");
		nacionalidadesPermitidas.add("Arabe");
		nacionalidadesPermitidas.add("Estadounidense");
		nacionalidadesPermitidas.add("Venezolana");
		nacionalidadesPermitidas.add("Espa�ola");
	}
	
	public void inicializarComienzoDireccionesPermitidas()
	{
		comienzoDireccionesPermitidas = new ArrayList<String>();
		comienzoDireccionesPermitidas.add("Calle");
		comienzoDireccionesPermitidas.add("Carrera");
	}

	/**
	 * Retorna un SimpleDateFormat con formato dd/MM/yyyy
	 * @return SimpleDateFormat con formato dd/MM/yyyy
	 */
	public SimpleDateFormat darFormato()
	{
		return format;
	}
	
	/**
	 * Genera una cadena de car�cteres aleatoria con el Random ingresado y con la longitud ingresada por par�metro.
	 * @param random objeto Random que utilizar� el m�todo.
	 * @param longitudString longitud del String que se generar�.
	 * @return una cadena de car�cteres de contenido aleatorio.
	 */
	public String generarStringRandom(Random random, int longitudString)
	{
		String rta = "";
		for (int i = 0; i < longitudString; i++)
		{
			rta += alphabet[ random.nextInt( alphabet.length ) ];
		}
		return rta;
	}
	
	public  String generarNombreRandom(Random random)
	{
		int longitudNombre = random.nextInt(15) + 1;
		String nombreMinusculas = generarStringRandom(random, longitudNombre);
		
		return Character.toUpperCase( nombreMinusculas.charAt(0) ) + nombreMinusculas.substring(1);
	}
	
	/**
	 * Genera una cadena de car�cteres que es una sucesi�n de d�gitos con la longitud
	 * ingresada por par�metro, utilizando el objeto Random ingresado.
	 * @param random objeto Random que el m�todo utilizar�.
	 * @param longitudSucesion longitud de la cadena de d�gitos.
	 * @return cadena de d�gito generda aleatoriamente.
	 */
	public String generarSucesionDigitosRandom(Random random, int longitudSucesion)
	{
		String sucesion = "";
		
		//Se genera una sucesi�n de digitos, es decir, de n�meros del 0 al 9.
		for (int i = 0; i < longitudSucesion; i++)
		{
			sucesion += random.nextInt(10); 
		}
		
		return sucesion;
	}
	
	/**
	 * Retorna una fecha aleatoria en formato dd/MM/AAAA
	 * @param random generador aleatorio
	 * @return
	 * @throws ParseException 
	 */
	public String generarFechaRandom(Random random) throws ParseException
	{
		return (random.nextInt(28) + 1) + "/" + (random.nextInt(12) + 1) + "/" + (random.nextInt(16) + 2000);
	}
	
	/**
	 * Genera una direcci�n aleatoria del estilo <Tipo> <menorA120> # <menorA120>-<menorA90> donde
	 * tipo es Calle o Carrera.
	 * @param random objeto Random que el m�todo usar�.
	 * @return direcci�n generada aleatoriamente.
	 */
	public String generarDireccionRandom(Random random)
	{
		String comienzoDireccion = comienzoDireccionesPermitidas.get( random.nextInt( comienzoDireccionesPermitidas.size()) );
		return comienzoDireccion + " " + (random.nextInt(120) + 1 ) + " # " + ( random.nextInt(120) + 1) + "-" + random.nextInt(90);
	}
	
	/**
	 * Retorna un arreglo de string de tama�o 2, en el cual la posici�n 0 es el nombre de la ciudad
	 * y la posici�n 1 el nombre del departamento al que pertenece.
	 */
	public String[] generarUbicacionRandom(Random random)
	{
		String ubicacion = ciudadesPermitidas.get( random.nextInt(ciudadesPermitidas.size() ));
		
		return ubicacion.split(",");
	}
	
	public String generarNacionalidadRandom(Random random)
	{
		return nacionalidadesPermitidas.get( random.nextInt( nacionalidadesPermitidas.size()) );
	}
	
	/**
	 * Genera un tel�fono aleatorio de entre 7 y 15 d�gitos de longitud.
	 * @param random
	 * @return
	 */
	public String generarTelefonoRandom(Random random)
	{
		int longitudTelefono = random.nextInt(9) + 7;
		return generarSucesionDigitosRandom(random, longitudTelefono);
	}
	
}
