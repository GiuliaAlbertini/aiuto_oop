package a01c.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SimpleIteratorFactoryImpl implements SimpleIteratorFactory {

    @Override
    public SimpleIterator<Integer> naturals() {
        return new SimpleIterator<Integer>() {
            int i = 0;

            @Override
            public Integer next() {
                return i++;
            }

        };
    }

    @Override
    public <X> SimpleIterator<X> circularFromList(List<X> list) {
        return new SimpleIterator<X>() {
            int i = 0;

            @Override
            public X next() {
                if (i == list.size()) {
                    i = 0;
                }
                X tmp = list.get(i);
                i++;
                return tmp;
            }

        };
    }

    @Override
    public <X> SimpleIterator<X> cut(int size, SimpleIterator<X> simpleIterator) {
        return new SimpleIterator<X>() {
            int i = 0;

            @Override
            public X next() {
                X tmp = simpleIterator.next();
                if (i < size) {
                    i++;
                    return tmp;
                }
                throw new NoSuchElementException();
            }

        };
    }

    @Override
    public <X> SimpleIterator<Pair<X, X>> window2(SimpleIterator<X> simpleIterator) {
        return new SimpleIterator<Pair<X,X>>() {
            X primo= simpleIterator.next();
            X secondo;
            Pair<X,X> key;
            @Override
            public Pair<X, X> next() {
                secondo= simpleIterator.next();
                if (primo != null && secondo != null){
                    key = new Pair<X,X>(primo, secondo);
                    
                }
                primo=secondo;
                return key;
            } 
        };
    }

    @Override
    public SimpleIterator<Integer> sumPairs(SimpleIterator<Integer> simpleIterator) {
        return new SimpleIterator<Integer>() {
            Integer primo= simpleIterator.next();
            Integer secondo;
            int somma=0;
            Pair<Integer,Integer> key;
            @Override
            public Integer next() {
                secondo= simpleIterator.next();
                if (primo != null && secondo != null){
                    somma= primo+secondo;
                    key = new Pair<>(primo, secondo);
                    
                }
                primo=secondo;
                return somma;
            }
            
        };
    }

    @Override
    public <X> SimpleIterator<List<X>> window(int windowSize, SimpleIterator<X> simpleIterator) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'window'");
    }

}
