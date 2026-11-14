public class Asiento {

    private String codigo;
    private char fila;
    private int numero;
    private EstadoAsiento estado;

    // Constructor: guarda fila, numero, deja DISPONIBLE y llama a la funcion que arma el codigo
    public Asiento(char fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
        //no tocar, toco asi porque sino no se podai hacer el codigo con al fila y columna etc
        generarCodigo();
    }

    private void generarCodigo() {
        String filaTexto = String.valueOf(fila);
        String numeroTexto = String.valueOf(numero);
        this.codigo = filaTexto + numeroTexto;
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
        generarCodigo();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
        generarCodigo();
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }


    public boolean estaDisponible() {
        return estado == EstadoAsiento.DISPONIBLE;
    }

    public void ocupar() {
        this.estado = EstadoAsiento.OCUPADO;
    }

    public void liberar() {
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public Asiento copiar() {
        Asiento copia = new Asiento(this.fila, this.numero);
        copia.setEstado(this.estado);
        return copia;
    }

    @Override
    public String toString() {
        return codigo + " - " + estado;
    }
}