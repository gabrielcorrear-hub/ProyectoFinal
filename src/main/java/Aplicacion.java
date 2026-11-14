import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Aplicacion {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

            Cinema cinema = new Cinema("CINEMAUQ");
            Reportes reportes = new Reportes(cinema);

            Cliente cliente1 = new Cliente("12345", "Juan Perez", TipoCliente.ESTUDIANTE);
            Cliente cliente2 = new Cliente("67890", "Maria Gomez", TipoCliente.GENERAL);
            cinema.registrarCliente(cliente1);
            cinema.registrarCliente(cliente2);

        int opcion;

        do {
            System.out.println("===========================");
            System.out.println("         CINEMAUQ          ");
            System.out.println("===========================");
            System.out.println("1. Venta de entrada");
            System.out.println("2. Cancelar venta");
            System.out.println("3. Consultar peliculas");
            System.out.println("4. Consultar funciones");
            System.out.println("5. Consultar disponibilidad");
            System.out.println("6. Administrar peliculas");
            System.out.println("7. Administrar funciones");
            System.out.println("8. Administrar salas");
            System.out.println("9. Generar reportes");
            System.out.println("10. Salir");
            System.out.println("===========================");
            System.out.print("Seleccione una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1: {
                    System.out.println("--- Venta de Entrada ---");
                    System.out.print("Codigo de la funcion: ");
                    String codFuncion = sc.nextLine();

                    System.out.print("Identificacion del cliente: ");
                    String idCliente = sc.nextLine();

                    System.out.print("Codigo del asiento (Ej. A1): ");
                    String codAsiento = sc.nextLine();

                    // Llamamos a la logica del Cinema
                    Venta venta = cinema.venderEntrada(codFuncion, idCliente, codAsiento);

                    if (venta != null) {
                        System.out.println("\n¡Venta exitosa!");
                        System.out.println(venta.generarEntrada()); // Imprime el ticket
                    } else {
                        System.out.println("\nError: No se pudo realizar la venta.");
                        System.out.println("Verifique que la funcion y el cliente existan, y que el asiento este disponible.");
                    }
                    break;
                }
                case 2: {
                    System.out.println("--- Cancelar Venta ---");
                    System.out.print("Codigo de la venta a cancelar (Ej. V-1): ");
                    String codVenta = sc.nextLine();

                    Venta venta = cinema.buscarVenta(codVenta);

                    if (venta == null) {
                        System.out.println("Error: La venta no existe.");
                    } else if (!venta.estaActiva()) {
                        System.out.println("Error: La venta ya se encuentra cancelada.");
                    } else {
                        cinema.cancelarVenta(codVenta);
                        System.out.println("Venta cancelada exitosamente. El asiento ha sido liberado.");
                    }
                    break;
                }
                case 3: {
                    System.out.println("--- Lista de Peliculas ---");
                    List<Pelicula> peliculas = cinema.consultarPeliculas();

                    if (peliculas.isEmpty()) {
                        System.out.println("No hay peliculas registradas.");
                    } else {
                        for (Pelicula p : peliculas) {
                            System.out.println(p.toString());
                            System.out.println("---------------------------");
                        }
                    }
                    break;
                }
                case 4: {
                    System.out.println("--- Lista de Funciones ---");
                    List<Funcion> funciones = cinema.consultarFunciones();

                    if (funciones.isEmpty()) {
                        System.out.println("No hay funciones programadas.");
                    } else {
                        for (Funcion f : funciones) {
                            System.out.println(f.toString());
                            System.out.println("---------------------------");
                        }
                    }
                    break;
                }
                case 5: {
                    System.out.println("--- Consultar Disponibilidad ---");
                    System.out.print("Codigo de la funcion: ");
                    String codFuncion = sc.nextLine();

                    Funcion f = cinema.buscarFuncion(codFuncion);
                    if (f == null) {
                        System.out.println("Error: La funcion no existe.");
                        break;
                    }

                    List<Asiento> asientos = cinema.consultarDisponibilidad(codFuncion);

                    System.out.println("Pelicula: " + f.getPelicula().getNombre());
                    System.out.println("Total asientos: " + asientos.size());
                    System.out.println("Disponibles: " + f.contarDisponibles());
                    System.out.println("Ocupados: " + f.contarOcupados());
                    System.out.println("Detalle:");
                    for (Asiento a : asientos) {
                        System.out.println(a.toString()); // Usa el toString() del Asiento
                    }
                    break;
                }
                case 6: {
                    System.out.println("--- Administrar Peliculas ---");
                    System.out.println("1. Registrar  2. Modificar  3. Deshabilitar");
                    System.out.print("Opcion: ");
                    int subOpcion = sc.nextInt();
                    sc.nextLine();

                    if (subOpcion == 1) {
                        System.out.print("Codigo: "); String codigo = sc.nextLine();
                        System.out.print("Nombre: "); String nombre = sc.nextLine();
                        System.out.print("Genero: "); String genero = sc.nextLine();
                        System.out.print("Duracion (minutos): "); int duracion = sc.nextInt(); sc.nextLine();
                        System.out.print("Clasificacion (1.TODOS, 2.MAYORES_7, 3.MAYORES_12, 4.MAYORES_15, 5.MAYORES_18): ");
                        int opcClas = sc.nextInt(); sc.nextLine();

                        Clasificacion clasificacion = Clasificacion.TODOS;
                        if (opcClas == 2) clasificacion = Clasificacion.MAYORES_7;
                        else if (opcClas == 3) clasificacion = Clasificacion.MAYORES_12;
                        else if (opcClas == 4) clasificacion = Clasificacion.MAYORES_15;
                        else if (opcClas == 5) clasificacion = Clasificacion.MAYORES_18;

                        Pelicula p = new Pelicula(codigo, nombre, genero, duracion, clasificacion);
                        cinema.registrarPelicula(p);
                        System.out.println("Pelicula registrada");

                    } else if (subOpcion == 2) {
                        System.out.print("Codigo de la pelicula a modificar: "); String codigo = sc.nextLine();
                        System.out.print("Nuevo Nombre: "); String nombre = sc.nextLine();
                        System.out.print("Nuevo Genero: "); String genero = sc.nextLine();
                        System.out.print("Nueva Duracion (minutos): "); int duracion = sc.nextInt(); sc.nextLine();
                        System.out.print("Nueva Clasificacion (1.TODOS, 2.MAYORES_7, 3.MAYORES_12, 4.MAYORES_15, 5.MAYORES_18): ");
                        int opcClas = sc.nextInt(); sc.nextLine();

                        Clasificacion clasificacion = Clasificacion.TODOS;
                        if (opcClas == 2) clasificacion = Clasificacion.MAYORES_7;
                        else if (opcClas == 3) clasificacion = Clasificacion.MAYORES_12;
                        else if (opcClas == 4) clasificacion = Clasificacion.MAYORES_15;
                        else if (opcClas == 5) clasificacion = Clasificacion.MAYORES_18;

                        cinema.modificarPelicula(codigo, nombre, genero, duracion, clasificacion);
                        System.out.println("Pelicula modificada ");

                    } else if (subOpcion == 3) {
                        System.out.print("Codigo de la pelicula a deshabilitar: ");
                        String codigo = sc.nextLine();
                        cinema.deshabilitarPelicula(codigo);
                        System.out.println("Pelicula deshabilitada ");
                    }
                    break;
                }
                case 7: {
                    System.out.println("--- Administrar Funciones ---");
                    System.out.println("1. Programar  2. Cancelar");
                    System.out.print("Opcion: ");
                    int subOpcion = sc.nextInt();
                    sc.nextLine();

                    if (subOpcion == 1) {
                        System.out.print("Codigo de la funcion: "); String codigo = sc.nextLine();
                        System.out.print("Codigo de la pelicula: "); String codPelicula = sc.nextLine();
                        System.out.print("Codigo de la sala: "); String codSala = sc.nextLine();

                        System.out.print("Fecha (AAAA-MM-DD): "); String fechaStr = sc.nextLine();
                        LocalDate fecha = LocalDate.parse(fechaStr); // Convierte texto a fecha

                        System.out.print("Hora (HH:MM): "); String horaStr = sc.nextLine();
                        LocalTime hora = LocalTime.parse(horaStr); // Convierte texto a hora

                        System.out.print("Valor Base de la entrada: "); double valorBase = sc.nextDouble(); sc.nextLine();

                        Funcion f = cinema.programarFuncion(codigo, codPelicula, codSala, fecha, hora, valorBase);

                        if (f != null) {
                            System.out.println("Funcion programada exitosamente.");
                        } else {
                            System.out.println("Error: La pelicula o la sala no existen, o la sala esta fuera de servicio.");
                        }

                    } else if (subOpcion == 2) {
                        System.out.print("Codigo de la funcion a cancelar: ");
                        String codigo = sc.nextLine();
                        cinema.cancelarFuncion(codigo);
                        System.out.println("Funcion cancelada");
                    }
                    break;
                }
                case 8: {
                    System.out.println("--- Administrar Salas ---");
                    System.out.println("1. Registrar  2. Modificar  3. Deshabilitar");
                    System.out.print("Opcion: ");
                    int subOpcion = sc.nextInt();
                    sc.nextLine();

                    if (subOpcion == 1) {
                        System.out.print("Codigo: "); String codigo = sc.nextLine();
                        System.out.print("Nombre: "); String nombre = sc.nextLine();
                        System.out.print("Cantidad de filas: "); int filas = sc.nextInt(); sc.nextLine();
                        System.out.print("Asientos por fila: "); int asientos = sc.nextInt(); sc.nextLine();

                        Sala s = new Sala(codigo, nombre, filas, asientos);
                        cinema.registrarSala(s);
                        System.out.println("Sala registrada (si el codigo no existia).");

                    } else if (subOpcion == 2) {
                        System.out.print("Codigo de la sala a modificar: "); String codigo = sc.nextLine();
                        System.out.print("Nuevo Nombre: "); String nombre = sc.nextLine();
                        System.out.print("Nuevas filas: "); int filas = sc.nextInt(); sc.nextLine();
                        System.out.print("Nuevos asientos por fila: "); int asientos = sc.nextInt(); sc.nextLine();

                        cinema.modificarSala(codigo, nombre, filas, asientos);
                        System.out.println("Sala modificada");

                    } else if (subOpcion == 3) {
                        System.out.print("Codigo de la sala a deshabilitar: ");
                        String codigo = sc.nextLine();
                        cinema.deshabilitarSala(codigo);
                        System.out.println("Sala deshabilitada");
                    }
                    break;
                }
                case 9: {
                    System.out.println("--- Generar Reportes ---");
                    System.out.println("1. Total entradas vendidas");
                    System.out.println("2. Total ingresos");
                    System.out.println("3. Pelicula mas vendida");
                    System.out.println("4. Asientos disponibles de una funcion");
                    System.out.println("5. Asientos ocupados de una funcion");
                    System.out.println("6. Funciones por fecha");
                    System.out.print("Opcion: ");
                    int subOpcion = sc.nextInt();
                    sc.nextLine();

                    if (subOpcion == 1) {
                        System.out.println("Total de entradas vendidas: " + reportes.totalEntradasVendidas());
                    } else if (subOpcion == 2) {
                        System.out.println("Total de ingresos: $" + reportes.totalIngresos());
                    } else if (subOpcion == 3) {
                        Pelicula masVendida = reportes.peliculaMasVendida();
                        if (masVendida != null) {
                            System.out.println("La pelicula mas vendida es: " + masVendida.getNombre());
                        } else {
                            System.out.println("Aun no hay ventas registradas.");
                        }
                    } else if (subOpcion == 4) {
                        System.out.print("Codigo de la funcion: "); String cod = sc.nextLine();
                        System.out.println("Asientos disponibles: " + reportes.asientosDisponibles(cod));
                    } else if (subOpcion == 5) {
                        System.out.print("Codigo de la funcion: "); String cod = sc.nextLine();
                        System.out.println("Asientos ocupados: " + reportes.asientosOcupados(cod));
                    } else if (subOpcion == 6) {
                        System.out.print("Fecha a consultar (AAAA-MM-DD): ");
                        String fechaStr = sc.nextLine();
                        LocalDate fecha = LocalDate.parse(fechaStr);

                        List<Funcion> funcionesFecha = reportes.funcionesPorFecha(fecha);
                        if (funcionesFecha.isEmpty()) {
                            System.out.println("No hay funciones programadas para esa fecha.");
                        } else {
                            for (Funcion f : funcionesFecha) {
                                System.out.println(f.toString());
                            }
                        }
                    }
                    break;
                }
                case 10: {
                    System.out.println("Saliendo del sistema CINEMAUQ...");
                    break;
                }
                default: {
                    System.out.println("Opcion invalida. Intente de nuevo.");
                    break;
                }
            }

        } while (opcion != 10);

        sc.close();
    }
}