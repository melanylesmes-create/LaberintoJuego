package configuracion;

import modelo.ListaEnlazada;

/* Esta clase es un DTO (Data Transfer Object).
   Su único propósito es agrupar la ruta encontrada y el tiempo de ejecución
   para devolverlos juntos desde Configuracion hacia la Vista.
   No contiene lógica de búsqueda, solo almacena datos de forma temporal.
*/
public class ResultadoDTO {
     public ListaEnlazada ruta;   // Secuencia de celdas desde INICIO hasta SALIDA
    public long tiempoMs;        // Tiempo que tardó el algoritmo en milisegundos

    /* Constructor simple que recibe y asigna los valores directamente.
       Mantiene el código claro y evita el uso de setters innecesarios.
    */
    public ResultadoDTO(ListaEnlazada ruta, long tiempoMs) {
        this.ruta = ruta;
        this.tiempoMs = tiempoMs;
    }
}
