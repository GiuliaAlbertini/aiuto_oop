package a01a.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicImpl implements Logic {

    Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
    private final int size;
    private boolean neighbors = true;
    private int i = 0;

    public LogicImpl(int size) {
        this.size = size;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var key = new Pair<>(i, j);
                map.put(key, 0);
            }
        }
    }

    // verifico se cella selezionata ha adiacenti con un valore diverso da 0
    public boolean selectCells(int x, int y) {
        Pair<Integer, Integer> current_cell = new Pair<>(x, y);
        for (Pair<Integer, Integer> selected_cell : map.keySet()) { 
            //controllo tutte le chiavi della mappa ovvero tutte le posizioni sulla mappa
            if (isAdjacent(current_cell, selected_cell) && map.get(selected_cell) != 0) { 
                //selectedcell perche devo verificare se la cella current ha delle celle adiacenti numerate
                return true; // se c'è almeno un adiacente
            }
        }
        return false; // altrimenti falso
    }

    // verifica
    public boolean isAdjacent(Pair<Integer, Integer> cell1, Pair<Integer, Integer> cell2) {
        int rowDiff = Math.abs(cell1.getX() - cell2.getX());
        int columnsdiff = Math.abs(cell1.getY() - cell2.getY());
        return rowDiff <= 1 && columnsdiff <= 1;
    }

    @Override
    public void hit(int x, int y) {
        if (neighbors) { // vero -> primo controllo ma non ho preso gli adiacenti, quindi incremento
            if (selectCells(x, y)) { // se è vero chd ho toccato i vicini allora attivo la funzione che ad ogni tocco
                                     // è diagolare
                neighbors = false;
                diagonal();// attivo la funzione diagonale
                
            } else {
            Pair<Integer, Integer> key = new Pair<>(x, y);
            i++;
            map.put(key, i);
            }
        } else {
            // ormai ho toccato i vicini e non diagonalizzo più
            diagonal();
        }
    }

    // DIPENDENZA
    @Override
    public void diagonal() {
        Map<Pair<Integer, Integer>, Integer> updates = new HashMap<>(); // Per aggiornamenti
        List<Pair<Integer, Integer>> toReset = new ArrayList<>(); // Per valori da azzerare

        // Itero su tutte le chiavi della mappa
        for (Pair<Integer, Integer> elem : map.keySet()) {
            int newValue = map.get(elem);
            if (newValue != 0) { // Se il valore in posizione elem è diverso da 0
                Pair<Integer, Integer> diagonalkey = new Pair<>(elem.getX() +1, elem.getY() - 1); // Nuova posizione
                if (diagonalkey.getX() > size || diagonalkey.getY() < 0) {
                    System.exit(0); // Uscita se si esce dalla griglia
                }
                updates.put(diagonalkey, newValue); // Aggiungi la nuova posizione
                toReset.add(elem); // Segna la posizione da azzerare
            }
        }

        // Applica gli aggiornamenti
        for (Pair<Integer, Integer> elem : toReset) {
            map.put(elem, 0); // Azzeramento
        }
        map.putAll(updates); // Aggiungi tutti gli aggiornamenti
    }

    // valore singola cella
    @Override
    public int getState(int x, int y) {
        Pair<Integer, Integer> key = new Pair<Integer, Integer>(x, y);
        return this.map.get(key);
    }
}
