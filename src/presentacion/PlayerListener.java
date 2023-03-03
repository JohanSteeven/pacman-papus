package presentacion;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerListener implements KeyListener {
    private boolean izquierda;
    private boolean derecha;
    private boolean arriba;
    private boolean abajo;
    private boolean fire;
    private boolean pausa;

    public boolean estaPresionandoIzquierda() {
        return izquierda;
    }

    public boolean estaPresionandoDerecha() {
        return derecha;
    }

    public boolean estaPresionandoArriba() {
        return arriba;
    }

    public boolean estaPresionandoAbajo() {
        return abajo;
    }

    public boolean hasPressedFire() {
        return fire;
    }

    public boolean hasPressedPause() {
        return pausa;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
            pausa = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            izquierda = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            derecha = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            arriba = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            abajo = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            izquierda = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            derecha = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            arriba = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            abajo = false;
        }

    }

    public void resetPause() {
        pausa = false;
    }

    public void resetFire() {
        fire = false;
    }
}
