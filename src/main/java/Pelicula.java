public class Pelicula {
    private String codigo;
    private String nombre;
    private String genero;
    private int duracionMinutos;
    private Clasificacion clasificacion;
    private EstadoPelicula estado;

    // Crea la pelicula y la deja DISPONIBLE por defecto.
    public Pelicula(String codigo, String nombre, String genero, int duracionMinutos, Clasificacion clasificacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.estado = EstadoPelicula.DISPONIBLE;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public Clasificacion getClasificacion() { return clasificacion; }
    public void setClasificacion(Clasificacion clasificacion) { this.clasificacion = clasificacion; }
    public EstadoPelicula getEstado() { return estado; }
    public void setEstado(EstadoPelicula estado) { this.estado = estado; }

    // Pone la pelicula en cartelera.
    public void habilitar() { this.estado = EstadoPelicula.DISPONIBLE; }

    // Saca la pelicula de cartelera.
    public void deshabilitar() { this.estado = EstadoPelicula.NO_DISPONIBLE; }

    // Comprueba si la pelicula esta activa.
    public boolean estaDisponible() { return estado == EstadoPelicula.DISPONIBLE; }

    @Override
    public String toString() {
        return "Pelicula: " + codigo + " - " + nombre + " | Genero: " + genero + " | Duracion: " + duracionMinutos + " min | Clasificacion: " + clasificacion;
    }
}