package modelo.entidades;

import java.io.Serializable;

public interface Factura extends Entidad{
    
    public String getIdentificador();
    public Cliente getCliente();
    public Double getImporte();
    public String getFecha();
    
    public void setIdentificador(String id);
    public void setCliente(Cliente cl);
    public void setImporte(Double im);
    public void setFecha(String fecha);

    
}
