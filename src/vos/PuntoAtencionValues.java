package vos;

public class PuntoAtencionValues 
{
	private int id;

	private String tipo;

	private int oficina;

	private String correoCajero;

	public PuntoAtencionValues(int id, String tipo, int oficina, String correoCajero) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.oficina = oficina;
		this.correoCajero = correoCajero;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCorreoCajero() {
		return correoCajero;
	}

	public void setCorreoCajero(String correoCajero) {
		this.correoCajero = correoCajero;
	}


}
