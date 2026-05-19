package es.unican.is2;

import java.util.ArrayList;

/**
 * Clase que representa a un conductor, con sus datos personales
 * y los transportes que ha realizado.
 *
 * Refactorizaciones aplicadas:
 * - Extract Class: datos personales extraidos a DatosPersonales
 * - Rename Method:eliminado dni() duplicado (se conserva getDni())
 * - Rename Field: dire -> direccion (ahora en DatosPersonales)
 * - Replace Magic Number with Symbolic Constant: SUELDO_BASE, TARIFA_POR_HORA
 * - Move Method: calculo de sueldo extra delegado a Transporte.calcularSueldoExtra()
 *
 * Metricas:
 * WMC = constructor 5 (1 + if + 3 operadores ||) + 5 getters + sueldo 2 (1 + for)
 *       + anhadeTransporte 1 = 13;
 * WMCn = 13 / 8 = 1.63.
 * CCog = constructor 2 (if + 1 secuencia de operadores ||) + sueldo 1 (for) = 3;
 * CCogn = 3 / 8 = 0.38.
 * CBO = 4: ArrayList, Transporte, DatosPersonales, IllegalArgumentException.
 * DIT = 1. NOC = 0.
 */
public class Conductor {

	private static final double SUELDO_BASE = 700;
	private static final double TARIFA_POR_HORA = 5;

	private ArrayList<Transporte> transportes = new ArrayList<Transporte>();
	private String dni;
	private DatosPersonales datosPersonales;

	public Conductor(String dni, String nombre, String apellido1,
			String apellido2, String direccion) {
		if (dni == null || nombre == null || apellido1 == null || direccion == null) {
			throw new IllegalArgumentException();
		}
		this.dni = dni;
		this.datosPersonales = new DatosPersonales(nombre, apellido1, apellido2, direccion);
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return datosPersonales.getNombre();
	}

	public String getApellido1() {
		return datosPersonales.getApellido1();
	}

	public String getApellido2() {
		return datosPersonales.getApellido2();
	}

	public String getDireccion() {
		return datosPersonales.getDireccion();
	}

	public double sueldo() {
		double sueldoTransportes = 0;
		for (Transporte t : transportes) {
			sueldoTransportes += t.getHoras() * TARIFA_POR_HORA + t.calcularSueldoExtra();
		}
		return SUELDO_BASE + sueldoTransportes;
	}

	public void anhadeTransporte(Transporte t) {
		transportes.add(t);
	}

}
