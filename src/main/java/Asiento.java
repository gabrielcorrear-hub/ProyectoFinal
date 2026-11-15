public class Asiento {

    private String codigo;
    private char fila;
    private int numero;
    private EstadoAsiento estado;

    // Crea un asiento con su fila y numero, calcula el codigo y lo deja DISPONIBLE.
    public Asiento(char fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
        generarCodigo();
    }

    // Convierte la fila y el numero en el codigo de texto (Ej: 'A' y 1 -> "A1").
    private void generarCodigo() {
        this.codigo = String.valueOf(fila) + String.valueOf(numero);
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public char getFila() { return fila; }

    // Actualiza la fila y recalcula el codigo automaticamente.
    public void setFila(char fila) {
        this.fila = fila;
        generarCodigo();
    }

    public int getNumero() { return numero; }

    // Actualiza el numero y recalcula el codigo automaticamente.
    public void setNumero(int numero) {
        this.numero = numero;
        generarCodigo();
    }

    public EstadoAsiento getEstado() { return estado; }
    public void setEstado(EstadoAsiento estado) { this.estado = estado; }

    // Comprueba si el asiento se puede vender.
    public boolean estaDisponible() {
        return estado == EstadoAsiento.DISPONIBLE;
    }

    // Marca el asiento como vendido.
    public void ocupar() {
        this.estado = EstadoAsiento.OCUPADO;
    }

    // Vuelve a poner el asiento como libre (para cancelaciones).
    public void liberar() {
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    // Crea un clon exacto de este asiento (util para que cada funcion tenga los suyos).
    public Asiento copiar() {
        Asiento copiaAsiento = new Asiento(this.fila, this.numero);
        copiaAsiento.setEstado(this.estado);
        return copiaAsiento;
    }

    // Devuelve el asiento en formato de texto para imprimir en consola.
    @Override
    public String toString() {
        return codigo + " - " + estado;
    }
}