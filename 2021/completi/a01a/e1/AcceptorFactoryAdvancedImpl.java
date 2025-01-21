package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AcceptorFactoryAdvancedImpl implements AcceptorFactory {

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
       return new Acceptor<String,Integer>() {
        private int count=0;
        @Override
        public boolean accept(String e) {
            if (e.equals("")){
                count++;
            } 
            return true;
        }

        @Override
        public Optional<Integer> end() {
            return Optional.of(count);
        }
        
       }; 
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return new Acceptor<Integer,String>() {
            List<Integer> list= new ArrayList<>();
            int i=0;
            int alert=0;
            @Override
            public boolean accept(Integer e) {
                //accetta una sequenza incrementale elem1<elem2
                //se uno della sequenza è falso allora è empty
                if(list.isEmpty()){
                    list.add(e);
                    i++;
                    return true;
                }else{
                    list.add(e);
                    if(list.get(i-1)< list.get(i)){
                        i++;
                        return true;
                    }
                    alert=1;
                    return false;
                }

            }

            @Override
            public Optional<String> end() {
                if (alert ==1){
                    return Optional.empty();
                }else{
                    String finale ="";
                    for (Integer elem : list) {
                        finale+= new StringBuilder().append(elem+":").toString();
                    }
                //10:15:20:
                int lenght = finale.length();
                finale = finale.substring(0, lenght-1);
                return Optional.of(finale);
                }
            }
            
        };
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return new Acceptor<Integer,Integer>() {
            int count=0;
            int somma=0;

            @Override
            public boolean accept(Integer e) { //accetta triplette
                int size=3;
                if (count>=size){
                    count=0;
                    return false;
                }
                somma+=e;
                count++;
                return true;
            }

            @Override
            public Optional<Integer> end() { //empty se < o > di 3
               if (count==3){
                return Optional.of(somma);
               }
               return Optional.empty();
            }
            
        };
    }

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
        return new Acceptor<E,Pair<O1,O2>>() {
            int alert=0;
            @Override
            public boolean accept(E e) {
                if(a1.accept(e) && a2.accept(e)){
                    return true;
                }
                alert=1;
                return false;
            
            }

            @Override
            public Optional<Pair<O1, O2>> end() {
                if (alert==1){
                return Optional.empty();
                }
                Pair<O1,O2> key= new Pair<O1,O2>(a1.end().get(), a2.end().get());
                return Optional.of(key);
            }
        };
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generalised'");
    }

}
