package logica;

import java.awt.*;

public class Comida implements Comestible {
    private int fila;
    private int columna;
    private int indice;
    private Rectangle hitBox;

    public Comida(int indice, Laberinto laberinto) {
        this.indice = indice;
        this.fila = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getFila();
        this.columna = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getColumna();
        hitBox = new Rectangle(columna * 20 + 5, fila * 20 + 50 + 5, 10, 10);
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

    public Rectangle getHitbox() {
        return hitBox;
    }
}
