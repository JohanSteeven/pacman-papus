package logica;
import java.util.Arrays;
public class Pinky extends Fantasma {

    public Pinky(int x, int y, Laberinto actLaberinto, Jugador jugador) {
        super(x, y, actLaberinto, jugador);
    }

    @Override
    public void getSiguienteDireccion(boolean perseguir) {
        // Obtener la celda actual del jugador
        int filaJugador = jugador.getY() / 20;
        int columnaJugador = jugador.getX() / 20;

        // Calcular la posición objetivo
        int filaObjetivo, columnaObjetivo;
        if (perseguir) {
            // Si se persigue al jugador, se toma la celda del jugador como objetivo
            filaObjetivo = filaJugador;
            columnaObjetivo = columnaJugador;
        } else {
            // Si no se persigue al jugador, se toma la celda de la esquina superior izquierda como objetivo
            filaObjetivo = 0;
            columnaObjetivo = 0;
        }

        // Calcular la distancia a cada celda adyacente
        Opcion[] opciones = new Opcion[4];
        opciones[0] = new Opcion(Direcciones.ARRIBA, calcularDistancia(filaObjetivo, columnaObjetivo, x, y - 30));
        opciones[1] = new Opcion(Direcciones.ABAJO, calcularDistancia(filaObjetivo, columnaObjetivo, x, y + 30));
        opciones[2] = new Opcion(Direcciones.IZQUIERDA, calcularDistancia(filaObjetivo, columnaObjetivo, x - 30, y));
        opciones[3] = new Opcion(Direcciones.DERECHA, calcularDistancia(filaObjetivo, columnaObjetivo, x + 30, y));

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
