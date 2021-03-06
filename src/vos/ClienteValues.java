package vos;

import java.util.GregorianCalendar;

public class ClienteValues 
{
	private String correo;

	private char tipoPersona;

	private String login;

	private String contraseña;

	private String numero_id;

	private  String tipo_id;

	private String nombre;

	private String nacionalidad;

	private String direccion;

	private String telefono;

	private String ciudad;

	private String departamento;

	private String cod_postal;

	private GregorianCalendar fecha_registro;

	public ClienteValues(String correo, char tipoPersona) 
	{
		super();
		this.correo = correo;
		this.tipoPersona = tipoPersona;
	}
	
	public ClienteValues(String correo, char tipoPersona, String nombre)
	{
		super();
		this.correo = correo;
		this.tipoPersona = tipoPersona;
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public char getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(char tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getLogin() 
	{
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getContraseña() 
	{
		return contraseña;
	}

	public void setContraseña(String contraseña) 
	{
		this.contraseña = contraseña;
	}

	public String getNumero_id() 
	{
		return numero_id;
	}

	public void setNumero_id(String numero_id) 
	{
		this.numero_id = numero_id;
	}

	public String getTipo_id() 
	{
		return tipo_id;
	}

	public void setTipo_id(String tipo_id)
	{
		this.tipo_id = tipo_id;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getNacionalidad() 
	{
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) 
	{
		this.nacionalidad = nacionalidad;
	}

	public String getDireccion() 
	{
		return direccion;
	}

	public void setDireccion(String direccion) 
	{
		this.direccion = direccion;
	}

	public String getTelefono() 
	{
		return telefono;
	}

	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}

	public String getCiudad() 
	{
		return ciudad;
	}

	public void setCiudad(String ciudad) 
	{
		this.ciudad = ciudad;
	}

	public String getDepartamento() 
	{
		return departamento;
	}

	public void setDepartamento(String departamento) 
	{
		this.departamento = departamento;
	}

	public String getCod_postal() 
	{
		return cod_postal;
	}

	public void setCod_postal(String cod_postal) 
	{
		this.cod_postal = cod_postal;
	}

	public GregorianCalendar getFecha_registro() 
	{
		return fecha_registro;
	}

	public void setFecha_registro(GregorianCalendar fecha_registro) 
	{
		this.fecha_registro = fecha_registro;
	}

}
