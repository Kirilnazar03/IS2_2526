package es.unican.is2;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la lista de conductores de la empresa.
 *
 * Refactorizaciones aplicadas:
 * - Rename Class: gestionTransportes -> GestionTransportes
 * - Rename Field: cs -> listaConductores
 * - Rename Method: usa getDni() en lugar del antiguo dni() eliminado de Conductor
 *
 * Metricas:
 * WMC = buscaConductor 3 (1 + for + if) + anhadeConductor 2 (1 + if) + conductores 1 = 6;
 * WMCn = 6 / 3 = 2.00.
 * CCog = buscaConductor 3 (for + if anidado) + anhadeConductor 1 = 4;
 * CCogn = 4 / 3 = 1.33.
 * CBO = 3: ArrayList, List, Conductor.
 * DIT = 1. 
 * NOC = 0.
 */
public class GestionTransportes {

	private ArrayList<Conductor> listaConductores = new ArrayList<Conductor>();

	public Conductor buscaConductor(String dni) {
		for (Conductor c : listaConductores)
			if (c.getDni().equals(dni))
				return c;
		return null;
	}

	public boolean anhadeConductor(String dni, String nombre, String apellido1,
			String apellido2, String direccion) {
		if (buscaConductor(dni) != null)
			return false;
		listaConductores.add(new Conductor(dni, nombre, apellido1, apellido2, direccion));
		return true;
	}

	public List<Conductor> conductores() {
		return listaConductores;
	}

}
