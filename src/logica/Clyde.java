package logica;

import java.util.*;

public class Clyde extends Fantasma{
    public Clyde(int x, int y, Laberinto actLaberinto, Jugador jugador) {
        super(x, y, actLaberinto, jugador);
    }

    @Override
    public void getSiguienteDireccion(boolean perseguir) {
        // Obtener la celda actual del jugador
        int filaJugador = jugador.getY() / 20;
        int columnaJugador = jugador.getX() / 20;

        // Calcular la distancia entre Clyde y el jugador
        double distancia = calcularDistancia(filaJugador, columnaJugador, x, y);

        // Calcular la posición objetivo
        int filaObjetivo, columnaObjetivo;
        if (perseguir) {
            // Si se persigue al jugador y la distancia entre Clyde y el jugador es mayor a 8 celdas, se toma la celda del jugador como objetivo
            if (distancia > 8 * 20) {
                filaObjetivo = filaJugador;
                columnaObjetivo = columnaJugador;
            } else {
                // Si la distancia es menor o igual a 8 celdas, Clyde se aleja hacia la esquina inferior derecha del laberinto
                filaObjetivo = actLaberinto.getFilas() - 1;
                columnaObjetivo = actLaberinto.getColumnas() - 1;
            }
        } else {
            // Si no se persigue al jugador, se toma la celda de la esquina superior derecha como objetivo
            filaObjetivo = 0;
            columnaObjetivo = actLaberinto.getColumnas() - 1;
        }

        // Calcular la distancia a cada celda adyacente
        Opcion[] opciones = new Opcion[4];
        opciones[0] = new Opcion(Direcciones.ARRIBA, calcularDistancia(filaObjetivo, columnaObjetivo, x, y - 20));
        opciones[1] = new Opcion(Direcciones.ABAJO, calcularDistancia(filaObjetivo, columnaObjetivo, x, y + 20));
        opciones[2] = new Opcion(Direcciones.IZQUIERDA, calcularDistancia(filaObjetivo, columnaObjetivo, x - 20, y));
        opciones[3] = new Opcion(Direcciones.DERECHA, calcularDistancia(filaObjetivo, columnaObjetivo, x + 20, y));

        // Ordenar las opciones de menor a mayor distancia
        Arrays.sort(opciones);

        // Elegir la dirección correspondiente a la celda más cercana que no choque con una pared
        for (int i = 0; i < opciones.length; i++) {
            if (!golpeaPared(opciones[i].direccion)) {
                dirección = opciones[i].direccion;
                return;
            }
        }
    }

    private double calcularDistancia(int fila, int columna, int x, int y) {
        // Calcular la distancia euclidiana entre la celda objetivo y la posición actual
        double deltaX = columna * 20 - x / 20 * 20;
        double deltaY = fila * 20 - y / 20 * 20;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

}

