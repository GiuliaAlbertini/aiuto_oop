package a02a.e2;

import java.util.HashMap;
import java.util.Map;

public class LogicImpl implements Logic {
    Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
    int size;
    boolean over=false;

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
        diagonal(x, y);
        if (!control()){
            map= new HashMap<>();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Pair<Integer, Integer> key2 = new Pair<Integer, Integer>(i, j);
                    map.put(key2, 0);
                }
            }
        }
    }



    
    void diagonal(int x, int y) {
        Pair<Integer, Integer> key = new Pair<Integer, Integer>(x, y);
        int count=0;
        int k = 0;
        while (true) {
            System.out.println("entro");
            Pair<Integer, Integer> diagonale1 = new Pair<Integer, Integer>(x + k, y + k);
            Pair<Integer, Integer> diagonale2 = new Pair<Integer, Integer>(x - k, y - k);
            Pair<Integer, Integer> diagonale3 = new Pair<Integer, Integer>(x + k, y - k);
            Pair<Integer, Integer> diagonale4 = new Pair<Integer, Integer>(x - k, y + k);

            if (inBoard(diagonale1.getX(), diagonale1.getY())) {
                map.put(diagonale1, 3);
                count++;
            }
            if (inBoard(diagonale2.getX(), diagonale2.getY())) {
                map.put(diagonale2, 3);
                count++;
            }
            if (inBoard(diagonale3.getX(), diagonale3.getY())) {
                map.put(diagonale3, 3);
                count++;
            }
            if (inBoard(diagonale4.getX(), diagonale4.getY())) {
                map.put(diagonale4, 3);
                count++;
            }
            
            if (count==0){
                break;
            }
            count=0;
            k++;
        }
        map.put(key, 1);
        
    }

    boolean control(){
        for (var elem : map.entrySet()) {
            if (map.get(elem.getKey())== 0){
                return true;  
            }
        }
        return false;

    }

    @Override
    public boolean isOver(){
        return this.over;
    }

    boolean inBoard(int x, int y) {
        return (x >= 0 && x <= size && y >= 0 && y <= size);
    }

    @Override
    public int selectCells(int x, int y) {
        Pair<Integer, Integer> key = new Pair<Integer, Integer>(x, y);
        return map.get(key);
    }

}
