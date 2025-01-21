package a02a.e1;

import java.util.ArrayList;
import java.util.List;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        if (list.isEmpty()) {
            return null;
        }
        return new RecursiveIterator<X>() {

            int i = 0;

            @Override
            public X getElement() {
                return list.get(i);
            }

            @Override
            public RecursiveIterator<X> next() {
                i++;
                if (i == list.size()) {
                    return null;
                }
                return this;
            }

        };
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        List<X> result = new ArrayList<>();
        int counter = 0;
        while (counter < max && input != null) {
            result.add(input.getElement());
            counter++;
            input = input.next();
        }
        return result;
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {

        return new RecursiveIterator<Pair<X, Y>>() {

            @Override
            public Pair<X, Y> getElement() {
                return new Pair<X, Y>(first.getElement(), second.getElement());

            }

            @Override
            public RecursiveIterator<Pair<X, Y>> next() {

                RecursiveIterator<X> firstnext = first.next();
                RecursiveIterator<Y> secondnext = second.next();

                if (firstnext == null || secondnext == null) {
                    return null;
                }
                return zip(firstnext, secondnext);
            }
        };
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {

        class ZipTemp implements RecursiveIterator<Pair<X, Integer>> {
            RecursiveIterator<X> iteratore;
            int indice;

            ZipTemp(RecursiveIterator<X> iteratore, int indice) {
                this.iteratore = iteratore;
                this.indice = indice;
            }

            @Override
            public Pair<X, Integer> getElement() {
                return new Pair<X, Integer>(iterator.getElement(), indice);
            }

            @Override
            public RecursiveIterator<Pair<X, Integer>> next() {

                RecursiveIterator<X> iteratornext = iterator.next();
                if (iterator == null || iteratornext == null) {
                    return null;
                }
                return new ZipTemp(iteratornext, indice + 1);// devo creare una nuova istanza

            }
        }
        return new ZipTemp(iterator, 0);
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        return new RecursiveIterator<X>() {

            private boolean indice = true;

            @Override
            public X getElement() {
                if (indice) {
                    return first.getElement();
                }
                return second.getElement();
            }

            @Override
            public RecursiveIterator<X> next() {

                if (indice) {
                    // passa a first
                    RecursiveIterator<X> firstnext = first.next();
                    if (firstnext != null) {
                        indice = false;
                        //se first è finito
                    }else{
                        RecursiveIterator<X> secondnext = second.next();
                        if (secondnext != null) {
                            indice = true;
                        } else{
                            return null;
                        }
                    }
                } else {
                    RecursiveIterator<X> secondnext = second.next();
                    if (secondnext != null) {
                        indice = true;
                    }
                }
                return this;

            }
        };
    }

}
