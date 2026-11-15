import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CinemaTest {

    private Cinema cinema;
    private Cliente cliente;
    private Pelicula pelicula;
    private Sala sala;

    // @BeforeEach hace que esto se ejecute ANTES de cada prueba.
    // Así cada prueba empieza con un cinema limpio y sin datos basura.
    @BeforeEach
    void setUp() {
        cinema = new Cinema("CINEMAUQ");

        cliente = new Cliente("12345", "Gabriel", TipoCliente.ESTUDIANTE);
        cinema.registrarCliente(cliente);

        pelicula = new Pelicula("P1", "Interestelar", "Sci-Fi", 169, Clasificacion.TODOS);
        cinema.registrarPelicula(pelicula);

        sala = new Sala("S1", "Sala 1", 2, 3); // Capacidad de 6 asientos
        cinema.registrarSala(sala);
    }


    // 1. PRUEBAS DE REGISTRO (Códigos duplicados)

    @Test
    void noDebePermitirRegistrarPeliculaDuplicada() {
        // Intentamos registrar otra película con el mismo código "P1"
        Pelicula peliculaDuplicada = new Pelicula("P1", "Otra peli", "Accion", 120, Clasificacion.TODOS);

        // assertThrows verifica que el código lance la excepción esperada
        assertThrows(PeliculaDuplicadaException.class, () -> {
            cinema.registrarPelicula(peliculaDuplicada);
        });
    }

    @Test
    void noDebePermitirRegistrarSalaDuplicada() {
        // Intentamos registrar otra sala con el mismo código "S1"
        Sala salaDuplicada = new Sala("S1", "Sala clon", 3, 3);

        assertThrows(SalaDuplicadaException.class, () -> {
            cinema.registrarSala(salaDuplicada);
        });
    }


    // 2. PRUEBAS DE PROGRAMACIÓN (Sala fuera de servicio)

    @Test
    void noDebePermitirProgramarFuncionEnSalaFueraDeServicio() {
        sala.deshabilitar(); // Ponemos la sala en estado FUERA_DE_SERVICIO

        assertThrows(SalaNoDisponibleException.class, () -> {
            cinema.programarFuncion("F1", "P1", "S1", LocalDate.now(), LocalTime.now(), 10000);
        });
    }


    // 3. PRUEBAS DE VENTA (Asiento ocupado, función inexistente, sin asientos)

    @Test
    void noDebePermitirVenderFuncionInexistente() {
        assertThrows(FuncionNoEncontradaException.class, () -> {
            // Intentamos vender para una función "F999" que no existe
            cinema.venderEntrada("F999", "12345", "A1");
        });
    }

    @Test
    void noDebePermitirVenderAsientoOcupado() {
        // Primero programamos una función válida y vendemos el asiento A1
        cinema.programarFuncion("F1", "P1", "S1", LocalDate.now(), LocalTime.now(), 10000);
        cinema.venderEntrada("F1", "12345", "A1");

        // Ahora intentamos vender el MISMO asiento A1 otra vez
        assertThrows(AsientoOcupadoException.class, () -> {
            cinema.venderEntrada("F1", "12345", "A1");
        });
    }

    @Test
    void noDebePermitirVenderCuandoNoHayAsientosDisponibles() {
        // Programamos función
        cinema.programarFuncion("F1", "P1", "S1", LocalDate.now(), LocalTime.now(), 10000);

        // La sala tiene 6 asientos. Los vendemos todos.
        cinema.venderEntrada("F1", "12345", "A1");
        cinema.venderEntrada("F1", "12345", "A2");
        cinema.venderEntrada("F1", "12345", "A3");
        cinema.venderEntrada("F1", "12345", "B1");
        cinema.venderEntrada("F1", "12345", "B2");
        cinema.venderEntrada("F1", "12345", "B3");

        // Intentamos vender un séptimo asiento (que no existe)
        assertThrows(SinAsientosDisponiblesException.class, () -> {
            cinema.venderEntrada("F1", "12345", "C1");
        });
    }


    // 4. PRUEBAS DE CANCELACIÓN (Venta inexistente)

    @Test
    void noDebePermitirCancelarVentaInexistente() {
        assertThrows(VentaNoEncontradaException.class, () -> {
            // Intentamos cancelar una venta "V-99" que no existe
            cinema.cancelarVenta("V-99");
        });
    }


    // 5. PRUEBA DEL CAMINO FELIZ (Todo sale bien)

    @Test
    void debeVenderEntradaYCalcularValorCorrectamente() {
        cinema.programarFuncion("F1", "P1", "S1", LocalDate.now(), LocalTime.now(), 10000);

        // Vendemos una entrada (Cliente es ESTUDIANTE, así que suman 12000 al valor base de 10000)
        Venta venta = cinema.venderEntrada("F1", "12345", "A1");

        // Verificamos que la venta no sea nula y que el valor sea el correcto (10000 + 12000 = 22000)
        assertNotNull(venta);
        assertEquals(22000, venta.getValorPagado());
        assertEquals("V-1", venta.getCodigo());
    }
}