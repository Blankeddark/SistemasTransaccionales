package JSonParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Principal 
{
	public Principal()
	{
		
	}
	
	public ArrayList darOficinas()
	{
		ArrayList<String> respuesta = new ArrayList<String>();
		
		JSONParser parser = new JSONParser();
		 
        try {
 
            Object obj = parser.parse(new FileReader(
                    "./oficinasJSON.txt"));
 
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray objects = (JSONArray) jsonObject.get("Objects");

            
            Iterator<JSONObject> iterator = objects.iterator();
            while(iterator.hasNext())
            {
            	JSONObject objeto = (JSONObject)iterator.next();
            	String nombre = (String)objeto.get("Nombre");
            	String direccion = (String)objeto.get("Direccion");
            	String telefono = (String)objeto.get("Telefono");
            	String gerente = (String)objeto.get("Gerente");
            	String departamento = (String)objeto.get("Departamento");
            	
            	String resp = nombre+","+direccion+","+telefono+","+gerente+","+departamento;
            	System.out.println(resp);
            	respuesta.add(resp);
            }
            
            
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		return respuesta;
	}
	
	public ArrayList darPuntosAtencion()
	{
		ArrayList<String> respuesta = new ArrayList<String>();
		
		JSONParser parser = new JSONParser();
		 
        try {
 
            Object obj = parser.parse(new FileReader(
                    "./puntosAtencionJSON.txt"));
 
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray objects = (JSONArray) jsonObject.get("Objects");

            
            Iterator<JSONObject> iterator = objects.iterator();
            while(iterator.hasNext())
            {
            	JSONObject objeto = (JSONObject)iterator.next();
            	String tipo = (String)objeto.get("Tipo");
            	String oficina = (String)objeto.get("Oficina");
            	String resp = tipo+","+oficina;
            	System.out.println(resp);
            	respuesta.add(resp);
            }
            
            
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		return respuesta;
	}
	
	
	public ArrayList darClientes()
	{
		ArrayList<String> respuesta = new ArrayList<String>();
		
		JSONParser parser = new JSONParser();
		 
        try {
 
            Object obj = parser.parse(new FileReader(
                    "./clientesJSON.txt"));
 
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray objects = (JSONArray) jsonObject.get("Objects");

            
            Iterator<JSONObject> iterator = objects.iterator();
            while(iterator.hasNext())
            {
            	JSONObject objeto = (JSONObject)iterator.next();
//            	"Correo": "ZUNPY@BANCANDES.COM",
//            	"TipoPersona": "J",	
//            	"Login": "ZUNPY",
//            	"Password": "CONTRASEÑA12345678",
//            	"NumeroId": "555555",
//            	"TipoId": "CC",
//            	"Nombre": "ZUNPYSOLUTIONS",
//            	"Nacionalidad": "Colombiano",
//            	"Direccion": "Calle5",
//            	"Telefono": "4252627",
//            	"Ciudad": "BOGOTA",
//            	"Departamento": "CUNDINAMARCA",
//            	"CodPostal": "4567",
//            	"FechaRegistro": "2011/10/06",
//            	"TipoUsuario": "CLIENTE"
            	String correo = (String)objeto.get("Correo");
            	String tipoPersona = (String)objeto.get("TipoPersona");
            	String login = (String)objeto.get("Login");
            	String password = (String)objeto.get("Password");
            	String numeroId = (String)objeto.get("NumeroId");
            	String tipoId = (String)objeto.get("TipoId");
            	String nombre = (String)objeto.get("Nombre");
            	String nacionalidad = (String)objeto.get("Nacionalidad");
            	String direccion = (String)objeto.get("Direccion");
            	String telefono = (String)objeto.get("Telefono");
            	String ciudad = (String)objeto.get("Ciudad");
            	String departamento = (String)objeto.get("Departamento");
            	String codPostal = (String)objeto.get("CodPostal");
            	String fechaRegistro = (String)objeto.get("FechaRegistro");
            	String tipoUsuario = (String)objeto.get("TipoUsuario");
            	
            	String resp = correo+","+tipoPersona+","+login+","+password+","+numeroId
            			+","+tipoId+","+nombre+","+nacionalidad+","+direccion+","+telefono
            			+","+ciudad+","+departamento+","+codPostal+","+fechaRegistro
            			+","+tipoUsuario;
            	System.out.println(resp);
            	respuesta.add(resp);
            }
            
            
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		return respuesta;
	}

	public ArrayList darCuentas()
	{
		ArrayList<String> respuesta = new ArrayList<String>();
		
		JSONParser parser = new JSONParser();
		 
        try {
 
            Object obj = parser.parse(new FileReader(
                    "./cuentasJSON.txt"));
 
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray objects = (JSONArray) jsonObject.get("Objects");

            
            Iterator<JSONObject> iterator = objects.iterator();
            while(iterator.hasNext())
            {
            	JSONObject objeto = (JSONObject)iterator.next();
//            	"Correo": "ZUNPY@BANCANDES.COM",
//            	"TipoCuenta": "Corriente",
//            	"Oficina": "2",	
//            	"FechaUltimoMovimiento": "2014/08/09",
//            	"Saldo": "14500000",
//            	"Estado": "Activa",
            	
            	String correo = (String)objeto.get("Correo");
            	String tipoCuenta = (String)objeto.get("TipoCuenta");
            	String oficina = (String)objeto.get("Oficina");
            	String fechaUltimoMovimiento = (String)objeto.get("FechaUltimoMovimiento");
            	String saldo = (String)objeto.get("Saldo");
            	String estado = (String)objeto.get("Estado");
            	
            	String resp = correo+","+tipoCuenta+","+oficina+","+fechaUltimoMovimiento
            			+","+saldo+","+estado;
            	System.out.println(resp);
            	respuesta.add(resp);
            }
            
            
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		return respuesta;
	}
	
	public ArrayList darTransacciones()
	{
		ArrayList<String> respuesta = new ArrayList<String>();
		
		JSONParser parser = new JSONParser();
		 
        try {
 
            Object obj = parser.parse(new FileReader(
                    "./transaccionesJSON.txt"));
 
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray objects = (JSONArray) jsonObject.get("Objects");

            
            Iterator<JSONObject> iterator = objects.iterator();
            while(iterator.hasNext())
            {
            	JSONObject objeto = (JSONObject)iterator.next();
//            	"CorreoUsuario": "ZUNPY@BANCANDES.COM",
//            	"Tipo": "PP",
//            	"FechaTransaccion":"2013/10/17",
//            	"IdPuntoAtencion":"10",	
            	
            	
            	
            	String correoUsuario = (String)objeto.get("CorreoUsuario");
            	String tipo = (String)objeto.get("Tipo");
            	String fechaTransaccion = (String)objeto.get("FechaTransaccion");
            	String idPuntoAtencion = (String)objeto.get("IdPuntoAtencion");
            	
            	String resp = correoUsuario+","+tipo+","+fechaTransaccion+","+idPuntoAtencion;
            	System.out.println(resp);
            	respuesta.add(resp);
            }
            
            
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		return respuesta;
	}
}
