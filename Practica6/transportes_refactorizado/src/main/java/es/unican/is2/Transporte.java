package es.unican.is2;

/**
 * Clase que representa un transporte realizado por un conductor.
 *
 * Refactorizaciones aplicadas:
 * - Rename Method: horas() -> getHoras(), categoria() -> getCategoria(), ton() -> getTon()
 * - Move Method: logica de calculo de sueldo extra movida desde Conductor.sueldo()
 * - Replace Conditional with Polymorphism: calculo extra delegado a CategoriaTransporte
 *
 * Metricas:
 * WMC = constructor 5 (1 + if + 2 operadores || + if) + getHoras 1 + getCategoria 1
 *       + getTon 1 + getPersonas 1 + calcularSueldoExtra 1 = 10;
 * WMCn = 10 / 6 = 1.67.
 * CCog = constructor 3 (if + 1 secuencia de operadores || + if) = 3;
 * CCogn = 3 / 6 = 0.50.
 * CBO = 2: CategoriaTransporte, IllegalArgumentException.
 * DIT = 1. 
 * NOC = 0.
 */
public class Transporte {

	private double horas;
	private int ton;
	private int personas;
	private CategoriaTransporte cat;

	/**
	 * Constructor de la clase Transporte
	 * @param horas Horas que ha durado el transporte
	 * @param cat Categoria del transporte
	 * @param valor En caso de ser un transporte de tipo Personas, representa el numero
	 *              de personas; en caso de ser de tipo Mercancias, las toneladas
	 */
	public Transporte(double horas, CategoriaTransporte cat, int valor) throws IllegalArgumentException {
		if (horas <= 0 || valor <= 0 || cat == null) {
			throw new IllegalArgumentException();
		}
		this.horas = horas;
		this.cat = cat;
		if (cat.equals(CategoriaTransporte.Personas)) {
			this.personas = valor;
		} else {
			this.ton = valor;
		}
	}

	public double getHoras() {
		return horas;
	}

	public CategoriaTransporte getCategoria() {
		return cat;
	}

	public int getTon() {
		return ton;
	}

	public int getPersonas() {
		return personas;
	}

	/**
	 * Calcula el sueldo extra generado por este transporte segun su categoria.
	 * Refactorizacion Move Method + Replace Conditional with Polymorphism.
	 */
	public double calcularSueldoExtra() {
		return cat.calcularSueldoExtra(horas, ton, personas);
	}

}
