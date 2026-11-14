public class Asiento {

    private String codigo;
    private char fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(char fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.codigo = "" + fila + numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public char getFila() {
        return fila;
    }

    public void setFila(char fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }
}
