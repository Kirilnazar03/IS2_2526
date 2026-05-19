package es.unican.is2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TransporteTest {

	@Test
	public void testConstructor() {
		// Casos validos
		Transporte sut = new Transporte(1, CategoriaTransporte.Mercancias, 1);
		assertEquals(1, sut.getHoras());
		assertEquals(CategoriaTransporte.Mercancias, sut.getCategoria());
		assertEquals(1, sut.getTon());
		assertEquals(0, sut.getPersonas());

		sut = new Transporte(10, CategoriaTransporte.MercanciasPeligrosas, 1000);
		assertEquals(10, sut.getHoras());
		assertEquals(CategoriaTransporte.MercanciasPeligrosas, sut.getCategoria());
		assertEquals(1000, sut.getTon());
		assertEquals(0, sut.getPersonas());

		sut = new Transporte(10, CategoriaTransporte.Personas, 10);
		assertEquals(10, sut.getHoras());
		assertEquals(CategoriaTransporte.Personas, sut.getCategoria());
		assertEquals(10, sut.getPersonas());
		assertEquals(0, sut.getTon());

		// Casos no validos
		assertThrows(IllegalArgumentException.class, () -> new Transporte(0, CategoriaTransporte.Mercancias, 1));
		assertThrows(IllegalArgumentException.class, () -> new Transporte(10, CategoriaTransporte.Mercancias, 0));
		assertThrows(IllegalArgumentException.class, () -> new Transporte(10, null, 10));
	}

	@Test
	public void testCalcularSueldoExtra() {
		// Mercancias: ton * 2
		Transporte sut = new Transporte(1, CategoriaTransporte.Mercancias, 5);
		assertEquals(10.0, sut.calcularSueldoExtra());

		// MercanciasPeligrosas: ton * 2 + 50
		sut = new Transporte(1, CategoriaTransporte.MercanciasPeligrosas, 5);
		assertEquals(60.0, sut.calcularSueldoExtra());

		// Personas con menos de 10 personas: horas * 0.5
		sut = new Transporte(4, CategoriaTransporte.Personas, 9);
		assertEquals(2.0, sut.calcularSueldoExtra());

		// Personas con 10 o mas personas: horas
		sut = new Transporte(4, CategoriaTransporte.Personas, 10);
		assertEquals(4.0, sut.calcularSueldoExtra());
	}

}
