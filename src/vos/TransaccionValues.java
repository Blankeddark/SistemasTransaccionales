package vos;

import java.util.Date;

public class TransaccionValues 
{

	private int idTransaccion;

	private String correoUsuario;

	private String tipo;

	private Date fechaTransaccion;

	private int idPuntoAtencion;

	public TransaccionValues(int idTransaccion, String correoUsuario,
			String tipo, Date fechaTransaccion, int idPuntoAtencion) {
		super();
		this.idTransaccion = idTransaccion;
		this.correoUsuario = correoUsuario;
		this.tipo = tipo;
		this.fechaTransaccion = fechaTransaccion;
		this.idPuntoAtencion = idPuntoAtencion;
	}

	public int getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(int idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public int getIdPuntoAtencion() {
		return idPuntoAtencion;
	}

	public void setIdPuntoAtencion(int idPuntoAtencion) {
		this.idPuntoAtencion = idPuntoAtencion;
	}





}
