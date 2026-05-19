package es.unican.is2;

/**
 * Categoria de un transporte.
 *
 * Refactorizaciones aplicadas:
 * - Replace Conditional with Polymorphism: cada constante implementa su propia
 *   forma de calcular el sueldo extra.
 * - Replace Magic Number with Symbolic Constant: tarifas y limites de calculo.
 *
 * Metricas:
 * WMC = Mercancias.calcularSueldoExtra 1 + MercanciasPeligrosas.calcularSueldoExtra 1
 *       + Personas.calcularSueldoExtra 2 (1 + if) = 4; 
 * WMCn = 4 / 3 = 1.33.
 * CCog = Personas.calcularSueldoExtra 1 = 1; 
 * CCogn = 1 / 3 = 0.33.
 * CBO = 0. 
 * DIT = 1. 
 * NOC = 0.
 */
public enum CategoriaTransporte {

	Mercancias {
		@Override
		public double calcularSueldoExtra(double horas, int toneladas, int personas) {
			return toneladas * TARIFA_MERCANCIAS;
		}
	},

	MercanciasPeligrosas {
		@Override
		public double calcularSueldoExtra(double horas, int toneladas, int personas) {
			return toneladas * TARIFA_MERCANCIAS + EXTRA_PELIGROSAS;
		}
	},

	Personas {
		@Override
		public double calcularSueldoExtra(double horas, int toneladas, int personas) {
			if (personas < MIN_PERSONAS_GRUPO_GRANDE) {
				return horas * TARIFA_PERSONAS_BASICA;
			}
			return horas;
		}
	};

	private static final double TARIFA_MERCANCIAS = 2.0;
	private static final double EXTRA_PELIGROSAS = 50.0;
	private static final double TARIFA_PERSONAS_BASICA = 0.5;
	private static final int MIN_PERSONAS_GRUPO_GRANDE = 10;

	public abstract double calcularSueldoExtra(double horas, int toneladas, int personas);
}
