package modelo.persistencia.JDBC;

import controlador.FacturaControllerImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.entidades.Cliente;
import modelo.entidades.ClienteImpl;
import modelo.entidades.Factura;
import modelo.entidades.FacturaImpl;
import modelo.persistencia.FacturaDAO;

public class FacturaDAOJDBC implements FacturaDAO {

    public List<Factura> listByCliente(String dni) {
        List<Factura> facturas = new ArrayList<Factura>();

        try {
            Statement stmt = Persistencia.createConnection().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM vfacturas where DNI=" + dni);
            String nombre, direccion, id_factura;
            Double importe;
            while (res.next()) {
                //DNI = res.getString("DNI");
                nombre = res.getString("nombre");
                direccion = res.getString("direccion");
                id_factura = res.getString("identificador");
                importe = res.getDouble("importe");

                //creo cliente
                Cliente cliente = new ClienteImpl(dni, nombre, direccion);
                //Añado la factura
                facturas.add(new FacturaImpl(id_factura, cliente, importe));
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
        return facturas;
    }

    public void create(Factura entidad) {
        String sql = "insert into facturas(identificador,id_cliente,importe,fecha_de_pago) values (?,?,?,?)";
        try {
            PreparedStatement stm = Persistencia.createConnection().prepareStatement(sql);
            stm.setString(1, entidad.getIdentificador());
            stm.setString(2, entidad.getCliente().getDNI());
            stm.setDouble(3, entidad.getImporte());
            String fechaEnString = entidad.getFecha();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = null; // Variable que almacenará la fecha
            // fechaEnString es de tipo String y contiene la fecha a convertir
            if (fechaEnString.length() > 0) {
                try {
                    fecha = formatter.parse(fechaEnString);
                } catch (ParseException ex) {
                    Logger.getLogger(FacturaControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            java.sql.Date variableSqlDate = new java.sql.Date(fecha.getTime());

            stm.setDate(3, variableSqlDate);

            stm.execute();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    public Factura read(String pk) {
        Factura f = null;
        try {
            Statement stmt = Persistencia.createConnection().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM facturas where identificador=" + pk);
            String identificador, id_cliente;
            Double importe;
            if (res.next()) {
                identificador = res.getString("identificador");
                id_cliente = res.getString("id_cliente");
                importe = res.getDouble("importe");
                //Leo el Cliente
                Cliente cliente = (new ClienteDAOJDBC()).read(id_cliente);
                //Creo la factura
                f = new FacturaImpl(identificador, cliente, importe);
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
        return f;
    }

    public void update(Factura entidad) {
        String sql = "update facturas set id_cliente=?, importe=?, fecha_de_pago=? where identificador like ?";

        try {
            PreparedStatement stm = Persistencia.createConnection().prepareStatement(sql);
            stm.setString(4, entidad.getIdentificador());
            stm.setString(1, entidad.getCliente().getDNI());
            stm.setDouble(2, entidad.getImporte());

            String fechaEnString = entidad.getFecha();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = null; // Variable que almacenará la fecha
            // fechaEnString es de tipo String y contiene la fecha a convertir
            if (fechaEnString.length() > 0) {
                try {
                    fecha = formatter.parse(fechaEnString);
                } catch (ParseException ex) {
                    Logger.getLogger(FacturaControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            java.sql.Date variableSqlDate = new java.sql.Date(fecha.getTime());

            stm.setDate(3, variableSqlDate);

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    public void delete(Factura entidad) {
        try {
            Statement stmt = Persistencia.createConnection().createStatement();
            stmt.executeUpdate("DELETE FROM facturas where identificador=" + entidad.getIdentificador());

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    public List<Factura> list() {
        List<Factura> facturas = new ArrayList<Factura>();

        try {
            Statement stmt = Persistencia.createConnection().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM vfacturas");
            String DNI, nombre, direccion, id_factura;
            Double importe;
            java.sql.Date variableSqlDate;
            while (res.next()) {
                DNI = res.getString("DNI");
                nombre = res.getString("nombre");
                direccion = res.getString("direccion");
                id_factura = res.getString("identificador");
                importe = res.getDouble("importe");
                variableSqlDate = res.getDate("fecha_de_pago");
                if (variableSqlDate == null) {
                    //creo cliente
                    Cliente cliente = new ClienteImpl(DNI, nombre, direccion);
                    //Añado la factura
                    facturas.add(new FacturaImpl(id_factura, cliente, importe, null));
                } else {
                    //creo cliente
                    Cliente cliente = new ClienteImpl(DNI, nombre, direccion);
                    //Añado la factura
                    facturas.add(new FacturaImpl(id_factura, cliente, importe, "1"));
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
        return facturas;
    }

    @Override
    public List<Factura> listByFecha(String fecha) {
        List<Factura> facturas = new ArrayList<Factura>();

        if (fecha.length() == 0) {
            try {
                Statement stmt = Persistencia.createConnection().createStatement();
                ResultSet res = stmt.executeQuery("SELECT * FROM vfacturas where fecha_de_pago IS NULL");
                String dni, nombre, direccion, id_factura;
                Double importe;
                while (res.next()) {
                    dni = res.getString("DNI");
                    nombre = res.getString("nombre");
                    direccion = res.getString("direccion");
                    id_factura = res.getString("identificador");
                    importe = res.getDouble("importe");

                    //creo cliente
                    Cliente cliente = new ClienteImpl(dni, nombre, direccion);
                    //Añado la factura
                    facturas.add(new FacturaImpl(id_factura, cliente, importe));
                }

            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                Persistencia.closeConnection();
            }
            return facturas;
        } else {
            try {
                Statement stmt = Persistencia.createConnection().createStatement();
                ResultSet res = stmt.executeQuery("SELECT * FROM vfacturas where YEAR(fecha_de_pago)=" + fecha);
                String dni, nombre, direccion, id_factura;
                Double importe;
                while (res.next()) {
                    dni = res.getString("DNI");
                    nombre = res.getString("nombre");
                    direccion = res.getString("direccion");
                    id_factura = res.getString("identificador");
                    importe = res.getDouble("importe");

                    //creo cliente
                    Cliente cliente = new ClienteImpl(dni, nombre, direccion);
                    //Añado la factura
                    facturas.add(new FacturaImpl(id_factura, cliente, importe, "1"));
                }

            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                Persistencia.closeConnection();
            }
            return facturas;
        }

    }

}
