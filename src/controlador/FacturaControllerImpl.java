package controlador;

import java.io.Serializable;
import java.util.List;
import modelo.FacturaModel;
import modelo.entidades.Cliente;
import modelo.entidades.Factura;
import modelo.entidades.FacturaImpl;
import vista.factura.FacturaView;

public class FacturaControllerImpl extends AbstractControllerImpl<FacturaModel, FacturaView, Serializable> implements FacturaController{
    
    @Override
    protected Factura generaEntidad(List<Serializable> datos){
        String identificador=(String)datos.get(0);
        Cliente cliente=(Cliente)datos.get(1);
        Double importe=new Double((String)datos.get(2));
        String fecha = (String)datos.get(3);
        Factura f=new FacturaImpl(identificador,cliente);
        f.setImporte(importe);
        f.setFecha(fecha);
        
        return f;

    }
    @Override
    protected Factura generaEntidad(Serializable pk){
        return new FacturaImpl((String)pk);
    }

    @Override
    public List<Factura> listaFacturaEntidadGesture() {
        return getModel().listar();
    }

    @Override
    public List<Factura> listarFacuraEntidadPorClienteGesture(String nombre) {
        return getModel().listarPorCliente(nombre);
    }

    @Override
    public List<Factura> listarFacuraEntidadPorFecha(String fecha) {
        return getModel().listarPorFecha(fecha);
    }

}
