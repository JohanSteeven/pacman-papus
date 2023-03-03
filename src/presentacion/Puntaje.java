package presentacion;

import javax.swing.*;
import logica.Pacman;
import ucd.comp2011j.engine.ScoreKeeper;
import java.awt.*;

public class Puntaje extends JPanel {
  private ScoreKeeper score;

  public Puntaje(ScoreKeeper score) {
    this.score = score;
  }

  public void paintComponent(Graphics g) {
    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, Pacman.ANCHO_PANTALLA, Pacman.ALTURA_PANTALLA);

    g.setColor(new Color(255, 255, 0));
    Font font = new Font("Arial", Font.BOLD, 30);
    g.setFont(font);
    g.drawString("Salon de la fama !!!", 50, 50);

    font = new Font("Arial", Font.BOLD, 18);
    g.setFont(font);
    int y = 100;
    for (ucd.comp2011j.engine.Score s : score.getScores()) {
      g.drawString(s.getName() + ": " + s.getScore(), 150, y);
      y += 30;
    }
    g.drawString("Presiona 'M' para regresar al menu", 50, 460);
  }
}
