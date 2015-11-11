package vos;

public class ConsignacionValues 
{
	private int id;

	private int idCuentaOrigen;

	private int idCuentaDestino;

	private int monto;

	public ConsignacionValues(int id, int idCuentaOrigen, int idCuentaDestino,
			int monto) 
	{
		super();
		this.id = id;
		this.idCuentaOrigen = idCuentaOrigen;
		this.idCuentaDestino = idCuentaDestino;
		this.monto = monto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCuentaOrigen() {
		return idCuentaOrigen;
	}

	public void setIdCuentaOrigen(int idCuentaOrigen) {
		this.idCuentaOrigen = idCuentaOrigen;
	}

	public int getIdCuentaDestino() {
		return idCuentaDestino;
	}

	public void setIdCuentaDestino(int idCuentaDestino) {
		this.idCuentaDestino = idCuentaDestino;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}


}
