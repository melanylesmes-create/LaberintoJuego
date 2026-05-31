package vista;

import configuracion.configuracionPrincipal;
import configuracion.ResultadoDTO;
import java.util.Scanner;
import modelo.Celda;


/* Esta clase es la interfaz de usuario en consola.
   Su única responsabilidad es manejar la entrada y salida de datos.
   Delega TODA la lógica a ConfiguracionPrincipal. 
   NO contiene algoritmos, ni manipu
   */
public class VistaConsola {
    private static Scanner entrada = new Scanner(System.in);
    private static configuracionPrincipal puente;

    /* Punto de entrada del programa.
       Configura dimensiones, inicializa el puente y mantiene el bucle de partidas.
    */
    public static void main(String[] args) {
        mostrarBienvenida();

        int filas = pedirEntero("Filas (impar >=5): ");
        int columnas = pedirEntero("Columnas (impar >=5): ");

        


        // Inicializamos el puente con una caché de 50 laberintos
        puente = new configuracionPrincipal(filas, columnas, 50);

        // Bucle que permite jugar múltiples veces sin reiniciar el programa
        while (confirmarContinuar()) {
            Long semilla = pedirSemilla();
            ejecutarPartida(semilla);
        }

        System.out.println("\n✅ Programa finalizado correctamente.");
    }

    /* Orquesta generación, resolución, comparación y visualización.
       Solo coordina llamadas al puente, sin lógica propia.
    */
    private static void ejecutarPartida(Long semilla) {
        System.out.print("¿Desea ver la búsqueda paso a paso? (s/n): ");
        boolean modoVisual = entrada.next().trim().equalsIgnoreCase("s");

        System.out.println("\n🔄 Generando laberinto...");
        char[][] matrizInicial = puente.generarLaberinto(semilla);
        imprimirMatriz(matrizInicial);

        System.out.println("\nQ)Buscando camino óptimo con BFS...");
        ResultadoDTO resultado = puente.resolverBFS(semilla);

        if (resultado.ruta.verificarEsVacia()) {
            System.out.println("X) No se encontró un camino válido para esta semilla.");
            return;
        }

        System.out.println("\n:) Ruta óptima encontrada:");
        // La matriz ya viene marcada con '·' desde resolverBFS()
        imprimirMatriz(puente.generarLaberinto(semilla));
        

        System.out.println("-- Pasos mínimos (BFS): " + (resultado.ruta.cantidad() - 1));
        System.out.println("--️ Tiempo de ejecución: " + resultado.tiempoMs + " ms");

        // Comparación opcional con DFS
        int pasosDFS = puente.compararDFS();
        if (pasosDFS != -1) {
            System.out.println(":D) Pasos encontrados por DFS: " + pasosDFS + " (no garantiza el mínimo)");
        } else {
            System.out.println("D:) DFS: No encontró camino válido.");
        }

        System.out.println("\n- TOP 5 RANKING (menor cantidad de pasos):");
        System.out.println(puente.obtenerRanking());
        System.out.println("-----------------------------------");
    }

    /* Dibuja la matriz fila por fila en la consola.
       Agrega espaciado para mejor legibilidad visual.
    */
    private static void imprimirMatriz(char[][] matriz) {
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                System.out.print(matriz[fila][columna] + "  ");
            }
            System.out.println();
        }
    }

    private static void imprimirMatrizPaso(char[][] matriz, Celda actual) {
    for (int fila = 0; fila < matriz.length; fila++) {
        for (int columna = 0; columna < matriz[fila].length; columna++) {
            if (fila == actual.getFila() && columna == actual.getColumna()) {
                // Verde para la celda actual
                System.out.print("\u001B[32m" + matriz[fila][columna] + "\u001B[0m  ");
            } else {
                System.out.print(matriz[fila][columna] + "  ");
            }
        }
        System.out.println();
    }
    System.out.println();
}


    // ================= MÉTODOS AUXILIARES DE I/O =================

    private static void mostrarBienvenida() {
        System.out.println("PROYECTO 5: Laberinto Aleatorio y Camino Mínimo");
        System.out.println("------------------------------------------------");
    }

    private static int pedirEntero(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextInt();
    }

    private static Long pedirSemilla() {
        System.out.print("Semilla (Long) o 0 para aleatoria: ");
        Long semilla = entrada.nextLong();
        // Si el usuario ingresa 0, usamos el tiempo actual como semilla aleatoria
        return semilla == 0 ? System.nanoTime() : semilla;
    }

    private static boolean confirmarContinuar() {
        System.out.print("\n¿Desea generar otra partida? (s/n): ");
        return entrada.next().trim().equalsIgnoreCase("s");
    }

    
}
