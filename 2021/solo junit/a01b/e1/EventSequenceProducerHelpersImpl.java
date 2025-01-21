package a01b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class EventSequenceProducerHelpersImpl implements EventSequenceProducerHelpers {

    @Override
    public <E> EventSequenceProducer<E> fromIterator(Iterator<Pair<Double, E>> iterator) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                return iterator.next();
            }

        };
    }

    @Override
    public <E> List<E> window(EventSequenceProducer<E> sequence, double fromTime, double toTime) {
        // creato la lista
        List<E> list = new ArrayList<>();
        Pair<Double, E> event;
        while ((event = sequence.getNext()) != null) {
            var time_event = event.get1();
            var name_event = event.get2();
            if (time_event > fromTime && time_event < toTime) {
                list.add(name_event);
            } else if (time_event > toTime) { // si ferma quando il tempo del mio evento è > di Totime
                break;
            }
        }
        return list;
    }

    @Override
    public <E> Iterable<E> asEventContentIterable(EventSequenceProducer<E> sequence) {
        Iterable<E> iterable = new Iterable<E>() {

            @Override
            public Iterator<E> iterator() {
                Iterator<E> iterator = new Iterator<E>() {
                    Pair<Double, E> event = null;
                    
                    @Override
                    public boolean hasNext() {
                        try{
                            event = sequence.getNext();
                            if (event != null) {
                                return true;
                            }
                            return false;
                        }catch(Exception NoSuchElementException){
                            return false;
                        }
                    }

                    @Override
                    public E next() {
                        return this.event.get2();
                    }
                };
                return iterator;
            }

        };
        return iterable;
    }

    @Override
    public <E> Optional<Pair<Double, E>> nextAt(EventSequenceProducer<E> sequence, double time) {
        List<Pair<Double, E>> list = new ArrayList<>();
        int count=0;
        Pair<Double, E> current_event;
        try{
            while ((current_event=sequence.getNext()) != null) {
                var time_event= current_event.get1();
                if (time_event> time && count==0){ //se time è 2.0 ,
                    list.add(current_event);
                    count=1;
                } else if (count==1){
                    break;
                }
            }
        }catch(Exception NoSuchElementException){
            if (count==1){
                return Optional.of(list.get(0));
            }else{
                return Optional.empty();
            }
        }
        if(list.isEmpty()){
             return Optional.empty();
        }else{
            return Optional.of(list.get(0));
        }
    }

    @Override
    public <E> EventSequenceProducer<E> filter(EventSequenceProducer<E> sequence, Predicate<E> predicate) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getNext'");
            }
            
        };
    }

}
