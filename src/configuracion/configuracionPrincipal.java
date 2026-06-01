package configuracion;

import modelo.*;

/* Esta clase actúa como puente entre el Modelo y la Vista.
   Recibe parámetros de la Vista, orquesta la lógica del Modelo
   y devuelve resultados limpios. NO maneja entrada/salida por consola.
   Cumple con el principio de responsabilidad única (SRP).
*/
public class configuracionPrincipal {
    private Laberinto motorLaberinto;    // Gestiona generación y resolución
    private TablaHash cacheLaberintos;   // Almacena laberintos por semilla
    private ArbolBinario rankingTiempos; // Ordena partidas por pasos/tiempo

    /* Constructor que inicializa todas las estructuras necesarias.
       Recibe dimensiones del laberinto y capacidad de la caché.
    */
    public configuracionPrincipal(int filas, int columnas, int capacidadCache) {
        this.cacheLaberintos = new TablaHash(capacidadCache);
        this.rankingTiempos = new ArbolBinario();
        this.motorLaberinto = new Laberinto(filas, columnas, cacheLaberintos);
    }

    /* Genera un nuevo laberinto o recupera uno existente desde la caché.
       Delegamos la lógica directamente al modelo para mantener capas separadas.
    */
    public char[][] generarLaberinto(Long semilla) {
        return motorLaberinto.generar(semilla);
    }

    /* Ejecuta BFS para encontrar el camino mínimo.
       Mide el tiempo de ejecución, guarda en el ranking si hay solución,
       marca el camino en la matriz y retorna un DTO con los resultados.
    */
    public ResultadoDTO resolverBFS(Long semilla) {
        long tiempoInicio = System.nanoTime();
        ListaEnlazada rutaOptima = motorLaberinto.ejecutarBFS();
        long tiempoEjecucion = (System.nanoTime() - tiempoInicio) / 1_000_000;

        // Si se encontró camino, actualizamos ranking y marcamos ruta
        if (!rutaOptima.verificarEsVacia()) {
            int cantidadPasos = rutaOptima.cantidad() - 1;
            rankingTiempos.insertar(semilla, cantidadPasos, tiempoEjecucion);
            motorLaberinto.marcarCamino(rutaOptima);
        }

        return new ResultadoDTO(rutaOptima, tiempoEjecucion);
    }
    

    /* Ejecuta DFS para comparar la longitud del camino con BFS.
       Retorna la cantidad de pasos o -1 si no hay solución.
       Nota: DFS no garantiza el camino más corto, solo es comparativo.
    */
    public int compararDFS() {
        ListaEnlazada rutaDFS = motorLaberinto.ejecutarDFS();
        return rutaDFS.verificarEsVacia() ? -1 : rutaDFS.cantidad() - 1;
    }

    /* Retorna un texto formateado con el Top 5 de mejores partidas.
       El ordenamiento ascendente por pasos lo maneja el Árbol Binario.
    */
    public String obtenerRanking() {
        return rankingTiempos.generarTop5();
    }
}
