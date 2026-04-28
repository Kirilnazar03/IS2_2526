package es.unican.is2.segurosgui;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.timing.Pause;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.unican.is2.H2ServerConnectionManager;
import es.unican.is2.ClientesDAO;
import es.unican.is2.SegurosDAO;
import es.unican.is2.GestionSeguros;

public class VistaAgenteIT {

    private FrameFixture window;

    @BeforeAll
    public static void setupOnce() {
        // Obligatorio para AssertJ Swing
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setup() throws Exception {
        System.out.println("DEBUG: Ejecutando Setup...");

        // 1. Capas reales
        H2ServerConnectionManager.getConnection();
        ClientesDAO daoClientes = new ClientesDAO();
        SegurosDAO daoSeguros = new SegurosDAO();
        GestionSeguros gestion = new GestionSeguros(daoClientes, daoSeguros);

        // 2. Crear vista en el hilo de Swing
        VistaAgente vista = GuiActionRunner.execute(() -> new VistaAgente(gestion, gestion, gestion));

        // 3. Inicializar el fixture (ESTO es lo que evita el NullPointerException)
        window = new FrameFixture(vista);
        window.show();
        window.focus(); // Reclama el foco del teclado/ratón
        window.moveToFront(); // La pone por encima de las demás

        System.out.println("DEBUG: Setup finalizado. Window: " + window);
    }

    @AfterEach
    public void tearDown() {
        if (window != null) {
            window.cleanUp();
        }
    }

    @Test
    public void testClienteExistente() {
        window.textBox("txtDNICliente").enterText("11111111A");
        window.button("btnBuscar").click();
        Pause.pause(2000);
        window.textBox("txtNombreCliente").requireText("Juan");
    }

    @Test
    public void testClienteSinSeguros() {
        window.textBox("txtDNICliente").enterText("33333333A");
        window.button("btnBuscar").click();
        window.textBox("txtNombreCliente").requireText("Luis");
        window.list().requireItemCount(0);
    }

    @Test
    public void testClienteNoExiste() {
        window.textBox("txtDNICliente").enterText("99999999Z");
        window.button("btnBuscar").click();
        window.textBox("txtNombreCliente").requireText("Error en BBDD");
    }
}