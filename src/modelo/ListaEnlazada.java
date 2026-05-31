
package modelo;
/* Esta clase nos servira para guardar la secuencia del camino
*/
public class ListaEnlazada {
    private Nodo cabecera;
    
    public ListaEnlazada(){
        cabecera = null;
    }
    
    /* Verificaremos si esta vacia
    */
    public boolean verificarEsVacia(){
        return cabecera == null;
    }
    
    /* Insertar un Nodo manteniendo el orden
    */
    public void insertarAlFinal(Object valor){
        Nodo nuevoNodo = new Nodo();
        nuevoNodo.setValor(valor);
        
        if(verificarEsVacia()){
            cabecera = nuevoNodo;
        }else {
            Nodo auxiliar = cabecera;
            while (auxiliar.getSiguiente() != null){
                auxiliar = auxiliar.getSiguiente();
            }
            auxiliar.setSiguiente(nuevoNodo);
        }
    }
    
    
    /* Retorna la cantidad de los nodos que estan enlazados
    */
    public int cantidad (){
        int cantidad = 0;
        Nodo auxiliar = cabecera;
        while(auxiliar != null){
            cantidad ++;
            auxiliar = auxiliar.getSiguiente();
        }
        return cantidad;
    }
    
    /* Pasa la lista a un arreglo de tipo Object 
    para poder manejarlo bien
    */
    public Object[] convertirArray(){
        Object [] datos = new Object [cantidad()];
        Nodo auxiliar = cabecera;
        for (int indice = 0; indice < datos.length; indice ++){
            datos [indice] = auxiliar.getValor();
            auxiliar = auxiliar.getSiguiente();
        }
        return datos;
    }
    
    /*Muestra lo almacenado
     */
    public void mostrarLista(){
        Nodo auxiliar = cabecera;
        while (auxiliar != null){
            System.out.println(auxiliar.getValor() + " - ");
            auxiliar = auxiliar.getSiguiente();
        }
        System.out.println("fin");
    }
}
