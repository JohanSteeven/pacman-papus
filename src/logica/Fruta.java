package logica;

import java.awt.*;

public class Fruta implements Comestible {
    private int fila;
    private int columna;
    private int indice;
    private Rectangle hitBox;

    public Fruta(int indice, Laberinto laberinto) {
        this.indice = indice;
        this.fila = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getFila();
        this.columna = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getColumna();
        hitBox = new Rectangle(columna * 20 + 3, fila * 20 + 50 + 3, 14, 14);
    }

    public Rectangle getHitbox() {
        return hitBox;
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
