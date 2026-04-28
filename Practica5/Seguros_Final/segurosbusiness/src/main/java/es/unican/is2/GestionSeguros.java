package es.unican.is2;

import java.util.List;

import es.unican.is2.seguroscommon.Cliente;
import es.unican.is2.seguroscommon.DataAccessException;
import es.unican.is2.seguroscommon.IClientesDAO;
import es.unican.is2.seguroscommon.IGestionClientes;
import es.unican.is2.seguroscommon.IGestionSeguros;
import es.unican.is2.seguroscommon.IInfoSeguros;
import es.unican.is2.seguroscommon.ISegurosDAO;
import es.unican.is2.seguroscommon.OperacionNoValida;
import es.unican.is2.seguroscommon.Seguro;

public class GestionSeguros implements IGestionClientes, IGestionSeguros, IInfoSeguros {

private ISegurosDAO seguroDao;
private IClientesDAO clienteDao;

public GestionSeguros (IClientesDAO clienteDao, ISegurosDAO seguroDao) {
    this.seguroDao = seguroDao;
    this.clienteDao = clienteDao;
}
@Override
    public Cliente nuevoCliente(Cliente c) throws DataAccessException {
        for (Cliente cliente : clienteDao.clientes()) {
            if (cliente.getDni().equals(c.getDni())) {
                return null;
            }
        }
        clienteDao.creaCliente(c);
        return c;
    }

    @Override
    public Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException {
       Cliente c = clienteDao.cliente(dni);
       if (c == null) {
        return null;
       }
       if(!c.getSeguros().isEmpty()) {
        throw new OperacionNoValida("El cliente tiene seguros asignados");
       }
       return clienteDao.eliminaCliente(dni);
    }

    @Override
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
         for(Cliente cliente : clienteDao.clientes()){
            if(cliente.getDni().equals(dni)){
                for(Seguro seguro : cliente.getSeguros()){
                    if(seguro.getMatricula().equals(s.getMatricula())){
                        throw new OperacionNoValida(dni);
                    }
                }
                cliente.getSeguros().add(s);
            }
        } 
        
        seguroDao.creaSeguro(s);
        return s;
    }

    @Override
    public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
        Cliente c = clienteDao.cliente(dni);

    if (c == null) {
        return null;
    }

    Seguro s = seguroDao.seguroPorMatricula(matricula);

    if (s == null) {
        return null;
    }

    if (!c.getSeguros().contains(s)) {
        throw new OperacionNoValida("El seguro no pertenece al cliente");
    }

    c.getSeguros().remove(s);
    seguroDao.eliminaSeguro(s.getId());

    return s;

    }

    @Override
    public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException {
        Seguro s = seguroDao.seguroPorMatricula(matricula);
        if(s == null) {
            return null;
        }
        s.setConductorAdicional(conductor);
        return seguroDao.actualizaSeguro(s);
    }

    @Override
    public Cliente cliente(String dni) throws DataAccessException {
        return clienteDao.cliente(dni);
    }

    @Override
    public Seguro seguro(String matricula) throws DataAccessException {
        return seguroDao.seguroPorMatricula(matricula);
    }
}


