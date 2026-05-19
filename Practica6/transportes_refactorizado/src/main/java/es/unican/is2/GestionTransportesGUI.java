package es.unican.is2;

import java.util.LinkedList;
import java.util.List;
import fundamentos.*;

/**
 * Gestion de una empresa de transportes.
 *
 * Refactorizaciones aplicadas:
 * - Rename Class: usa GestionTransportes en lugar de gestionTransportes
 * - Correccion de bug: MEJOR_CONDUCTOR mostraba nombre() dos veces en lugar
 *   de nombre() + apellido1()
 *
 * Metricas:
 * WMC = main 17 (1 + while + switch exterior 4 casos + ifs + switch interno 3 casos
 *       + bucles de busqueda de mejor conductor) + mensaje 1 = 18;
 * WMCn = 18 / 2 = 9.00.
 * CCog = main 24 (while, switch exterior, decisiones anidadas en cada case,
 *       switch interno y bucles de construccion del resultado) + mensaje 0 = 24;
 * CCogn = 24 / 2 = 12.00.
 * CBO = 9: LinkedList, List, Lectura, Menu, Mensaje, GestionTransportes, Conductor,
 *       Transporte, CategoriaTransporte.
 * DIT = 1. NOC = 0.
 */
public class GestionTransportesGUI {

	public static void main(String[] args) {
		final int ANHADE_CONDUCTOR = 0, ANHADE_TRANSPORTE = 1,
		SUELDO_CONDUCTOR = 2, MEJOR_CONDUCTOR = 3;

		String dni;
		Lectura lect;
		Conductor c;

		GestionTransportes gt = new GestionTransportes();
		Menu menu = new Menu("Transportes");
		menu.insertaOpcion("Anhade conductor", ANHADE_CONDUCTOR);
		menu.insertaOpcion("Anhade transporte", ANHADE_TRANSPORTE);
		menu.insertaOpcion("Sueldo conductor", SUELDO_CONDUCTOR);
		menu.insertaOpcion("Mejor conductor", MEJOR_CONDUCTOR);

		int opcion;

		while (true) {
			opcion = menu.leeOpcion();

			switch (opcion) {
			case ANHADE_CONDUCTOR:
				lect = new Lectura("Datos Conductor");
				lect.creaEntrada("DNI", "");
				lect.creaEntrada("Nombre", "");
				lect.creaEntrada("Apellido1", "");
				lect.creaEntrada("Apellido2", "");
				lect.creaEntrada("Direccion", "");
				lect.esperaYCierra();
				dni = lect.leeString("DNI");
				String nombre = lect.leeString("Nombre");
				String apellido1 = lect.leeString("Apellido1");
				String apellido2 = lect.leeString("Apellido2");
				String direccion = lect.leeString("Direccion");
				if (!gt.anhadeConductor(dni, nombre, apellido1, apellido2, direccion))
					mensaje("ERROR", "Ya existe un conductor con DNI " + dni);
				break;

			case ANHADE_TRANSPORTE:
				lect = new Lectura("Nuevo transporte");
				lect.creaEntrada("DNI", "");
				lect.creaEntrada("Tipo Transporte: P | M | MP", "");
				lect.creaEntrada("Horas", 0);
				lect.creaEntrada("Personas", 0);
				lect.creaEntrada("Toneladas", 0);
				lect.esperaYCierra();
				dni = lect.leeString("DNI");
				String tipo = lect.leeString("Tipo Transporte: P | M | MP");
				int horas = lect.leeInt("Horas");
				int personas = lect.leeInt("Personas");
				int toneladas = lect.leeInt("Toneladas");

				Transporte t = null;
				c = gt.buscaConductor(dni);
				if (c != null) {
					switch (tipo) {
						case "P":
							t = new Transporte(horas, CategoriaTransporte.Personas, personas);
							c.anhadeTransporte(t);
							break;
						case "M":
							t = new Transporte(horas, CategoriaTransporte.Mercancias, toneladas);
							c.anhadeTransporte(t);
							break;
						case "MP":
							t = new Transporte(horas, CategoriaTransporte.MercanciasPeligrosas, toneladas);
							c.anhadeTransporte(t);
							break;
					}
				} else {
					mensaje("ERROR", "No existe un conductor con DNI " + dni);
				}
				break;

			case SUELDO_CONDUCTOR:
				lect = new Lectura("Sueldo Conductor");
				lect.creaEntrada("DNI", "");
				lect.esperaYCierra();
				dni = lect.leeString("DNI");
				c = gt.buscaConductor(dni);
				if (c != null) {
					mensaje("Sueldo", "El sueldo del conductor es: " + c.sueldo());
				} else {
					mensaje("ERROR", "No existe un conductor con DNI " + dni);
				}
				break;

			case MEJOR_CONDUCTOR:
				List<Conductor> resultado = new LinkedList<Conductor>();
				double maxSueldo = 0.0;
				for (Conductor conductor : gt.conductores()) {
					if (conductor.sueldo() > maxSueldo) {
						maxSueldo = conductor.sueldo();
						resultado.clear();
						resultado.add(conductor);
					} else if (conductor.sueldo() == maxSueldo) {
						resultado.add(conductor);
					}
				}
				String msj = "";
				if (resultado.size() == 0) {
					msj = "No hay conductores";
				} else {
					for (Conductor conductor : resultado) {
						msj += conductor.getNombre() + " " + conductor.getApellido1() + "\n";
					}
				}
				mensaje("MEJOR CONDUCTOR", msj);
				break;
			}
		}
	}

	private static void mensaje(String titulo, String txt) {
		Mensaje msj = new Mensaje(titulo);
		msj.escribe(txt);
	}

}
