package a01b.e2;

import java.util.HashMap;
import java.util.Map;

public class LogicImpl implements Logic {

    Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
    boolean switchleft= false;
    boolean switchright= true;
    boolean over= false;
    int size;
    int i=1;

    public LogicImpl(int size){
        this.size= size;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                Pair<Integer,Integer> key = new Pair<Integer,Integer>(i, j);
                map.put(key, 0);
            }
        }
    }

    boolean present(int x, int y){
        Pair<Integer,Integer> key = new Pair<Integer,Integer>(x, y);
        if (map.get(key) == 0){ //se è vuoto
           return true;
        }
        return false;
    }

    @Override
    public void hit(int x, int y) {
        Pair<Integer,Integer> key = new Pair<Integer,Integer>(x, y);
        if (present(x, y)){
            if (i<=5){
                map.put(key, i);
                i++;
            }else {
                if (switchright){
                    switchleft= true;
                    spostamentoleft();
                }else{
                    spostamentodestra();
                }
            }
        }
    }


    void spostamentoleft(){
        Map<Pair<Integer, Integer>, Integer> tmp = new HashMap<>(map);
        for (Map.Entry<Pair<Integer, Integer>, Integer> mappa : tmp.entrySet()) {
            Pair<Integer,Integer> coppia= mappa.getKey();
            if (tmp.get(coppia) != 0){ //seil valore nella chiave è diverso da 0
                Pair<Integer,Integer> newcoppia=new Pair<Integer,Integer>(mappa.getKey().getX()-1, mappa.getKey().getY());
                    map.put(coppia, 0);
                    map.put(newcoppia, mappa.getValue());
                }

            
        }
    }

    void spostamentodestra(){
        /* 
        Map<Pair<Integer, Integer>, Integer> tmp = new HashMap<>(map);
        for (Map.Entry<Pair<Integer, Integer>, Integer> mappa : tmp.entrySet()) {
            Pair<Integer,Integer> coppia= mappa.getKey();
            if (tmp.get(coppia) != 0){ //seil valore nella chiave è diverso da 0
                Pair<Integer,Integer> newcoppia=new Pair<Integer,Integer>(mappa.getKey().getX()+1, mappa.getKey().getY());
                map.put(coppia, 0);
                map.put(newcoppia, mappa.getValue());
                
            
                if (newcoppia.getX() == 0){
                    System.out.println("sto andando a destra");
                }
            }
        } */
    }

    

    @Override
    public int getstate(int x, int y) {
       Pair<Integer,Integer> key = new Pair<Integer,Integer>(x, y);
       return map.get(key);
    }

    @Override
    public boolean isOver() {
        return this.over;
    }
}
