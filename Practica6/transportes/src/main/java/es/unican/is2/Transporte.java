package es.unican.is2;

/* Clase que representa un transporte realizado por un conductor */
/*
 * Metricas iniciales:
 * WMC = constructor 4 (1 + if + 2 operadores &&) + 4 getters = 8; 
 * WMCn = 8 / 5 = 1.60.
 * CCog = constructor 3 (if + 2 operadores &&) + 4 getters 0 = 3; 
 * CCogn = 3 / 5 = 0.60.
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
	 * @param valor En caso de ser un transporte de tipo Personas, 
	 * representa el numero de personas, en caso de ser de tipo Mercancias 
	 * representa las toneladas
	 */ 
	public Transporte(double horas, CategoriaTransporte cat, int valor) throws IllegalArgumentException {
		if (horas <= 0 || valor <= 0 || cat == null) { // Complejidad ciclomatica = 4 (1 + if + 2 operadores &&)
			throw new IllegalArgumentException();
		}
		this.horas = horas;
		this.cat = cat;
		if (cat.equals(CategoriaTransporte.Personas)) {
			this.personas = valor;
		} else  {
			this.ton = valor;
		}
	}
	
	public double horas() { // Complejidad ciclomatica = 1
		return horas;
	}

	public CategoriaTransporte categoria() { // Complejidad ciclomatica = 1
		return cat;
	}

	public int ton() { // Complejidad ciclomatica = 1
		return ton;
	}

	public int getPersonas() { // Complejidad ciclomatica = 1
		return personas;
	}
	
}
