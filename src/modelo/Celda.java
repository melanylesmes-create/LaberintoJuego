
package modelo;
// Esta clase será la representación de una posición 
//En el laberinto
public class Celda {
    private int fila;
    private int columna;
    private char tipo;
    
    public Celda (int ejeX, int ejeY, char tipo){
        this.fila = ejeX;
        this.columna = ejeY;
        this.tipo = tipo; /*Para saber si es: Pared, camino, inicio, salida, espacio */
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
    
    /*Retorna el tipo de celda: pared, camino, inicio, salida, espacio*/
    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
    
    /*Compara si la celda esta en la misma posición que otra*/
    public boolean verificarSonIguales (Celda otra){
        return (this.fila == otra.getFila()) && (this.columna == otra.getColumna());
    }
    /*Retorna true si es una pared o false si no*/
    public boolean verificarPared(){
        return tipo == '|';
    }
}
