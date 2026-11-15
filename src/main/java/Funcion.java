import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Funcion {

    private String codigo;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDate fecha;
    private LocalTime hora;
    private double valorBase;
    private EstadoFuncion estado;
    private ArrayList<Asiento> listaAsientos;

    // Crea la funcion y hace una COPIA independiente de los asientos de la sala.
    public Funcion(String codigo, Pelicula pelicula, Sala sala, LocalDate fecha, LocalTime hora, double valorBase) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.valorBase = valorBase;
        this.estado = EstadoFuncion.PROGRAMADA;

        this.listaAsientos = new ArrayList<>();
        for (Asiento asientoDeLaSala : sala.getAsientos()) {
            this.listaAsientos.add(asientoDeLaSala.copiar());
        }
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Pelicula getPelicula() { return pelicula; }
    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public double getValorBase() { return valorBase; }
    public void setValorBase(double valorBase) { this.valorBase = valorBase; }
    public EstadoFuncion getEstado() { return estado; }
    public void setEstado(EstadoFuncion estado) { this.estado = estado; }
    public List<Asiento> getAsientos() { return listaAsientos; }

    // Busca un asiento en especifico dentro de esta funcion usando su codigo.
    public Asiento buscarAsiento(String codigoAsiento) {
        for (Asiento asientoDeLaFuncion : listaAsientos) {
            if (asientoDeLaFuncion.getCodigo().equals(codigoAsiento)) {
                return asientoDeLaFuncion;
            }
        }
        return null;
    }

    // Si el asiento esta libre, lo ocupa y lo devuelve. Si no, devuelve null.
    public Asiento ocuparAsiento(String codigoAsiento) {
        Asiento asientoBuscado = buscarAsiento(codigoAsiento);
        if (asientoBuscado != null && asientoBuscado.estaDisponible()) {
            asientoBuscado.ocupar();
            return asientoBuscado;
        }
        return null;
    }

    // Pone el asiento como DISPONIBLE de nuevo.
    public void liberarAsiento(String codigoAsiento) {
        Asiento asientoBuscado = buscarAsiento(codigoAsiento);
        if (asientoBuscado != null) {
            asientoBuscado.liberar();
        }
    }

    // Crea una lista solo con los asientos que aun se pueden vender.
    public List<Asiento> obtenerAsientosDisponibles() {
        List<Asiento> asientosDisponibles = new ArrayList<>();
        for (Asiento asientoDeLaFuncion : listaAsientos) {
            if (asientoDeLaFuncion.estaDisponible()) {
                asientosDisponibles.add(asientoDeLaFuncion);
            }
        }
        return asientosDisponibles;
    }

    // Cuenta cuantos asientos libres quedan.
    public int contarDisponibles() {
        return obtenerAsientosDisponibles().size();
    }

    // Cuenta cuantos asientos vendidos hay.
    public int contarOcupados() {
        return listaAsientos.size() - contarDisponibles();
    }

    // Devuelve true si aun hay sillas para vender.
    public boolean hayAsientosDisponibles() {
        return contarDisponibles() > 0;
    }

    // Suma el precio base de la funcion + el recargo del tipo de cliente.
    public double calcularValor(TipoCliente tipoCliente) {
        return this.valorBase + tipoCliente.getValor();
    }

    public void cancelar() { this.estado = EstadoFuncion.CANCELADA; }
    public void finalizar() { this.estado = EstadoFuncion.FINALIZADA; }
    public boolean estaProgramada() { return estado == EstadoFuncion.PROGRAMADA; }

    @Override
    public String toString() {
        return "Funcion: " + codigo + " | Pelicula: " + pelicula.getNombre() + " | Sala: " + sala.getNombre() + " | Disponibles: " + contarDisponibles();
    }
}