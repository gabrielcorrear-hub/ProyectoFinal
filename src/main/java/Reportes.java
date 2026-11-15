import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reportes {

    private Cinema cinema;

    public Reportes(Cinema cinema) {
        this.cinema = cinema;
    }

    public Cinema getCinema() { return cinema; }
    public void setCinema(Cinema cinema) { this.cinema = cinema; }

    // Suma 1 por cada venta que siga ACTIVA en el sistema.
    public int totalEntradasVendidas() {
        int totalEntradas = 0;
        for (Venta ventaEnLista : cinema.getVentas()) {
            if (ventaEnLista.estaActiva()) totalEntradas++;
        }
        return totalEntradas;
    }

    // Suma el dinero de todas las ventas que no hayan sido canceladas.
    public double totalIngresos() {
        double ingresosTotales = 0;
        for (Venta ventaEnLista : cinema.getVentas()) {
            if (ventaEnLista.estaActiva()) ingresosTotales += ventaEnLista.getValorPagado();
        }
        return ingresosTotales;
    }

    // Compara las ventas de cada pelicula para encontrar la que mas tickets vendio.
    public Pelicula peliculaMasVendida() {
        Pelicula peliculaGanadora = null;
        int mayorCantidadVentas = -1;

        for (Pelicula peliculaEnLista : cinema.consultarPeliculas()) {
            int ventasDeEstaPelicula = 0;

            for (Venta ventaEnLista : cinema.getVentas()) {
                if (ventaEnLista.estaActiva() && ventaEnLista.getFuncion().getPelicula().equals(peliculaEnLista)) {
                    ventasDeEstaPelicula++;
                }
            }

            if (ventasDeEstaPelicula > mayorCantidadVentas) {
                mayorCantidadVentas = ventasDeEstaPelicula;
                peliculaGanadora = peliculaEnLista;
            }
        }

        if (mayorCantidadVentas == 0) return null;
        return peliculaGanadora;
    }

    // Devuelve cuantos asientos libres tiene una funcion.
    public int asientosDisponibles(String codFuncion) {
        Funcion funcionEncontrada = cinema.buscarFuncion(codFuncion);
        if (funcionEncontrada != null) return funcionEncontrada.contarDisponibles();
        return 0;
    }

    // Devuelve cuantos asientos vendidos tiene una funcion.
    public int asientosOcupados(String codFuncion) {
        Funcion funcionEncontrada = cinema.buscarFuncion(codFuncion);
        if (funcionEncontrada != null) return funcionEncontrada.contarOcupados();
        return 0;
    }

    // Filtra y devuelve una lista con las funciones que coinciden con la fecha y estan programadas.
    public List<Funcion> funcionesPorFecha(LocalDate fecha) {
        List<Funcion> funcionesFiltradas = new ArrayList<>();

        for (Funcion funcionEnLista : cinema.consultarFunciones()) {
            if (funcionEnLista.getFecha().equals(fecha) && funcionEnLista.estaProgramada()) {
                funcionesFiltradas.add(funcionEnLista);
            }
        }
        return funcionesFiltradas;
    }
}