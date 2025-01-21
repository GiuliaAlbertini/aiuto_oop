package a01b.e2;

import java.util.HashMap;
import java.util.Map;

public class LogicImpl implements Logic {

    Map<Pair<Integer, Integer>, Boolean> map = new HashMap<>();
    int size;
    boolean over = false;

    public LogicImpl(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer, Integer> key = new Pair<Integer, Integer>(i, j);
                map.put(key, false);
            }
        }

    }

    boolean limiti(Pair<Integer, Integer> x) {
        if (x.getX() >= 0 && x.getX() < size && x.getY() >= 0 && x.getY() < size) {
            return true;
        } else
            return false;
    }

    @Override
    public void hit(int x, int y) {
        int count = 0;
        Pair<Integer, Integer> key = new Pair<>(x, y);
        Pair<Integer, Integer> uno = new Pair<>(x + 1, y + 1);
        Pair<Integer, Integer> due = new Pair<>(x - 1, y - 1);
        Pair<Integer, Integer> tre = new Pair<>(x - 1, y + 1);
        Pair<Integer, Integer> quattro = new Pair<>(x + 1, y - 1);
        if (limiti(uno)) {
            if (map.get(uno)) { // togli
                map.put(uno, false);
                count++;
            } else {
                if (limiti(uno)) {
                    map.put(uno, true);
                }
            }
        }

        if (limiti(due)) {
            if (map.get(due)) {
                count++;
                map.put(due, false);
            } else {
                if (limiti(due)) {
                    map.put(due, true);
                }
            }
        }

        if (limiti(tre)) {
            if (map.get(tre)) {
                count++;
                map.put(tre, false);
            } else {
                if (limiti(tre)) {
                    map.put(tre, true);
                }

            }
        }

        if (limiti(quattro)) {
            if (map.get(quattro)) {
                count++;
                map.put(quattro, false);
            } else {
                if (limiti(quattro)) {
                    map.put(quattro, true);
                }
            }
        }

        if (count == 3) {
            over = true;
        }

    }

    @Override
    public boolean selectedcells(int x, int y) {
        Pair<Integer, Integer> key = new Pair<Integer, Integer>(x, y);
        return map.get(key);
    }

    @Override
    public boolean isOver() {
        return this.over;
    }
}
