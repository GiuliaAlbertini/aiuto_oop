package a01a.e2;

import java.util.HashMap;
import java.util.Map;

public class LogicImpl implements Logic {

    private Map<Pair<Integer, Integer>, Boolean> map = new HashMap<>();
    private int size;
    private boolean over = false;

    public LogicImpl(int size) {
        this.size = size;
        // Inizializzare la mappa con tutte le celle vuote (false)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map.put(new Pair<>(i, j), false);
            }
        }
    }

    @Override
    public void hit(int x, int y) {
        Pair<Integer, Integer> key = new Pair<>(x, y);

        // Se il pulsante è vuoto, metti una stellina
        if (!map.get(key)) {
            map.put(key, true);
        } else {
            // Se è già una stellina, rimuovila
            map.put(key, false);
        }

        // Verifica se il gioco deve terminare controllando le diagonali
        if (hasDiagonal()) {
            over = true;
        }
    }

    // Controlla se esiste una diagonale con almeno 3 stelline in tutta la matrice
    private boolean hasDiagonal() {
        // Controlla tutte le diagonali principali (top-left -> bottom-right)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (checkDiagonal(i, j, 1, 1)) {
                    return true;
                }
                if (checkDiagonal(i, j, 1, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Verifica se una diagonale contiene almeno 3 stelline in qualsiasi ordine
    private boolean checkDiagonal(int startX, int startY, int dx, int dy) {
        int count = 0;
        int x = startX;
        int y = startY;
        for (int i = 0; i < size; i++) {
            if (x >= 0 && x < size && y >= 0 && y < size && 
                map.getOrDefault(new Pair<>(x, y), false)) {
                count++;
            }
            x += dx;
            y += dy;
        }
        return count >= 3;
    }



    @Override
    public boolean selecCells(int x, int y) {
        Pair<Integer, Integer> key = new Pair<>(x, y);
        return map.getOrDefault(key, false);
    }

    @Override
    public boolean isOver() {
        return this.over;
    }
}
