import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Aplicacion {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Cinema cinema = new Cinema("CINEMAUQ");
        Reportes reportes = new Reportes(cinema);

        // Clientes quemados para pruebas
        cinema.registrarCliente(new Cliente("12345", "Juan Perez", TipoCliente.ESTUDIANTE));
        cinema.registrarCliente(new Cliente("67890", "Maria Gomez", TipoCliente.GENERAL));

        int opcion;

        do {
            System.out.println("\n===========================");
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
            sc.nextLine();

            switch (opcion) {
                case 1: {
                    System.out.println("--- Venta de Entrada ---");
                    System.out.print("Codigo de la funcion: "); String codFuncion = sc.nextLine();
                    System.out.print("Identificacion del cliente: "); String idCliente = sc.nextLine();
                    System.out.print("Codigo del asiento (Ej. A1): "); String codAsiento = sc.nextLine();

                    try {
                        Venta venta = cinema.venderEntrada(codFuncion, idCliente, codAsiento);
                        System.out.println("\n¡Venta exitosa!");
                        System.out.println(venta.generarEntrada());
                    } catch (Exception e) {
                        // Aquí atrapamos AsientoOcupadoException, FuncionNoEncontradaException, etc.
                        System.out.println("\nERROR: " + e.getMessage());
                    }
                    break;
                }
                case 2: {
                    System.out.println("--- Cancelar Venta ---");
                    System.out.print("Codigo de la venta a cancelar (Ej. V-1): "); String codVenta = sc.nextLine();

                    try {
                        cinema.cancelarVenta(codVenta);
                        System.out.println("Venta cancelada exitosamente. El asiento ha sido liberado.");
                    } catch (Exception e) {
                        System.out.println("\nERROR: " + e.getMessage());
                    }
                    break;
                }
                case 3: {
                    List<Pelicula> peliculas = cinema.consultarPeliculas();
                    if (peliculas.isEmpty()) System.out.println("No hay peliculas registradas.");
                    else for (Pelicula p : peliculas) System.out.println(p.toString() + "\n---------------------------");
                    break;
                }
                case 4: {
                    List<Funcion> funciones = cinema.consultarFunciones();
                    if (funciones.isEmpty()) System.out.println("No hay funciones programadas.");
                    else for (Funcion f : funciones) System.out.println(f.toString() + "\n---------------------------");
                    break;
                }
                case 5: {
                    System.out.print("Codigo de la funcion: "); String codFuncion = sc.nextLine();
                    List<Asiento> asientos = cinema.consultarDisponibilidad(codFuncion);
                    if (asientos.isEmpty()) {
                        System.out.println("La funcion no existe o no tiene asientos.");
                    } else {
                        Funcion f = cinema.buscarFuncion(codFuncion);
                        System.out.println("Pelicula: " + f.getPelicula().getNombre());
                        System.out.println("Disponibles: " + f.contarDisponibles() + " | Ocupados: " + f.contarOcupados());
                        for (Asiento a : asientos) System.out.println(a.toString());
                    }
                    break;
                }
                case 6: {
                    System.out.println("1. Registrar  2. Modificar  3. Deshabilitar");
                    System.out.print("Opcion: "); int subOpcion = sc.nextInt(); sc.nextLine();

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

                        try {
                            cinema.registrarPelicula(new Pelicula(codigo, nombre, genero, duracion, clasificacion));
                            System.out.println("Pelicula registrada exitosamente.");
                        } catch (Exception e) {
                            System.out.println("\nERROR: " + e.getMessage());
                        }
                    } else if (subOpcion == 2) {
                        System.out.print("Codigo a modificar: "); String codigo = sc.nextLine();
                        System.out.print("Nuevo Nombre: "); String nombre = sc.nextLine();
                        System.out.print("Nuevo Genero: "); String genero = sc.nextLine();
                        System.out.print("Nueva Duracion: "); int duracion = sc.nextInt(); sc.nextLine();
                        System.out.print("Nueva Clasificacion (1-5): "); int opcClas = sc.nextInt(); sc.nextLine();

                        Clasificacion clasificacion = Clasificacion.TODOS;
                        if (opcClas == 2) clasificacion = Clasificacion.MAYORES_7;
                        else if (opcClas == 3) clasificacion = Clasificacion.MAYORES_12;
                        else if (opcClas == 4) clasificacion = Clasificacion.MAYORES_15;
                        else if (opcClas == 5) clasificacion = Clasificacion.MAYORES_18;

                        cinema.modificarPelicula(codigo, nombre, genero, duracion, clasificacion);
                        System.out.println("Pelicula modificada (si existia).");
                    } else if (subOpcion == 3) {
                        System.out.print("Codigo a deshabilitar: "); String codigo = sc.nextLine();
                        cinema.deshabilitarPelicula(codigo);
                        System.out.println("Pelicula deshabilitada (si existia).");
                    }
                    break;
                }
                case 7: {
                    System.out.println("1. Programar  2. Cancelar");
                    System.out.print("Opcion: "); int subOpcion = sc.nextInt(); sc.nextLine();

                    if (subOpcion == 1) {
                        System.out.print("Codigo de la funcion: "); String codigo = sc.nextLine();
                        System.out.print("Codigo de la pelicula: "); String codPelicula = sc.nextLine();
                        System.out.print("Codigo de la sala: "); String codSala = sc.nextLine();
                        System.out.print("Fecha (AAAA-MM-DD): "); LocalDate fecha = LocalDate.parse(sc.nextLine());
                        System.out.print("Hora (HH:MM): "); LocalTime hora = LocalTime.parse(sc.nextLine());
                        System.out.print("Valor Base: "); double valorBase = sc.nextDouble(); sc.nextLine();

                        try {
                            cinema.programarFuncion(codigo, codPelicula, codSala, fecha, hora, valorBase);
                            System.out.println("Funcion programada exitosamente.");
                        } catch (Exception e) {
                            System.out.println("\nERROR: " + e.getMessage());
                        }
                    } else if (subOpcion == 2) {
                        System.out.print("Codigo de la funcion a cancelar: "); String codigo = sc.nextLine();
                        cinema.cancelarFuncion(codigo);
                        System.out.println("Funcion cancelada (si existia).");
                    }
                    break;
                }
                case 8: {
                    System.out.println("1. Registrar  2. Modificar  3. Deshabilitar");
                    System.out.print("Opcion: "); int subOpcion = sc.nextInt(); sc.nextLine();

                    if (subOpcion == 1) {
                        System.out.print("Codigo: "); String codigo = sc.nextLine();
                        System.out.print("Nombre: "); String nombre = sc.nextLine();
                        System.out.print("Filas: "); int filas = sc.nextInt(); sc.nextLine();
                        System.out.print("Asientos por fila: "); int asientos = sc.nextInt(); sc.nextLine();

                        try {
                            cinema.registrarSala(new Sala(codigo, nombre, filas, asientos));
                            System.out.println("Sala registrada exitosamente.");
                        } catch (Exception e) {
                            System.out.println("\nERROR: " + e.getMessage());
                        }
                    } else if (subOpcion == 2) {
                        System.out.print("Codigo a modificar: "); String codigo = sc.nextLine();
                        System.out.print("Nuevo Nombre: "); String nombre = sc.nextLine();
                        System.out.print("Nuevas filas: "); int filas = sc.nextInt(); sc.nextLine();
                        System.out.print("Nuevos asientos por fila: "); int asientos = sc.nextInt(); sc.nextLine();
                        cinema.modificarSala(codigo, nombre, filas, asientos);
                        System.out.println("Sala modificada (si existia).");
                    } else if (subOpcion == 3) {
                        System.out.print("Codigo a deshabilitar: "); String codigo = sc.nextLine();
                        cinema.deshabilitarSala(codigo);
                        System.out.println("Sala deshabilitada (si existia).");
                    }
                    break;
                }
                case 9: {
                    System.out.println("1.Entradas  2.Ingresos  3.Mas vendida  4.Disp. Funcion  5.Ocup. Funcion  6.Func. por Fecha");
                    System.out.print("Opcion: "); int subOpcion = sc.nextInt(); sc.nextLine();

                    if (subOpcion == 1) System.out.println("Total entradas: " + reportes.totalEntradasVendidas());
                    else if (subOpcion == 2) System.out.println("Total ingresos: $" + reportes.totalIngresos());
                    else if (subOpcion == 3) {
                        Pelicula p = reportes.peliculaMasVendida();
                        System.out.println(p != null ? "Mas vendida: " + p.getNombre() : "Aun no hay ventas.");
                    } else if (subOpcion == 4) {
                        System.out.print("Codigo funcion: "); String cod = sc.nextLine();
                        System.out.println("Disponibles: " + reportes.asientosDisponibles(cod));
                    } else if (subOpcion == 5) {
                        System.out.print("Codigo funcion: "); String cod = sc.nextLine();
                        System.out.println("Ocupados: " + reportes.asientosOcupados(cod));
                    } else if (subOpcion == 6) {
                        System.out.print("Fecha (AAAA-MM-DD): "); LocalDate fecha = LocalDate.parse(sc.nextLine());
                        List<Funcion> fs = reportes.funcionesPorFecha(fecha);
                        if (fs.isEmpty()) System.out.println("No hay funciones para esa fecha.");
                        else for (Funcion f : fs) System.out.println(f.toString());
                    }
                    break;
                }
                case 10: System.out.println("Saliendo..."); break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 10);

        sc.close();
    }
}