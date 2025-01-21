package a02b.e2;

import java.util.HashMap;
import java.util.Map;

public class LogicImpl implements Logic{

    Map<Pair<Integer,Integer>, Boolean> map = new HashMap<>();
    int size;
    boolean step1=false;
    Pair<Integer,Integer> pos;
    Pair<Integer,Integer> togli1;
    Pair<Integer,Integer> togli2;
    Pair<Integer,Integer> punto1= new Pair<Integer,Integer>(2, 2);
    Pair<Integer,Integer> punto2= new Pair<Integer,Integer>(7, 7);
    boolean isOver=false;

    public LogicImpl(int size){
        this.size= size;
        for(int i=0; i< size; i++){
            for(int j=0; j<size; j++){
                Pair<Integer,Integer> key= new Pair<Integer,Integer>(i, j);
                map.put(key, false);
            } 
        }
    }

    void implement(int x, int y){
        for(int i=0; i< size; i++){
            for(int j=0; j<size; j++){
                Pair<Integer,Integer> key= new Pair<Integer,Integer>(i, j);
                map.put(key, false);
            } 
        }

        Pair<Integer,Integer> chiave1= new Pair<Integer,Integer>(x-2, y-2);
        Pair<Integer,Integer> chiave2= new Pair<Integer,Integer>(x+2, y+2);

            int xmin=Math.min(chiave1.getX(), chiave2.getX());
            int xmax=Math.max(chiave1.getX(), chiave2.getX());
            int ymin=Math.min(chiave1.getY(), chiave2.getY());
            int ymax=Math.max(chiave1.getY(), chiave2.getY());
            
            for(int i=xmin; i<= xmax; i++){
                for(int j=ymin; j<= ymax; j++){
                    Pair<Integer,Integer> aggiungi= new Pair<Integer,Integer>(i, j);
                    map.put(aggiungi, true);
                } 
            }

            togli1= new Pair<Integer,Integer>(x-1, y-1);
            togli2= new Pair<Integer,Integer>(x+1, y+1);
            Pair<Integer,Integer> togli3= new Pair<Integer,Integer>(x+1, y-1);
            Pair<Integer,Integer> togli4= new Pair<Integer,Integer>(x-1, y+1);
            map.put(togli1, false);
            map.put(togli2, false);
            map.put(togli3, false);
            map.put(togli4, false);
    }


    @Override
    public void hit(int x, int y) {
        pos = new Pair<Integer,Integer>(x, y);
        if(step1== false){
            implement(x, y);
            step1=true;
        }else{
            if(pos.equals(punto1) || pos.equals(punto2)){
                isOver= true;
            }
            if (pos.equals(togli1)){
                implement(punto1.getX(), punto1.getY());
            }else if (pos.equals(togli2)){
                implement(punto2.getX(), punto2.getY());
            }
        }
    }

    @Override
    public boolean selectcells(int x, int y) {
        Pair<Integer,Integer> key= new Pair<Integer,Integer>(x, y);
        return map.get(key);
    }

    @Override
    public boolean Over() {
        return this.isOver;
    }
    
}
