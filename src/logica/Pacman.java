package logica;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import presentacion.PlayerListener;
import ucd.comp2011j.engine.Game;

public class Pacman implements Game {

    public static final int ALTURA_PANTALLA = 530 + 50;
    public static final int ANCHO_PANTALLA = 380;
    private static final int NO_NIVELES = 3;

    private final PlayerListener listener;
    private int vidasJugador;
    private int puntajeJugador;
    private int puntajeTemp = 0;
    private boolean pausado = true;
    private int nivelActual = 1;
    private final String[] nivel;
    private Laberinto curLaberinto;

    private Jugador jugador;
    private ArrayList<Fantasma> listaFantasmas;
    private ArrayList<Fantasma> listaFantasmasBlinky;
    private ArrayList<Fantasma> listaFantasmasEnBase;
    private final ArrayList<Fantasma> listaFantasmasPorSalir = new ArrayList<>(); // start timer
    private ArrayList<Comida> listaComidas;
    private ArrayList<Fruta> listaFrutas;
    private ArrayList<Poder> listaPoder;
    private ArrayList<Muro> listaMuros;
    private ArrayList<Camino> listaCaminos;
    private Fruta frutaActual;
    private int curIndiceFrutas;
    private int contadorComerFantasma;

    private final Timer timer = new Timer();
    private boolean actualizarFruta = true;

    public Pacman(PlayerListener listener) {
        this.listener = listener;
        this.nivel = getNiveltxt();
        startNewGame();
    }

    @Override
    public int getPlayerScore() {
        return puntajeJugador;
    }

    @Override
    public int getLives() {
        return vidasJugador;
    }

    @Override
    public void updateGame() {
        if (!isPaused()) {
            moverJugador();
            ordenarFantasmas();
            moverFantasmas();
            comer();
            comerFantasma();
            generarFrutaAleatoria();
        }
    }

    public void comerFantasma() {
        for (Fantasma g : listaFantasmas) {
            if (g.comer(jugador)) {
                jugador.reiniciar();
                vidasJugador--;

            }
        }
    }

    public void ordenarFantasmas() {
        Random rand = new Random();
        int tiempoEspera = 0;
        if (listaFantasmasEnBase.size() == listaFantasmas.size()) {
            listaFantasmasEnBase.get(0).setMover(true);
            listaFantasmasEnBase.remove(0);
        }
        for (Fantasma fantasma : listaFantasmasEnBase) {
            tiempoEspera += rand.nextInt(5 * 100, 50 * 100);
            listaFantasmasPorSalir.add(fantasma);
            //listaFantasmasPorSalir.add(Blinky);
            TimerTask pend = new TimerTask() {
                public void run() {
                    fantasma.setMover(true);
                    listaFantasmasPorSalir.remove(fantasma);
                }
            };
            timer.schedule(pend, tiempoEspera);
        }
        listaFantasmasEnBase.clear();
    }

    public void moverFantasmas() {
        for (Fantasma fantasma : listaFantasmas) {
            fantasma.mover();
        }
    }

    public void generarFrutaAleatoria() {
        if (actualizarFruta && listaFrutas.size() != 0) {
            Random rand = new Random();
            curIndiceFrutas = rand.nextInt(listaFrutas.size());
            frutaActual = listaFrutas.get(curIndiceFrutas);
            actualizarFruta = !actualizarFruta;
            TimerTask changeRefreshState = new TimerTask() {
                public void run() {
                    actualizarFruta = !actualizarFruta;
                }
            };
            int delay = rand.nextInt(5000, 10001);
            timer.schedule(changeRefreshState, delay);
        }
    }

    public void comer() {
        // Comer puntos
        for (int i = 0; i < listaComidas.size(); i++) {
            if (jugador.come(listaComidas.get(i))) {
                listaComidas.remove(i);
                addPuntaje(10);
            }
        }
        // Comer frutas
        if (frutaActual != null) {
            if (jugador.come((frutaActual))) {
                listaFrutas.remove(curIndiceFrutas);
                frutaActual = null;
                addPuntaje(500);
            }
        }
        // Comer poderes
        for (int i = 0; i < listaPoder.size(); i++) {
            if (jugador.come(listaPoder.get(i))) {
                listaPoder.remove(i);
                jugador.setPoder();
                addPuntaje(50);
                contadorComerFantasma = 0;
            }
        }
        // Comer fantasmas
        if (jugador.estaMoviendose()) {
            for (Fantasma fantasma : listaFantasmas) {
                fantasma.setVelocidad(1); //Se reduce la velocidad cuando estan asustados
                if (jugador.come(fantasma)) {
                    fantasma.reiniciar();
                    listaFantasmasEnBase.add(fantasma);
                    addPuntaje(200 * (int) Math.pow(2, contadorComerFantasma));
                    contadorComerFantasma++;
                }
            }
        }
    }

