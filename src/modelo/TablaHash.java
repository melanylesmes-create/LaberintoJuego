package modelo;

import java.util.Arrays;

/* Esta clase implementa una Tabla Hash con sondeo lineal.
   Nos permite guardar y recuperar laberintos usando una semilla como clave.
   Así evitamos regenerar el mismo laberinto y mejoramos el rendimiento.
*/
public class TablaHash {
    private Long[] claves;           // Almacena las semillas (keys)
    private char[][][] valores;      // Almacena los laberintos (values)
    private int longitud;            // Tamaño fijo de la tabla

    /* Constructor que inicializa la tabla con un tamaño dado.
       Llenamos las claves con -1L para marcar posiciones vacías.
    */
    public TablaHash(int longi) {
        longitud = longi;
        claves = new Long[longi];
        valores = new char[longi][][];
        Arrays.fill(claves, -1L);
    }

    /* Función hash simple: aplica módulo y resuelve colisiones con sondeo lineal.
       Si la posición está ocupada, busca la siguiente disponible.
    */
    private int funcionHash(Long semilla) {
        int indice = Math.abs(semilla.hashCode()) % longitud;
        
        // Sondeo lineal: si hay colisión, avanzamos hasta encontrar espacio
        while (claves[indice] != null && claves[indice] != -1L) {
            indice++;
            indice %= longitud;  // Volvemos al inicio si llegamos al final
        }
        return indice;
    }

    /* Guarda un laberinto asociado a una semilla.
       Clonamos la matriz para evitar referencias compartidas.
    */
    public void guardar(Long semilla, char[][] laberinto) {
        int indice = funcionHash(semilla);
        claves[indice] = semilla;
        valores[indice] = clonarMatriz(laberinto);
    }

    /* Busca y retorna el laberinto asociado a una semilla.
       Si no existe, retorna null.
    */
    public char[][] buscar(Long semilla) {
        int indice = Math.abs(semilla.hashCode()) % longitud;
        int contador = 0;
        
        // Recorremos hasta encontrar la clave o dar la vuelta completa
        while (claves[indice] != null && contador < longitud) {
            if (claves[indice].equals(semilla)) {
                return clonarMatriz(valores[indice]);
            }
            indice++;
            indice %= longitud;
            contador++;
        }
        return null;  // No encontrado
    }

    /* Muestra en consola el estado actual de la tabla hash.
       Útil para depuración y verificación.
    */
    public void mostrar() {
        System.out.println("📊 TABLA HASH (Semilla → Índice):");
        for (int indice = 0; indice < longitud; indice++) {
            if (claves[indice] != null && claves[indice] != -1L) {
                System.out.println("Índice: " + indice + " | Semilla: " + claves[indice]);
            }
        }
    }

    /* Método auxiliar que clona una matriz bidimensional.
       Importante: en Java, clonar evita que se modifiquen los datos originales.
    */
    private char[][] clonarMatriz(char[][] original) {
        char[][] copia = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i].clone();
        }
        return copia;
    }
}
