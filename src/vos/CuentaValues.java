package vos;

import java.util.GregorianCalendar;

public class CuentaValues 
{
	private int idCuenta;

	private String correo;

	private String tipoCuenta;

	private int oficina;

	private GregorianCalendar fechaUltimoMovimiento;

	private int  saldo;

	private String estado;

	public CuentaValues(int idCuenta, String correo, String tipoCuenta,
			int oficina, GregorianCalendar fechaUltimoMovimiento, int saldo,
			String estado) {
		super();
		this.idCuenta = idCuenta;
		this.correo = correo;
		this.tipoCuenta = tipoCuenta;
		this.oficina = oficina;
		this.fechaUltimoMovimiento = fechaUltimoMovimiento;
		this.saldo = saldo;
		this.estado = estado;
	}

	public int getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public int getOficina() {
		return oficina;
	}

	public void setOficina(int oficina) {
		this.oficina = oficina;
	}

	public GregorianCalendar getFechaUltimoMovimiento() {
		return fechaUltimoMovimiento;
	}

	public void setFechaUltimoMovimiento(GregorianCalendar fechaUltimoMovimiento) {
		this.fechaUltimoMovimiento = fechaUltimoMovimiento;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}




}