    public void moverJugador() {
        verificarSiJugadorChocaConLaPared();
        if (!jugador.estaGolpeandoPared()) {
            jugador.mover();
        }

        if ((jugador.getX() / 20.0) % 1 == 0 && ((jugador.getY() - 50) / 20.0) % 1 == 0) {
            if (listener.estaPresionandoIzquierda()) {
                if (puedeCambiarDeDireccion(Direcciones.IZQUIERDA)) {
                    jugador.cambiarDireccion(Direcciones.IZQUIERDA);
                }
            } else if (listener.estaPresionandoDerecha()) {
                if (puedeCambiarDeDireccion(Direcciones.DERECHA)) {
                    jugador.cambiarDireccion(Direcciones.DERECHA);
                }
            } else if (listener.estaPresionandoArriba()) {
                if (puedeCambiarDeDireccion(Direcciones.ARRIBA)) {
                    jugador.cambiarDireccion(Direcciones.ARRIBA);
                }
            } else if (listener.estaPresionandoAbajo()) {
                if (puedeCambiarDeDireccion(Direcciones.ABAJO)) {
                    jugador.cambiarDireccion(Direcciones.ABAJO);
                }
            }
        }
    }

    public boolean puedeCambiarDeDireccion(Direcciones direccion) {
        int fila, columna, indice;
        switch (direccion) {
            case ARRIBA:
                fila = (jugador.getY() - 1 - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    return false;
                }
                break;
            case ABAJO:
                fila = (jugador.getY() - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila + 1, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    return false;
                }
                break;
            case IZQUIERDA:
                fila = (jugador.getY() - 50) / 20;
                columna = (jugador.getX() - 1) / 20;
                indice = Coordenadas.getIndice(fila, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    return false;
                }
                break;
            case DERECHA:
                fila = (jugador.getY() - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila, columna + 1, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    return false;
                }
                break;
        }
        return true;
    }

    public void verificarSiJugadorChocaConLaPared() {
        int fila, columna, indice;
        switch (jugador.getDireccion()) {
            case ARRIBA:
                fila = (jugador.getY() - 1 - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    jugador.setGolpeaPared(true);
                }
                break;
            case ABAJO:
                fila = (jugador.getY() - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila + 1, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    jugador.setGolpeaPared(true);
                }
                break;
            case IZQUIERDA:
                fila = (jugador.getY() - 50) / 20;
                columna = (jugador.getX() - 1) / 20;
                indice = Coordenadas.getIndice(fila, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    jugador.setGolpeaPared(true);
                }
                break;
            case DERECHA:
                fila = (jugador.getY() - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila, columna + 1, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    jugador.setGolpeaPared(true);
                }
                break;
        }
    }

    public Jugador getJugador() {
        return jugador;
    }

    @Override
    public boolean isPaused() {
        return pausado;
    }

    @Override
    public void checkForPause() {
        if (listener.hasPressedPause()) {
            pausado = !pausado;
            listener.resetPause();
        }
    }

    @Override
    public void startNewGame() {
        vidasJugador = 3;
        puntajeJugador = 0;
        contadorComerFantasma = 0;
        curLaberinto = new Laberinto(nivel[nivelActual]);

        jugador = generatePlayer();
        listaFantasmas = generateGhostList();
        listaComidas = listaComida();
        listaFrutas = generarListaFrutas();
        listaPoder = generatePowerList();
        listaMuros = generarListaPared();
        listaCaminos = generarListaCaminos();
        frutaActual = listaFrutas.get(0);

        listaFantasmasEnBase = new ArrayList<>();
        for (Fantasma g : listaFantasmas) {
            listaFantasmasEnBase.add(g);
        }
    }

    public ArrayList<Comida> listaComida() {
        ArrayList<String> listaLaberinto = curLaberinto.getLaberinto();
        ArrayList<Comida> listaComida = new ArrayList<>();
        for (int i = 0; i < listaLaberinto.size(); i++) {
            if (listaLaberinto.get(i).equals(".")) {
                listaComida.add(new Comida(i, curLaberinto));
            }
        }
        return listaComida;
    }

    public ArrayList<Comida> getListaComidas() {
        return listaComidas;
    }

