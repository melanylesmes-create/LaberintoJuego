package modelo;

/* Esta clase implementa un Árbol Binario de Búsqueda (ABB).
   Lo usamos para ordenar las partidas por cantidad de pasos (menor a mayor).
   Así podemos mostrar un ranking con las mejores soluciones.
*/

/* Clase interna que representa un nodo del árbol.
   Cada nodo guarda: semilla, pasos y tiempo de ejecución.
*/
class NodoArbol {
    long semilla;
    int pasos;
    long tiempoMs;
    NodoArbol izquierdo;
    NodoArbol derecho;
    
    /* Constructor simple que inicializa los datos del nodo.
       Los hijos izquierdo y derecho empiezan en null.
    */
    NodoArbol(long s, int p, long t) {
        semilla = s;
        pasos = p;
        tiempoMs = t;
        izquierdo = null;
        derecho = null;
    }
}

public class ArbolBinario {
    private NodoArbol raiz;  // Punto de entrada al árbol

    /* Constructor que inicializa el árbol vacío.
    */
    public ArbolBinario() {
        raiz = null;
    }

    /* Método público para insertar un nuevo registro.
       Delegamos la lógica recursiva al método auxiliar.
    */
    public void insertar(long semilla, int pasos, long tiempoMs) {
        raiz = insertarRecursivo(raiz, new NodoArbol(semilla, pasos, tiempoMs));
    }
    
    /* Método recursivo que ubica el nodo en la posición correcta.
       Si los pasos son menores, va a la izquierda; si no, a la derecha.
       Esto mantiene el árbol ordenado ascendentemente por pasos.
    */
    private NodoArbol insertarRecursivo(NodoArbol actual, NodoArbol nuevo) {
        if (actual == null) {
            return nuevo;  // Caso base: encontramos dónde insertar
        }
        
        if (nuevo.pasos < actual.pasos) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, nuevo);
        } else {
            actual.derecho = insertarRecursivo(actual.derecho, nuevo);
        }
        return actual;
    }

    /* Genera un texto con el Top 5 de mejores soluciones.
       Usa recorrido InOrden (izq - raíz - der) para obtener orden ascendente.
    */
    public String generarTop5() {
        StringBuilder texto = new StringBuilder();
        int[] contador = {0};  // Array para pasar por referencia y contar
        recorrerInOrden(raiz, texto, contador);
        
        if (texto.toString().isEmpty()) {
            return "Sin registros aún.";
        }
        return texto.toString();
    }
    
    /* Recorrido InOrden que se detiene al llegar a 5 registros.
       Concatena la información de cada nodo en el StringBuilder.
    */
    private void recorrerInOrden(NodoArbol nodo, StringBuilder texto, int[] contador) {
        if (nodo == null || contador[0] >= 5) {
            return;  // Caso base: nodo vacío o ya tenemos 5
        }
        
        // Primero visitamos el subárbol izquierdo (menores valores)
        recorrerInOrden(nodo.izquierdo, texto, contador);
        
        // Procesamos el nodo actual si aún no llegamos a 5
        if (contador[0] < 5) {
            texto.append("🥇 Semilla: ").append(nodo.semilla)
                 .append(" | Pasos: ").append(nodo.pasos)
                 .append(" | Tiempo: ").append(nodo.tiempoMs).append(" ms\n");
            contador[0]++;
        }
        
        // Luego visitamos el subárbol derecho (mayores valores)
        recorrerInOrden(nodo.derecho, texto, contador);
    }
}
