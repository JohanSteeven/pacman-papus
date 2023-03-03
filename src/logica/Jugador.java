package logica;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Jugador implements Comestible {
    private int x;
    private int y;
    private int initX;
    private int initY;
    private Direcciones direccion;
    private Boolean tienePoder;
    private Boolean golpeaPared;
    private Rectangle hitBox;

    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        hitBox = new Rectangle(x, y, 20, 20);
        direccion = Direcciones.IZQUIERDA;
        tienePoder = false;
        golpeaPared = false;
    }

    public void reiniciar() {
        x = initX;
        y = initY;
        hitBox = new Rectangle(x, y, 20, 20);
    }

    public void mover() {
        int velocidad = 2;
        switch (direccion) {
            case ARRIBA:
                y -= velocidad;
                break;
            case ABAJO:
                y += velocidad;
                break;
            case IZQUIERDA:
                x -= velocidad;
                break;
            case DERECHA:
                x += velocidad;
                break;
        }
        hitBox = new Rectangle(x, y, 20, 20);
    }

    public void mover(int distX, int distY) {
        x += distX;
        y += distY;
        hitBox = new Rectangle(x, y, 20, 20);
    }

    public boolean seLoComió(Fantasma fantasma) {
        Rectangle s = fantasma.getHitbox();
        return s.intersects(hitBox.getBounds());
    }

    public void cambiarDireccion(Direcciones direcciones) {
        golpeaPared = false;
        direccion = direcciones;
    }

    public Rectangle getHitbox() {
        return hitBox;
    }

    public boolean come(Comestible comestible) {
        return hitBox.intersects(comestible.getHitbox());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direcciones getDireccion() {
        return this.direccion;
    }

    public Boolean estaGolpeandoPared() {
        return golpeaPared;
    }

    public void setGolpeaPared(Boolean golpeaPared) {
        this.golpeaPared = golpeaPared;
    }

    public boolean estaMoviendose() {
        return tienePoder;
    }

    public void setPoder() {
        tienePoder = true;
        Timer timer = new Timer();
        TimerTask poder = new TimerTask() {
            public void run() {
                tienePoder = false;

            }
        };
        timer.schedule(poder, 5000);
    }

    public void setTienePoder(Boolean tienePoder) {
        this.tienePoder = tienePoder;
    }
}
