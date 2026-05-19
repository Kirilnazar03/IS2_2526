package es.unican.is2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DatosPersonalesTest {

	@Test
	public void testConstructorYGetters() {
		// Caso valido con todos los campos
		DatosPersonales sut = new DatosPersonales("Pepe", "Martinez", "Fernandez", "Avda. de los Castros s/n");
		assertEquals("Pepe", sut.getNombre());
		assertEquals("Martinez", sut.getApellido1());
		assertEquals("Fernandez", sut.getApellido2());
		assertEquals("Avda. de los Castros s/n", sut.getDireccion());

		// Caso valido con apellido2 null
		sut = new DatosPersonales("Ana", "Garcia", null, "Calle Mayor 1");
		assertEquals("Ana", sut.getNombre());
		assertEquals("Garcia", sut.getApellido1());
		assertNull(sut.getApellido2());
		assertEquals("Calle Mayor 1", sut.getDireccion());
	}

}
