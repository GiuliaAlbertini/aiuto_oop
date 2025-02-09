package a06.e2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LogicImpl implements Logic{
    int size;
    Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
    Random rand = new Random();
    List<Pair<Integer,Integer>> cellsToShow = new ArrayList<>();
    Set<Pair<Integer,Integer>> notEnableCells = new HashSet<>();
    int counter = 0;

    public LogicImpl(int size){
        this.size = size;

        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                var key = new Pair<>(i, j);
                map.put(key, 0);
            }
        }

    }

    @Override
    public void initialMap(){
        int i = 0;

        while (i < size * 2) {
            int counter = 0;
            int value = rand.nextInt(6);

            do {
                var pos = new Pair<>(rand.nextInt(size), rand.nextInt(size));
                if (map.get(pos) == 0) {
                    map.put(pos, value);
                    counter++;
                }
            } while (counter != 2);
            i++;
            counter = 0;
        }

    }

    @Override
 public void hit(Pair<Integer, Integer> pos){
    System.out.println("qui ci entro");

        this.counter++;    
        if (counter==3){
            System.out.println("resetto la lista");
            cellsToShow= new ArrayList<>();
            this.counter = 1;
        }    

        cellsToShow.add(pos);
        
        if (this.counter == 2) {
            System.out.println("faccio il confronto");
            System.out.println(cellsToShow);
            if (map.get(cellsToShow.get(0)).equals(map.get(cellsToShow.get(1)))) {
                notEnableCells.addAll(cellsToShow);
                System.out.println("faccio il confronto");
            }
            
        }
    }


    @Override
    public int getValue (Pair<Integer,Integer> pos){
        return map.get(pos);
    }

    @Override
    public List<Pair<Integer,Integer>> getList(){
        return this.cellsToShow;
    }

    @Override
    public Set<Pair<Integer,Integer>> getSet(){
        return this.notEnableCells;
    }
    
}
