package a03b.e1;

import java.util.ArrayList;
import java.util.List;

public class InfiniteIteratorHelpersImpl implements InfiniteIteratorsHelpers {

    @Override
    public <X> InfiniteIterator<X> of(X x) {
        return new InfiniteIterator<X>() {

            @Override
            public X nextElement() {
                return x;
            }

        };
    }

    @Override
    public <X> InfiniteIterator<X> cyclic(List<X> l) {
        return new InfiniteIterator<X>() {
            int i = -1;

            @Override
            public X nextElement() {
                if (i == l.size() - 1) {
                    i = -1;
                }
                i++;
                return l.get(i);
            }

        };
    }

    @Override
    public InfiniteIterator<Integer> incrementing(int start, int increment) {
        return new InfiniteIterator<Integer>() {
            int k = -1;

            @Override
            public Integer nextElement() {
                int valore = start + increment * k;
                k++;
                return start + increment * k;
            }

        };
    }

    @Override
    public <X> InfiniteIterator<X> alternating(InfiniteIterator<X> i, InfiniteIterator<X> j) {
        return new InfiniteIterator<X>() {
            int count = 0;

            @Override
            public X nextElement() {
                if (count % 2 == 0) {
                    count++;
                    return i.nextElement();
                }
                count++;
                return j.nextElement();
            }

        };
    }

    @Override
    public <X> InfiniteIterator<List<X>> window(InfiniteIterator<X> i, int n) {
        return new InfiniteIterator<List<X>>() {
            int count = 0;

            List<X> list = new ArrayList<>();

            @Override
            public List<X> nextElement() {
                if (count != n) {
                    while (count != n) {
                        list.add(i.nextElement());
                        count++;
                    }
                    List<X> tmp = List.copyOf(list);
                    return tmp;
                } else {
                    list.remove(0);
                    list.add(i.nextElement());
                    List<X> tmp = List.copyOf(list);
                    return tmp;
                }
            }

        };
    }

}
