package vos;

import java.util.Date;
import java.util.GregorianCalendar;

public class EmpleadoValues 
{
	private String correo;
	   
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
	 
	 private Date fecha_registro;
     
	 private String tipo;
	 
	 private int oficina;
	 
	public EmpleadoValues(String correo, String login, String contraseña,String numero_id, String tipo_id, String nombre, String nacionalidad,
			String direccion, String tipo, int oficina,  String telefono, String ciudad, String departamento,String cod_postal, Date fecha_registro) 
	{
		
		this.tipo = tipo;
		this.oficina = oficina;
		this.correo = correo;
		this.login = login;
		this.contraseña = contraseña;
		this.numero_id = numero_id;
		this.tipo_id = tipo_id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.direccion = direccion;
		this.telefono = telefono;
		this.ciudad = ciudad;
		this.departamento = departamento;
		this.cod_postal = cod_postal;
		this.fecha_registro = fecha_registro;
	}
	
	public EmpleadoValues(UsuarioValues usuario, int oficina)
	{
		this.oficina = oficina;
		tipo = usuario.getTipo_usuario();
		correo = usuario.getCorreo();
		login = usuario.getLogin();
		contraseña = usuario.getContraseña();
		numero_id = usuario.getNumero_id();
		nombre = usuario.getNombre();
		nacionalidad = usuario.getNacionalidad();
		direccion = usuario.getNacionalidad();
		telefono = usuario.getTelefono();
		ciudad = usuario.getCiudad();
		departamento = usuario.getDepartamento();
		cod_postal = usuario.getCod_postal();
		fecha_registro = usuario.getFecha_registro();
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getOficina() {
		return oficina;
	}

	public void setOficina(int oficina) {
		this.oficina = oficina;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getNumero_id() {
		return numero_id;
	}

	public void setNumero_id(String numero_id) {
		this.numero_id = numero_id;
	}

	public String getTipo_id() {
		return tipo_id;
	}

	public void setTipo_id(String tipo_id) {
		this.tipo_id = tipo_id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCod_postal() {
		return cod_postal;
	}

	public void setCod_postal(String cod_postal) {
		this.cod_postal = cod_postal;
	}

	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}


}
