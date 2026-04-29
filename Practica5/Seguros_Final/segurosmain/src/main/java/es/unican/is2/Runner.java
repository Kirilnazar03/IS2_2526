package es.unican.is2;

import es.unican.is2.seguroscommon.IClientesDAO;
import es.unican.is2.seguroscommon.ISegurosDAO;
import es.unican.is2.segurosgui.VistaAgente;

public class Runner {

	public static void main(String[] args) {
		IClientesDAO daoClientes = (IClientesDAO) new ClientesDAO();
		ISegurosDAO daoSeguros = (ISegurosDAO) new SegurosDAO();
		GestionSeguros negocio = new GestionSeguros(daoClientes, daoSeguros);
		VistaAgente vista = new VistaAgente(negocio, negocio, negocio);
		vista.setVisible(true);
	}

}
