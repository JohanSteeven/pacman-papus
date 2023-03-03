package logica;

import java.awt.*;

public class Poder implements Comestible {
    private int row;
    private int col;
    private int index;
    private Rectangle hitBox;

    public Poder(int index, Laberinto maze) {
        this.index = index;
        this.row = Coordenadas.getCordenadas(index, maze.getColumnas()).getFila();
        this.col = Coordenadas.getCordenadas(index, maze.getColumnas()).getColumna();
        hitBox = new Rectangle(col * 20 + 3, row * 20 + 50 + 3, 14, 14);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getIndex() {
        return index;
    }

    public Rectangle getHitbox() {
        return hitBox;
    }
}
