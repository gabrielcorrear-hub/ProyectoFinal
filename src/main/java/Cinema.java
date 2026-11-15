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

    // --- GESTION DE PELICULAS ---

    // Agrega una pelicula a la lista. Lanza error si el codigo ya existe.
    public void registrarPelicula(Pelicula pelicula) {
        if (buscarPelicula(pelicula.getCodigo()) != null) {
            throw new PeliculaDuplicadaException("Ya existe una pelicula con el codigo: " + pelicula.getCodigo());
        }
        listaPeliculas.add(pelicula);
    }

    // Recorre la lista buscando una pelicula por su codigo.
    public Pelicula buscarPelicula(String codigo) {
        for (Pelicula peliculaEnLista : listaPeliculas) {
            if (peliculaEnLista.getCodigo().equals(codigo)) return peliculaEnLista;
        }
        return null;
    }

    // Busca la pelicula y actualiza sus datos.
    public void modificarPelicula(String codigo, String nombre, String genero, int duracionMinutos, Clasificacion clasificacion) {
        Pelicula peliculaEncontrada = buscarPelicula(codigo);
        if (peliculaEncontrada != null) {
            peliculaEncontrada.setNombre(nombre);
            peliculaEncontrada.setGenero(genero);
            peliculaEncontrada.setDuracionMinutos(duracionMinutos);
            peliculaEncontrada.setClasificacion(clasificacion);
        }
    }

    // Saca la pelicula de cartelera.
    public void deshabilitarPelicula(String codigo) {
        Pelicula peliculaEncontrada = buscarPelicula(codigo);
        if (peliculaEncontrada != null) peliculaEncontrada.deshabilitar();
    }

    public List<Pelicula> consultarPeliculas() { return listaPeliculas; }

    // --- GESTION DE SALAS ---

    // Agrega una sala a la lista. Lanza error si el codigo ya existe.
    public void registrarSala(Sala sala) {
        if (buscarSala(sala.getCodigo()) != null) {
            throw new SalaDuplicadaException("Ya existe una sala con el codigo: " + sala.getCodigo());
        }
        listaSalas.add(sala);
    }

    // Recorre la lista buscando una sala por su codigo.
    public Sala buscarSala(String codigo) {
        for (Sala salaEnLista : listaSalas) {
            if (salaEnLista.getCodigo().equals(codigo)) return salaEnLista;
        }
        return null;
    }

    // Busca la sala y actualiza sus datos (regenerando los asientos).
    public void modificarSala(String codigo, String nombre, int filas, int asientosPorFila) {
        Sala salaEncontrada = buscarSala(codigo);
        if (salaEncontrada != null) {
            salaEncontrada.setNombre(nombre);
            salaEncontrada.generarAsientos(filas, asientosPorFila);
        }
    }

    // Pone la sala fuera de servicio.
    public void deshabilitarSala(String codigo) {
        Sala salaEncontrada = buscarSala(codigo);
        if (salaEncontrada != null) salaEncontrada.deshabilitar();
    }

    public List<Sala> consultarSalas() { return listaSalas; }

    // --- GESTION DE FUNCIONES ---

    // Valida que la pelicula y la sala existan, y que la sala no este en mantenimiento.
    public Funcion programarFuncion(String codigo, String codPelicula, String codSala, LocalDate fecha, LocalTime hora, double valorBase) {
        Pelicula peliculaEncontrada = buscarPelicula(codPelicula);
        Sala salaEncontrada = buscarSala(codSala);

        if (peliculaEncontrada == null) {
            throw new FuncionNoEncontradaException("La pelicula " + codPelicula + " no existe.");
        }
        if (salaEncontrada == null) {
            throw new SalaNoDisponibleException("La sala " + codSala + " no existe.");
        }
        if (!salaEncontrada.estaDisponible()) {
            throw new SalaNoDisponibleException("La sala " + codSala + " esta fuera de servicio.");
        }

        Funcion nuevaFuncion = new Funcion(codigo, peliculaEncontrada, salaEncontrada, fecha, hora, valorBase);
        listaFunciones.add(nuevaFuncion);
        return nuevaFuncion;
    }

    // Recorre la lista buscando una funcion por su codigo.
    public Funcion buscarFuncion(String codigo) {
        for (Funcion funcionEnLista : listaFunciones) {
            if (funcionEnLista.getCodigo().equals(codigo)) return funcionEnLista;
        }
        return null;
    }

    // Busca la funcion y cambia su estado a cancelada.
    public void cancelarFuncion(String codigo) {
        Funcion funcionEncontrada = buscarFuncion(codigo);
        if (funcionEncontrada != null) funcionEncontrada.cancelar();
    }

    public List<Funcion> consultarFunciones() { return listaFunciones; }

    // Devuelve la lista de asientos de una funcion para ver disponibilidad.
    public List<Asiento> consultarDisponibilidad(String codFuncion) {
        Funcion funcionEncontrada = buscarFuncion(codFuncion);
        if (funcionEncontrada != null) {
            return funcionEncontrada.getAsientos();
        }
        return new ArrayList<>();
    }

    // --- GESTION DE CLIENTES Y VENTAS ---

    public void registrarCliente(Cliente cliente) { listaClientes.add(cliente); }

    // Recorre la lista buscando un cliente por su cedula.
    public Cliente buscarCliente(String identificacion) {
        for (Cliente clienteEnLista : listaClientes) {
            if (clienteEnLista.getIdentificacion().equals(identificacion)) return clienteEnLista;
        }
        return null;
    }

    // Ejecuta el proceso completo de venta: valida, ocupa el asiento, calcula precio y crea el ticket.
    public Venta venderEntrada(String codFuncion, String idCliente, String codAsiento) {
        Funcion funcionEncontrada = buscarFuncion(codFuncion);
        Cliente clienteEncontrado = buscarCliente(idCliente);

        if (funcionEncontrada == null) {
            throw new FuncionNoEncontradaException("La funcion " + codFuncion + " no existe.");
        }
        if (clienteEncontrado == null) {
            throw new IllegalArgumentException("El cliente con identificacion " + idCliente + " no existe.");
        }
        if (!funcionEncontrada.hayAsientosDisponibles()) {
            throw new SinAsientosDisponiblesException("No hay asientos disponibles para esta funcion.");
        }

        Asiento asientoVendido = funcionEncontrada.ocuparAsiento(codAsiento);
        if (asientoVendido == null) {
            throw new AsientoOcupadoException("El asiento " + codAsiento + " ya esta ocupado o no existe.");
        }

        double valorPagado = funcionEncontrada.calcularValor(clienteEncontrado.getTipoCliente());
        String codVenta = "V-" + (listaVentas.size() + 1);

        Venta nuevaVenta = new Venta(codVenta, clienteEncontrado, funcionEncontrada, asientoVendido, valorPagado);
        listaVentas.add(nuevaVenta);

        return nuevaVenta;
    }

    // Cancela el ticket y libera el asiento en la funcion.
    public void cancelarVenta(String codVenta) {
        Venta ventaEncontrada = buscarVenta(codVenta);

        if (ventaEncontrada == null) {
            throw new VentaNoEncontradaException("La venta " + codVenta + " no existe.");
        }
        if (!ventaEncontrada.estaActiva()) {
            throw new IllegalArgumentException("La venta " + codVenta + " ya se encuentra cancelada.");
        }

        ventaEncontrada.cancelar();
        ventaEncontrada.getFuncion().liberarAsiento(ventaEncontrada.getAsiento().getCodigo());
    }

    // Recorre la lista buscando un ticket de venta por su codigo.
    public Venta buscarVenta(String codVenta) {
        for (Venta ventaEnLista : listaVentas) {
            if (ventaEnLista.getCodigo().equals(codVenta)) return ventaEnLista;
        }
        return null;
    }
}