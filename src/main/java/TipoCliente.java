public enum TipoCliente {
    GENERAL(15000),
    ESTUDIANTE(12000),
    NINO(10000),
    ADULTO_MAYOR(11000);

    private double valor;

    private TipoCliente(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }
}
