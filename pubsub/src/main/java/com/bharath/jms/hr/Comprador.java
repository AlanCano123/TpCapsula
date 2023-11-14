package com.bharath.jms.hr;

import java.io.Serializable;

import com.bharath.jms.aplicaciones.BilleteraApp;

public class Comprador implements Serializable {
	private int dni;
	private int cantJavacoinAComprar;
	private Double cotizacion;
	private int cantOperaciones=0;
	private BilleteraApp billetera;
	
	
	
	public BilleteraApp getBilletera() {
		return billetera;
	}
	public void setBilletera(BilleteraApp billetera) {
		this.billetera = billetera;
	}
	public int getCantOperaciones() {
		return cantOperaciones;
	}
	public void setCantOperaciones(int cantOperaciones) {
		this.cantOperaciones = cantOperaciones;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public int getCantJavacoinAComprar() {
		return cantJavacoinAComprar;
	}
	public void setcantJavacoinAComprar(int cantJavacoin) {
		this.cantJavacoinAComprar = cantJavacoin;
	}
	public Double getCotizacion() {
		return cotizacion;
	}
	public void setCotizacion(Double cotizacion) {
		this.cotizacion = cotizacion;
	}
	
	

}
