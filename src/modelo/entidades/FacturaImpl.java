package modelo.entidades;
import java.util.List;


public class FacturaImpl implements Factura {

    private String identificador;
    private Cliente cliente;
    private Double importe;
    private String fecha;

    public FacturaImpl(String identifiador, Cliente cliente, Double importe) {
        this.identificador = identifiador;
        this.cliente = cliente;
        this.importe = importe;
        this.fecha = null;
    }

    public FacturaImpl(String identificador) {
        this(identificador,null,null,null);
    }
    
    public FacturaImpl(String identificador, Cliente cliente){
        this.identificador = identificador;
        this.cliente = cliente;
        this.fecha = null;
    }

    public FacturaImpl(String identificador, Cliente cliente, Double importe, String fecha) {
        this.identificador = identificador;
        this.cliente = cliente;
        this.importe = importe;
        this.fecha = fecha;
    }
    
    

    @Override
    public String getIdentificador() {
        return this.identificador;
    }

    @Override
    public Cliente getCliente() {
        return this.cliente;
    }

    @Override
    public Double getImporte() {
        return this.importe;
    }

    @Override
    public void setIdentificador(String id) {
        this.identificador = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    

   
    
}
