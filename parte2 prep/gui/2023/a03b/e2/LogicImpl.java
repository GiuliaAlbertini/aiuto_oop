package a03b.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicImpl implements Logic {
    Map<Pair<Integer, Integer>, Boolean> map = new HashMap<>();
    int height;
    int width;


    public LogicImpl(int height, int width) {
        this.height = height;
        this.width = width;

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                Pair<Integer, Integer> key = new Pair<Integer, Integer>(i, j);
                map.put(key, false);
            }
        }
    }

    @Override
    public void hit(int x, int y) {
        diagonal(x, y);

    }

    void diagonal(int x, int y) {
        int x1 = x, y1 = y;
        int x2 = x, y2 = y;

        int dx1 = 1;
        int dy1 = -1;

        int dx2 = 1;
        int dy2 = 1;

        while (true) {
            if (!onBoard(x1, y1)) {
                break;
            }

            if (!onBoard(x2, y2)) {
                break;
            }
            Pair<Integer, Integer> diagonal1 = new Pair<Integer, Integer>(x1, y1);
            map.put(diagonal1, true);

            Pair<Integer, Integer> diagonal2 = new Pair<Integer, Integer>(x2, y2);
            map.put(diagonal2, true);

            for (int j=y1; j<y2; j++){
                Pair<Integer, Integer> chiavare = new Pair<Integer, Integer>(x1, j);
                map.put(chiavare, true);
            }

            x1 += dx1;
            y1 += dy1;

            x2 += dx2;
            y2 += dy2;

        }
    }


    boolean onBoard(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCells(int x, int y) {
        Pair<Integer, Integer> key = new Pair<Integer, Integer>(x, y);
        return map.get(key);
    }

}
