package com.bharath.jms.hr;

import java.io.Serializable;

public class Vendedor implements Serializable {
	private int dni;
	private int cantOperaciones=0;
	

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
	

}
