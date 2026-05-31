
package modelo;

public class Cola {
    public Nodo inicio;
    public Nodo fin;
    
    public Cola() {
       inicio = null;
       fin = null;
    }
    
    /*Es como hacer el metodo IsEmpty, devuelve verdadero o falso
    */
    public boolean verificarEsVacia(){
        return inicio == null;
    }
    
    /*Metodo encolar nos ayudara a insertar un elemento al final
    */
    public void encolar (Object valor){
        Nodo nuevoNodo = new Nodo();
        nuevoNodo.setValor(valor);
        
        if(verificarEsVacia()){
            inicio = nuevoNodo;
            fin = nuevoNodo;
        }else {
            fin.setSiguiente(nuevoNodo);
            fin = nuevoNodo;
        }
    }
    
    /* Metodo desencolar donde saca y retorna el primer elemento
    pero si esta vacia retornara null
    */
    public Object desencolar(){
        if (!verificarEsVacia()){
            Object dato = inicio.getValor();
            if(inicio == fin){
                inicio = null;
                fin = null;
            }else {
                inicio = inicio.getSiguiente();
            }
            return dato;
        } else{
            return null;
        }
    }
    
    /* Mostrar la información de la cola
    */
    public void mostrarCola(){
        Nodo auxiliar = inicio;
        while (auxiliar != null){
            System.out.println("[" + auxiliar.getValor() + "]");
            auxiliar = auxiliar.getSiguiente();
        }
        System.out.println("null");
    }
    
    /* retornar la cantidad de elementos en la cola
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
