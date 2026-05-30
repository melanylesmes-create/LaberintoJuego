
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
        this.tipo = tipo;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
    
    public boolean verificarSonIguales (Celda otra){
        return (this.fila == otra.getFila()) && (this.columna == otra.getColumna());
    }
    
    public boolean verificarPared(){
        return tipo == '|';
    }
}
