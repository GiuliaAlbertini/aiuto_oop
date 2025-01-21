package a02a.e2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class LogicImpl implements Logic {

    Map<Pair<Integer, Integer>, Boolean> map = new HashMap<>();
    List<Pair<Integer, Integer>> list = new LinkedList<>();
    int size;
    boolean init = true;
    int i=0;
    boolean isOver= false;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public void init() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer, Integer> key = new Pair<Integer, Integer>(i, j);
                map.put(key, true);
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer, Integer> key = new Pair<Integer, Integer>(i, j);
                if (key.getX() % 2 == 0 && key.getY() % 2 == 0) {
                    map.put(key, false);
                }
            }
        }
    }

    @Override
public void hit(int x, int y) {
    Pair<Integer, Integer> key = new Pair<>(x, y);
    if (!map.get(key)) {  // La cella è vuota (rappresentata da "*")
        map.put(key, false);  // Impostiamo la cella come "o"
        i++;
        list.add(key);
        if (i >= 4) {
            
            if (control() == true) {
                System.out.println("ciao");
                isOver = true;  // Quadrato trovato, termina il gioco
                System.out.println("Quadrato trovato!");
            }
        }
    } else {
        i = 0;  // Se la cella è già occupata, resettiamo la sequenza
    }
}

boolean control() {
    // Preleva gli ultimi 4 click dalla lista
    Pair<Integer,Integer> po4 = list.get(list.size() - 1); // Ultimo: alto-sinistra
    var po3 = list.get(list.size() - 2); // Terzo: alto-destra
    var po2 = list.get(list.size() - 3); // Secondo: basso-destra
    var po1 = list.get(list.size() - 4); // Primo: basso-sinistra

    System.out.println("Controllando: " + po1 + " " + po2 + " " + po3 + " " + po4);

    // Verifica che le celle siano disposte correttamente per formare il quadrato
    // Ordine: basso-sinistra (po1), basso-destra (po2), alto-destra (po3), alto-sinistra (po4)
    if (po1.getX() == po2.getX() - 2 && po1.getY() == po2.getY()) {  // Basso-sinistra -> Basso-destra
        System.out.println("step1");
        if (po2.getX() == po3.getX() && po2.getY() == po3.getY() +2) {  // Basso-destra -> Alto-destra
            System.out.println("step2");
            if (po3.getX() == po4.getX() + 2 && po3.getY() == po4.getY()) {  // Alto-destra -> Alto-sinistra
                System.out.println("step3");
                return true;  // Quadrato trovato, termina il gioco
            }
        }
    }

    return false;  // Non è un quadrato
}

    


    @Override
    public boolean SetCells(int x, int y) {
        Pair<Integer, Integer> key = new Pair<Integer, Integer>(x, y);
        return map.get(key);
    }

    @Override
    public boolean getSet(int x, int y){
        var key = new Pair<>(x, y);
        return list.contains(key);
    }

    @Override
    public boolean getIsOver(){
        return isOver;
    }

}
