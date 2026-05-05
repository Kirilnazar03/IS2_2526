package es.unican.is2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConjuntoOrdenadoTest {

    private ConjuntoOrdenado<Integer> conjunto;

    @BeforeEach
    void setUp() {
        conjunto = new ConjuntoOrdenado<>();
    }

    // ================= ADD =================

    @Test
    void addElementoValido() {
        assertTrue(conjunto.add(5));
        assertEquals(1, conjunto.size());
    }

    @Test
    void addDuplicado() {
        conjunto.add(5);
        assertFalse(conjunto.add(5)); // según interfaz
    }

    @Test
    void addNull() {
        assertThrows(NullPointerException.class, () -> conjunto.add(null));
    }

    @Test
    void addMantieneOrden() {
        conjunto.add(3);
        conjunto.add(1);
        conjunto.add(2);

        assertEquals(1, conjunto.get(0));
        assertEquals(2, conjunto.get(1));
        assertEquals(3, conjunto.get(2));
    }

    // ================= GET =================

    @Test
    void getIndiceValido() {
        conjunto.add(10);
        conjunto.add(20);

        assertEquals(10, conjunto.get(0));
        assertEquals(20, conjunto.get(1));
    }

    @Test
    void getIndiceNegativo() {
        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.get(-1));
    }

    @Test
    void getIndiceMayorSize() {
        conjunto.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.get(1));
    }

    // ================= REMOVE =================

    @Test
    void removeValido() {
        conjunto.add(1);
        conjunto.add(2);

        int eliminado = conjunto.remove(0);

        assertEquals(1, eliminado);
        assertEquals(1, conjunto.size());
    }

    @Test
    void removeIndiceIncorrecto() {
        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.remove(0));
    }

    // ================= SIZE =================

    @Test
    void sizeInicial() {
        assertEquals(0, conjunto.size());
    }

    @Test
    void sizeTrasOperaciones() {
        conjunto.add(1);
        conjunto.add(2);
        conjunto.remove(0);

        assertEquals(1, conjunto.size());
    }

    // ================= CLEAR =================

    @Test
    void clearLista() {
        conjunto.add(1);
        conjunto.add(2);

        conjunto.clear();

        assertEquals(0, conjunto.size());
    }

    @Test
    void clearSobreListaVacia() {
        conjunto.clear();
        assertEquals(0, conjunto.size());
    }
}