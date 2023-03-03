package logica;

public class Coordenadas {
    private int fila;
    private int columna;
    private int indice;

    public Coordenadas(int fila, int columna, int numColumnas) {
        this.fila = fila;
        this.columna = columna;
        this.indice = columna + fila * numColumnas;
    }

    public Coordenadas(int indice, int numColumnas) {
        this.indice = indice;
        this.fila = indice / numColumnas;
        this.columna = indice % numColumnas;
    }

    public static Coordenadas getCordenadas(int i, int numColumnas) {
        return new Coordenadas(i, numColumnas);
    }

    public static Coordenadas getIndice(int fila, int columna, int numColumnas) {
        return new Coordenadas(fila, columna, numColumnas);
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getIndice() {
        return indice;
    }
}
