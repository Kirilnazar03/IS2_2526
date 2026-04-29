<<<<<<< HEAD
package es.unican.is2;

public class Runner {

	public static void main(String[] args) {
		IClientesDAO daoClientes = new ClientesDAO();
		ISegurosDAO daoSeguros = new SegurosDAO();
		GestionSeguros negocio = new GestionSeguros(daoClientes, daoSeguros);
		VistaAgente vista = new VistaAgente(negocio, negocio, negocio);
		vista.setVisible(true);
	}

}
=======
package es.unican.is2;

public class Runner {

	public static void main(String[] args) throws DataAccessException {
		IClientesDAO daoClientes = new ClientesDAO();
		Cliente juan = daoClientes.cliente("11111111A");
		Cliente ana  = daoClientes.cliente("22222222A");
		Cliente luis = daoClientes.cliente("33333333A");
		ISegurosDAO daoSeguros = new SegurosDAO();
		GestionSeguros negocio = new GestionSeguros(daoClientes, daoSeguros);
		VistaAgente vista = new VistaAgente(negocio, negocio, negocio);
		vista.setVisible(true);
	}

	
}


>>>>>>> Practica2
