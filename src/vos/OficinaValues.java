package vos;

public class OficinaValues 
{

	private int idOficina;

	private String nombre;

	private String direccion;

	private String telefono;

	private String gerente;

	public OficinaValues(int idOficina, String nombre, String direccion,
			String telefono, String gerente) {
		super();
		this.idOficina = idOficina;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.gerente = gerente;
	}

	public int getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(int idOficina) {
		this.idOficina = idOficina;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getGerente() {
		return gerente;
	}

	public void setGerente(String gerente) {
		this.gerente = gerente;
	}


}
