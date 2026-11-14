import java.time.LocalDateTime;

public class Venta {

    private String codigo;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;
    private LocalDateTime fechaHora;
    private double valorPagado;
    private EstadoVenta estado;

    public Venta(String codigo, Cliente cliente, Funcion funcion,
                 Asiento asiento, LocalDateTime fechaHora, double valorPagado) {

        this.codigo = codigo;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asiento = asiento;
        this.fechaHora = fechaHora;
        this.valorPagado = valorPagado;
        this.estado = EstadoVenta.ACTIVA;
    }

    public Venta(String codigo, Cliente cliente, Funcion funcion,
                 Asiento asiento, double valorPagado) {

        this(codigo, cliente, funcion, asiento, LocalDateTime.now(), valorPagado);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }
}