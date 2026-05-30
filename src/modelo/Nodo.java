
package modelo;

public class Nodo {
    private Object valor; //Se usa de tipo Objeto para reutilizarlo
    private Nodo siguiente;
    
    //Inicializar los atributos donde valor sera null y siguiente igual.
    
    public Nodo(){
        this.valor = null;
        this.siguiente = null;
    }
    
    public Nodo (Object valor){
        this.valor = valor;
        this.siguiente = null;
    }
    
    public Object getValor(){
        return valor;
    }
    
    public void setValor (Object valor){
        this.valor = valor;
    }
    
    //Nos ayudara para avanzar en la cadena o recorrer
    public Nodo getSiguiente(){
        return siguiente;
    }
    //Nos servira para agarrar el cajon que esta en el parametro
    // y contruir la cadena 
    public void setSiguiente (Nodo siguiente){
        this.siguiente = siguiente;
    }
    
}
