package vos;

public class EmpleadoValues 
{
	private String correo;

	private String tipo;

	private int oficina;

	public EmpleadoValues(String correo, String tipo, int oficina) {
		super();
		this.correo = correo;
		this.tipo = tipo;
		this.oficina = oficina;
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

}
