package a01c.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LogicImpl implements Logic {

    Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
    List<Pair<Integer, Integer>> list = new ArrayList<>();
    boolean step1 = false;
    int size;
    int i = 1;
    int k = 1;
    boolean isOver = false;

    public LogicImpl(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer, Integer> key = new Pair<Integer, Integer>(i, j);
                map.put(key, 0);
            }
        }
    }

    @Override
    public void hit(int x, int y) {
        Pair<Integer, Integer> key = new Pair<Integer, Integer>(x, y);
        if (map.get(key) == 0 && i < 3) { // non c'è nulla
            map.put(key, i); // ci devo mettere i numeri però
            i++;
            list.add(key);
        } else if (step1 == false) {
            fill();
            step1 = true;
        } else {
            fill2();
        }
    }

    void fill() {
        int xmin = Math.min(list.get(0).getX(), list.get(1).getX());
        int xmax = Math.max(list.get(0).getX(), list.get(1).getX());
        int ymin = Math.min(list.get(0).getY(), list.get(1).getY());
        int ymax = Math.max(list.get(0).getY(), list.get(1).getY());

        for (int i = xmin; i <= xmax; i++) {
            for (int j = ymin; j <= ymax; j++) {
                Pair<Integer, Integer> key = new Pair<Integer, Integer>(i, j);
                map.put(key, 9);
                map.put(list.get(0), 1);
                map.put(list.get(1), 2);
            }
        }
    }

    void fill2() {

        var pos1 = list.get(0);
        var pos2 = list.get(1);

        var newpos1 = new Pair<>(pos1.getX()-k, pos1.getY()-k);
        var newpos2 = new Pair<>(pos2.getX()+k, pos2.getY()+k);
        k++;

        int xmin = Math.min(newpos1.getX(), newpos2.getX());
        int xmax = Math.max(newpos1.getX(), newpos2.getX());
        int ymin = Math.min(newpos1.getY(), newpos2.getY());
        int ymax = Math.max(newpos1.getY(), newpos2.getY());

        for (int i = xmin; i <= xmax; i++) {
            for (int j = ymin; j <= ymax; j++) {
                Pair<Integer, Integer> key = new Pair<Integer, Integer>(i, j);
                map.put(key, 9);
                map.put(list.get(0), 1);
                map.put(list.get(1), 2);
            }
        }

        for (var elem : map.entrySet()) {
            var pos = elem.getKey();
            var value = elem.getValue();

            if(value != 0){
                if(pos.getX()+1 == 0 || pos.getX()-1 == size || pos.getY()+1 == 0 || pos.getY()-1 == size){
                    isOver = true;
                }
            }
        }

    }

    @Override
    public int selectdcells(int x, int y) {
        Pair<Integer, Integer> key = new Pair<Integer, Integer>(x, y);
        return map.get(key);
    }

    @Override
    public boolean getisOver(){
        return isOver;
    }

}
