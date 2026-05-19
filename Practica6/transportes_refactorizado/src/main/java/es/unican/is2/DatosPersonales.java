package es.unican.is2;

/**
 * Clase que agrupa los datos personales de un conductor.
 * Refactorizacion aplicada: Extract Class (extraida de Conductor).
 *
 * Metricas:
 * WMC = constructor 1 + 4 getters = 5; 
 * WMCn = 5 / 5 = 1.00.
 * CCog = 0; 
 * CCogn = 0.00.
 * CBO = 0. 
 * DIT = 1. 
 * NOC = 0.
 */
public class DatosPersonales {

	private String nombre;
	private String apellido1;
	private String apellido2;
	private String direccion;

	public DatosPersonales(String nombre, String apellido1, String apellido2, String direccion) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public String getDireccion() {
		return direccion;
	}

}
