package es.unican.is2.seguroscommon;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeguroTest {

    // Fechas de referencia relativas a hoy
    private static final LocalDate HOY          = LocalDate.now();
    private static final LocalDate MANANA       = HOY.plusDays(1);
    private static final LocalDate HACE_11_MESES = HOY.minusMonths(11); // < 1 año → descuento 20%
    private static final LocalDate HACE_1_ANIO  = HOY.minusYears(1);   // AVL: exactamente 1 año
    private static final LocalDate HACE_13_MESES = HOY.minusMonths(13); // > 1 año → precio completo

    private Seguro seguro;

    @BeforeEach
    public void setUp() {
        seguro = new Seguro();
        seguro.setMatricula("TEST001");
    }

    // -------------------------------------------------------------------------
    // EP1: fechaInicio en el futuro → precio = 0 (sin importar cobertura/potencia)
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_fechaFutura_retornaCero() {
        seguro.setFechaInicio(MANANA);
        seguro.setCobertura(Cobertura.TODO_RIESGO);
        seguro.setPotencia(50);
        assertEquals(0.0, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // AVL fechaInicio: hoy mismo (límite entre futuro y pasado → NO es futuro)
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_fechaHoy_noEsCero() {
        seguro.setFechaInicio(HOY);
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(50);
        // Hoy no es "after now", así que sí calcula precio
        // < 1 año → descuento 0.8 → 600 * 0.8 = 480
        assertEquals(480.0, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // EP cobertura: TODO_RIESGO, potencia baja (<90), > 1 año → precio completo
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_todoRiesgo_potenciaBaja_masDeUnAnio() {
        seguro.setFechaInicio(HACE_13_MESES);
        seguro.setCobertura(Cobertura.TODO_RIESGO);
        seguro.setPotencia(50); // < 90
        assertEquals(1000.0, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // EP cobertura: TERCEROS, potencia baja (<90), > 1 año
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_terceros_potenciaBaja_masDeUnAnio() {
        seguro.setFechaInicio(HACE_13_MESES);
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(50);
        assertEquals(600.0, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // EP cobertura: TERCEROS_LUNAS, potencia baja (<90), > 1 año
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_tercerosLunas_potenciaBaja_masDeUnAnio() {
        seguro.setFechaInicio(HACE_13_MESES);
        seguro.setCobertura(Cobertura.TERCEROS_LUNAS);
        seguro.setPotencia(50);
        assertEquals(400.0, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // AVL potencia: límite inferior del tramo medio (90) → ×1.05
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_potencia90_aplicaRecargo105() {
        seguro.setFechaInicio(HACE_13_MESES);
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(90); // límite inferior, inclusive
        assertEquals(600 * 1.05, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // AVL potencia: justo por debajo del tramo medio (89) → sin recargo
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_potencia89_sinRecargo() {
        seguro.setFechaInicio(HACE_13_MESES);
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(89);
        assertEquals(600.0, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // AVL potencia: límite superior del tramo medio (110) → ×1.05
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_potencia110_aplicaRecargo105() {
        seguro.setFechaInicio(HACE_13_MESES);
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(110); // límite superior inclusive
        assertEquals(600 * 1.05, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // AVL potencia: justo por encima del tramo medio (111) → ×1.2
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_potencia111_aplicaRecargo120() {
        seguro.setFechaInicio(HACE_13_MESES);
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(111);
        assertEquals(600 * 1.2, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // EP fecha: < 1 año contratado → descuento 20%
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_menosDeUnAnio_aplicaDescuento20() {
        seguro.setFechaInicio(HACE_11_MESES);
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(50);
        assertEquals(600 * 0.8, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // AVL fecha: exactamente 1 año → el descuento ya NO se aplica
    // plusYears(1).isAfter(now) es FALSE cuando fechaInicio = hoy - 1 año
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_exactamenteUnAnio_sinDescuento() {
        seguro.setFechaInicio(HACE_1_ANIO);
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(50);
        assertEquals(600.0, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // Combinación: TODO_RIESGO + potencia alta + < 1 año
    // 1000 * 1.2 * 0.8 = 960
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_todoRiesgo_potenciaAlta_menosDeUnAnio() {
        seguro.setFechaInicio(HACE_11_MESES);
        seguro.setCobertura(Cobertura.TODO_RIESGO);
        seguro.setPotencia(150); // > 110
        assertEquals(1000 * 1.2 * 0.8, seguro.precio(), 0.01);
    }

    // -------------------------------------------------------------------------
    // Combinación: TERCEROS_LUNAS + potencia media + > 1 año
    // 400 * 1.05 = 420
    // -------------------------------------------------------------------------
    @Test
    public void testPrecio_tercerosLunas_potenciaMedia_masDeUnAnio() {
        seguro.setFechaInicio(HACE_13_MESES);
        seguro.setCobertura(Cobertura.TERCEROS_LUNAS);
        seguro.setPotencia(100); // 90–110
        assertEquals(400 * 1.05, seguro.precio(), 0.01);
    }

    
}