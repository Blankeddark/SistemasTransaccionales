package vos;

public class top10Values 
{

	int puesto;
	
	String tipo;
	
	int vecesUsada;
	
	int promedio;

	public top10Values(int puesto, String tipo, int vecesUsada, int promedio) {
		super();
		this.puesto = puesto;
		this.tipo = tipo;
		this.vecesUsada = vecesUsada;
		this.promedio = promedio;
	}

	public int getPuesto() {
		return puesto;
	}

	public void setPuesto(int puesto) {
		this.puesto = puesto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getVecesUsada() {
		return vecesUsada;
	}

	public void setVecesUsada(int vecesUsada) {
		this.vecesUsada = vecesUsada;
	}

	public int getPromedio() {
		return promedio;
	}

	public void setPromedio(int promedio) {
		this.promedio = promedio;
	}
	
	
}
