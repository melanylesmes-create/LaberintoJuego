package modelo;

import java.util.Random;

/* Esta clase gestiona toda la lógica del laberinto:
   - Generación aleatoria con semilla
   - Búsqueda de camino con BFS (Cola) y DFS (Pila)
   - Reconstrucción y marcado de la ruta óptima
   Todo usando estructuras de datos manuales, sin librerías externas.
*/
public class Laberinto {
    private char[][] matriz;       // Representación del laberinto
    private int filas;             // Número de filas
    private int columnas;          // Número de columnas
    private TablaHash cache;       // Caché para reutilizar laberintos

    /* Constructor que inicializa dimensiones y la tabla hash.
    */
    public Laberinto(int filas, int columnas, TablaHash cache) {
        this.filas = filas;
        this.columnas = columnas;
        this.cache = cache;
    }

    /* Genera un nuevo laberinto o recupera uno existente desde la caché.
       Usa la semilla para garantizar reproducibilidad.
    */
    public char[][] generar(Long semilla) {
        // Primero intentamos buscar en caché para no regenerar
        char[][] guardado = cache.buscar(semilla);
        if (guardado != null) {
            matriz = guardado;
            return matriz;
        }

        // Si no está en caché, generamos uno nuevo
        Random generador = new Random(semilla);
        matriz = new char[filas][columnas];
        
        // Inicializamos todo como pared
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                matriz[f][c] = '#';
            }
        }

        // Abrimos caminos aleatorios con probabilidad del 60%
        for (int i = 0; i < filas * columnas; i++) {
            int x = generador.nextInt(columnas);
            int y = generador.nextInt(filas);
            if (generador.nextDouble() < 0.6) {
                matriz[y][x] = ' ';
            }
        }
        
        // Forzamos posición de inicio y salida
        matriz[1][1] = 'S';
        matriz[filas - 2][columnas - 2] = 'E';
        
        // Guardamos en caché para futuras consultas
        cache.guardar(semilla, matriz);
        return matriz;
    }

    /* Ejecuta BFS (Breadth-First Search) usando nuestra Cola manual.
       Garantiza encontrar el camino más corto en laberintos no ponderados.
    */
    public ListaEnlazada ejecutarBFS() {
        Cola frontera = new Cola();
        boolean[][] visitados = new boolean[filas][columnas];
        Celda[][] padres = new Celda[filas][columnas];  // Para reconstruir camino
        
        Celda inicio = new Celda(1, 1, 'S');
        frontera.encolar(inicio);
        visitados[1][1] = true;
        
        // Vectores para moverse: derecha, izquierda, abajo, arriba
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        boolean encontrado = false;

        // Mientras haya celdas por explorar
        while (!frontera.verificarEsVacia()) {
            Celda actual = (Celda) frontera.desencolar();
            
            // Si llegamos a la salida, terminamos
            if (matriz[actual.getFila()][actual.getColumna()] == 'E') {
                encontrado = true;
                break;
            }
            
            // Exploramos los 4 vecinos
            for (int i = 0; i < 4; i++) {
                int nuevaFila = actual.getFila() + dy[i];
                int nuevaColumna = actual.getColumna() + dx[i];
                
                // Validamos que sea celda caminable y no visitada
                if (validar(nuevaColumna, nuevaFila) && !visitados[nuevaFila][nuevaColumna]) {
                    visitados[nuevaFila][nuevaColumna] = true;
                    padres[nuevaFila][nuevaColumna] = actual;  // Guardamos de dónde venimos
                    frontera.encolar(new Celda(nuevaFila, nuevaColumna, ' '));
                }
            }
        }
        
        // Reconstruimos y retornamos el camino encontrado
        int finY = filas - 2;
        int finX = columnas - 2;
        return reconstruirCamino(padres, encontrado, finY, finX);
    }

    /* Ejecuta DFS (Depth-First Search) usando nuestra Pila manual.
       Útil para comparar, pero NO garantiza camino mínimo.
    */
    public ListaEnlazada ejecutarDFS() {
        Pila exploracion = new Pila();
        boolean[][] visitados = new boolean[filas][columnas];
        Celda[][] padres = new Celda[filas][columnas];
        
        Celda inicio = new Celda(1, 1, 'S');
        exploracion.apilar(inicio);
        visitados[1][1] = true;
        boolean encontrado = false;
        
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        while (!exploracion.verificarEsVacia()) {
            Celda actual = (Celda) exploracion.desapilar();
            
            if (matriz[actual.getFila()][actual.getColumna()] == 'E') {
                encontrado = true;
                break;
            }
            
            for (int i = 0; i < 4; i++) {
                int nuevaFila = actual.getFila() + dy[i];
                int nuevaColumna = actual.getColumna() + dx[i];
                
                if (validar(nuevaColumna, nuevaFila) && !visitados[nuevaFila][nuevaColumna]) {
                    visitados[nuevaFila][nuevaColumna] = true;
                    padres[nuevaFila][nuevaColumna] = actual;
                    exploracion.apilar(new Celda(nuevaFila, nuevaColumna, ' '));
                }
            }
        }
        
        int finY = filas - 2;
        int finX = columnas - 2;
        return reconstruirCamino(padres, encontrado, finY, finX);
    }
    

    /* Reconstruye el camino desde la salida hasta el inicio.
       Usa el arreglo de padres para retroceder paso a paso.
    */
    private ListaEnlazada reconstruirCamino(Celda[][] padres, boolean exito, int finY, int finX) {
        ListaEnlazada camino = new ListaEnlazada();
        
        if (!exito) {
            return camino;  // Si no hay solución, retornamos lista vacía
        }
        
        Celda actual = new Celda(finY, finX, 'E');
        while (actual != null) {
            camino.insertarAlFinal(actual);
            actual = padres[actual.getFila()][actual.getColumna()];
        }
        
        return camino;
    }

    /* Verifica que una coordenada esté dentro del laberinto y no sea pared.
       Método auxiliar clave para los algoritmos de búsqueda.
    */
    private boolean validar(int x, int y) {
        return y >= 0 && y < filas && x >= 0 && x < columnas && matriz[y][x] != '|';
    }

    /* Marca visualmente la ruta óptima en la matriz con el carácter '·'.
       Solo modifica celdas que sean camino (' '), no inicio ni salida.
    */
    public void marcarCamino(ListaEnlazada ruta) {
        Object[] datos = ruta.convertirArray();
        for (int i = 0; i < datos.length; i++) {
            Celda c = (Celda) datos[i];
            if (matriz[c.getFila()][c.getColumna()] == ' ') {
                matriz[c.getFila()][c.getColumna()] = '*';
            }
        }
    }

    /* Getter para acceder a la matriz desde otras capas.
    */
    public char[][] getMatriz() {
        return matriz;
    }
}
