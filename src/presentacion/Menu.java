package presentacion;

import logica.Muro;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Menu extends JPanel {
    private Image imagenFondo;
    public Menu(){
        try{
            imagenFondo = ImageIO.read(new File("src/recursos/fondoMenu.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {

        g.drawImage(imagenFondo,0,0,getWidth(),getHeight(),null);
        g.setColor(Color.YELLOW);
        Font font = new Font("Goudy Stout",Font.BOLD, 28);
        g.setFont(font);
        g.drawString("PACMAN", 90, 100);

        font = new Font("Showcard Gothic", Font.ROMAN_BASELINE, 16);

        g.setFont(font);
        g.setColor(Color.WHITE);
    }




}
