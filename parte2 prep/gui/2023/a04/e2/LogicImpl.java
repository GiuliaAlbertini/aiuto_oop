package a04.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LogicImpl implements Logic {
    Map<Pair<Integer, Integer>, Boolean> map = new HashMap<>();
    List<Pair<Integer,Integer>> list = new ArrayList<>();
    int size;
    boolean over= false;

    public LogicImpl(int size){
        this.size=size;
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                Pair<Integer,Integer> key = new Pair<Integer,Integer>(i, j);
                map.put(key, false);
            }
        }
        
    }

    @Override   
    public void metti() {
            Pair<Integer, Integer> pair = new Pair<>(new Random().nextInt(size), 0);
            map.put(pair, true);
            list.add(pair);
            System.out.println(list);
    }


    @Override
    public void hit(int x, int y) {
        Pair<Integer,Integer> key = new Pair<Integer,Integer>(x, y);
        if (y != 0 &&  map.get(key) == false){
           if (y==size-1){
                list.add(key);  
                map.put(key, true);
                zizag(x, y);
           }
           map.put(key, true);
           list.add(key);
        }
        
    }

    @Override
    public void zizag(int x, int y) {
        Pair<Integer,Integer> diagonal1 = new Pair<Integer,Integer>(x-1, y-1);
        Pair<Integer,Integer> diagonal2 = new Pair<Integer,Integer>(x+1, y-1);
        for (Pair<Integer,Integer> elem : list) {
            if(elem.equals(diagonal1) || elem.equals(diagonal2)){
                if (elem== list.get(0)){
                    over= true;
                }
                zizag(elem.getX(), elem.getY());
            }
        }
    }

    @Override
    public boolean isOver() {
       return over;
    }

    @Override
    public boolean SetCells(int x, int y) {
        Pair<Integer,Integer> key = new Pair<Integer,Integer>(x, y);
        return map.get(key);
    }
}
