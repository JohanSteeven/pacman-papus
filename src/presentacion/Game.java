package presentacion;

import javax.imageio.ImageIO;
import javax.sound.midi.Soundbank;
import javax.swing.*;

import logica.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Game extends JPanel {
    private final Pacman game;
    private SoundManager soundManager;
    public Game(Pacman game) {
        this.game = game;
    }

    public void paintComponent(Graphics g) {
        if (game != null) {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, Pacman.ANCHO_PANTALLA, Pacman.ALTURA_PANTALLA);

            if (!game.isGameOver()) {
                soundManager = new SoundManager();

                dibujarMuro(g, game.getlistaPared());
                dibujarMuroAire(g, game.getAirWallList());
                dibujarComida(g, game.getListaComidas());
                dibujarComida(g, game.getFrutaActual());
                dibujarPoder(g, game.getPowerList());
                dibujarFantasmas(g, game.getListaFantasmas(), game.getJugador());
                dibujarJugador(g, game.getJugador());

                dibujarInformacion(g);

                if (game.isPaused()) {
                    dibujarPausa(g);
                }
            }
        }
    }

    public void drawGameOver() {

    }

    public void dibujarJugador(Graphics g, Jugador p) {
        try {
            BufferedImage img = ImageIO.read(new File("src/recursos/buhofinal.png"));
            g.drawImage(img, p.getX()+1 , p.getY()+1 , null);
        } catch (IOException e) {
            e.printStackTrace();
        }
       }

    public void dibujarComida(Graphics g, ArrayList<Comida> listaComida) {
        g.setColor(new Color(255, 184, 151));
        for (Comida dot : listaComida) {
            int posX = dot.getColumna() * 20;
            int posY = dot.getFila() * 20 + 50;
            g.fillOval(posX + 7, posY + 7, 6, 6);
        }
    }

    public void dibujarComida(Graphics g, Fruta fruta) {
        if (fruta != null) {
            g.setColor(new Color(237, 175, 31));
            int posX = fruta.getColumna() * 20;
            int posY = fruta.getFila() * 20 + 50;
            g.fillOval(posX + 3, posY + 3, 14, 14);
        }
    }

    public void dibujarPoder(Graphics g, ArrayList<Poder> listaPoderes) {
        g.setColor(new Color(255, 255, 255));
        for (Poder power : listaPoderes) {
            int posX = power.getCol() * 20;
            int posY = power.getRow() * 20 + 50;
            g.fillOval(posX + 3, posY + 3, 14, 14);

        }
    }

    public void dibujarFantasmas(Graphics g, ArrayList<Fantasma> listaFantasmas, Jugador jugador) {
        for (Fantasma p : listaFantasmas) {
            if (jugador.estaMoviendose()) {
                g.setColor(new Color(0, 0, 255));
            } else if (p instanceof Blinky) {
                g.setColor(Color.GREEN);
            } else if (p instanceof Pinky) {
                g.setColor(Color.RED);
            } else if (p instanceof Clyde) {
                g.setColor(Color.MAGENTA);
            } else if (p instanceof Inky) {
                g.setColor(Color.white);
            }
            g.fillOval(p.getX(), p.getY(), 20, 20);

        }
    }

    public void dibujarMuro(Graphics g, ArrayList<Muro> listaMuros) {
        g.setColor(new Color(90, 148, 233));
        for (Muro wall : listaMuros) {
            int posX = wall.getColumna() * 20;
            int posY = wall.getFila() * 20 + 50;
            g.fillRect(posX, posY, 19, 19);
        }
    }

    public void dibujarMuroAire(Graphics g, ArrayList<Camino> listaCaminos) {
        g.setColor(new Color(115, 115, 115));
        for (Camino wall : listaCaminos) {
            int posX = wall.getColumna() * 20;
            int posY = wall.getFila() * 20 + 50;
            g.fillRect(posX, posY, 20, 20);
        }
    }

    public void dibujarPausa(Graphics g) {
        g.setColor(new Color(255, 255, 0));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Presiona 'P' para continuar ", 80, 280);
    }

    public void dibujarInformacion(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Nivel: " + game.getNivel(), 20, 35);
        g.drawString("Intentos: " + game.getLives(), 120, 35);
        g.drawString("Puntaje: " + game.getPlayerScore(), 250, 35);
    }


}
