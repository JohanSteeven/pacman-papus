import presentacion.*;
import presentacion.Menu;
import ucd.comp2011j.engine.GameManager;
import ucd.comp2011j.engine.ScoreKeeper;

import javax.swing.*;
import java.awt.*;

public class Pacman {
    public static void main(String[] args) throws Exception {
        JFrame pantallaMenu = new JFrame();
        pantallaMenu.setSize(logica.Pacman.ANCHO_PANTALLA, logica.Pacman.ALTURA_PANTALLA);
        pantallaMenu.setResizable(false);
        pantallaMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantallaMenu.setTitle("Pacman");
        pantallaMenu.setLocationRelativeTo(null);


        PlayerListener playerListener = new PlayerListener();
        pantallaMenu.addKeyListener(playerListener);
        MenuListener menuListener = new MenuListener();
        pantallaMenu.addKeyListener(menuListener);

        logica.Pacman pacman = new logica.Pacman(playerListener);
        ScoreKeeper scoreKeeper = new ScoreKeeper("scores.txt");

        Menu menu = new Menu();
        Instrucciones instrucciones = new Instrucciones();
        Puntaje puntaje = new Puntaje(scoreKeeper);
        Game game = new Game(pacman);

        GameManager juego = new GameManager(pacman, pantallaMenu, menuListener, menu, instrucciones, puntaje, game, scoreKeeper);
        pantallaMenu.setVisible(true);

        juego.run();
    }
}
