import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Cinema {

    private String nombre;
    private ArrayList<Pelicula> listaPeliculas;
    private ArrayList<Sala> listaSalas;
    private ArrayList<Funcion> listaFunciones;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Venta> listaVentas;

    public Cinema(String nombre) {
        this.nombre = nombre;
        this.listaPeliculas = new ArrayList<>();
        this.listaSalas = new ArrayList<>();
        this.listaFunciones = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.listaVentas = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Venta> getVentas() { return listaVentas; }

    public void registrarPelicula(Pelicula pelicula) {
        if (buscarPelicula(pelicula.getCodigo()) == null) {
            listaPeliculas.add(pelicula);
        }
    }

    public Pelicula buscarPelicula(String codigo) {
        for (Pelicula p : listaPeliculas) {
            if (p.getCodigo().equals(codigo)) return p;
        }
        return null;
    }

    public void modificarPelicula(String codigo, String nombre, String genero, int duracionMinutos, Clasificacion clasificacion) {
        Pelicula p = buscarPelicula(codigo);
        if (p != null) {
            p.setNombre(nombre);
            p.setGenero(genero);
            p.setDuracionMinutos(duracionMinutos);
            p.setClasificacion(clasificacion);
        }
    }

    public void deshabilitarPelicula(String codigo) {
        Pelicula p = buscarPelicula(codigo);
        if (p != null) p.deshabilitar();
    }

    public List<Pelicula> consultarPeliculas() { return listaPeliculas; }

    public void registrarSala(Sala sala) {
        if (buscarSala(sala.getCodigo()) == null) {
            listaSalas.add(sala);
        }
    }

    public Sala buscarSala(String codigo) {
        for (Sala s : listaSalas) {
            if (s.getCodigo().equals(codigo)) return s;
        }
        return null;
    }

    public void modificarSala(String codigo, String nombre, int filas, int asientosPorFila) {
        Sala s = buscarSala(codigo);
        if (s != null) {
            s.setNombre(nombre);
            s.generarAsientos(filas, asientosPorFila);
        }
    }

    public void deshabilitarSala(String codigo) {
        Sala s = buscarSala(codigo);
        if (s != null) s.deshabilitar();
    }

    public List<Sala> consultarSalas() { return listaSalas; }

    public Funcion programarFuncion(String codigo, String codPelicula, String codSala, LocalDate fecha, LocalTime hora, double valorBase) {
        Pelicula pelicula = buscarPelicula(codPelicula);
        Sala sala = buscarSala(codSala);

        if (pelicula != null && sala != null && sala.estaDisponible()) {
            Funcion nuevaFuncion = new Funcion(codigo, pelicula, sala, fecha, hora, valorBase);
            listaFunciones.add(nuevaFuncion);
            return nuevaFuncion;
        }
        return null;
    }

    public Funcion buscarFuncion(String codigo) {
        for (Funcion f : listaFunciones) {
            if (f.getCodigo().equals(codigo)) return f;
        }
        return null;
    }

    public void cancelarFuncion(String codigo) {
        Funcion f = buscarFuncion(codigo);
        if (f != null) f.cancelar();
    }

    public List<Funcion> consultarFunciones() { return listaFunciones; }

    public List<Asiento> consultarDisponibilidad(String codFuncion) {
        Funcion f = buscarFuncion(codFuncion);
        if (f != null) {
            return f.getAsientos();
        }
        return new ArrayList<>();
    }

    public void registrarCliente(Cliente cliente) { listaClientes.add(cliente); }

    public Cliente buscarCliente(String identificacion) {
        for (Cliente c : listaClientes) {
            if (c.getIdentificacion().equals(identificacion)) return c;
        }
        return null;
    }

    public Venta venderEntrada(String codFuncion, String idCliente, String codAsiento) {
        Funcion funcion = buscarFuncion(codFuncion);
        Cliente cliente = buscarCliente(idCliente);

        if (funcion == null || cliente == null) return null;

        Asiento asientoOcupado = funcion.ocuparAsiento(codAsiento);
        if (asientoOcupado == null) return null;

        double valorPagado = funcion.calcularValor(cliente.getTipoCliente());
        String codVenta = "V-" + (listaVentas.size() + 1);

        Venta nuevaVenta = new Venta(codVenta, cliente, funcion, asientoOcupado, valorPagado);
        listaVentas.add(nuevaVenta);

        return nuevaVenta;
    }

    public void cancelarVenta(String codVenta) {
        Venta v = buscarVenta(codVenta);
        if (v != null && v.estaActiva()) {
            v.cancelar();
            v.getFuncion().liberarAsiento(v.getAsiento().getCodigo());
        }
    }

    public Venta buscarVenta(String codVenta) {
        for (Venta v : listaVentas) {
            if (v.getCodigo().equals(codVenta)) return v;
        }
        return null;
    }
}