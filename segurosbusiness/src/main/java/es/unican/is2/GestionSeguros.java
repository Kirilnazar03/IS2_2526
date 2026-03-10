package es.unican.is2;

<<<<<<< HEAD
public class GestionSeguros implements IGestionSeguros, IGestionClientes, IInfoSeguros {


    private IClientesDAO clientesDAO;
    private ISegurosDAO segurosDAO;

    public GestionSeguros(IClientesDAO clientesDAO, ISegurosDAO segurosDAO) {
        this.clientesDAO = clientesDAO;
        this.segurosDAO = segurosDAO;
    }

    @Override
    public Cliente cliente(String dni) throws DataAccessException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cliente'");
    }

    @Override
    public Seguro seguro(String matricula) throws DataAccessException {
        // TODO Auto-generated method stub

        throw new UnsupportedOperationException("Unimplemented method 'seguro'");
    }

    @Override
    public Cliente nuevoCliente(Cliente c) throws DataAccessException {
        for (Cliente cliente : clientesDAO.clientes()) {
=======
import java.util.List;

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
>>>>>>> c25143f7018d1a3d8db45f969c1936c87983326d
            if (cliente.getDni().equals(c.getDni())) {
                return null;
            }
        }
<<<<<<< HEAD
        clientesDAO.creaCliente(c);
=======
        clienteDao.creaCliente(c);
>>>>>>> c25143f7018d1a3d8db45f969c1936c87983326d
        return c;
    }

    @Override
<<<<<<< HEAD
    public Cliente bajaCliente(String dni) throws  DataAccessException,OperacionNoValida {
        for (Cliente cliente : clientesDAO.clientes()) {
            if (cliente.getDni().equals(dni)) {
               if (!cliente.getSeguros().isEmpty()){

                }
                clientesDAO.eliminaCliente(dni);
                return cliente;
                }else{
                    return null; 
                }
            }
        }
=======
    public Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException {
       Cliente c = clienteDao.cliente(dni);
       if (c == null) {
        return null;
       }
       if(!c.getSeguros().isEmpty()) {
        throw new OperacionNoValida("El cliente tiene seguros asignados");
       }
       return clienteDao.eliminaCliente(dni);
>>>>>>> c25143f7018d1a3d8db45f969c1936c87983326d
    }

    @Override
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
<<<<<<< HEAD
        for(Cliente cliente : clientesDAO.clientes()){
=======
         for(Cliente cliente : clienteDao.clientes()){
>>>>>>> c25143f7018d1a3d8db45f969c1936c87983326d
            if(cliente.getDni().equals(dni)){
                for(Seguro seguro : cliente.getSeguros()){
                    if(seguro.getMatricula().equals(s.getMatricula())){
                        throw new OperacionNoValida(dni);
                    }
                }
                cliente.getSeguros().add(s);
<<<<<<< HEAD
                segurosDAO.creaSeguro(s);
                return s;
            }
        } 
=======
            }
        } 
        
        seguroDao.creaSeguro(s);
        return s;
>>>>>>> c25143f7018d1a3d8db45f969c1936c87983326d
    }

    @Override
    public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
<<<<<<< HEAD
        for(Cliente c : clientesDAO.clientes()){
            if(c.getDni().equals(dni)){
                for(Seguro s : c.getSeguros()){
                    if(s.getMatricula().equals(matricula)){
                        c.getSeguros().remove(s);
                        segurosDAO.eliminaSeguro(s.getId());
                        return s;
                    }
                    throw new OperacionNoValida(matricula);
                }
                
            }
        }
        return null;
    }

    @Override
    public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'anhadeConductorAdicional'");
    }
    


}
=======
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


>>>>>>> c25143f7018d1a3d8db45f969c1936c87983326d
