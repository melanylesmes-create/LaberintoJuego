
package modelo;

public class Pila {
    private Nodo inicio;
    
    public Pila(){
        inicio = null;
    }
    
    /* Metodo IsEmpty
    */
    public boolean verificarEsVacia(){
        return inicio == null;
    }
    
    /* Metodo apilar que inserta un elemento en la cima respetando LIFO.
    */
    public void apilar(Object valor){
        Nodo nuevoNodo = new Nodo();
        nuevoNodo.setValor(valor);
        
        if(verificarEsVacia()){
            inicio = nuevoNodo;
        }else {
            nuevoNodo.setSiguiente(inicio);
            inicio = nuevoNodo;
        }
    }
    
    /* Metodo desapilar saca y retorna el elemento superior
       Si esta vacia retorna null
    */
    public Object desapilar(){
         if(!verificarEsVacia()){
            Object dato = inicio.getValor();
            inicio = inicio.getSiguiente();
            return dato;
        }else {
            return null;
        }
    }
    
    /* Muestra toda la informacion de la pila
    */
    public void mostrarCola(){
        Nodo auxiliar = inicio;
        while (auxiliar != null){
            System.out.println("|\t" + auxiliar.getValor() + "\t|");
            auxiliar = auxiliar.getSiguiente();
        }
    } 

    /* retornar la cantidad de elementos en la pila
    */
    public int cantidad(){
        int cantidad = 0;
        Nodo auxiliar = inicio;
        while(auxiliar != null){
            cantidad ++;
            auxiliar = auxiliar.getSiguiente();
        }
        return cantidad;
    }
    
}
