package logica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Laberinto {
    private ArrayList<String> laberintoArray;
    private int fila;
    private int columnas;

    public Laberinto(String fileDir) {
        fila = 0;
        laberintoArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileDir));
            String line = null;
            while ((line = reader.readLine()) != null) {
                for (String s : line.split("")) {
                    laberintoArray.add(s);
                    columnas = line.length();
                }
                fila += 1;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getLaberinto() {
        return laberintoArray;
    }

    public int getFilas() {
        return fila;
    }

    public int getColumnas() {
        return columnas;
    }

}
