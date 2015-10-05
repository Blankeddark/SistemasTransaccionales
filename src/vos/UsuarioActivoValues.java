package vos;

public class UsuarioActivoValues 
{
    String nombre;
    String numeroID;
    String tipoID;
    int numTransacciones;
    String tipoUsuario;
    String correo;
	public UsuarioActivoValues(String nombre, String numeroID, String tipoID,
			int numTransacciones, String tipoUsuario, String correo) {
		super();
		this.nombre = nombre;
		this.numeroID = numeroID;
		this.tipoID = tipoID;
		this.numTransacciones = numTransacciones;
		this.tipoUsuario = tipoUsuario;
		this.correo = correo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNumeroID() {
		return numeroID;
	}
	public void setNumeroID(String numeroID) {
		this.numeroID = numeroID;
	}
	public String getTipoID() {
		return tipoID;
	}
	public void setTipoID(String tipoID) {
		this.tipoID = tipoID;
	}
	public int getNumTransacciones() {
		return numTransacciones;
	}
	public void setNumTransacciones(int numTransacciones) {
		this.numTransacciones = numTransacciones;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
    
    
}
