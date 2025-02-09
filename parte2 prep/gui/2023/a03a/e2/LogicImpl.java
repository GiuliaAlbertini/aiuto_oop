package a03a.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LogicImpl implements Logic {
    Map<Pair<Integer, Integer>, Boolean> map = new HashMap<>();
    int width;
    int height;
    Boolean reverse = false;

    public LogicImpl(int width, int height) {
        this.width = width;
        this.height = height;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                var key = new Pair<>(i, j);
                map.put(key, false);
            }
        }
    }

    @Override
    public void initialize() {
        Pair<Integer, Integer> key = new Pair<>(new Random().nextInt(8), new Random().nextInt(7));
        System.out.println(key.getX() + "\n" + key.getY());
        map.put(key, true);
    }

    @Override

    public void hit(int x, int y) {
        int dx = -1; // Movimento iniziale verso l'alto
        int dy = 1; // Movimento a destra
        while (y < width) {
            Pair<Integer, Integer> key = new Pair<>(x, y);
            //se non è nella griglia allora stoppa
            if (!onBoard(x, y)) break;
            
            //mette il valore
            map.put(key, true);

            if (map.getOrDefault(new Pair<>(x, width - 1), false)) {
                // Colpisce il target
            }


            if (x == 0) {
                dx = 1; // Rimbalzo verso il basso
            } else if (x == height - 1) {
                dx = -1; // Rimbalzo verso l'alto
            }

            //
            x += dx;
            y += dy;
        }
    }


    boolean onBoard(int x, int y) {
        if (x >= 0 && x <= height && y >= 0 && y <= width) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCells(int x, int y) {
        var key = new Pair<>(x, y);
        return map.get(key);
    }

}
