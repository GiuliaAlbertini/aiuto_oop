package a01c.e1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EventHistoryFactoryImpl implements EventHistoryFactory {

    @Override
    public <E> EventHistory<E> fromMap(Map<Double, E> map) {
        List<Pair<Double, E>> list = new ArrayList<>();

        for (Map.Entry<Double,E> elem: map.entrySet()) {
            Pair<Double, E> key = new Pair<Double,E>(elem.getKey(), elem.getValue());
            list.add(key);
        }        
        

        list.sort(new Comparator<Pair<Double, E>>() {
            @Override
            public int compare(Pair<Double, E> p1, Pair<Double, E> p2) {
                return p1.get1().compareTo(p2.get1());  // Confronta i valori Double
            }
        });

    return new EventHistory<E>() { 
        int i=0;
            @Override
            public double getTimeOfEvent() {
                return list.get(i).get1();
            }

            @Override
            public E getEventContent() {
                return list.get(i).get2();
            }

            @Override
            public boolean moveToNextEvent() {
                //i+1 perchè quando faccio i confronti nei test mi calcola uno in più
                if (i +1 < list.size()){
                    i++;
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public <E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content) {
        return new EventHistory<E>() {

            @Override
            public double getTimeOfEvent() {
                return times.next();
            }

            @Override
            public E getEventContent() {
                return content.next();
            }

            @Override
            public boolean moveToNextEvent() {
                if (content.hasNext()){
                    return true;
                }
                return false;
            } 
        };
    }

    @Override
    public <E> EventHistory<E> fromListAndDelta(List<E> content, double initial, double delta) {
     //content ha un lista di eventi
     // initial è dove metti il double + delta incrementato ogni volta7
     // e(event), initial + (n * delta),  n = numero di evento
     return new EventHistory<E>() {
        int i=0;
        @Override
        public double getTimeOfEvent() {
           return initial+(i*delta);
        }

        @Override
        public E getEventContent() {
            return content.get(i);
        }

        @Override
        public boolean moveToNextEvent() {
            if (i+1<content.size()){
                i++;
                return true;
            }
            return false;
        }
        
     };

    }
    //@return the history (t1,evento)..(tn,en) where e1,e2,... are obtained from @content,
    //* t1, t2-t1, t3-t2 are obtained from Math.random(), and n=size
    @Override
    public <E> EventHistory<E> fromRandomTimesAndSupplier(Supplier<E> content, int size) {
        return new EventHistory<E>() {
            int i=0;
            @Override
            public double getTimeOfEvent() {
                return Math.random();
            }

            @Override
            public E getEventContent() {
                return content.get();
            }

            @Override
            public boolean moveToNextEvent() {
                if (i+1 < size){
                    i++;
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public EventHistory<String> fromFile(String file) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromFile'");
    }

}
