package vista;

import configuracion.configuracionPrincipal;
import configuracion.ResultadoDTO;
import java.util.Scanner;
import modelo.Celda;


<<<<<<< HEAD
/* Esta clase es la interfaz de usuario en consola.
   Su única responsabilidad es manejar la entrada y salida de datos.
   Delega TODA la lógica a ConfiguracionPrincipal. 
   NO contiene algoritmos, ni manipu
   */
=======

>>>>>>> 685431134a0ce14baaf7f1897f113f89d62b6d99
public class VistaConsola {
    private static Scanner entrada = new Scanner(System.in);
    private static configuracionPrincipal controlador;

     /* Punto de entrada del programa.
       Configura el tamaño del laberinto e inicia las partidas.
     */
    public static void main(String[] args) {
        mostrarInicioJuego();
   

        int altoLaberinto  = pedirNumero("Para altura del laberinto (ingresa un numero impar mayor o igual a 5): ");
        int anchoLaberinto  = pedirNumero("Para anchura del laberinto (ingresa un numero impar mayor o igual a 5): ");

<<<<<<< HEAD
        


        // Inicializamos el puente con una caché de 50 laberintos
        puente = new configuracionPrincipal(filas, columnas, 50);
=======
        // Inicializamos el controlador con una caché de 50 laberintos
        controlador = new configuracionPrincipal(altoLaberinto, anchoLaberinto, 50);
>>>>>>> 685431134a0ce14baaf7f1897f113f89d62b6d99

        // Bucle que permite jugar múltiples veces sin reiniciar el juego
    do{
        Long codigoLaberinto = pedirCodigoLaberinto();
        iniciarPartida(codigoLaberinto);
    }while (jugarOtraPartida());

        System.out.println("\n Partida finalizada.");
}
        
    /* Controla el flujo completo de una partida:
       generación, búsqueda y visualización.
     */
        private static void iniciarPartida(Long CodigoLaberinto) {
        System.out.println("\n Creando laberinto...");
        char[][] laberintoGenerado = controlador.generarLaberinto(CodigoLaberinto);
         mostrarLaberinto(laberintoGenerado);

<<<<<<< HEAD
    /* Orquesta generación, resolución, comparación y visualización.
       Solo coordina llamadas al puente, sin lógica propia.
    */
    private static void ejecutarPartida(Long semilla) {
        System.out.print("¿Desea ver la búsqueda paso a paso? (s/n): ");
        boolean modoVisual = entrada.next().trim().equalsIgnoreCase("s");

        System.out.println("\n🔄 Generando laberinto...");
        char[][] matrizInicial = puente.generarLaberinto(semilla);
        imprimirMatriz(matrizInicial);
=======
        System.out.println("\n Buscando la mejor ruta...");
        ResultadoDTO resultadoBusqueda = controlador.resolverBFS(CodigoLaberinto);
>>>>>>> 685431134a0ce14baaf7f1897f113f89d62b6d99

        if (resultadoBusqueda.ruta.verificarEsVacia()) {
            System.out.println("No se encontro una salida para este laberinto.");
            return;
        }

<<<<<<< HEAD
        System.out.println("\n:) Ruta óptima encontrada:");
        // La matriz ya viene marcada con '·' desde resolverBFS()
        imprimirMatriz(puente.generarLaberinto(semilla));
        
=======
        System.out.println("\n: Mejor ruta encontrada: ");
        // La matriz ya viene marcada con el camino encontrado
         mostrarLaberinto(controlador.generarLaberinto(CodigoLaberinto));
>>>>>>> 685431134a0ce14baaf7f1897f113f89d62b6d99

        System.out.println("Pasos necesarios: " + (resultadoBusqueda.ruta.cantidad() - 1));
        System.out.println("Tiempo de busqueda: " + resultadoBusqueda.tiempoMs + " ms");

        // Comparación opcional con DFS
        int pasosRutaAlternativa = controlador.compararDFS();
        if (pasosRutaAlternativa != -1) {
            System.out.println("Ruta alternativa encontrada: " + pasosRutaAlternativa
                    + "pasos");
        } else {
            System.out.println("No se encontró una ruta alternativa.");
        }

        System.out.println("\n TOP 5 MEJORES PARTIDAS: ");
        System.out.println(controlador.obtenerRanking());
        System.out.println("-----------------------------------");
    }

    /* Dibuja la matriz fila por fila en la consola.*/

  
    private static void  mostrarLaberinto(char[][] laberinto) {
        for (int fila = 0; fila < laberinto.length; fila++) {
            for (int columna = 0; columna < laberinto[fila].length; columna++) {
                System.out.print(laberinto[fila][columna] + "  ");
            }
            System.out.println();
        }
    }

<<<<<<< HEAD
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
=======
    //MÉTODOS AUXILIARES
>>>>>>> 685431134a0ce14baaf7f1897f113f89d62b6d99

    private static void mostrarInicioJuego() {
        System.out.println("------------------------Escape del Laberinto-------------------------");
        System.out.println("---------------------------------------------------------------------");
    }

    private static int pedirNumero(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextInt();
    }

    private static Long pedirCodigoLaberinto() {
        System.out.print("Codigo del laberinto (0 para aleatorio):");
        Long codigoGenerado   = entrada.nextLong();
       
        return codigoGenerado == 0 ? System.nanoTime() : codigoGenerado ;
    }

    private static boolean jugarOtraPartida() {
        System.out.print("\n ¿Desea jugar otra partida? (s/n): ");
        return entrada.next().trim().equalsIgnoreCase("s");
    }

    
}
