package logica;

import java.util.ArrayList;
import java.util.Collections;

public class Blinky extends Fantasma {


    public Blinky(int x, int y, Laberinto actLaberinto, Jugador jugador) {
        super(x, y, actLaberinto, jugador);
    }
    public void getSiguienteDireccion(boolean perseguir){
        {
            int velocidad = 2;
            ArrayList<Direcciones> todasDirecciones = new ArrayList<>();
            ArrayList<Direcciones> direccionCorrecta = new ArrayList<>();
            ArrayList<Opcion> todasOpciones = new ArrayList<>();
            todasDirecciones.add(Direcciones.ARRIBA);
            todasDirecciones.add(Direcciones.ABAJO);
            todasDirecciones.add(Direcciones.IZQUIERDA);
            todasDirecciones.add(Direcciones.DERECHA);


            for (int i = 0; i < todasDirecciones.size(); i++) {
                if (!golpeaPared(todasDirecciones.get(i))) {
                    direccionCorrecta.add(todasDirecciones.get(i));
                }
            }
            todasDirecciones = direccionCorrecta;


            if (todasDirecciones.size() == 1) {
                dirección = todasDirecciones.get(0);
            }


            for (int i = 0; i < todasDirecciones.size(); i++) {
                int postempoX, postempY;
                double distancia;
                switch (todasDirecciones.get(i)) {
                    case ARRIBA:
                        postempoX = x;
                        postempY = y - velocidad;
                        distancia = Math.abs(Math.pow(postempoX - jugador.getX(), 2) + Math.pow(postempY - jugador.getY(), 2));
                        todasOpciones.add(new Opcion(Direcciones.ARRIBA, distancia));
                        break;
                    case ABAJO:
                        postempoX = x;
                        postempY = y + velocidad;
                        distancia = Math.abs(Math.pow(postempoX - jugador.getX(), 2) + Math.pow(postempY - jugador.getY(), 2));
                        todasOpciones.add(new Opcion(Direcciones.ABAJO, distancia));
                        break;
                    case IZQUIERDA:
                        postempoX = x - velocidad;
                        postempY = y;
                        distancia = Math.abs(Math.pow(postempoX - jugador.getX(), 2) + Math.pow(postempY - jugador.getY(), 2));
                        todasOpciones.add(new Opcion(Direcciones.IZQUIERDA, distancia));
                        break;
                    case DERECHA:
                        postempoX = x + velocidad;
                        postempY = y;
                        distancia = Math.abs(Math.pow(postempoX - jugador.getX(), 2) + Math.pow(postempY - jugador.getY(), 2));
                        todasOpciones.add(new Opcion(Direcciones.DERECHA, distancia));
                        break;
                }
            }

            Collections.sort(todasOpciones);
            if (perseguir) {
                dirección = todasOpciones.get(0).direccion;
            } else {
                dirección = todasOpciones.get(todasOpciones.size() - 1).direccion;
            }
        }
    }


}