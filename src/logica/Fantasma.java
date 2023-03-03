package logica;

import java.awt.*;

public abstract class Fantasma implements Comestible {
    protected int x;
    protected int y;
    protected int initX;
    protected int initY;
    protected Rectangle hitBox;
    protected Direcciones dirección;
    protected Laberinto actLaberinto;
    protected Jugador jugador;
    protected boolean puedeMoverse;

    private double velocidad =2 ;

    public Fantasma(int x, int y, Laberinto actLaberinto, Jugador jugador) {
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.actLaberinto = actLaberinto;
        this.jugador = jugador;
        hitBox = new Rectangle(x, y, 20, 20);
        dirección = null;
        puedeMoverse = false;

    }

    public void reiniciar() {
        puedeMoverse = false;
        x = initX;
        y = initY;
        hitBox = new Rectangle(x, y, 20, 20);

    }

    public boolean comer(Jugador jugador) {
        return hitBox.intersects(jugador.getHitbox());
    }

    protected class Opcion implements Comparable<Opcion> {
        protected Direcciones direccion;
        protected double distancia;

        public Opcion(Direcciones direccion, double distancia) {
            this.direccion = direccion;
            this.distancia = distancia;
        }

        public int compareTo(Opcion opcion) {
            if (distancia > opcion.distancia) {
                return 1;

            } else if (distancia < opcion.distancia) {
                return -1;
            } else {
                return 0;
            }
        }

        public String toString() {
            return direccion + " " + distancia;
        }
    }

    public void mover() {
        if (!puedeMoverse) {
            return;
        }

        if ((x / 20.0) % 1 == 0 && ((y - 50) / 20.0) % 1 == 0) {
            if (jugador.estaMoviendose()) {
                getSiguienteDireccion(false);
            } else {
                getSiguienteDireccion(true);
            }
        }

        switch (dirección) {
            case ARRIBA -> y -= velocidad;
            case ABAJO -> y += velocidad;
            case IZQUIERDA -> x -= velocidad;
            case DERECHA -> x += velocidad;
        }
        hitBox = new Rectangle(x, y, 20, 20);
    }

    public abstract void getSiguienteDireccion(boolean perseguir);

    public boolean golpeaPared(Direcciones direccion) {
        int fila, columna, indice;
        switch (direccion) {
            case ARRIBA:
                fila = (y - 1 - 50) / 20;
                columna = x / 20;
                indice = Coordenadas.getIndice(fila, columna, actLaberinto.getColumnas()).getIndice();
                if (actLaberinto.getLaberinto().get(indice).equals("W")) {
                    return true;
                }
                break;
            case ABAJO:
                fila = (y - 50) / 20;
                columna = x / 20;
                indice = Coordenadas.getIndice(fila + 1, columna, actLaberinto.getColumnas()).getIndice();
                if (actLaberinto.getLaberinto().get(indice).equals("W")) {
                    return true;
                }
                break;
            case IZQUIERDA:
                fila = (y - 50) / 20;
                columna = (x - 1) / 20;
                indice = Coordenadas.getIndice(fila, columna, actLaberinto.getColumnas()).getIndice();
                if (actLaberinto.getLaberinto().get(indice).equals("W")) {
                    return true;
                }
                break;
            case DERECHA:
                fila = (y - 50) / 20;
                columna = x / 20;
                indice = Coordenadas.getIndice(fila, columna + 1, actLaberinto.getColumnas()).getIndice();
                if (actLaberinto.getLaberinto().get(indice).equals("W")) {
                    return true;
                }
                break;
        }
        return false;
    }

    public Rectangle getHitbox() {
        return hitBox;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direcciones getNuevaDireccion() {
        return dirección;
    }

    public void setMover(boolean t) {
        puedeMoverse = t;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }
}
