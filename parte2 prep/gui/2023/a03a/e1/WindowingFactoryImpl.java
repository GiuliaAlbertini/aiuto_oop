package a03a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WindowingFactoryImpl implements WindowingFactory{

    @Override
    public <X> Windowing<X, X> trivial() {
        return new Windowing<X,X>() {

            @Override
            public Optional<X> process(X x) {
                return Optional.of(x);
            }
            
        };
    }

    @Override
    public <X> Windowing<X, Pair<X, X>> pairing() {
       return new Windowing<X,Pair<X,X>>() {
        int count=0;
        List<X> list= new ArrayList<>();

        @Override
        public Optional<Pair<X, X>> process(X x) {
            if (count==0){
                list.add(x);
                count++;
                return Optional.empty();
            }else{
                list.add(x);
                Pair<X,X> key = new Pair<X,X>(list.get(count-1), list.get(count));
                count++;
                return Optional.of(key);
            }
        }
        
       };
    }

    @Override
    public Windowing<Integer, Integer> sumLastFour() {
       return new Windowing<Integer,Integer>() {
        int count=0;
        List<Integer> list= new ArrayList<>();
        int somma=0;
        @Override
        public Optional<Integer> process(Integer x) {
           count++;
           list.add(x);
           if (count>=4){
            somma=0;
                for (Integer elem : list) {
                    somma+=elem;
                }
                list.remove(0);
            }else{
                return Optional.empty();
            }
           return Optional.of(somma);
        }
       };
    }

    @Override
    public <X> Windowing<X, List<X>> lastN(int n) {
        return new Windowing<X,List<X>>() {
            int count=0;
            List<X> list= new ArrayList<>();
            

            @Override
            public Optional<List<X>> process(X x) {
                count++;
                list.add(x);
                if (count<4){
                    return Optional.empty();
                }else if (count>4){
                    list.remove(0);
                }
                return Optional.of(list);
                
            }
        };
    }

    @Override
    public Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n) {
        return new Windowing<Integer,List<Integer>>() {
            int somma=0;
            List<Integer> list= new ArrayList<>();


            @Override
            public Optional<List<Integer>> process(Integer x) {
                somma+=x;
                list.add(x);
                if (somma<10){
                    return Optional.empty();
                }else if (somma>10){
                   list.remove(0);
                }
                return Optional.of(list);
            }
            
        };
    }

}
