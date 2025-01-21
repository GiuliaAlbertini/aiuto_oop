package a01a.e2;

import java.util.List;
import java.util.Set;

public interface Logic {
    void hit (int x, int y);
    void square();
    Set<Pair<Integer,Integer>> getSelectedCells();
    List<Pair<Integer,Integer>> getList();
    boolean isOver();

}
