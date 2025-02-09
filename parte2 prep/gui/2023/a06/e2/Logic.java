package a06.e2;

import java.util.List;
import java.util.Set;

public interface Logic {
    void initialMap();
    int getValue (Pair<Integer,Integer> pos);
    List<Pair<Integer,Integer>> getList();
    void hit(Pair<Integer, Integer> pos);
    Set<Pair<Integer,Integer>> getSet();
}