    public ArrayList<Fruta> generarListaFrutas() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        ArrayList<Fruta> fruitArr = new ArrayList<>();
        for (int i = 0; i < mazeArr.size(); i++) {
            if (mazeArr.get(i).equals("F")) {
                fruitArr.add(new Fruta(i, curLaberinto));
            }
        }
        return fruitArr;
    }

    public ArrayList<Fruta> getListaFrutas() {
        return listaFrutas;
    }

    public ArrayList<Muro> generarListaPared() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        ArrayList<Muro> wallArr = new ArrayList<>();
        for (int i = 0; i < mazeArr.size(); i++) {
            if (mazeArr.get(i).equals("W")) {
                wallArr.add(new Muro(i, curLaberinto));
            }
        }
        return wallArr;
    }

    public ArrayList<Muro> getlistaPared() {
        return listaMuros;
    }

    public ArrayList<Camino> generarListaCaminos() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        ArrayList<Camino> wallArr = new ArrayList<>();
        for (int i = 0; i < mazeArr.size(); i++) {
            if (mazeArr.get(i).equals("-")) {
                wallArr.add(new Camino(i, curLaberinto));
            }
        }
        return wallArr;
    }

    public ArrayList<Camino> getAirWallList() {
        return listaCaminos;
    }

    public ArrayList<Poder> generatePowerList() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        ArrayList<Poder> powerArr = new ArrayList<>();
        for (int i = 0; i < mazeArr.size(); i++) {
            if (mazeArr.get(i).equals("*")) {
                powerArr.add(new Poder(i, curLaberinto));
            }
        }
        return powerArr;
    }

    public ArrayList<Poder> getPowerList() {
        return listaPoder;
    }

    public Jugador generatePlayer() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        for (int i = 0; i < mazeArr.size(); i++) {
            if (mazeArr.get(i).equals("P")) {
                int posX = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getColumna() * 20;
                int posY = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getFila() * 20 + 50;
                return new Jugador(posX, posY);
            }
        }
        throw new Error("Jugador no existe");
    }

    public ArrayList<Fantasma> generateGhostList() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        ArrayList<Fantasma> ghostArr = new ArrayList<>();
        for (int i = 0; i < mazeArr.size(); i++) {
            switch (mazeArr.get(i)) {
                case "B" -> {
                    int posX = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getColumna() * 20;
                    int posY = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getFila() * 20 + 50;
                    ghostArr.add(new Blinky(posX, posY, curLaberinto, jugador));
                    break;
                }
                case "K" -> {
                    int posX = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getColumna() * 20;
                    int posY = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getFila() * 20 + 50;
                    ghostArr.add(new Pinky(posX, posY, curLaberinto, jugador));
                    break;
                }
                case "C" -> {
                    int posX = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getColumna() * 20;
                    int posY = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getFila() * 20 + 50;
                    ghostArr.add(new Clyde(posX, posY, curLaberinto, jugador));
                    break;
                }
                case "I" -> {
                    int posX = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getColumna() * 20;
                    int posY = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getFila() * 20 + 50;
                    ghostArr.add(new Inky(posX, posY, curLaberinto, jugador));
                    break;
                }
            }
        }
        return ghostArr;
    }


    public ArrayList<Fantasma> getListaFantasmas() {
        return listaFantasmas;
    }

    public String[] getNiveltxt() {
        String[] arr = new String[NO_NIVELES];
        for (int i = 0; i < NO_NIVELES; i++) {
            arr[i] = "./maze/" + i + ".txt";
        }
        return arr;
    }

    public void addPuntaje(int num) {
        puntajeJugador += num;
        puntajeTemp += num;
        if (puntajeTemp >= 10000) {
            vidasJugador += 1;
            puntajeTemp = 0;
        }
    }

    public Fruta getFrutaActual() {
        return frutaActual;
    }

    public int getNivel() {
        return nivelActual;
    }

    public Laberinto getMaze() {
        return curLaberinto;
    }

    @Override
    public boolean isLevelFinished() {
        return listaComidas.size() == 0 && listaPoder.size() == 0;
    }

    @Override
    public boolean isPlayerAlive() {
        return true;
    }

    @Override
    public void resetDestroyedPlayer() {
    }

    @Override
    public void moveToNextLevel() {
        pausado = true;
        nivelActual++;
        contadorComerFantasma = 0;
        curLaberinto = new Laberinto(nivel[nivelActual]);

        jugador = generatePlayer();
        listaFantasmas = generateGhostList();
        listaComidas = listaComida();
        listaFrutas = generarListaFrutas();
        listaPoder = generatePowerList();
        listaMuros = generarListaPared();
        listaCaminos = generarListaCaminos();
        frutaActual = listaFrutas.get(0);

        listaFantasmasEnBase = new ArrayList<>();
        listaFantasmasEnBase.addAll(listaFantasmas);
    }

    @Override
    public boolean isGameOver() {
        if (vidasJugador == 0) {
            return true;
        }
        return nivelActual > NO_NIVELES;
    }

    @Override
    public int getScreenWidth() {
        return ANCHO_PANTALLA;
    }

    @Override
    public int getScreenHeight() {
        return ALTURA_PANTALLA;
    }
}
