package vos;

import java.util.Date;
import java.util.GregorianCalendar;

public class PrestamoValues 
{
	private int id;

	private String correoCliente;

	private int montoPrestado;

	private String tipo;

	private Date fechaPrestamo;

	private int diaPagoMensual;

	private int cuota;

	private int saldoPendiente;

	private String estado;

	private int numeroCuotas;

	private float interes;

	private int cuotasEfectivas;

	public PrestamoValues(int id, String correoCliente, int montoPrestado,
			String tipo, Date fechaPrestamo, int diaPagoMensual,
			int cuota, int saldoPendiente, String estado, int numeroCuotas,
			float interes, int cuotasEfectivas) {
		super();
		this.id = id;
		this.correoCliente = correoCliente;
		this.montoPrestado = montoPrestado;
		this.tipo = tipo;
		this.fechaPrestamo = fechaPrestamo;
		this.diaPagoMensual = diaPagoMensual;
		this.cuota = cuota;
		this.saldoPendiente = saldoPendiente;
		this.estado = estado;
		this.numeroCuotas = numeroCuotas;
		this.interes = interes;
		this.cuotasEfectivas = cuotasEfectivas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorreoCliente() {
		return correoCliente;
	}

	public void setCorreoCliente(String correoCliente) {
		this.correoCliente = correoCliente;
	}

	public int getMontoPrestado() {
		return montoPrestado;
	}

	public void setMontoPrestado(int montoPrestado) {
		this.montoPrestado = montoPrestado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public int getDiaPagoMensual() {
		return diaPagoMensual;
	}

	public void setDiaPagoMensual(int diaPagoMensual) {
		this.diaPagoMensual = diaPagoMensual;
	}

	public int getCuota() {
		return cuota;
	}

	public void setCuota(int cuota) {
		this.cuota = cuota;
	}

	public int getSaldoPendiente() {
		return saldoPendiente;
	}

	public void setSaldoPendiente(int saldoPendiente) {
		this.saldoPendiente = saldoPendiente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getNumeroCuotas() {
		return numeroCuotas;
	}

	public void setNumeroCuotas(int numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}

	public float getInteres() {
		return interes;
	}

	public void setInteres(float interes) {
		this.interes = interes;
	}

	public int getCuotasEfectivas() {
		return cuotasEfectivas;
	}

	public void setCuotasEfectivas(int cuotasEfectivas) {
		this.cuotasEfectivas = cuotasEfectivas;
	}



}
