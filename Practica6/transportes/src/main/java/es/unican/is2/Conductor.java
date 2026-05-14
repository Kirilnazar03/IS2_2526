package es.unican.is2;

import java.util.ArrayList;

/**
 * Clase que representa a un conductor, con sus datos personales
 * y los transportes que ha realizado.
 *
 * Metricas iniciales:
 * WMC = constructor 5 (1 + if + 3 operadores ||) + 6 getters + sueldo 6(1 + for + 3 case + if) + anhadeTransporte 1 = 18;
 * WMCn = 18 / 9 = 2.00.
 * CCog = constructor 2 (if + 1 secuencia de  operadores ||) + sueldo 7 (for + switch(anidado) + if anidado + else) = 9; 
 * CCogn = 9 / 9 = 1.00.
 * CBO = 4: ArrayList, Transporte, CategoriaTransporte, IllegalArgumentException. 
 * DIT = 1. 
 * NOC = 0.
 */
public class Conductor {

	private ArrayList<Transporte> transportes = new ArrayList<Transporte>();
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dire;

	public Conductor(String dni, String nombre, String apellido1,
			String apellido2, String direccion) {// Complejidad ciclomatica = 5 (1 + if + 3 operadores ||)
		if (dni == null || nombre == null || apellido1 == null || direccion == null) {
			throw new IllegalArgumentException();
		}
		this.dni = dni;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dire = direccion;
	}

	public String dni() {// Complejidad ciclomatica = 1
		return dni;
	}

	public String getDni() {// Complejidad ciclomatica = 1
		return dni;
	}

	public String getNombre() {// Complejidad ciclomatica = 1
		return nombre;
	}

	public String getApellido1() {// Complejidad ciclomatica = 1
		return apellido1;
	}

	public String apellido2() {// Complejidad ciclomatica = 1
		return apellido2;
	}

	public String getDire() { // Complejidad ciclomatica = 1
		return dire;
	}

	public double sueldo() {// Complejidad ciclomatica = 6 (1 + for + 3 case + if)
		double sueldoTransportes = 0;
		for (Transporte t : transportes) {
			double sueldoExtraTransporte = 0.0;
			switch (t.categoria()) {
				case Mercancias:
					sueldoExtraTransporte = t.ton() * 2;
					break;
				case MercanciasPeligrosas:
					sueldoExtraTransporte = t.ton() * 2 + 50;
					break;
				case Personas:
					if (t.getPersonas() < 10)
						sueldoExtraTransporte = t.horas() * 0.5;
					else
						sueldoExtraTransporte = t.horas();
					break;
			}
			sueldoTransportes += t.horas() * 5 + sueldoExtraTransporte;
		}
		return 700 + sueldoTransportes;
	}

	public void anhadeTransporte(Transporte t) {// Complejidad ciclomatica = 1
		transportes.add(t);
	}

}
