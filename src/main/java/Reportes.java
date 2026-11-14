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

    // 1. Total de entradas vendidas (solo ventas ACTIVAS)
    public int totalEntradasVendidas() {
        int total = 0;
        for (Venta v : cinema.getVentas()) {
            if (v.estaActiva()) {
                total++;
            }
        }
        return total;
    }

    // 2. Total de ingresos (suma de ventas ACTIVAS)
    public double totalIngresos() {
        double total = 0;
        for (Venta v : cinema.getVentas()) {
            if (v.estaActiva()) {
                total += v.getValorPagado();
            }
        }
        return total;
    }

    // 3. Pelicula mas vendida
    public Pelicula peliculaMasVendida() {
        Pelicula masVendida = null;
        int mayorCantidadVentas = -1;

        for (Pelicula p : cinema.consultarPeliculas()) {
            int ventasDeEstaPelicula = 0;

            for (Venta v : cinema.getVentas()) {
                if (v.estaActiva() && v.getFuncion().getPelicula().equals(p)) {
                    ventasDeEstaPelicula++;
                }
            }

            if (ventasDeEstaPelicula > mayorCantidadVentas) {
                mayorCantidadVentas = ventasDeEstaPelicula;
                masVendida = p;
            }
        }

        if (mayorCantidadVentas == 0) {
            return null;
        }
        return masVendida;
    }

    // 4. Asientos disponibles de una funcion
    public int asientosDisponibles(String codFuncion) {
        Funcion f = cinema.buscarFuncion(codFuncion);
        if (f != null) {
            return f.contarDisponibles();
        }
        return 0;
    }

    // 5. Asientos ocupados de una funcion
    public int asientosOcupados(String codFuncion) {
        Funcion f = cinema.buscarFuncion(codFuncion);
        if (f != null) {
            return f.contarOcupados();
        }
        return 0;
    }

    // 6. Funciones para una fecha especifica
    public List<Funcion> funcionesPorFecha(LocalDate fecha) {
        List<Funcion> funcionesFiltradas = new ArrayList<>();

        for (Funcion f : cinema.consultarFunciones()) {
            if (f.getFecha().equals(fecha) && f.estaProgramada()) {
                funcionesFiltradas.add(f);
            }
        }
        return funcionesFiltradas;
    }
}