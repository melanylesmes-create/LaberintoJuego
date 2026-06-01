package vista;

import configuracion.ResultadoDTO;
import configuracion.configuracionPrincipal;
import configuracion.ResultadoDTO;
import modelo.Celda;
import java.util.Scanner;

/**
 * Esta clase es la interfaz de usuario en consola.
 * Su única responsabilidad es manejar la entrada y salida de datos.
 * Delega TODA la lógica a ConfiguracionPrincipal.
 * NO contiene algoritmos, ni manipulación directa del modelo.
 */
public class VistaConsola {
    
    private static final Scanner entrada = new Scanner(System.in);
    private static configuracionPrincipal controlador;

    /**
     * Punto de entrada del programa.
     * Configura el tamaño del laberinto e inicia las partidas.
     */
    public static void main(String[] args) {
        mostrarInicioJuego();
        
        // Pedir dimensiones del laberinto
        int filas = pedirNumero("Para altura del laberinto (ingresa un numero impar mayor o igual a 5): ");
        int columnas = pedirNumero("Para anchura del laberinto (ingresa un numero impar mayor o igual a 5): ");
        
        // Inicializamos el controlador con una caché de 50 laberintos
        controlador = new configuracionPrincipal(filas, columnas, 50);
        
        // Bucle que permite jugar múltiples veces sin reiniciar el juego
        do {
            Long semilla = pedirCodigoLaberinto();
            ejecutarPartida(semilla);
        } while (jugarOtraPartida());
        
        System.out.println("\n✅ Partida finalizada. ¡Gracias por jugar!");
        entrada.close();
    }
    
    /**
     * Controla el flujo completo de una partida:
     * generación, búsqueda y visualización.
     * Solo coordina llamadas al controlador, sin lógica propia.
     */
    private static void ejecutarPartida(Long semilla) {
        System.out.println("\n.... Generando laberinto...");
        
        // Generar y mostrar laberinto inicial
        char[][] laberinto = controlador.generarLaberinto(semilla);
        mostrarLaberinto(laberinto);
        
        // Resolver con BFS (camino óptimo)
        System.out.println("\n----Buscando la mejor ruta con BFS...");
        ResultadoDTO resultado = controlador.resolverBFS(semilla);
        
        // Verificar si se encontró camino
        if (resultado.ruta.verificarEsVacia()) {
            System.out.println(" X) No se encontró una salida para este laberinto.");
            return;
        }
        
        // Mostrar laberinto con el camino marcado
        System.out.println("\n---- Ruta óptima encontrada:");
        char[][] laberintoConCamino = controlador.generarLaberinto(semilla);
        mostrarLaberinto(laberintoConCamino);
        
        // Mostrar estadísticas
        int pasos = resultado.ruta.cantidad() - 1;
        System.out.println("\n #) Pasos necesarios (BFS): " + pasos);
        System.out.println(" --- Tiempo de búsqueda: " + resultado.tiempoMs + " ms");
        
        // Comparación opcional con DFS
        int pasosDFS = controlador.compararDFS();
        if (pasosDFS != -1) {
            System.out.println("--- Ruta alternativa (DFS): " + pasosDFS + " pasos (no garantiza mínimo)");
        }
        
        // Mostrar ranking
        System.out.println("\n TOP 5 MEJORES PARTIDAS:");
        System.out.println(controlador.obtenerRanking());
        System.out.println("-----------------------------------");
    }
    
    /**
     * Dibuja la matriz fila por fila en la consola.
     */
    private static void mostrarLaberinto(char[][] laberinto) {
        for (int fila = 0; fila < laberinto.length; fila++) {
            for (int columna = 0; columna < laberinto[fila].length; columna++) {
                System.out.print(laberinto[fila][columna] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Muestra el laberinto resaltando la celda actual (para modo visual/depuración).
     */
    private static void imprimirMatrizPaso(char[][] matriz, Celda actual) {
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                if (fila == actual.getFila() && columna == actual.getColumna()) {
                    // Resaltar celda actual con color verde (códigos ANSI)
                    System.out.print("\u001B[32m" + matriz[fila][columna] + "\u001B[0m  ");
                } else {
                    System.out.print(matriz[fila][columna] + "  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Muestra la pantalla de bienvenida del juego.
     */
    private static void mostrarInicioJuego() {
        System.out.println(" ---------------- Escape del Laberinto ---------------- 🧩");
        System.out.println("   Encuentra la salida con el menor número de pasos");
        System.out.println("--------------------------------------------------------\n");
    }
    
    /**
     * Pide un número entero al usuario mostrando un mensaje.
     */
    private static int pedirNumero(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextInt();
    }
    
    /**
     * Pide el código/semilla del laberinto.
     * Si el usuario ingresa 0, genera una semilla aleatoria.
     */
    private static Long pedirCodigoLaberinto() {
        System.out.print("Código del laberinto (0 para aleatorio): ");
        Long codigo = entrada.nextLong();
        return (codigo == 0) ? System.nanoTime() : codigo;
    }
    
    /**
     * Pregunta al usuario si desea jugar otra partida.
     */
    private static boolean jugarOtraPartida() {
        System.out.print("\n¿Desea jugar otra partida? (s/n): ");
        return entrada.next().trim().equalsIgnoreCase("s");
    }
}
