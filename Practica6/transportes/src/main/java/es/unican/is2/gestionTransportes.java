package es.unican.is2;

import java.util.ArrayList;
import java.util.List;

/*
 * Metricas iniciales:
 * WMC = buscaConductor 3 (1 + for + if) + anhadeConductor 2 (1 + if) + conductores 1 = 6; 
 * WMCn = 6 / 3 = 2.00.
 * CCog = buscaConductor 3 (for + if anidado) + anhadeConductor 1 = 4; 
 * CCogn = 4 / 3 = 1.33.
 * CBO = 3: ArrayList, List, Conductor.
 * DIT = 1. 
 * NOC = 0.
 */
public class gestionTransportes {

	private ArrayList<Conductor> cs = new ArrayList<Conductor>();
	
	public Conductor buscaConductor(String DNI) { // Complejidad ciclomatica = 3 (1 + for + if)
		for(Conductor c: cs) 
			if (c.dni().equals(DNI))
				return c;
		
		return null;
	}
	
	public boolean anhadeConductor(String dni, String nombre, String apellido1, String apellido2, String direccion) {
		if (buscaConductor(dni) != null) // Complejidad ciclomatica = 2 (1 + if)
			return false;
		cs.add(new Conductor(dni, nombre, apellido1, apellido2,direccion));
		return true;
	}

	public List<Conductor> conductores() { // Complejidad ciclomatica = 1
		return cs;
	}
	
}
