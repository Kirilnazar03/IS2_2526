package es.unican.is2;

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
            if (cliente.getDni().equals(c.getDni())) {
                return null;
            }
        }
        clientesDAO.creaCliente(c);
        return c;
    }

    @Override
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
    }

    @Override
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
        for(Cliente cliente : clientesDAO.clientes()){
            if(cliente.getDni().equals(dni)){
                for(Seguro seguro : cliente.getSeguros()){
                    if(seguro.getMatricula().equals(s.getMatricula())){
                        throw new OperacionNoValida(dni);
                    }
                }
                cliente.getSeguros().add(s);
                segurosDAO.creaSeguro(s);
                return s;
            }
        } 
    }

    @Override
    public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
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