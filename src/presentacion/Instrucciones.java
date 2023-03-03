package presentacion;

import javax.swing.*;

import logica.Pacman;

import java.awt.*;

public class Instrucciones extends JPanel {
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, Pacman.ANCHO_PANTALLA, Pacman.ALTURA_PANTALLA);

        g.setColor(new Color(255, 255, 0));
        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);
        g.drawString("Instrucciones Pacman", 40, 50);

        font = new Font("Arial", Font.BOLD, 18);
        g.setFont(font);
        g.drawString("Mover Arriba: flecha arriba", 65, 150);
        g.drawString("Mover Abajo: flecha abajo", 65, 200);
        g.drawString("Move Izquierda: flecha izquierda", 65, 250);
        g.drawString("Mover Derecha: flecha derecha", 65, 300);
        g.drawString("Presiona 'M' para regresar al menu", 45, 400);
    }
}
