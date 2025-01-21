package a02a.e2;

public interface Logic {
    void init();
    void hit(int x, int y);
    boolean SetCells(int x, int y);
    boolean getSet(int x, int y);
    boolean getIsOver();
}
