package a01a.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class LogicImpl implements Logic{

    Map<Pair<Integer,Integer>, Integer> map= new HashMap<>();
    List<Pair<Integer,Integer>> list= new ArrayList<>();
    Set<Pair<Integer,Integer>> selectedCells= new HashSet<>();
    private int count=0;
    private int size;

    public LogicImpl(int size){
        this.size=size;

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                Pair<Integer,Integer> key= new Pair<>(i, j);
                map.put(key, 0);
            }
        }
    }

    boolean toggle(int x, int y){
        Pair<Integer,Integer> key= new Pair<>(x, y);
        //controllo se è già stato cliccato
        if(map.get(key) != 0){
            return false;
        }
        if((!list.isEmpty()) && list.get(0).getX()== x && list.get(0).getY()==y){
            //se riclicco sulla stessa cella count è già a 1 ma è false, non entra nel controllo hit
            return false; 
        }else{
            count++;
            return true;
        }
    }


    @Override
    public void hit(int x, int y) {
        
        Pair<Integer,Integer> key= new Pair<>(x, y);

        if(count==0 && toggle(x, y)){
            //metto il valore nella mappa
            map.put(key, 1);
            list.add(key); //mi salvo la prima coppia in una lista
        }else if(count==1){
            ///mi salvo la seconda coppia in una lista
            list.add(key);
            square();
            count=0;
        }

    }

    @Override
    public void square() {
        int xMin = Math.min(list.get(0).getX(), list.get(1).getX());
        int xMax = Math.max(list.get(0).getX(), list.get(1).getX());
        int yMin = Math.min(list.get(0).getY(), list.get(1).getY());
        int yMax = Math.max(list.get(0).getY(), list.get(1).getY());
        //seleziono le celle
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                Pair<Integer,Integer> key= new Pair<>(i,j);
                selectedCells.add(key);
            }
        }


        
        list.removeAll(list);
    }

    
    @Override
    public Set<Pair<Integer,Integer>> getSelectedCells(){
        return Set.copyOf(selectedCells);
    }

    public List<Pair<Integer,Integer>> getList(){
        return List.copyOf(list);
    }

    @Override
    public boolean isOver(){
        return selectedCells.size() == map.size();
    }
}
