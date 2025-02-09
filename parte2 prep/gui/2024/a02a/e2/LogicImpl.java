package a02a.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LogicImpl implements Logic {
    Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
    int size;
    Random random = new Random();
    Pair<Integer, Integer> pedina = new Pair<Integer, Integer>(0, 0);
    int dx = 3;
    int dy = 0;
    boolean right= false, up= false, down= true, left = false;

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
    public void initialize() {
        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                Pair<Integer, Integer> key = new Pair<Integer, Integer>(i, j);
                map.put(key, 9);
            }
        }

        int count = 0;
        while (count < 3) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            var couple = new Pair<Integer, Integer>(x, y);
            if (x == 0 || y == 0 || x == size || y == size) {
                if (map.get(couple) != 0) {
                    break;
                }
                var elem = new Pair<Integer, Integer>(x, y);
                map.put(elem, 2);
                count++;
            }
        }

        map.put(pedina, 1);

    }

    @Override
    public void hit(int x, int y) {
            int step =2;
            for(int i=0; i<step; i++){
                if (down){
                    var key = new Pair<Integer, Integer>(pedina.e1()+dx, pedina.e2());
                    System.out.println(dx);
                    if (key.e1() > size-1){
                        var val= Math.abs(key.e1()+1-size);
                        var valore = new Pair<Integer, Integer>(size-1,val);
                        map.put(valore, 1);
                        map.put(pedina, 0);
                        right=true;
                        down=false;
                        dx++;
                        dy=dx;
                        pedina=valore;
                        break;
                    }
                    dx++;
                    map.put(pedina, 0);

                    pedina=key;
                    map.put(key, 1);
                    
                }else if (right){
                    var key2 = new Pair<Integer, Integer>(size-1, pedina.e2()+dy);
                    if (key2.e2()>size-1){
                        var val= Math.abs(key2.e1()-1-size);
                        var valore = new Pair<Integer, Integer>(val,size-1);
                        map.put(valore, 1);
                        map.put(pedina, 0);
                        right=false;
                        up=true;
                        dy--;
                        dx=dy;
                        pedina=valore;
                        break;
                    }
                    dy++;
                    map.put(pedina, 0);

                    pedina=key2;
                    map.put(key2, 1);
                } else if (up){
                    var key2 = new Pair<Integer, Integer>(0, pedina.e1()-dy);
                    if (key2.e2()<0){
                        var val= Math.abs(key2.e2());
                        var valore = new Pair<Integer, Integer>(0,val);
                        map.put(valore, 1);
                        map.put(pedina, 0);
                        dx--;
                        dx=dy;
                        pedina=valore;
                    }
                    dx--;
                    pedina=key2;
                    map.put(key2, 1);
                }
                i++;
            }

    }

    boolean inBound(int x, int y) {
        return (x >= 0 && x <= size && y >= 0 && y <= size);

    }



    @Override
    public int SetCells(int x, int y) {
        Pair<Integer, Integer> key = new Pair<Integer, Integer>(x, y);
        return map.get(key);

    }

}
