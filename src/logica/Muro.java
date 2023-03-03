package logica;

public class Muro {
    private int fila;
    private int columna;
    private int indice;

    public Muro(int indice, Laberinto laberinto) {
        this.indice = indice;
        this.fila = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getFila();
        this.columna = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getColumna();
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
