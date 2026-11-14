import java.time.LocalDateTime;

public class Venta {

    private String codigo;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;
    private LocalDateTime fechaHora;
    private double valorPagado;
    private EstadoVenta estado;

    public Venta(String codigo, Cliente cliente, Funcion funcion, Asiento asiento, double valorPagado) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asiento = asiento;
        this.valorPagado = valorPagado;
        this.fechaHora = LocalDateTime.now(); // Se registra automáticamente
        this.estado = EstadoVenta.ACTIVA;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Funcion getFuncion() { return funcion; }
    public void setFuncion(Funcion funcion) { this.funcion = funcion; }
    public Asiento getAsiento() { return asiento; }
    public void setAsiento(Asiento asiento) { this.asiento = asiento; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public double getValorPagado() { return valorPagado; }
    public void setValorPagado(double valorPagado) { this.valorPagado = valorPagado; }
    public EstadoVenta getEstado() { return estado; }
    public void setEstado(EstadoVenta estado) { this.estado = estado; }

    public void cancelar() {
        this.estado = EstadoVenta.CANCELADA;
    }

    public boolean estaActiva() {
        return estado == EstadoVenta.ACTIVA;
    }

    public String generarEntrada() {
        return "===== ENTRADA CINEMAUQ =====\n"
                + "Codigo de venta: " + codigo + "\n"
                + "Cliente: " + cliente.getNombre() + "\n"
                + "Identificacion: " + cliente.getIdentificacion() + "\n"
                + "Pelicula: " + funcion.getPelicula().getNombre() + "\n"
                + "Sala: " + funcion.getSala().getNombre() + "\n"
                + "Fecha: " + funcion.getFecha() + "\n"
                + "Hora: " + funcion.getHora() + "\n"
                + "Asiento: " + asiento.getCodigo() + "\n"
                + "Tipo de entrada: " + cliente.getTipoCliente() + "\n"
                + "Valor pagado: $" + valorPagado + "\n"
                + "Estado: " + estado + "\n"
                + "=============================";
    }

    @Override
    public String toString() {
        return "Venta: " + codigo + " | Cliente: " + cliente.getNombre()
                + " | Asiento: " + asiento.getCodigo() + " | Estado: " + estado;
    }
}