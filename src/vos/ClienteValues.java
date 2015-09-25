package vos;

public class ClienteValues 
{
	private String correo;

	private char tipoPersona;

	public ClienteValues(String correo, char tipoPersona) 
	{
		super();
		this.correo = correo;
		this.tipoPersona = tipoPersona;
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




}
