package es.unican.is2.seguroscommon;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteTest {

    private Cliente clienteSinMinusvalia;
    private Cliente clienteConMinusvalia;

    @BeforeEach
    public void setUp() {
        clienteSinMinusvalia = new Cliente();
        clienteSinMinusvalia.setDni("11111111A");
        clienteSinMinusvalia.setNombre("Juan");
        clienteSinMinusvalia.setMinusvalia(false);

        clienteConMinusvalia = new Cliente();
        clienteConMinusvalia.setDni("33333333A");
        clienteConMinusvalia.setNombre("Luis");
        clienteConMinusvalia.setMinusvalia(true);
    }

    /**
     * EP: empty list of policies → total must be 0.0
     */
    @Test
    public void testTotalSeguros_sinSeguros() {
        assertEquals(0.0, clienteSinMinusvalia.totalSeguros(), 0.01);
    }

    /**
     * EP: one policy, no disability
     * The expected value must match Seguro.precio() for these inputs
     */
    @Test
    public void testTotalSeguros_unSeguro_sinMinusvalia() {
        Seguro s = new Seguro();
        s.setMatricula("1111AAA");
        s.setCobertura(Cobertura.TERCEROS);
        s.setFechaInicio(LocalDate.of(2002, 1, 15));
        s.setPotencia(15);
        clienteSinMinusvalia.getSeguros().add(s);

        double expected = s.precio(); // derive expected from the real method
        assertEquals(expected, clienteSinMinusvalia.totalSeguros(), 0.01);
    }

    /**
     * EP: multiple policies, no disability
     * Uses data from the DB initial state (Juan: 1111AAA, 1111BBB, 1111CCC)
     */
    @Test
    public void testTotalSeguros_variosSeguros_sinMinusvalia() {
        Seguro s1 = new Seguro();
        s1.setMatricula("1111AAA");
        s1.setCobertura(Cobertura.TERCEROS);
        s1.setFechaInicio(LocalDate.of(2002, 1, 15));
        s1.setPotencia(15);

        Seguro s2 = new Seguro();
        s2.setMatricula("1111BBB");
        s2.setCobertura(Cobertura.TODO_RIESGO);
        s2.setFechaInicio(LocalDate.of(2016, 5, 20));
        s2.setPotencia(20);
        s2.setConductorAdicional("Pepe");

        Seguro s3 = new Seguro();
        s3.setMatricula("1111CCC");
        s3.setCobertura(Cobertura.TERCEROS);
        s3.setFechaInicio(LocalDate.of(2022, 5, 21));
        s3.setPotencia(100);

        clienteSinMinusvalia.getSeguros().add(s1);
        clienteSinMinusvalia.getSeguros().add(s2);
        clienteSinMinusvalia.getSeguros().add(s3);

        double expected = s1.precio() + s2.precio() + s3.precio();
        assertEquals(expected, clienteSinMinusvalia.totalSeguros(), 0.01);
    }

    /**
     * EP: one or more policies, WITH disability → 25% discount applied
     * Uses Luis (33333333A) with a policy added manually
     */
    @Test
    public void testTotalSeguros_conMinusvalia() {
        Seguro s = new Seguro();
        s.setMatricula("3333AAA");
        s.setCobertura(Cobertura.TERCEROS);
        s.setFechaInicio(LocalDate.of(2020, 3, 10));
        s.setPotencia(50);

        clienteConMinusvalia.getSeguros().add(s);

        double expectedConDescuento = s.precio() * 0.75;
        assertEquals(expectedConDescuento, clienteConMinusvalia.totalSeguros(), 0.01);
    }

    /**
     * White-box: disability discount with multiple policies
     * Ensures discount is applied to the sum, not per policy
     */
    @Test
    public void testTotalSeguros_variosSeguros_conMinusvalia() {
        Seguro s1 = new Seguro();
        s1.setMatricula("3333AAA");
        s1.setCobertura(Cobertura.TERCEROS);
        s1.setFechaInicio(LocalDate.of(2020, 3, 10));
        s1.setPotencia(50);

        Seguro s2 = new Seguro();
        s2.setMatricula("3333BBB");
        s2.setCobertura(Cobertura.TODO_RIESGO);
        s2.setFechaInicio(LocalDate.of(2021, 6, 15));
        s2.setPotencia(80);

        clienteConMinusvalia.getSeguros().add(s1);
        clienteConMinusvalia.getSeguros().add(s2);

        double expectedConDescuento = (s1.precio() + s2.precio()) * 0.75;
        assertEquals(expectedConDescuento, clienteConMinusvalia.totalSeguros(), 0.01);
    }
}